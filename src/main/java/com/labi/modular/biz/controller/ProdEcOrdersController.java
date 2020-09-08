package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.ProdEcOrders;
import com.labi.modular.biz.service.IProdEcOrdersService;
import com.labi.modular.biz.warpper.ProdEcOrdersWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 退货申请 管理控制器
 */
@Controller
@RequestMapping("/prodEcOrders")
public class ProdEcOrdersController extends BaseController {
    private String PREFIX = "/biz/prodEcOrders/";

    @Autowired
    private IProdEcOrdersService prodEcOrdersService;//退货申请

    /**
     * 跳转到 退货申请 管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prodEcOrders.html";
    }


    /**
     * 跳转到 退货申请 管理
     */
    @RequestMapping("/prodEcOrders_update/{id}")
    public String courseTimeUpdate(@PathVariable Integer id, Model model) {
        ProdEcOrders prodEcOrders = prodEcOrdersService.selectById(id);
        model.addAttribute("item", prodEcOrders);
        LogObjectHolder.me().set(prodEcOrders);
        return PREFIX + "prodEcOrders_edit.html";
    }

    /**
     * 获取退货申请 列表
     *
     * @param orderNo  订单号
     * @param userName 用户名
     * @param progress 订单状态
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String orderNo, String userName, String progress,String logisticsNo) {
        Page<ProdEcOrders> page = new PageFactory<ProdEcOrders>().defaultPage();

        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            depid = null;
        }

        List<Map<String, Object>> result = prodEcOrdersService.getEcOrderList(page, orderNo, progress,logisticsNo, userName, depid);
        page.setRecords((List<ProdEcOrders>) new ProdEcOrdersWarpper(result).warp());
        return super.packForBT(page);
    }


    /**
     * 退款审核
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProdEcOrders prodEcOrders) {
        //根据id查询
        ProdEcOrders ecOrders = prodEcOrdersService.selectById(prodEcOrders.getId());
        if (prodEcOrders.getProgress().equals("F")) {
            ecOrders.setRefuseRemark(prodEcOrders.getRefuseRemark());
            ecOrders.setProgress("refuse");//拒绝
        } else {
            ecOrders.setProgress("waitDelivery");//待买家发货
            ecOrders.setRefuseRemark("");
        }
        ecOrders.setOprtime(new Date());//更新时间
        prodEcOrdersService.updateById(ecOrders);
        return SUCCESS_TIP;
    }

    /**
     * 完成退款页面
     */
    @RequestMapping("/complete/{id}")
    public String finish(@PathVariable Integer id, Model model) {
        ProdEcOrders prodEcOrders = prodEcOrdersService.selectById(id);
        model.addAttribute("item", prodEcOrders);
        LogObjectHolder.me().set(prodEcOrders);
        return PREFIX + "prodEcOrders_finish.html";
    }

    /**
     * 退款完成
     */
    @RequestMapping(value = "/complete")
    @ResponseBody
    public Object complete(@RequestParam Integer id, @RequestParam BigDecimal realPrice) {
        prodEcOrdersService.updateFinish(id, realPrice);
        return SUCCESS_TIP;
    }

}
