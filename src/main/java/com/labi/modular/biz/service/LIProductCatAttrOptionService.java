package com.labi.modular.biz.service;


import com.labi.modular.biz.model.ProdCatAttrOption;

import java.util.List;

/**
 * @author liuyuwen
 */
public interface LIProductCatAttrOptionService {


    List<ProdCatAttrOption> findProductCatAttrOption(ProdCatAttrOption prodCatAttrOption);

    Boolean saveProdCatAttrOption(ProdCatAttrOption prodCatAttrOption);

    Boolean updateProdCatAttrOption(ProdCatAttrOption prodCatAttrOption);

    Boolean deleteProdCatAttrOption(ProdCatAttrOption prodCatAttrOption);
}
