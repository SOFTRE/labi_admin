package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.BizAgent;
import com.labi.modular.biz.model.CoachEvaluateOption;
import com.labi.modular.biz.model.CoachOrder;
import com.labi.modular.biz.model.CoachReservationEvaluate;

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

import com.labi.modular.biz.service.ICoachEvaluateOptionService;
import com.labi.modular.biz.service.ICoachOrderService;
import com.labi.modular.biz.service.ICoachReservationEvaluateService;

/**
 * 预约反馈评分管理控制器
 *
 */
@Controller
@RequestMapping("/coachReservationEvaluate")
public class CoachReservationEvaluateController extends BaseController {

    private String PREFIX = "/biz/coachReservationEvaluate/";

    @Autowired
    private ICoachOrderService coachOrderService;//教练预约
    @Autowired
    private ICoachReservationEvaluateService coachReservationEvaluateService;//预约反馈评分
    @Autowired
    private ICoachEvaluateOptionService coachEvaluateOptionService;//教练评价
    /**
     * 跳转到预约反馈评分管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "coachReservationEvaluate.html";
    }

    /**
     * 跳转到添加预约反馈评分管理
     */
    @RequestMapping("/coachReservationEvaluate_add")
    public String coachOrderAdd() {
        return PREFIX + "coachReservationEvaluate_add.html";
    }

    /**
     * 跳转到修改预约反馈评分管理
     */
    @RequestMapping("/coachReservationEvaluate_update/{coachOrderId}")
    public String coachOrderUpdate(@PathVariable Integer coachOrderId, Model model) {
        CoachOrder coachOrder = coachOrderService.selectById(coachOrderId);
        model.addAttribute("item",coachOrder);
        LogObjectHolder.me().set(coachOrder);
        return PREFIX + "coachReservationEvaluate_edit.html";
    }

    
    /**
     * 获取预约反馈评分管理列表
     * @param condition 教练名称
     * @param userName 学员名称
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,String userName) {
    	//分页类
    	Page<CoachReservationEvaluate> page = new PageFactory<CoachReservationEvaluate>().defaultPage();
    	Wrapper<CoachReservationEvaluate> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("type", depid);
        }
    	
    	if (condition !=null && condition!="") {
    		wrapper.like("coach_name", condition);//教练名称
		}
    	
    	if (userName !=null && userName!="") {
    		wrapper.like("user_name", userName);//学员名称
		}
    	
    	//获取教练评价选项列表
    	Wrapper<CoachEvaluateOption> reWrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
    	if(depid!=Const.IS_ADMIN_FLG) {
    		reWrapper.eq("type", depid);//所属公司
        }
    	reWrapper.eq("status", Const.ACTIVE);//正常状态数据
    	List<CoachEvaluateOption> ceoList= coachEvaluateOptionService.selectList(reWrapper);
    	
    	
    	Page<CoachReservationEvaluate> result =   coachReservationEvaluateService.selectPage(page, wrapper);
    	List<CoachReservationEvaluate> resultList =result.getRecords(); 
    	for (CoachReservationEvaluate coachReservationEvaluate : resultList) {
    		for (CoachEvaluateOption coachEvaluateOption : ceoList) {
    			//解析评价选项id 对应的问题描述
    			if(coachReservationEvaluate.getEvaluateId()==coachEvaluateOption.getId() ) {
    				//map.put("desName", coachEvaluateOption.getName());
    				coachReservationEvaluate.setDesQuerName(coachEvaluateOption.getName());
    			}
    		}
		}
    	return   super.packForBT(  result);
    	//return  super.packForBT(coachReservationEvaluateService.selectPage(page, wrapper));
    }

    /**
     * 新增预约反馈评分管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CoachOrder coachOrder) {
    	//所属公司
    	
    	//创建时间
    	coachOrder.setCreatetime(new Date());
        coachOrderService.insert(coachOrder);
        return SUCCESS_TIP;
    }

    /**
     * 删除预约反馈评分管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer coachOrderId) {
        coachOrderService.deleteById(coachOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 修改预约反馈评分管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CoachOrder coachOrder) {
        coachOrderService.updateById(coachOrder);
        return SUCCESS_TIP;
    }

    /**
     * 预约反馈评分管理详情
     */
    @RequestMapping(value = "/detail/{coachOrderId}")
    @ResponseBody
    public Object detail(@PathVariable("coachOrderId") Integer coachOrderId) {
        return coachOrderService.selectById(coachOrderId);
    }
}
