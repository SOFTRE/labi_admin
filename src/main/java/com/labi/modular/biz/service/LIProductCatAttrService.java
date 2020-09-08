package com.labi.modular.biz.service;

import com.labi.modular.biz.model.ProdCatAttr;


import java.util.List;

/**
 * @author liuyuwen
 */
public interface LIProductCatAttrService {

    List<ProdCatAttr> findProductCatAttrList(ProdCatAttr prodCatAttr);

    Boolean saveProdCatAttr(ProdCatAttr prodCatAttr);

    Boolean updateProdCatAttr(ProdCatAttr prodCatAttr);

    Boolean deleteProdCatAttr(ProdCatAttr prodCatAttr);
}
