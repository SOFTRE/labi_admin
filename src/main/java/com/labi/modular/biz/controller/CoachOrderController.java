package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.CoachOrder;
import com.labi.modular.biz.service.ICoachOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 预约缴费管理控制器
 */
@Controller
@RequestMapping("/coachOrder")
public class CoachOrderController extends BaseController {

    private String PREFIX = "/biz/coachOrder/";

    @Autowired
    private ICoachOrderService coachOrderService;//教练预约

    /**
     * 跳转到预约缴费管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "coachOrder.html";
    }

    /**
     * 跳转到添加预约缴费管理
     */
    @RequestMapping("/coachOrder_add")
    public String coachOrderAdd() {
        return PREFIX + "coachOrder_add.html";
    }

    /**
     * 跳转到修改预约缴费管理
     */
    @RequestMapping("/coachOrder_update/{coachOrderId}")
    public String coachOrderUpdate(@PathVariable Integer coachOrderId, Model model) {
        CoachOrder coachOrder = coachOrderService.selectById(coachOrderId);
        model.addAttribute("item", coachOrder);
        LogObjectHolder.me().set(coachOrder);
        return PREFIX + "coachOrder_edit.html";
    }

    /**
     * 获取预约缴费管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer userId,String search) {
        //分页类
        Page<CoachOrder> page = new PageFactory<CoachOrder>().defaultPage();
        Wrapper<CoachOrder> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.ne("progress", "waitPay");
        //所属公司
        Integer depid = ShiroKit.getUser().getDeptId();
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (condition != null && condition != "") {
            wrapper.and("(instr(coach_name,{0})>0 or instr(coach_grade_name,{1})>0 or instr(user_name,{2})>0)", condition, condition, condition);
        }
        if (StringUtils.isNotBlank(search)) {
            wrapper.and("instr(coach_grade_name,{0})>0", search);
        }
        return super.packForBT(coachOrderService.selectPage(page, wrapper));
        //return coachOrderService.selectList(null);
    }

    /**
     * 新增预约缴费管理
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
     * 删除预约缴费管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer coachOrderId) {
        coachOrderService.deleteById(coachOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 修改预约缴费管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CoachOrder coachOrder) {
        coachOrderService.updateById(coachOrder);
        return SUCCESS_TIP;
    }

    /**
     * 预约缴费管理详情
     */
    @RequestMapping(value = "/detail/{coachOrderId}")
    @ResponseBody
    public Object detail(@PathVariable("coachOrderId") Integer coachOrderId) {
        return coachOrderService.selectById(coachOrderId);
    }
}
