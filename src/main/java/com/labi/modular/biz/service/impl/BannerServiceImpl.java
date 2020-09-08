package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.model.Banner;
import com.labi.modular.biz.dao.BannerMapper;
import com.labi.modular.biz.service.IBannerService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * <p>
 * Banner 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

	@Resource
	private BannerMapper bannerMapper;
	
	@Override
	public List<Map<String, Object>> getBnnerList(Page<Banner> page, String name,Integer gsType) {
		return bannerMapper.getBnnerList(page, name,gsType);
	}

}
