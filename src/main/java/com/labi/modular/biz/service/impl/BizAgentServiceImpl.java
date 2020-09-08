package com.labi.modular.biz.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.common.constant.Const;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.dao.BizAgentMapper;
import com.labi.modular.biz.dao.BizUserMapper;
import com.labi.modular.biz.model.BizAgent;
import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.service.IBizAgentService;
import com.labi.modular.biz.util.SMSUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分销商 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-22
 */
@Service
public class BizAgentServiceImpl extends ServiceImpl<BizAgentMapper, BizAgent> implements IBizAgentService {

    @Resource
    BizAgentMapper bizAgentMapper;

    @Resource
    BizUserMapper bizUserMapper;

    @Override
    public List<Map<String, Object>> selectAllByPage(Page<BizAgent> page, String progress, String beginDate, String endDate, String condition) {
        return bizAgentMapper.selectAllByPage(page, progress, beginDate, endDate, condition);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean insertAgent(BizAgent agent) {

        agent.setProgress(Const.SUCCESS);
        agent.setTotalBalance(BigDecimal.ZERO);
        agent.setTotalSubUser(0);
        agent.setTotalOrder(0);
        agent.setIfFrozen(Const.FALSE);
        agent.setBalance(BigDecimal.ZERO);
        agent.setCreatetime(new Date());
        //查询是否存在申请记录
        Wrapper<BizAgent> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", agent.getUserId());
        List<BizAgent> list = bizAgentMapper.selectList(wrapper);
        //不存在插入
        if (list.isEmpty()) {
            bizAgentMapper.insert(agent);
        } else {
            if (!Const.FAIL.equalsIgnoreCase(list.get(0).getProgress())) {
                throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
            }
            //存在记录
            agent.setId(list.get(0).getId());
            agent.setOprtime(new Date());
            bizAgentMapper.updateById(agent);
        }

        //更新会员表
        BizUser user = new BizUser();
        user.setId(agent.getUserId());
        user.setAgentStatus(Const.TRUE);
        user.setBankName(agent.getBankName());
        user.setBankNo(agent.getBankNo());
        user.setBankSubname(agent.getBankSubname());
        bizUserMapper.updateById(user);
        return true;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAgent(BizAgent agent) {

        agent.setOprtime(new Date());
        bizAgentMapper.updateById(agent);
        BizAgent agentOld = bizAgentMapper.selectById(agent.getId());
        //更新会员表
        BizUser user = new BizUser();
        user.setId(agentOld.getUserId());
        user.setBankName(agent.getBankName());
        user.setBankNo(agent.getBankNo());
        user.setBankSubname(agent.getBankSubname());
        user.setOprtime(new Date());
        bizUserMapper.updateById(user);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAgentApplyFail(Integer agentId, String remarks) {
        BizAgent old = bizAgentMapper.selectById(agentId);
        if (!Const.WAIT.equals(old.getProgress())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        BizAgent bizAgent = new BizAgent();
        bizAgent.setId(agentId);
        bizAgent.setProgress(Const.FAIL);
        bizAgent.setIfFrozen(Const.TRUE);
        bizAgent.setRemarks(remarks);
        bizAgent.setOprtime(new Date());
        bizAgentMapper.updateById(bizAgent);

        BizUser user = new BizUser();
        user.setId(old.getUserId());
        user.setAgentStatus(Const.FALSE);
        user.setOprtime(new Date());
        bizUserMapper.updateById(user);
        user = SpringContextHolder.getBean(BizUserMapper.class).selectById(old.getUserId());
        SMSUtil.sendAgentApplyFailMsg(user.getPhoneNum(), old.getAgentName());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAgentApplySuccess(Integer agentId) {
        BizAgent old = bizAgentMapper.selectById(agentId);
        if (!Const.WAIT.equals(old.getProgress()))
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);

        BizAgent bizAgent = new BizAgent();
        bizAgent.setId(agentId);
        bizAgent.setProgress(Const.SUCCESS);
        bizAgent.setIfFrozen(Const.FALSE);
        bizAgent.setOprtime(new Date());
        bizAgentMapper.updateById(bizAgent);

        BizUser oldUser = SpringContextHolder.getBean(BizUserMapper.class).selectById(old.getUserId());

        BizUser user = new BizUser();
        user.setId(old.getUserId());
        user.setAgentStatus(Const.TRUE);
        user.setOprtime(new Date());
        bizUserMapper.updateById(user);
        SMSUtil.sendAgentApplySuccessMsg(oldUser.getPhoneNum(), old.getAgentName());
        return true;
    }
}
