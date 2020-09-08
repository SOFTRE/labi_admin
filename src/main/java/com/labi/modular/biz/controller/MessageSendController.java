package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.model.CourseCat;
import com.labi.modular.biz.model.CourseOrder;
import com.labi.modular.biz.model.Message;

import com.labi.modular.biz.util.SMSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.labi.modular.biz.service.IBizUserService;
import com.labi.modular.biz.service.ICourseCatService;
import com.labi.modular.biz.service.ICourseOrderService;
import com.labi.modular.biz.service.IMessageService;

/**
 * 短信发送管理控制器
 *
 */
@Controller
@RequestMapping("/messageSend")
public class MessageSendController extends BaseController {

    private String PREFIX = "/biz/messageSend/";

    @Autowired
    private ICourseOrderService courseOrderService;//课程报名

    @Autowired
    private ICourseCatService courseCatService;//课程分类
    
    @Autowired
    private IMessageService messageService;//短信模板
    
    @Autowired
    private IBizUserService bizUserService;//用户
    
    
    
    /**
     * 用户列表
     * @param model
     * @param condition 用户名、电话
     * @return
     */
    @RequestMapping("userlist")
    public String userList(Model model,String condition) {
    	
    	//短信模板
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
    	Wrapper<Message> mesWrapper = new EntityWrapper<>();
		mesWrapper.orderBy("createtime",false);
    	if(depid!=Const.IS_ADMIN_FLG) {
    		mesWrapper.eq("type", depid);
        }
    	List<Message> meslist = messageService.selectList(mesWrapper);
    	model.addAttribute("meslist", meslist);//短信模板list
    	return PREFIX + "chooseUser.html";
    }
    
    /**
     * 用户搜索
     * @param model
     * @param condition
     * @param phone
     * @return
     */
    @RequestMapping("seluserlist")
    @ResponseBody
    public Object selUserList(Model model,String condition,String phone) {
    	Page<BizUser> page = new PageFactory<BizUser>().defaultPage();
    	Wrapper<BizUser> wrapper = new EntityWrapper<>();
    	if(condition!=null && condition!="") {
    		wrapper.where("instr(name,{0})>0 or instr(phone_num,{1})>0", condition, condition);
    	}
    	return super.packForBT(bizUserService.selectPage(page,wrapper)); 
    	
    }
    
    /***
     * 
     * @param userId 用户id
     * @param templateId 模板id
     * @return
     */
    @RequestMapping(value = "/usermsgsend")
    @ResponseBody
    public Object userMsgSend(String userId,Integer templateId) {
//    	System.out.println("==userId====="+userId+  " templateId:"+templateId);
    	String struserid[] = userId.split(",");
    	for (String string : struserid) {
    		BizUser user = bizUserService.selectById(string);
    		if(user.getPhoneNum()!=null && user.getPhoneNum()!="" && templateId!=null) {
    			ArrayList<String> params = new ArrayList<>();
    			params.add(user.getName());//用户名
    			params.add("财富爆棚");//课程名
    			params.add(user.getPhoneNum());//发送电话
    			SMSUtil.sendMsg(user.getPhoneNum(), templateId, params);
    		}else {
    			throw new BussinessException(BizExceptionEnum.MESSAGE_SENG_MSG);
    		}
		}
    	return SUCCESS_TIP;
    }
    
    /**
     * 跳转到短信发送管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	//获取课程分类列表
    	Wrapper<CourseCat> catwrapper = new EntityWrapper<>();
		catwrapper.orderBy("createtime",false);
    	catwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	catwrapper.eq("type", depid);
        }
    	List<CourseCat> catlist = courseCatService.selectList(catwrapper);
    	model.addAttribute("catlist", catlist);//课程分类list
    	
    	//短信模板
    	Wrapper<Message> mesWrapper = new EntityWrapper<>();
		mesWrapper.orderBy("createtime",false);
    	if(depid!=Const.IS_ADMIN_FLG) {
    		mesWrapper.eq("type", depid);
        }
    	List<Message> meslist = messageService.selectList(mesWrapper);
    	model.addAttribute("meslist", meslist);//短信模板list
        return PREFIX + "messageSend.html";
    }

   
    /**
     * 获取短信发送管理列表
     * @param condition 学员名称
     * @param courseName 课程名称
     * @param extendMode 课程分类
     * @param listenTime 上课时间
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,String courseName, String extendMode,String listenTime) {
    	//分页类
    	Page<CourseOrder> page = new PageFactory<CourseOrder>().defaultPage();
    	
    	Wrapper<CourseOrder> wrapper = new EntityWrapper<>();
		wrapper.orderBy("createtime",false);
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("type", depid);
        }
        
    	//学员名称
    	if (condition!=null && condition!="") {
    		  wrapper.like("user_name", condition);
		}
    	
    	//课程名称
    	if(courseName!=null && courseName!="") {
    		 wrapper.like("course_name", courseName);
    	}
    	
    	//课程分类
    	if (extendMode!=null && extendMode!="") {
    		 wrapper.eq("course_cat_id", extendMode);
		}
    	
    	//上课时间
    	if(listenTime!=null && listenTime!="") {
    		wrapper.between("listen_time", listenTime, listenTime+" 23:59:59");
    	}
    	
    	return super.packForBT(courseOrderService.selectPage(page,wrapper));    
    }

 
 

    /****
     * 短信单发
     * @param courseOrderId 订单号
     * @param userId 用户id
     * @param templateId 模板ID
     * @return
     */
    @RequestMapping(value = "/alone")
    @ResponseBody
    public Object alone(Integer courseOrderId,Integer userId,Integer templateId) {
    	System.out.println("==userId====="+userId+" courseOrderId:"+courseOrderId+ " templateId:"+templateId);
    	
    	//根据id查询订单
    	CourseOrder courseOrder = courseOrderService.selectById(courseOrderId);
    	if(courseOrder!=null &&  courseOrder.getPhoneNum().length()==11) {
    		ArrayList<String> params = new ArrayList<>();
	    	params.add(courseOrder.getUserName());//用户名
	    	params.add(courseOrder.getCourseName());//课程名
	    	params.add(courseOrder.getPhoneNum());//发送电话
	    	SMSUtil.sendMsg(courseOrder.getPhoneNum(), templateId, params);
    	}else {
    		throw new BussinessException(BizExceptionEnum.MESSAGE_SENG_MSG);
    	}
    	return SUCCESS_TIP;
    }
    
    /***
     * 短信群发
     * @param courseOrderId 订单
     * @param userId 用户id
     * @param templateId 模板id
     * * @param condition学员名称
     * * @param courseName 课程名称
     * * @param courseCatId 课程分类
     * @return
     */
    @RequestMapping(value = "/group")
    @ResponseBody
    public Object group (Integer courseOrderId,Integer userId,Integer templateId,String condition,String courseName,Integer courseCatId) {
    	System.out.println("==userId====="+userId+" courseOrderId:"+courseOrderId+ " templateId:"+templateId);

    	Wrapper<CourseOrder> wrapper = new EntityWrapper<>();
		wrapper.orderBy("createtime",false);
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("type", depid);
        }
        
    	//学员名称
    	if (condition!=null && condition!="") {
    		  wrapper.like("user_name", condition);
		}
    	
    	//课程名称
    	if(courseName!=null && courseName!="") {
    		 wrapper.like("course_name", courseName);
    	}
    	
    	//课程分类
    	if (courseCatId!=null) {
    		 wrapper.eq("course_cat_id", courseCatId);
		}
    	List<CourseOrder> orderList= courseOrderService.selectList(wrapper);
        System.out.println(orderList.size());
    	
        for (CourseOrder courseOrder : orderList) {
        	ArrayList<String> params = new ArrayList<>();
	    	params.add(courseOrder.getUserName());//用户名
	    	params.add(courseOrder.getCourseName());//课程名
	    	params.add(courseOrder.getPhoneNum());//发送电话
	    	SMSUtil.sendMsg(courseOrder.getPhoneNum(), templateId, params);
	    	continue;
		}
    	return SUCCESS_TIP;
    }
}
