package com.labi.modular.biz.dao;

import com.labi.modular.biz.model.Banner;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * Banner Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface BannerMapper extends BaseMapper<Banner> {

	 List<Map<String, Object>> getBnnerList(@Param("page") Page<Banner> page, @Param("name") String name,@Param("gsType") Integer gsType);
}
