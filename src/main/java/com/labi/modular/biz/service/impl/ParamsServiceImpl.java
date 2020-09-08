package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.service.IParamsService;
import com.labi.modular.system.dao.ParamsMapper;
import com.labi.modular.system.model.Params;
import org.springframework.stereotype.Service;

/**
 * <p>
 * params 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class ParamsServiceImpl extends ServiceImpl<ParamsMapper, Params> implements IParamsService {

}
