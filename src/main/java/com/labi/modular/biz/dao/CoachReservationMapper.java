package com.labi.modular.biz.dao;

import com.labi.modular.biz.model.CoachReservation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 教练预约记录及反馈 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface CoachReservationMapper extends BaseMapper<CoachReservation> {
	
	 //查询教练下所预约的学员
	 List<Map<String, Object>> selectCoachStudentList(@Param("page") Page<CoachReservation> page, @Param("coachName") String coachName,@Param("type") Integer type);

}
