package com.labi.modular.biz.service;

import com.labi.modular.biz.model.*;

import java.util.List;

/**
 * @author liuyuwen
 */
public interface LIProductService {
    List<Product> findProductList(Product product);

    void saveProduct(Product product, String attrValueArrStr);

    Boolean deleteByIds(String ids);

    Boolean updateProduct(Product product, String attrValueArrStr);

    List<ProdAttr> findProductListAttr(Integer prodId);
}
