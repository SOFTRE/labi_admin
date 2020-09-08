package com.labi.modular.biz.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.modular.biz.model.ProdAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品属性 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface ProdAttrMapper extends BaseMapper<ProdAttr> {

    /**
     * 删除商品属性
     * @param ids
     * @return
     */
    int updateStatusByIds(@Param("status") String status, @Param("prodId") Integer prodId, @Param("ids") List<Integer> ids);
}
