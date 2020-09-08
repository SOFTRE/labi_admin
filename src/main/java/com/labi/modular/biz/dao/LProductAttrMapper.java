package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.modular.biz.model.ProdAttr;
import com.labi.modular.biz.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface LProductAttrMapper extends BaseMapper<Product> {
    void saveAttr(ProdAttr prodAttr);

    List<ProdAttr> findAttrsByProdId(Integer prodId);
}
