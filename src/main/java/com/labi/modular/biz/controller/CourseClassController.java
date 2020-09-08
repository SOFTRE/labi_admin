package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.modular.biz.model.Course;
import com.labi.modular.biz.model.CourseClass;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;

import org.springframework.web.bind.annotation.RequestParam;
import com.labi.modular.biz.service.ICourseClassService;
import com.labi.modular.biz.service.ICourseService;

/**
 * 班级管理控制器
 *
 */
@Controller
@RequestMapping("/courseClass")
public class CourseClassController extends BaseController {

    private String PREFIX = "/biz/courseClass/";

    @Autowired
    private ICourseClassService courseClassService;//班级
    @Autowired
    private ICourseService courseService;//课程
    
    
    /**
     * 跳转到班级管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "courseClass.html";
    }

    /**
     * 跳转到添加班级管理
     */
    @RequestMapping("/courseClass_add")
    public String courseClassAdd(Model model) {
    	
    	//获取课程列表
    	Wrapper<Course> catwrapper = new EntityWrapper<>();
        catwrapper.orderBy("createtime",false);
    	catwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	catwrapper.eq("type", depid);
        }
    	
    	List<Course> catlist = courseService.selectList(catwrapper);
    	model.addAttribute("catlist", catlist);
    	
        return PREFIX + "courseClass_add.html";
    }

    /**
     * 跳转到修改班级管理
     */
    @RequestMapping("/courseClass_update/{courseClassId}")
    public String courseClassUpdate(@PathVariable Integer courseClassId, Model model) {
    	//获取课程列表
    	Wrapper<Course> catwrapper = new EntityWrapper<>();
        catwrapper.orderBy("createtime",false);
    	catwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
    	
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	catwrapper.eq("type", depid);
        }
        
    	List<Course> catlist = courseService.selectList(catwrapper);
    	model.addAttribute("catlist", catlist);
    	
        CourseClass courseClass = courseClassService.selectById(courseClassId);
        model.addAttribute("item",courseClass);
        LogObjectHolder.me().set(courseClass);
        return PREFIX + "courseClass_edit.html";
    }

    /**
     * 获取班级管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	
    	//获取课程列表
    	Wrapper<Course> catwrapper = new EntityWrapper<>();
        catwrapper.orderBy("createtime",false);
    	catwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	catwrapper.eq("type", depid);
        }
        
    	List<Course> catlist = courseService.selectList(catwrapper);
    	
    	
    	//分页类
    	Page<CourseClass> page = new PageFactory<CourseClass>().defaultPage();
    	
    	Wrapper<CourseClass> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
    	wrapper.eq("status", Const.ACTIVE);//获取状态正常（A）的数据
    	if(depid!=Const.IS_ADMIN_FLG) {
        	catwrapper.eq("type", depid);
        }
    	
        if(depid!=0) {
        	wrapper.eq("type", depid);
        }
        
    	//名称
    	if(condition!=null && condition!="") {
    		wrapper.like("name", condition);
    	}
    	
    	 //x循环处理课程id对应的名称
    	 Page<Map<String, Object>> showPage =courseClassService.selectMapsPage(page, wrapper);
    	 List<Map<String, Object>> list=showPage.getRecords();
    	 for (Map<String, Object> map : list) {
    		 for (Course course : catlist) {
    				if (Integer.parseInt( map.get("courseId")+"")==course.getId()) {
    					map.put("courseName", course.getName());
					}
    			}
		}
    	 
    	 return   super.packForBT(showPage);
    }

    /**
     * 新增班级管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CourseClass courseClass) {
    	courseClass.setType(ShiroKit.getUser().getDeptId());//所属公司
    	courseClass.setCreatetime(new Date());//创建时间
        courseClassService.insert(courseClass);
        return SUCCESS_TIP;
    }

    /**
     * 删除班级管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer courseClassId) {
    	
    	 
    		CourseClass courseClass = new CourseClass();
    		courseClass.setId(courseClassId);
    		courseClass.setStatus(Const.DELETE);//修改删除状态
    		courseClassService.updateById(courseClass);
    		return SUCCESS_TIP;
    }

    /**
     * 修改班级管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CourseClass courseClass) {
    	courseClass.setOprtime(new Date());
        courseClassService.updateById(courseClass);
        return SUCCESS_TIP;
    }

    /**
     * 班级管理详情
     */
    @RequestMapping(value = "/detail/{courseClassId}")
    @ResponseBody
    public Object detail(@PathVariable("courseClassId") Integer courseClassId) {
        return courseClassService.selectById(courseClassId);
    }
}
