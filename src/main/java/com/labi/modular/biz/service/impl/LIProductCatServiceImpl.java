package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.dao.LProductCatMapper;
import com.labi.modular.biz.model.ProdCat;
import com.labi.modular.biz.service.LIProductCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LIProductCatServiceImpl implements LIProductCatService {

    @Autowired
    private LProductCatMapper productCatMapper;

    @Override
    public List<ProdCat> findProductCatList(ProdCat prodCat) {
        return productCatMapper.findProductCatList(prodCat);
    }

    @Override
    public Boolean saveProdCat(ProdCat prodCat) {
        return productCatMapper.saveProdCat(prodCat);
    }

    @Override
    public Boolean updateProdCat(ProdCat prodCat) {
        return productCatMapper.updateProdCat(prodCat);
    }

    @Override
    public Boolean deleteProdCat(ProdCat prodCat) {
        return productCatMapper.deleteProdById(prodCat);
    }

}
