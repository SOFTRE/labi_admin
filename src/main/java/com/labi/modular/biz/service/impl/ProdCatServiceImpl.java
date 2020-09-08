package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.dao.ProductMapper;
import com.labi.modular.biz.model.ProdCat;
import com.labi.modular.biz.dao.ProdCatMapper;
import com.labi.modular.biz.model.VideoCat;
import com.labi.modular.biz.service.IProdCatService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 商品类目表 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class ProdCatServiceImpl extends ServiceImpl<ProdCatMapper, ProdCat> implements IProdCatService {
    @Resource
    ProductMapper productMapper;

    /**
     * 修改分类信息
     *
     * @param prodCat
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByCatId(ProdCat prodCat) {
        prodCat.setOprtime(new Date());
        updateById(prodCat);
        productMapper.updateProdCatNameByCatId(prodCat.getId(), prodCat.getCatName());
    }
}
