package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.CouponRule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;
import com.labi.modular.biz.service.ICouponRuleService;

/**
 * 优惠券管理控制器
 *
 */
@Controller
@RequestMapping("/couponRule")
public class CouponRuleController extends BaseController {

    private String PREFIX = "/biz/couponRule/";

    @Autowired
    private ICouponRuleService couponRuleService;

    /**
     * 跳转到优惠券管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "couponRule.html";
    }

    /**
     * 跳转到添加优惠券管理
     */
    @RequestMapping("/couponRule_add")
    public String couponRuleAdd() {
        return PREFIX + "couponRule_add.html";
    }

    /**
     * 跳转到修改优惠券管理
     */
    @RequestMapping("/couponRule_update/{couponRuleId}")
    public String couponRuleUpdate(@PathVariable Integer couponRuleId, Model model) {
        CouponRule couponRule = couponRuleService.selectById(couponRuleId);
        model.addAttribute("item",couponRule);
        LogObjectHolder.me().set(couponRule);
        return PREFIX + "couponRule_edit.html";
    }

    /**
     * 获取优惠券管理列表
     * @param condition 优惠券名称
     * @param extendMode 赠送方式
     * @param useMode 使用方式
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,String extendMode,String useMode) {
    	
    	//分页类
    	Page<CouponRule> page = new PageFactory<CouponRule>().defaultPage();
    	Wrapper<CouponRule> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("type", depid);
        }
        
    	//优惠券名称
    	if(condition!=null && condition!="") {
    		wrapper.like("coupon_name", condition);
    	}
    	//赠送方式
    	if(extendMode!=null && extendMode!="") {
    		wrapper.eq("extend_mode", extendMode);
    	}
    	
    	//使用方式
    	if(useMode!=null && useMode!="") {
    		wrapper.eq("use_mode", useMode);
    	}
    	Page<Map<String, Object>> showPage = couponRuleService.selectMapsPage(page, wrapper);
    	return this.packForBT(showPage);
        //return couponRuleService.selectList(null);
    	
    }

    /**
     * 新增优惠券
     * @param couponRule
     * @param issueBeginDateTwo 发放开始日期
     * @param issueEndDateTwo 发放结束日期
     * @param validBeginDateTwo 使用开始日期
     * @param validEndDateTwo 使用结束日期
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CouponRule couponRule,String issueBeginDateTwo,String issueEndDateTwo,String validBeginDateTwo,String validEndDateTwo) {
    	try {
    		
    		couponRule.setIssueBeginDate(ToolUtil.strToDateYmd(issueBeginDateTwo));//发放开始日期
    		couponRule.setIssueEndDate(ToolUtil.strToDateYmd(issueEndDateTwo));//发放结束日期
    		couponRule.setValidBeginDate(ToolUtil.strToDateYmd(validBeginDateTwo));//使用开始日期
    		couponRule.setValidEndDate(ToolUtil.strToDateYmd(validEndDateTwo));//使用结束日期
    		
    		
        	//赠送时的最低消费金额
        	//couponRule.setPresentMinVal(couponRule.getPresentMinVal()==""?null:couponRule.getPresentMinVal());
           couponRule.setCreatetime(new Date());
           couponRuleService.insert(couponRule);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return SUCCESS_TIP;
    }

    /**
     * 删除优惠券管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer couponRuleId) {
        couponRuleService.deleteById(couponRuleId);
        return SUCCESS_TIP;
    }

    /**
     * 修改优惠券管理
     * @param couponRule
     * @param issueBeginDateTwo
     * @param issueEndDateTwo
     * @param validBeginDateTwo
     * @param validEndDateTwo
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CouponRule couponRule,String issueBeginDateTwo,String issueEndDateTwo,String validBeginDateTwo,String validEndDateTwo) {
    	try {
    		couponRule.setIssueBeginDate(ToolUtil.strToDateYmd(issueBeginDateTwo));//发放开始日期
    		couponRule.setIssueEndDate(ToolUtil.strToDateYmd(issueEndDateTwo));//发放结束日期
    		couponRule.setValidBeginDate(ToolUtil.strToDateYmd(validBeginDateTwo));//使用开始日期
    		couponRule.setValidEndDate(ToolUtil.strToDateYmd(validEndDateTwo));//使用结束日期
    		couponRule.setOprtime(new Date());//修改时间
            couponRuleService.updateById(couponRule);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return SUCCESS_TIP;
    }

    /**
     * 优惠券管理详情
     */
    @RequestMapping(value = "/detail/{couponRuleId}")
    @ResponseBody
    public Object detail(@PathVariable("couponRuleId") Integer couponRuleId) {
        return couponRuleService.selectById(couponRuleId);
    }
    
    public static void main(String[] args) {
    	System.out.println(System.getProperty("os.name"));
    	System.out.println(System.getProperty("java.io.tmpdir"));
	}
}
