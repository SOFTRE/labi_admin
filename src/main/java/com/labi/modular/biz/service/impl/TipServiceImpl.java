package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.TipMapper;
import com.labi.modular.biz.model.Tip;
import com.labi.modular.biz.service.ITipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Tip 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TipServiceImpl extends ServiceImpl<TipMapper, Tip> implements ITipService {


}
