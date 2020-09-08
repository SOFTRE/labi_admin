package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.dao.LProductCatAttrMapper;
import com.labi.modular.biz.model.ProdCatAttr;
import com.labi.modular.biz.service.LIProductCatAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LIProductCatAttrServiceImpl implements LIProductCatAttrService {

    @Autowired
    private LProductCatAttrMapper productCatAttrMapper;


    @Override
    public List<ProdCatAttr> findProductCatAttrList(ProdCatAttr prodCatAttr) {
        return productCatAttrMapper.findProductCatAttrList(prodCatAttr);
    }

    @Override
    public Boolean saveProdCatAttr(ProdCatAttr prodCatAttr) {
        return productCatAttrMapper.saveProductCatAttr(prodCatAttr);
    }

    @Override
    public Boolean updateProdCatAttr(ProdCatAttr prodCatAttr) {
        return productCatAttrMapper.updateProductCatAttr(prodCatAttr);
    }

    @Override
    public Boolean deleteProdCatAttr(ProdCatAttr prodCatAttr) {
        return productCatAttrMapper.deleteProductCatAttr(prodCatAttr);
    }

}
