package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.ProductHotMapper;
import com.labi.modular.biz.model.ProductHot;
import com.labi.modular.biz.service.IProductHotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductHotServiceImpl extends ServiceImpl<ProductHotMapper, ProductHot> implements IProductHotService {

}
