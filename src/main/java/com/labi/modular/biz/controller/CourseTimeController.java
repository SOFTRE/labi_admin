package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.dao.CourseOrderMapper;
import com.labi.modular.biz.model.CourseOrder;
import com.labi.modular.biz.model.CourseTime;
import com.labi.modular.biz.service.ICourseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 课程时间管理控制器
 */
@Controller
@RequestMapping("/courseTime")
public class CourseTimeController extends BaseController {

    private String PREFIX = "/biz/course/";

    @Autowired
    private ICourseTimeService courseTimeService;//教练课程时间

    /**
     * 跳转到课程时间管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "courseTime.html";
    }

    /**
     * 跳转到添加课程时间管理
     */
    @RequestMapping("/courseTime_add")
    public String courseTimeAdd(Model model) {
        return PREFIX + "courseTime_add.html";
    }

    /**
     * 跳转到修改课程时间管理
     */
    @RequestMapping("/courseTime_update/{courseTimeId}")
    public String courseTimeUpdate(@PathVariable Integer courseTimeId, Model model) {
        CourseTime courseTime = courseTimeService.selectById(courseTimeId);
        model.addAttribute("item", courseTime);
        LogObjectHolder.me().set(courseTime);
        return PREFIX + "courseTime_edit.html";
    }

    /**
     * 获取课程时间管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer courseId) {
        Page<CourseTime> page = new PageFactory<CourseTime>().defaultPage();
        Wrapper<CourseTime> wrapper = new EntityWrapper<>();
        wrapper.orderBy("start_time", false);
        wrapper.eq("course_id", courseId);
        return super.packForBT(courseTimeService.selectPage(page, wrapper));
    }

    /**
     * 新增课程时间管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CourseTime courseTime) {
        courseTime.setCreatetime(new Date());
        courseTimeService.insert(courseTime);
        return SUCCESS_TIP;
    }

    /**
     * 删除课程时间管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer courseTimeId) {

        //根据时间id查询
        Wrapper<CourseOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("course_time_id", courseTimeId);
        wrapper.ne("progress", "waitPay");
        int flg = SpringContextHolder.getBean(CourseOrderMapper.class).selectCount(wrapper);
        if (flg > 0) {
            throw new BussinessException(BizExceptionEnum.LABI_COURSE_TIME_MSG);
        } else {
            courseTimeService.deleteById(courseTimeId);
        }
        return SUCCESS_TIP;
    }

    /**
     * 修改课程时间管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CourseTime courseTime) {
        courseTimeService.updateById(courseTime);
        return SUCCESS_TIP;
    }

}
