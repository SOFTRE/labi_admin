package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.CourseTimeMapper;
import com.labi.modular.biz.model.CourseTime;
import com.labi.modular.biz.service.ICourseTimeService;
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
public class CourseTimeServiceImpl extends ServiceImpl<CourseTimeMapper, CourseTime> implements ICourseTimeService {

}
