package com.labi.modular.biz.service;


import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.BizAgentWithdraw;

/**
 * <p>
 * 分销商提现 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-22
 */
public interface IBizAgentWithdrawService extends IService<BizAgentWithdraw> {

    void failWithdraw(BizAgentWithdraw withdraw);
}
