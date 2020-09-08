package com.labi.modular.biz.service;

import com.labi.modular.biz.model.CourseOrder;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 课程报名 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface ICourseOrderService extends IService<CourseOrder> {

    void updateAgentOrderNum(Integer courseOrderId , Integer agentId) throws Exception;

}
