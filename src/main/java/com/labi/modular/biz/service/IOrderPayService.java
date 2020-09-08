package com.labi.modular.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.OrderPay;

import java.util.Map;

/**
 * <p>
 * 支付单 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IOrderPayService extends IService<OrderPay> {

    Map<String, Object> loadTotalMoney(String type,Integer deptId);

    Map<String, Object> loadTotalNumber(String type, Integer deptId);

}
