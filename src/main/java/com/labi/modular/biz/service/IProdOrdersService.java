package com.labi.modular.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.ProdOrders;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户商城订单 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IProdOrdersService extends IService<ProdOrders> {

    List<Map<String, Object>> selectVideoOrdersByPage(Page<ProdOrders> page, String progress, String condition,Integer userId);

}
