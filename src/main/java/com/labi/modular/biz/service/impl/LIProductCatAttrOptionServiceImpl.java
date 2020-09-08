package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.dao.LProductCatAttrOptionMapper;
import com.labi.modular.biz.model.ProdCatAttrOption;
import com.labi.modular.biz.service.LIProductCatAttrOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author liuyuwen
 */
@Service
public class LIProductCatAttrOptionServiceImpl implements LIProductCatAttrOptionService {

    @Autowired
    private LProductCatAttrOptionMapper productCatAttrOptionMapper;

    @Override
    public List<ProdCatAttrOption> findProductCatAttrOption(ProdCatAttrOption prodCatAttrOption) {
        return productCatAttrOptionMapper.findProductCatAttrOptionList(prodCatAttrOption);
    }

    @Override
    public Boolean saveProdCatAttrOption(ProdCatAttrOption prodCatAttrOption) {
        return productCatAttrOptionMapper.saveProdCatAttrOption(prodCatAttrOption);
    }

    @Override
    public Boolean updateProdCatAttrOption(ProdCatAttrOption prodCatAttrOption) {
        return productCatAttrOptionMapper.updateProdCatAttrOption(prodCatAttrOption);
    }

    @Override
    public Boolean deleteProdCatAttrOption(ProdCatAttrOption prodCatAttrOption) {
        return productCatAttrOptionMapper.deleteProdCatAttrOption(prodCatAttrOption);
    }

}
