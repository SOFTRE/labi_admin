package com.labi.modular.biz.controller;

import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.ProdOrdersDtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.labi.core.log.LogObjectHolder;
import com.labi.modular.biz.service.IProdOrdersDtlService;

/**
 * 订单详情管理控制器
 *
 */
@Controller
@RequestMapping("/prodOrdersDtl")
public class ProdOrdersDtlController extends BaseController {

    private String PREFIX = "/biz/prodOrdersDtl/";

    @Autowired
    private IProdOrdersDtlService prodOrdersDtlService;

    /**
     * 跳转到广告管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prodOrdersDtl.html";
    }

    /**
     * 跳转到添加广告管理
     */
    @RequestMapping("/prodOrdersDtl_add")
    public String prodOrdersDtlAdd() {
        return PREFIX + "prodOrdersDtl_add.html";
    }

    /**
     * 跳转到修改广告管理
     */
    @RequestMapping("/prodOrdersDtl_update/{prodOrdersDtlId}")
    public String prodOrdersDtlUpdate(@PathVariable Integer prodOrdersDtlId, Model model) {
        ProdOrdersDtl prodOrdersDtl = prodOrdersDtlService.selectById(prodOrdersDtlId);
        model.addAttribute("item",prodOrdersDtl);
        LogObjectHolder.me().set(prodOrdersDtl);
        return PREFIX + "prodOrdersDtl_edit.html";
    }

    /**
     * 获取广告管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return prodOrdersDtlService.selectList(null);
    }

    /**
     * 新增广告管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProdOrdersDtl prodOrdersDtl) {
        prodOrdersDtlService.insert(prodOrdersDtl);
        return SUCCESS_TIP;
    }

    /**
     * 删除广告管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer prodOrdersDtlId) {
        prodOrdersDtlService.deleteById(prodOrdersDtlId);
        return SUCCESS_TIP;
    }

    /**
     * 修改广告管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProdOrdersDtl prodOrdersDtl) {
        prodOrdersDtlService.updateById(prodOrdersDtl);
        return SUCCESS_TIP;
    }

    /**
     * 广告管理详情
     */
    @RequestMapping(value = "/detail/{prodOrdersDtlId}")
    @ResponseBody
    public Object detail(@PathVariable("prodOrdersDtlId") Integer prodOrdersDtlId) {
        return prodOrdersDtlService.selectById(prodOrdersDtlId);
    }
}
