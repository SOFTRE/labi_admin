package com.labi.modular.biz.dao;

import com.labi.modular.biz.model.OrderLogistics;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 订单物流信息 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface OrderLogisticsMapper extends BaseMapper<OrderLogistics> {

    OrderLogistics selectByOrderId(Integer orderId);
}
