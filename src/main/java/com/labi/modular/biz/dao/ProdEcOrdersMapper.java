package com.labi.modular.biz.dao;

import com.labi.modular.biz.model.ProdEcOrders;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 退货申请 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface ProdEcOrdersMapper extends BaseMapper<ProdEcOrders> {
	List<Map<String, Object>> getEcOrderList(@Param("page") Page<ProdEcOrders> page, @Param("orderNo") String orderNo,
			@Param("progress") String progress,@Param("logisticsNo")String logisticsNo,
			@Param("userName") String userName, @Param("deprId") Integer deprId);
}
