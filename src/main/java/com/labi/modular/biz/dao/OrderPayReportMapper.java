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
 * 报表Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface OrderPayReportMapper extends BaseMapper<OrderPayReport> {

    List<Map<String, Object>> selectAll(@Param("page") Page<OrderPayReport> page, @Param("deptId") Integer deptId, @Param("businessType") String businessType, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    List<Map<String, Object>> selectByDay(@Param("page") Page<OrderPayReport> page, @Param("deptId") Integer deptId, @Param("businessType") String businessType, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    List<Map<String, Object>> selectByMonth(@Param("page") Page<OrderPayReport> page, @Param("deptId") Integer deptId, @Param("businessType") String businessType, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    List<Map<String, Object>> selectByCustom(@Param("page") Page<OrderPayReport> page, @Param("deptId") Integer deptId, @Param("businessType") String businessType, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

}
