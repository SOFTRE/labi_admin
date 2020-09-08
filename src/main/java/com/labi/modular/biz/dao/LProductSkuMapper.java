package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.modular.biz.model.*;

import java.util.List;


public interface LProductSkuMapper extends BaseMapper<Banner> {

    List<ProdSku> findProductListSku(Integer prodId);

    Boolean saveProductSku(ProdSku prodSku);

    Boolean updateProductSku(ProdSku prodSku);

    Boolean deleteProductSku(ProdSku prodSku);
}
