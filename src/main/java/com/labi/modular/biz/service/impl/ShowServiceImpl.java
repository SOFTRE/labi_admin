package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.model.Show;
import com.labi.modular.biz.dao.ShowMapper;
import com.labi.modular.biz.service.IShowService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 拉比展示 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class ShowServiceImpl extends ServiceImpl<ShowMapper, Show> implements IShowService {

}
