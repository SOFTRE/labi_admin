package com.labi.modular.biz.service.impl;

import com.labi.common.constant.Const;
import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.model.Coach;
import com.labi.modular.biz.dao.BizUserMapper;
import com.labi.modular.biz.dao.CoachMapper;
import com.labi.modular.biz.service.ICoachService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 教练表 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements ICoachService {
	
	@Autowired
	private CoachMapper coachMapper;//教练
	@Autowired
	private BizUserMapper bizUserMapper;//用户

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean addCoach(Coach coach,BizUser bizUser) {
		BizUser user = new BizUser();
		//刷新用户表是否教练状态
		user.setId(bizUser.getId());
		user.setIfCoach(Const.TRUE);
		user.setOprtime(new Date());
		int userflg = bizUserMapper.updateById(user);//修改用户教练状态
		int coachflg = coachMapper.insert(coach);//添加教练
		if(userflg>0 && coachflg>0) {
			return true;
		}else {
			return false;
		}
	}

}
