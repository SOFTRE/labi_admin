package com.labi.modular.biz.dao;

import com.labi.modular.biz.model.CoachTime;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 教练上课时间 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface CoachTimeMapper extends BaseMapper<CoachTime> {
	//根据参数查询
	public List<Map<String, Object>> getCoachTimeListByName( @Param("page") Page<CoachTime> page,  @Param("coachName") String coachName,@Param("type") Integer type);
}
