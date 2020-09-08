package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.modular.biz.model.*;

import java.util.List;

public interface LProductCatMapper extends BaseMapper<Banner> {

    List<ProdCat> findProductCatList(ProdCat prodCat);

    Boolean saveProdCat(ProdCat prodCat);

    Boolean updateProdCat(ProdCat prodCat);

    Boolean deleteProdById(ProdCat prodCat);
}
