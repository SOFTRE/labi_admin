package com.labi.modular.biz.service;


import com.labi.modular.biz.model.ProdSku;

import java.util.List;

/**
 * @author liuyuwen
 */
public interface LIProductSkuService {

    List<ProdSku> findProductListSku(Integer prodId);

    Boolean saveProductSku(ProdSku prodSku);

    Boolean updateProductSku(ProdSku prodSku);

    Boolean deleteProductSku(ProdSku prodSku);
}
