package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.ProdEcOrdersMapper;
import com.labi.modular.biz.dao.ProdOrdersDtlMapper;
import com.labi.modular.biz.model.ProdEcOrders;
import com.labi.modular.biz.model.ProdOrdersDtl;
import com.labi.modular.biz.service.IProdEcOrdersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 退货服务 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class ProdEcOrdersServiceImpl extends ServiceImpl<ProdEcOrdersMapper, ProdEcOrders> implements IProdEcOrdersService {

    @Resource
    private ProdEcOrdersMapper prodEcOrdersMapper;

    @Resource
    private ProdOrdersDtlMapper prodOrdersDtlMapper;

    @Override
    public List<Map<String, Object>> getEcOrderList(Page<ProdEcOrders> page, String orderNo, String progress,String logisticsNo, String userName, Integer deprId) {
        return prodEcOrdersMapper.getEcOrderList(page, orderNo, progress,logisticsNo, userName, deprId);
    }

    /**
     * 完成退款
     *
     * @param id
     * @param realPrice
     * @throws Exception
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void updateFinish(Integer id, BigDecimal realPrice){
        //根据id查询
        ProdEcOrders ecOrders = prodEcOrdersMapper.selectById(id);
        ecOrders.setRealPrice(realPrice);
        ecOrders.setProgress("finish");
        ecOrders.setOprtime(new Date());
        prodEcOrdersMapper.updateById(ecOrders);
        ProdOrdersDtl dtl = new ProdOrdersDtl();
        dtl.setId(ecOrders.getOrderDtlId());
        dtl.setIfReturn("O");
        dtl.setOprtime(new Date());
        prodOrdersDtlMapper.updateById(dtl);
    }

}
