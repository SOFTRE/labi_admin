package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.model.CoachReservation;
import com.labi.modular.biz.dao.CoachReservationMapper;
import com.labi.modular.biz.service.ICoachReservationService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 教练预约记录及反馈 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class CoachReservationServiceImpl extends ServiceImpl<CoachReservationMapper, CoachReservation> implements ICoachReservationService {

	@Resource
	private CoachReservationMapper coachReservationMapper;
	
	 /***
	  * 该教练下的预约的学员
	  */
	@Override
	public List<Map<String, Object>> selectCoachStudentList(Page<CoachReservation> page, String coachName,
			Integer type) {
		return coachReservationMapper.selectCoachStudentList(page, coachName, type);
	}

}
