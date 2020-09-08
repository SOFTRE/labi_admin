package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.Course;
import com.labi.modular.biz.model.CourseCat;
import com.labi.modular.biz.service.ICourseCatService;
import com.labi.modular.biz.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 课程分类管理控制器
 */
@Controller
@RequestMapping("/courseCat")
public class CourseCatController extends BaseController {

    private String PREFIX = "/biz/courseCat/";

    @Autowired
    private ICourseCatService courseCatService;//课程分类

    @Autowired
    private ICourseService courseService;//课程

    /**
     * 跳转到课程分类管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "courseCat.html";
    }

    /**
     * 跳转到添加课程分类管理
     */
    @RequestMapping("/courseCat_add")
    public String courseCatAdd() {
        return PREFIX + "courseCat_add.html";
    }

    /**
     * 跳转到修改课程分类管理
     */
    @RequestMapping("/courseCat_update/{courseCatId}")
    public String courseCatUpdate(@PathVariable Integer courseCatId, Model model) {
        CourseCat courseCat = courseCatService.selectById(courseCatId);
        model.addAttribute("item", courseCat);
        LogObjectHolder.me().set(courseCat);
        return PREFIX + "courseCat_edit.html";
    }

    /**
     * 获取课程分类管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        //分页类
        Page<CourseCat> page = new PageFactory<CourseCat>().defaultPage();
        Wrapper<CourseCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//获取状态正常（A）的数据
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司

        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }

        if (condition != null && condition != "") {
            wrapper.like("name", condition);
        }
        return this.packForBT(courseCatService.selectMapsPage(page, wrapper));
        //return courseCatService.selectList(null);
    }

    /**
     * 新增课程分类管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CourseCat courseCat) {
        courseCat.setType(ShiroKit.getUser().getDeptId());//所属公司
        courseCat.setCreatetime(new Date());//创建时间
        courseCatService.insert(courseCat);
        return SUCCESS_TIP;
    }

    /**
     * 删除课程分类管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer courseCatId) {

        //根据分类id查询分类下是否有数据
        Wrapper<Course> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("course_cat_id", courseCatId);
        int flg = courseService.selectCount(wrapper);
        if (flg > 0) {
            //拒绝删除
            throw new BussinessException(BizExceptionEnum.LABI_SHOW_MSG);
        } else {
            CourseCat courseCat = new CourseCat();
            courseCat.setId(courseCatId);
            courseCat.setStatus(Const.DELETE);//修改删除状态
            courseCatService.updateById(courseCat);
        }
        //courseCatService.deleteById(courseCatId);
        return SUCCESS_TIP;
    }

    /**
     * 修改课程分类管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CourseCat courseCat) {
        courseCatService.updateById(courseCat);

        Wrapper<Course> wrapper = new EntityWrapper<>();
        wrapper.eq("course_cat_id", courseCat.getId());
        Course course = new Course();
        course.setCourseCatName(courseCat.getName());
        courseService.update(course, wrapper);
        return SUCCESS_TIP;
    }

    /**
     * 课程分类管理详情
     */
    @RequestMapping(value = "/detail/{courseCatId}")
    @ResponseBody
    public Object detail(@PathVariable("courseCatId") Integer courseCatId) {
        return courseCatService.selectById(courseCatId);
    }
}
