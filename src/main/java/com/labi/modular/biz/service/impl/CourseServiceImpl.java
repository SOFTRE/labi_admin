package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.model.Course;
import com.labi.modular.biz.dao.CourseMapper;
import com.labi.modular.biz.service.ICourseService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
