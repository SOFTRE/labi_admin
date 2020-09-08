package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.labi.common.constant.Const;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.modular.biz.model.ProdCatAttr;
import com.labi.modular.biz.model.ProdCatAttrOption;
import com.labi.modular.biz.service.IProdCatAttrOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 商品分类属性可选项管理控制器
 *
 */
@Controller
@RequestMapping("/prodCatAttrOption")
public class ProdCatAttrOptionController extends BaseController {

    private String PREFIX = "/biz/prodCatAttrOption/";

    @Autowired
    private IProdCatAttrOptionService prodCatAttrOptionService;
    /**
     * 跳转到添加商品分类属性管理
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String prodCatAdd() {
        return PREFIX + "prodCatAttrOption_add.html";
    }
    /**
     * 跳转到修改商品分类属性管理
     */
    @RequestMapping(value = "/update/{optionId}",method =RequestMethod.GET)
    public String prodCatUpdate(@PathVariable Integer optionId, Model model) {
        ProdCatAttrOption option = prodCatAttrOptionService.selectById(optionId);
        model.addAttribute("item", option);
        LogObjectHolder.me().set(option);
        return PREFIX + "prodCatAttrOption_edit.html";
    }
    /**
     * 根据商品分类属性id查询属性值
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object attrsOptions(Integer attrId) {
        Wrapper<ProdCatAttrOption> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        wrapper.eq("status", Const.ACTIVE);
        wrapper.eq("attr_id", attrId);
        return prodCatAttrOptionService.selectList(wrapper);
    }
    /**
     * 新增商品分类属性可选项管理
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(ProdCatAttrOption prodCatAttrOption) {
        prodCatAttrOption.setCreatetime(new Date());
        prodCatAttrOption.setStatus(Const.ACTIVE);
        prodCatAttrOptionService.insert(prodCatAttrOption);
        return SUCCESS_TIP;
    }

    /**
     * 删除商品分类属性可选项管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(ProdCatAttrOption prodCatAttrOption) {
        prodCatAttrOption.setOprtime(new Date());
        prodCatAttrOption.setStatus(Const.DELETE);
        prodCatAttrOptionService.updateById(prodCatAttrOption);
        return SUCCESS_TIP;
    }

    /**
     * 修改商品分类属性可选项管理
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(ProdCatAttrOption prodCatAttrOption) {
        prodCatAttrOption.setOprtime(new Date());
        prodCatAttrOptionService.updateById(prodCatAttrOption);
        return SUCCESS_TIP;
    }
}
