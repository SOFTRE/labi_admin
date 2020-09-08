package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.core.util.DateUtil;
import com.labi.modular.biz.model.CourseCat;
import com.labi.modular.biz.model.CourseOrder;
import com.labi.modular.biz.model.CourseTime;
import com.labi.modular.biz.service.ICourseCatService;
import com.labi.modular.biz.service.ICourseOrderService;
import com.labi.modular.biz.service.ICourseTimeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 课程报名管理控制器
 */
@Controller
@RequestMapping("/courseOrder")
public class CourseOrderController extends BaseController {

    private String PREFIX = "/biz/courseOrder/";

    @Autowired
    private ICourseOrderService courseOrderService;//课程报名

    @Autowired
    private ICourseCatService courseCatService;//课程分类

    @Autowired
    private ICourseTimeService courseTimeService;//上课时间


    /**
     * 跳转到课程报名管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取课程分类列表
        Wrapper<CourseCat> catwrapper = new EntityWrapper<>();
        catwrapper.orderBy("createtime", false);
        catwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            catwrapper.eq("type", depid);
        }

        List<CourseCat> catlist = courseCatService.selectList(catwrapper);
        model.addAttribute("catlist", catlist);//课程分类list
        return PREFIX + "courseOrder.html";
    }

    /**
     * 课程签到
     *
     * @param courseOrderId
     * @return
     */
    @RequestMapping(value = "/signer")
    @ResponseBody
    public Object Signer(@RequestParam String courseOrderId) {
        String idstr[] = courseOrderId.split(",");
        for (String string : idstr) {
            CourseOrder courseOrder = courseOrderService.selectById(string);
            courseOrder.setProgress("finish");
            courseOrderService.updateById(courseOrder);
        }
        return SUCCESS_TIP;
    }

    /**
     * 跳转到添加课程报名管理
     */
    @RequestMapping("/courseOrder_add")
    public String courseOrderAdd() {
        return PREFIX + "courseOrder_add.html";
    }

    /**
     * 跳转到修改课程报名管理
     */
    @RequestMapping("/courseOrder_update/{courseOrderId}")
    public String courseOrderUpdate(@PathVariable Integer courseOrderId, Model model) {

        //根据id查询
        CourseOrder courseOrder = courseOrderService.selectById(courseOrderId);

        //根据课程id查询该课程上课时间
        Wrapper<CourseTime> wrapper = new EntityWrapper<>();
        wrapper.eq("course_id", courseOrder.getCourseId());
        List<CourseTime> listtime = courseTimeService.selectList(wrapper);

        model.addAttribute("item", courseOrder);
        model.addAttribute("listtime", listtime);
        LogObjectHolder.me().set(courseOrder);
        return PREFIX + "courseOrder_edit.html";
    }

    /**
     * 获取课程报名管理列表
     *
     * @param condition  学员名称
     * @param courseName 课程名称
     * @param extendMode 课程分类
     * @param className  班级名称
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String courseName, String ifDivided, String extendMode, String className, Integer userId, String search) {
        if (StringUtils.isNotBlank(search)) {
            courseName = search;
        }
        //分页类
        Page<CourseOrder> page = new PageFactory<CourseOrder>().defaultPage();

        Wrapper<CourseOrder> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);

        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (ifDivided != null && ifDivided != "") {
            wrapper.eq("if_divided", ifDivided);
        }
        //学员名称
        if (condition != null && condition != "") {
            wrapper.like("user_name", condition);
        }
        //课程名称
        if (courseName != null && courseName != "") {
            wrapper.like("course_name", courseName);
        }
        //班级名称
        if (className != null && className != "") {
            wrapper.like("class_name", className);
        }
        //课程分类
        if (extendMode != null && extendMode != "") {
            wrapper.eq("course_cat_id", extendMode);
        }
        wrapper.ne("progress", "waitPay");
        return super.packForBT(courseOrderService.selectPage(page, wrapper));
    }

    /**
     * 新增课程报名管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CourseOrder courseOrder) {
        courseOrder.setCreatetime(new Date());//创建时间
        courseOrder.setType(ShiroKit.getUser().getDeptId());//所属公司

        courseOrderService.insert(courseOrder);
        return SUCCESS_TIP;
    }

    /**
     * 删除课程报名管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer courseOrderId) {
        courseOrderService.deleteById(courseOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 修改课程报名管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CourseOrder courseOrder, String adjustListenT) {
        courseOrder = courseOrderService.selectById(courseOrder.getId());
        courseOrder.setAdjustListenTime(DateUtil.parseDate(adjustListenT));//上课时间
        courseOrderService.updateById(courseOrder);
        return SUCCESS_TIP;
    }

    /**
     * 课程报名管理详情
     */
    @RequestMapping(value = "/detail/{courseOrderId}")
    @ResponseBody
    public Object detail(@PathVariable("courseOrderId") Integer courseOrderId) {
        return courseOrderService.selectById(courseOrderId);
    }

    /**
     * 给分销商分成
     *
     * @param courseOrderId
     * @return
     */
    @RequestMapping(value = "/divided")
    @ResponseBody
    public Object divided(Integer courseOrderId, Integer agentId) throws Exception {
        courseOrderService.updateAgentOrderNum(courseOrderId, agentId);
        return SUCCESS_TIP;
    }
}
