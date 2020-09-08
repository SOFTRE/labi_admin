package com.labi.modular.biz.service;

import com.labi.modular.biz.model.CoachReservation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 教练预约记录及反馈 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface ICoachReservationService extends IService<CoachReservation> {

	//查询教练下所预约的学员
	List<Map<String, Object>> selectCoachStudentList(@Param("page") Page<CoachReservation> page, @Param("coachName") String coachName,@Param("type") Integer type);

}
