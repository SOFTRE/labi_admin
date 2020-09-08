package com.labi.modular.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.ProdEcOrders;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 退货申请 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IProdEcOrdersService extends IService<ProdEcOrders> {
    List<Map<String, Object>> getEcOrderList(@Param("page") Page<ProdEcOrders> page, @Param("orderNo") String orderNo,
                                             @Param("progress") String progress,@Param("logisticsNo")String logisticsNo,
                                             @Param("userName") String userName, @Param("deprId") Integer deprId);

    void updateFinish(Integer id, BigDecimal realPrice);
}
