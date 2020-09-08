package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.modular.biz.model.Product;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface ProductMapper extends BaseMapper<Product> {
    /**
     * 删除商品属性
     *
     * @param ids
     * @return
     */
    int deleteBatchByIds(@Param("ids") String ids);

    void updateVideoCatNameByCatId(@Param("catId") Integer catId,@Param("catName") String catName);

    void updateProdCatNameByCatId(@Param("catId") Integer catId,@Param("catName") String catName);
}
