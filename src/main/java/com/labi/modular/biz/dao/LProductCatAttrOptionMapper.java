package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.modular.biz.model.*;

import java.util.List;

public interface LProductCatAttrOptionMapper extends BaseMapper<Banner> {

    List<ProdCatAttrOption> findProductCatAttrOptionList(ProdCatAttrOption prodCatAttrOption);

    Boolean saveProdCatAttrOption(ProdCatAttrOption prodCatAttrOption);

    Boolean updateProdCatAttrOption(ProdCatAttrOption prodCatAttrOption);

    Boolean deleteProdCatAttrOption(ProdCatAttrOption prodCatAttrOption);
}
