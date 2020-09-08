package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.modular.biz.model.ProdOrders;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户商城订单 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface ProdOrdersMapper extends BaseMapper<ProdOrders> {

    List<Map<String, Object>> selectVideoOrdersByPage(@Param("page") Page<ProdOrders> page, @Param("progress") String progress,
                                                      @Param("condition") String condition, @Param("userId") Integer userId);
}
