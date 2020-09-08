package com.labi.modular.biz.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.common.constant.Const;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.dao.BizAgentBalanceRecordMapper;
import com.labi.modular.biz.dao.BizAgentMapper;
import com.labi.modular.biz.dao.BizAgentWithdrawMapper;
import com.labi.modular.biz.dao.BizUserMapper;
import com.labi.modular.biz.model.BizAgent;
import com.labi.modular.biz.model.BizAgentBalanceRecord;
import com.labi.modular.biz.model.BizAgentWithdraw;
import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.service.IBizAgentWithdrawService;
import com.labi.modular.biz.util.SMSUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 分销商提现 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-22
 */
@Service
public class BizAgentWithdrawServiceImpl extends ServiceImpl<BizAgentWithdrawMapper, BizAgentWithdraw> implements IBizAgentWithdrawService {
    @Resource
    BizAgentMapper bizAgentMapper;

    @Resource
    BizAgentBalanceRecordMapper bizAgentBalanceRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    public void failWithdraw(BizAgentWithdraw withdraw) {
        BizAgentWithdraw old = selectById(withdraw.getId());
        if (!Const.WAIT.equals(old.getProgress()))
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        withdraw.setProgress(Const.FAIL);
        withdraw.setOprtime(new Date());
        updateById(withdraw);
        //还钱
        BizAgent agent = bizAgentMapper.selectById(old.getAgentId());
        BizAgent newAgent = new BizAgent();
        newAgent.setId(agent.getId());
        newAgent.setBalance(agent.getBalance().add(new BigDecimal(old.getTotalAmount())));
        newAgent.setOprtime(new Date());
        bizAgentMapper.updateById(newAgent);

        //余额记录
        BizAgentBalanceRecord record = new BizAgentBalanceRecord();
        record.setAgentId(old.getAgentId());
        record.setBalance(new BigDecimal(old.getTotalAmount()));
        record.setLeftBalance(agent.getBalance().add(new BigDecimal(old.getTotalAmount())));
        record.setCreatetime(new Date());
        record.setType(Const.EXPEND);
        record.setRemarks("申请提现拒绝返还");
        bizAgentBalanceRecordMapper.insert(record);

        SMSUtil.sendWithdrawFailMsg(old.getPhoneNum(), agent.getAgentName(), old.getTotalAmount());
    }
}
