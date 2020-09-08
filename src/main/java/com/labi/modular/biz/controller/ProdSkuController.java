package com.labi.modular.biz.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.labi.common.constant.Const;
import com.labi.common.controller.BaseController;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.model.ProdAttr;
import com.labi.modular.biz.model.ProdSku;
import com.labi.modular.biz.service.IProdAttrService;
import com.labi.modular.biz.service.IProdSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * SKU管理控制器
 */
@Controller
@RequestMapping("/prodSku")
public class ProdSkuController extends BaseController {

    @Autowired
    private IProdSkuService prodSkuService;

    /**
     * 根据商品ID查询商品属性和sku
     *
     * @param productId productId
     * @return Object
     */
    @RequestMapping(value = "/attrs", method = RequestMethod.POST)
    @ResponseBody
    public Object loadGoodsAttrList(Integer productId) {
        Wrapper<ProdAttr> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);
        wrapper.eq("prod_id", productId);
        List<ProdAttr> attrs = SpringContextHolder.getBean(IProdAttrService.class).selectList(wrapper);

        Wrapper<ProdSku> wrapperSku = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapperSku.eq("prod_id", productId);
        wrapperSku.eq("status", Const.ACTIVE);
        List<ProdSku> skus = prodSkuService.selectList(wrapperSku);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("attrs", attrs);
        jsonObject.put("skus", skus);

        return jsonObject;
    }

    /**
     * 新增SKU管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProdSku prodSku) {
        return prodSkuService.insertSku(prodSku);
    }

    /**
     * 删除SKU管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer prodSkuId) {
        ProdSku prodSku = new ProdSku();
        prodSku.setId(prodSkuId);
        prodSku.setStatus(Const.DELETE);
        prodSku.setOprtime(new Date());
        prodSkuService.updateById(prodSku);
        return SUCCESS_TIP;
    }

    /**
     * 修改SKU管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProdSku prodSku) {
        prodSkuService.updateById(prodSku);
        return SUCCESS_TIP;
    }
}
