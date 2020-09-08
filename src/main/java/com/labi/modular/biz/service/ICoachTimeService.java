package com.labi.modular.biz.service;

import com.labi.modular.biz.model.CoachTime;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 教练上课时间服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface ICoachTimeService extends IService<CoachTime> {
	 List<Map<String, Object>> getCoachTimeListByName(Page<CoachTime> page, String coachName,Integer type);
}
