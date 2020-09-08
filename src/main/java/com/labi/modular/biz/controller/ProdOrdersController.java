package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.shiro.ShiroKit;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.model.OrderLogistics;
import com.labi.modular.biz.model.ProdOrders;
import com.labi.modular.biz.model.ProdOrdersDtl;
import com.labi.modular.biz.service.IOrderLogisticsService;
import com.labi.modular.biz.service.IProdOrdersDtlService;
import com.labi.modular.biz.service.IProdOrdersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单管理控制器
 */
@Controller
@RequestMapping("/prodOrders")
public class ProdOrdersController extends BaseController {

    private String PREFIX = "/biz/prodOrders/";

    @Autowired
    private IProdOrdersService prodOrdersService;

    @Autowired
    private IOrderLogisticsService orderLogisticsService;

    /**
     * 跳转到订单管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prodOrders.html";
    }

    /**
     * 跳转到订单详情
     */
    @RequestMapping("/prodOrders_dtl/{prodOrdersId}")
    public String prodOrdersDtl(@PathVariable Integer prodOrdersId, Model model) {
        ProdOrders prodOrders = prodOrdersService.selectById(prodOrdersId);
        model.addAttribute("item", prodOrders);
//        LogObjectHolder.me().set(prodOrders);
        return PREFIX + "prodOrders_dtl.html";
    }

    /**
     * 跳转到订单发货
     *
     * @param prodOrdersId
     * @param model
     * @return
     */
    @RequestMapping("/prodOrders_delivery/{prodOrdersId}")
    public String prodOrdersUpdate(@PathVariable Integer prodOrdersId, Model model) {
        model.addAttribute("prodOrdersId", prodOrdersId);
        return PREFIX + "prodOrders_deliver.html";
    }

    /**
     * 获取订单管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String progress,Integer userId) {
        Page<ProdOrders> page = new PageFactory<ProdOrders>().defaultPage();
        Wrapper<ProdOrders> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        wrapper.eq("if_video", Const.FALSE);
        if (condition != null && condition != "") {
            wrapper.where("instr(user_name,{0})>0", condition);
        }
        if (StringUtils.isNotBlank(progress)) {
            wrapper.eq("progress", progress);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        Integer deptid = ShiroKit.getUser().getDeptId();//所属公司
        if (deptid != Const.IS_ADMIN_FLG) {
            wrapper.eq("dept_id", deptid);
        }
        return super.packForBT(prodOrdersService.selectMapsPage(page, wrapper));
    }

    /**
     * 获取订单详情
     */
    @RequestMapping(value = "/dtl")
    @ResponseBody
    public Object dtl(Integer prodOrdersId) {
        Wrapper<ProdOrdersDtl> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        wrapper.eq("order_id", prodOrdersId);
        return SpringContextHolder.getBean(IProdOrdersDtlService.class).selectList(wrapper);
    }

    /**
     * 订单发货
     */
    @RequestMapping(value = "/delivery")
    @ResponseBody
    public Object add(OrderLogistics orderLogistics) {
        orderLogisticsService.updateDelivery(orderLogistics);
        return SUCCESS_TIP;
    }
}
