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
import com.labi.core.util.DateUtil;
import com.labi.core.util.HtmlUtils;
import com.labi.modular.biz.model.Course;
import com.labi.modular.biz.model.CourseCat;
import com.labi.modular.biz.model.CourseClass;
import com.labi.modular.biz.service.ICourseCatService;
import com.labi.modular.biz.service.ICourseClassService;
import com.labi.modular.biz.service.ICourseService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 课程管理控制器
 */
@Controller
@RequestMapping("/course")
public class CourseController extends BaseController {

    private String PREFIX = "/biz/course/";

    @Autowired
    private ICourseService courseService;//课程

    @Autowired
    private ICourseCatService courseCatService;//课程分类\

    @Autowired
    private ICourseClassService classService;//课程班级关系表

    /**
     * 跳转到课程管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "course.html";
    }

    /**
     * 跳转到添加课程管理
     */
    @RequestMapping("/course_add")
    public String courseAdd(Model model) {

        //课程分类查询
        Wrapper<CourseCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }

        //查询结果集
        List<CourseCat> coursecatlist = courseCatService.selectList(wrapper);
        model.addAttribute("coursecatlist", coursecatlist);

        return PREFIX + "course_add.html";
    }

    /**
     * 跳转到修改课程管理
     */
    @RequestMapping("/course_update/{courseId}")
    public String courseUpdate(@PathVariable Integer courseId, Model model) {
        //课程分类查询
        Wrapper<CourseCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        //查询结果集
        List<CourseCat> coursecatlist = courseCatService.selectList(wrapper);
        model.addAttribute("coursecatlist", coursecatlist);

        Course course = courseService.selectById(courseId);
        model.addAttribute("item", course);
        LogObjectHolder.me().set(course);
        return PREFIX + "course_edit.html";
    }

    /**
     * 获取课程管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        //分页类
        Page<Course> page = new PageFactory<Course>().defaultPage();

        //课程名称
        Wrapper<Course> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//获取状态正常（A）的数据
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }

        if (condition != null && condition != "") {
            wrapper.like("name", condition);
        }
        return super.packForBT(courseService.selectPage(page, wrapper));
        //return courseService.selectList(null);
    }


    /***
     * 新增课程管理
     * @param course实体对象
     * @param info 富文本
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Course course, String info, HttpServletRequest request) throws Exception {
        try {
            //生成详情html
            if (StringUtils.isNotBlank(info)) {
                //生成文件名称
                String desFile = DateUtil.getCurrentDateStr();
                //文件上传
                HtmlUtils.upload(info, desFile, "coacthFile", request);
                //属性设置
                course.setDesFile("coacthFile/" + desFile + ".html");
            }

            course.setCreatetime(new Date());//创建时间
            course.setType(ShiroKit.getUser().getDeptId());//所属公司
            courseService.insert(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS_TIP;
    }

    /**
     * 删除课程管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer courseId) {

        //查看该课程下是否存在班级
        Wrapper<CourseClass> wrapper = new EntityWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("status", Const.ACTIVE);
        int flg = classService.selectCount(wrapper);
        if (flg > 0) {
            //拒绝删除
            throw new BussinessException(BizExceptionEnum.LABI_SHOW_CLASS_MSG);
        } else {
            Course course = new Course();
            course.setId(courseId);
            course.setStatus(Const.DELETE);//修改删除状态
            courseService.updateById(course);
            //courseService.deleteById(courseId);
        }
        return SUCCESS_TIP;
    }

    /**
     * 修改课程管理
     *
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Course course, String info, HttpServletRequest request) throws Exception {

        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            info = StringEscapeUtils.unescapeHtml(info);
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, "coacthFile", request);
            //属性设置
            course.setDesFile("coacthFile/" + desFile + ".html");
        }
        course.setOprtime(new Date());
        courseService.updateById(course);
        return SUCCESS_TIP;
    }

}
