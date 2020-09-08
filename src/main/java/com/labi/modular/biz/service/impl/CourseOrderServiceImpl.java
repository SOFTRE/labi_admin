package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.common.constant.Const;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.dao.BizAgentBalanceRecordMapper;
import com.labi.modular.biz.dao.BizAgentMapper;
import com.labi.modular.biz.dao.BizUserMapper;
import com.labi.modular.biz.dao.CourseOrderMapper;
import com.labi.modular.biz.model.BizAgent;
import com.labi.modular.biz.model.BizAgentBalanceRecord;
import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.model.CourseOrder;
import com.labi.modular.biz.service.ICourseOrderService;
import com.labi.modular.biz.service.IParamsService;
import com.labi.modular.system.dao.ParamsMapper;
import com.labi.modular.system.model.Params;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 课程报名 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class CourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder> implements ICourseOrderService {

    @Resource
    private BizUserMapper bizUserMapper;

    @Resource
    private BizAgentMapper bizAgentMapper;

    @Resource
    private BizAgentBalanceRecordMapper bizAgentBalanceRecordMapper;



    /**
     * 根据报名订单ID更新代理商累计推广订单数量,并且分成
     *
     * @param courseOrderId
     * @param agentId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAgentOrderNum(Integer courseOrderId, Integer agentId) throws Exception {
        CourseOrder courseOrder = selectById(courseOrderId);
        BizAgent agent = bizAgentMapper.selectById(agentId);
        //判断是否在合同期内,如果不在合同期内，则不递加
        if (agent == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Long now = new Date().getTime();
        if (agent.getBeginDate().getTime() > now || agent.getEndDate().getTime() < now) {
            throw new BussinessException(BizExceptionEnum.AGENT_CONTRACT);
        }
        if (Const.TRUE.equalsIgnoreCase(agent.getIfFrozen())) {
            throw new BussinessException(BizExceptionEnum.AGENT_FROZEN);
        }
        BizAgent agent1 = new BizAgent();
        agent1.setId(agentId);
        agent1.setTotalOrder(agent.getTotalOrder() + 1);
        //代理商分成
        Wrapper<Params> wrapper = new EntityWrapper<>();
        wrapper.eq("param_key", "subordinate-member-consumption");
        Params params = SpringContextHolder.getBean(IParamsService.class).selectOne(wrapper);
        BigDecimal proportion = new BigDecimal(params.getParamValue());
        int money = proportion.multiply(courseOrder.getTotalPrice()).multiply(new BigDecimal(1000)).intValue();

        if (money > 0) {
            BigDecimal divideMoney = new BigDecimal(money).divide(new BigDecimal(1000));
            //更新代理商余额
            agent1.setBalance(agent.getBalance().add(divideMoney));
            agent1.setTotalBalance(agent.getTotalBalance().add(divideMoney));
            agent1.setOprtime(new Date());
            bizAgentMapper.updateById(agent1);
            //余额记录
            BizAgentBalanceRecord record = new BizAgentBalanceRecord();
            record.setAgentId(agent.getId());
            record.setBalance(divideMoney);
            record.setLeftBalance(agent1.getBalance());
            record.setCreatetime(new Date());
            record.setType(Const.INCOME);
            record.setRemarks("下级会员报名");
            bizAgentBalanceRecordMapper.insert(record);
        } else {
            agent1.setOprtime(new Date());
            bizAgentMapper.updateById(agent1);
        }

        CourseOrder courseOrder1 = new CourseOrder();
        courseOrder1.setId(courseOrder.getId());
        courseOrder1.setIfDivided(Const.TRUE);
        courseOrder1.setOprtime(new Date());
        updateById(courseOrder1);
        BizUser user = bizUserMapper.selectById(courseOrder.getUserId());
        //查询该用户是否已经绑定代理商
        if (user.getAgentId() == null) {
            BizUser agentUser = bizUserMapper.selectById(agent.getUserId());
            BizUser user1 = new BizUser();
            user1.setId(user.getId());
            user1.setAgentId(agentId);
            user1.setRefUserId(agentUser.getId());
            user1.setRootUserId(agentUser.getRootUserId());
            user1.setOprtime(new Date());
            bizUserMapper.updateById(user1);
        }
    }
}
