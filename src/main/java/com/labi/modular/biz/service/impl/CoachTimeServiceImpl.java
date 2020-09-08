package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.model.CoachTime;
import com.labi.modular.biz.dao.CoachTimeMapper;
import com.labi.modular.biz.service.ICoachTimeService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 教练上课时间 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class CoachTimeServiceImpl extends ServiceImpl<CoachTimeMapper, CoachTime> implements ICoachTimeService {
    
	@Resource
	private CoachTimeMapper coachTimeMapper;
	/**
	 * 根据参数查询
	 */
	@Override
	public List<Map<String, Object>> getCoachTimeListByName(Page<CoachTime> page, String coachName,Integer type) {
		return coachTimeMapper.getCoachTimeListByName(page, coachName,type);
	}

}
