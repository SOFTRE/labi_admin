package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.ProdVideoMapper;
import com.labi.modular.biz.model.ProdVideo;
import com.labi.modular.biz.service.IProdVideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * ProdVideo 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class ProdVideoServiceImpl extends ServiceImpl<ProdVideoMapper, ProdVideo> implements IProdVideoService {

	@Resource
	private ProdVideoMapper prodVideoMapper;
	
}
