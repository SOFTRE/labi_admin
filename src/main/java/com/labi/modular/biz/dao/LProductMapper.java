package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.modular.biz.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LProductMapper extends BaseMapper<Banner> {

    List<Product> findProduct(Product product);

    void saveProduction(Product product);

    Integer deleteBatch(List<Integer> ids);


    Boolean updateProduct(Product product);

    ProdAttr selectOneAttr(ProdAttr prodAttr);

    void saveProdAttr(ProdAttr prodAttr1);

    void updateProductAttrById(ProdAttr prodAttr1);

    Boolean updateStatusByIds(@Param("productId") Integer productId, @Param("status") String status, @Param("attrIdList") List<Integer> attrIdList);
}
