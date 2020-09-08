package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.OrderLogisticsMapper;
import com.labi.modular.biz.dao.ProdOrdersMapper;
import com.labi.modular.biz.model.OrderLogistics;
import com.labi.modular.biz.model.ProdOrders;
import com.labi.modular.biz.service.IOrderLogisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 订单物流信息 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements IOrderLogisticsService {

    @Resource
    private OrderLogisticsMapper orderLogisticsMapper;

    @Resource
    private ProdOrdersMapper prodOrdersMapper;

    @Override
    public OrderLogistics selectByOrderId(Integer orderId) {
        return orderLogisticsMapper.selectByOrderId(orderId);
    }

    @Override
    public void updateDelivery(OrderLogistics orderLogistics) {
        orderLogistics.setOrderType("S");
        orderLogistics.setCreatetime(new Date());
        orderLogisticsMapper.insert(orderLogistics);
        ProdOrders orders = new ProdOrders();
        orders.setId(orderLogistics.getOrderId());
        orders.setProgress("waitTake");
        orders.setDeliveryTime(new Date());
        orders.setOprtime(new Date());
        prodOrdersMapper.updateById(orders);
    }
}
