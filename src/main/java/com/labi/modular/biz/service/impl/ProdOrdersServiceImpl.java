package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.ProdOrdersMapper;
import com.labi.modular.biz.model.ProdOrders;
import com.labi.modular.biz.service.IProdOrdersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户商城订单 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class ProdOrdersServiceImpl extends ServiceImpl<ProdOrdersMapper, ProdOrders> implements IProdOrdersService {
    @Resource
    ProdOrdersMapper prodOrdersMapper;

    @Override
    public List<Map<String, Object>> selectVideoOrdersByPage(Page<ProdOrders> page, String progress, String condition,Integer userId) {
        return prodOrdersMapper.selectVideoOrdersByPage(page, progress, condition, userId);
    }
}
