package com.labi.modular.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.BizUser;

import java.util.Map;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IBizUserService extends IService<BizUser> {

    Map<String, Object> loadUserTotalCount(String type);

}
