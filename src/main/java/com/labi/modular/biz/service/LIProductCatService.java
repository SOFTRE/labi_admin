package com.labi.modular.biz.service;

import com.labi.modular.biz.model.ProdCat;

import java.util.List;

/**
 * @author liuyuwen
 */
public interface LIProductCatService {

    List<ProdCat> findProductCatList(ProdCat prodCat);

    Boolean saveProdCat(ProdCat prodCat);

    Boolean updateProdCat(ProdCat prodCat);

    Boolean deleteProdCat(ProdCat prodCat);
}
