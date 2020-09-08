package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.OrderPayMapper;
import com.labi.modular.biz.model.OrderPay;
import com.labi.modular.biz.service.IOrderPayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * <p>
 * 订单支付表 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OrderPay> implements IOrderPayService {

    @Resource
    private OrderPayMapper orderPayMapper;


    @Override
    public Map<String, Object> loadTotalMoney(String type, Integer deptId) {
        Map<String, Object> map = orderPayMapper.loadTotalMoney(type, deptId).get(0);
        if(StringUtils.isNotBlank(type)){
            Object totalMoney = map.get("totalMoney");
            Object totalMoney1 = orderPayMapper.loadBeforeTotalMoney(type, deptId).get(0).get("totalMoney");
            BigDecimal proportion = new BigDecimal(totalMoney.toString()).divide( new BigDecimal(totalMoney1.toString()),10,RoundingMode.CEILING).multiply(new BigDecimal(100));
            map.put("proportion",proportion);
        } else {
            map.put("proportion",100);
        }
        return map;
    }

    @Override
    public Map<String, Object> loadTotalNumber(String type, Integer deptId) {
        Map<String, Object> map = orderPayMapper.loadTotalCount(type, deptId).get(0);
        if(StringUtils.isNotBlank(type)){
            Object totalMoney = map.get("totalNumber");
            Object totalMoney1 = orderPayMapper.loadBeforeTotalCount(type, deptId).get(0).get("totalNumber");
            BigDecimal proportion = new BigDecimal(totalMoney.toString()).divide( new BigDecimal(totalMoney1.toString()),10,RoundingMode.CEILING).multiply(new BigDecimal(100));
            map.put("proportion",proportion);
        } else {
            map.put("proportion",100);
        }
        return map;
    }
}
