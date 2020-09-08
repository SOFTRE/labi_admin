package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.Banner;
import com.labi.modular.biz.model.CouponList;
import com.labi.modular.biz.model.CouponRule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;

import org.springframework.web.bind.annotation.RequestParam;
import com.labi.modular.biz.service.ICouponListService;

/**
 * 用户优惠券管理控制器
 *
 */
@Controller
@RequestMapping("/couponList")
public class CouponListController extends BaseController {

    private String PREFIX = "/biz/couponList/";

    @Autowired
    private ICouponListService couponListService;

    /**
     * 跳转到用户优惠券管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "couponList.html";
    }

    /**
     * 跳转到添加用户优惠券管理
     */
    @RequestMapping("/couponList_add")
    public String couponListAdd() {
        return PREFIX + "couponList_add.html";
    }

    /**
     * 跳转到修改用户优惠券管理
     */
    @RequestMapping("/couponList_update/{couponListId}")
    public String couponListUpdate(@PathVariable Integer couponListId, Model model) {
        CouponList couponList = couponListService.selectById(couponListId);
        model.addAttribute("item",couponList);
        LogObjectHolder.me().set(couponList);
        return PREFIX + "couponList_edit.html";
    }

    /***
     * 获取用户优惠券管理列表
     * @param condition 用户名
     * @param couponName 优惠券名称
     * @param extendMode 发放方式
     * @param useMode 使用方式
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,String couponName,String extendMode,String useMode) {
    	//分页类
    	 Page<CouponList> page = new PageFactory<CouponList>().defaultPage();
    	 Wrapper<CouponList> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
    	 
    	 Integer depid = ShiroKit.getUser().getDeptId();//所属公司
         if(depid!=Const.IS_ADMIN_FLG) {
         	wrapper.eq("type", depid);
         }
         
    	//用户名称
         if(condition!=null && condition!="") {
             //wrapper.like("user_name", condition);
             wrapper.where("instr(user_name,{0})>0", condition);
         }
         //优惠券名称
         if(couponName!=null && couponName!="") {
        	 wrapper.where("instr(coupon_name,{0})>0", condition);
         }
         // 发放方式
         if(extendMode!=null && extendMode!="") {
        	 wrapper.eq("extend_mode", extendMode);
         }
         //使用方式
         if(useMode!=null && useMode!="") {
        	 wrapper.eq("use_mode", useMode);
         }
         
         return super.packForBT(couponListService.selectPage(page,wrapper));
    }

    /**
     * 新增用户优惠券管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CouponList couponList) {
        couponListService.insert(couponList);
        return SUCCESS_TIP;
    }

    /**
     * 删除用户优惠券管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer couponListId) {
        couponListService.deleteById(couponListId);
        return SUCCESS_TIP;
    }

    /**
     * 修改用户优惠券管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CouponList couponList) {
        couponListService.updateById(couponList);
        return SUCCESS_TIP;
    }

    /**
     * 用户优惠券管理详情
     */
    @RequestMapping(value = "/detail/{couponListId}")
    @ResponseBody
    public Object detail(@PathVariable("couponListId") Integer couponListId) {
        return couponListService.selectById(couponListId);
    }
}
