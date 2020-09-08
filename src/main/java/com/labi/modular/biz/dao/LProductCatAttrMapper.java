package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.modular.biz.model.*;

import java.util.List;

public interface LProductCatAttrMapper extends BaseMapper<Banner> {

    List<ProdCatAttr> findProductCatAttrList(ProdCatAttr prodCatAttr);

    Boolean saveProductCatAttr(ProdCatAttr prodCatAttr);

    Boolean updateProductCatAttr(ProdCatAttr prodCatAttr);

    Boolean deleteProductCatAttr(ProdCatAttr prodCatAttr);
}
