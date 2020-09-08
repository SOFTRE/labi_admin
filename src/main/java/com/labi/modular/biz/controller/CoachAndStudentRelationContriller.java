package com.labi.modular.biz.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.CoachReservation;
import com.labi.modular.biz.service.ICoachReservationService;
import com.labi.modular.biz.warpper.BizAgentWarpper;

/**
 * 综合查询控制器
 *
 */
@Controller
@RequestMapping("/coachAndStudentRelation")
public class CoachAndStudentRelationContriller extends BaseController{
		
	private String PREFIX = "/biz/comperQuery/";
	
	@Autowired
    private ICoachReservationService coachReservationService;
	
	/**
     * 综合查询首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "coachAndStudentRelation.html";
    }
    
    /**
     * 教练学员关系查询
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String coachname,String username) {
    	
    	Page<CoachReservation> page = new PageFactory<CoachReservation>().defaultPage();
    	
    	Wrapper<CoachReservation> wrapper = new EntityWrapper<>();
		wrapper.orderBy("createtime",false);
    	Integer depid =null;//所属公司
    	if(depid!=Const.IS_ADMIN_FLG) {
    		depid = ShiroKit.getUser().getDeptId();
    	}
    	
    	if(coachname!=null && coachname!="") {
    		wrapper.like("coach_name", coachname);//教练名称 
    	}
    	
    	if(username!=null && username!="") {
    		wrapper.like("user_name", username);//教练名称 
    	}
    	
    	if (depid!=null) {
    		wrapper.eq("type", depid);
		}
    	
    	wrapper.orderBy("createtime");
    	return super.packForBT( coachReservationService.selectPage(page, wrapper));
    	
    	 //结果集
    	 //List<Map<String, Object>> result =  coachReservationService.selectCoachStudentList(page, coachname, depid);
    	 //page.setRecords((List<CoachReservation>) new BizAgentWarpper(result).warp());
         //return super.packForBT(page);
    }
}
