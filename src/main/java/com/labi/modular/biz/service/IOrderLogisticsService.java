package com.labi.modular.biz.service;

import com.labi.modular.biz.model.OrderLogistics;
import com.baomidou.mybatisplus.service.IService;

import java.io.Serializable;

/**
 * <p>
 * 订单物流信息 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IOrderLogisticsService extends IService<OrderLogistics> {

    OrderLogistics selectByOrderId(Integer orderId);

    void updateDelivery(OrderLogistics orderLogistics);
}
