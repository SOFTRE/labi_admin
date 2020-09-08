package com.labi.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.Banner;

/**
 * <p>
 * Banner 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IBannerService extends IService<Banner> {
	 List<Map<String, Object>> getBnnerList(@Param("page") Page<Banner> page,String name,Integer gsType);
}
