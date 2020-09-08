package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.dao.LProductSkuMapper;
import com.labi.modular.biz.model.ProdSku;
import com.labi.modular.biz.service.LIProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LIProductSkuServiceImpl implements LIProductSkuService {

    @Autowired
    private LProductSkuMapper productSkuMapper;

    @Override
    public List<ProdSku> findProductListSku(Integer prodId) {
        return productSkuMapper.findProductListSku(prodId);
    }

    @Override
    public Boolean saveProductSku(ProdSku prodSku) {
        return productSkuMapper.saveProductSku(prodSku);
    }

    @Override
    public Boolean updateProductSku(ProdSku prodSku) {
        return productSkuMapper.updateProductSku(prodSku);
    }

    @Override
    public Boolean deleteProductSku(ProdSku prodSku) {
        return productSkuMapper.deleteProductSku(prodSku);
    }
}
