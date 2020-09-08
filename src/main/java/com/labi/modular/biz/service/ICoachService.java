package com.labi.modular.biz.service;

import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.model.Coach;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 教练表 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface ICoachService extends IService<Coach> {

	boolean addCoach(Coach coach,BizUser bizUser);
}
