package com.labi.modular.biz.controller;

import com.labi.common.constant.Const;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.ProdCatAttr;
import com.labi.modular.biz.service.LIProductCatAttrService;
import com.labi.modular.biz.service.LIProductService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类属性管理控制器
 */
@Controller
@RequestMapping("/LproductCatAttr")
public class LProductCatAttrController extends BaseController {

    @Autowired
    private LIProductCatAttrService productCatAttrService;

    /**
     * 获取商品分类属性列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<ProdCatAttr> list3(ProdCatAttr prodCatAttr) {

        List<ProdCatAttr> productCatAttrList = productCatAttrService.findProductCatAttrList(prodCatAttr);
        return productCatAttrList;

    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String,String> add(ProdCatAttr prodCatAttr){
       Map<String,String> result = new HashMap<>();
       prodCatAttr.setCreatetime(new Date());
       prodCatAttr.setOprtime(prodCatAttr.getCreatetime());
       prodCatAttr.setStatus(Const.ACTIVE);
       Boolean success = productCatAttrService.saveProdCatAttr(prodCatAttr);
       if (success){
           result.put("code","200");
           result.put("message","新增成功！");
           return result;
       }
       result.put("code","500");
       result.put("message","新增失败！");
       return result;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,String> update(ProdCatAttr prodCatAttr){
        Map<String,String> result = new HashMap<>();
        prodCatAttr.setOprtime(new Date());
        Boolean success = productCatAttrService.updateProdCatAttr(prodCatAttr);
        if (success){
            result.put("code","200");
            result.put("message","更新成功！");
            return result;
        }
        result.put("code","500");
        result.put("message","更新失败！");
        return result;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,String> delete(ProdCatAttr prodCatAttr){
        Map<String,String> result = new HashMap<>();
        prodCatAttr.setOprtime(new Date());
        Boolean success = productCatAttrService.deleteProdCatAttr(prodCatAttr);
        if (success){
            result.put("code","200");
            result.put("message","删除成功！");
            return result;
        }
        result.put("code","500");
        result.put("message","删除失败！");
        return result;
    }

}
