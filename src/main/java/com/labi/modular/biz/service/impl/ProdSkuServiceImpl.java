package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.common.constant.Const;
import com.labi.modular.biz.dao.ProdSkuMapper;
import com.labi.modular.biz.model.ProdSku;
import com.labi.modular.biz.service.IProdSkuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 商品销量单位 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class ProdSkuServiceImpl extends ServiceImpl<ProdSkuMapper, ProdSku> implements IProdSkuService {

    @Transactional(rollbackFor = Exception.class)
    public Integer insertSku(ProdSku prodSku) {
        if (prodSku.getId() != null) {
            prodSku.setOprtime(new Date());
            updateById(prodSku);
            return prodSku.getId();
        } else {
            Wrapper<ProdSku> wrapper = new EntityWrapper<>();
            wrapper.orderBy("createtime",false);
            wrapper.eq("prod_id", prodSku.getProdId());
            wrapper.eq("attr_option_ids", prodSku.getAttrOptionIds());
            ProdSku old = selectOne(wrapper);
            if (old != null) {
                old.setImg(prodSku.getImg());
                old.setSalePrice(prodSku.getSalePrice());
                old.setOriginPrice(prodSku.getOriginPrice());
                old.setProvidePrice(prodSku.getProvidePrice());
                old.setLeftNum(prodSku.getLeftNum());
                old.setAttrOptionNames(prodSku.getAttrOptionNames());
                old.setOprtime(new Date());
                old.setStatus(Const.ACTIVE);
                updateById(old);
                return old.getId();
            } else {
                prodSku.setStatus(Const.ACTIVE);
                prodSku.setCreatetime(new Date());
                insert(prodSku);
                return prodSku.getId();
            }
        }
    }
}
