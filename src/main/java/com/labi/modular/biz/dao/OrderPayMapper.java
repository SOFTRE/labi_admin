package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.modular.biz.model.OrderPay;
import com.labi.modular.biz.pojo.OrderPayReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单支付 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface OrderPayMapper extends BaseMapper<OrderPay> {

    List<Map<String, Object>> loadTotalMoney(@Param("type") String type, @Param("deptId") Integer deptId);

    List<Map<String, Object>> loadBeforeTotalMoney(@Param("type") String type, @Param("deptId") Integer deptId);

    List<Map<String, Object>> loadTotalCount(@Param("type") String type, @Param("deptId") Integer deptId);

    List<Map<String, Object>> loadBeforeTotalCount(@Param("type") String type, @Param("deptId") Integer deptId);

    List<Map<String, Object>> selectList(@Param("page") Page<OrderPay> page, @Param("deptId") Integer deptId, @Param("businessType") String businessType, @Param("beginDate") String beginDate, @Param("endDate") String endDate);
}
