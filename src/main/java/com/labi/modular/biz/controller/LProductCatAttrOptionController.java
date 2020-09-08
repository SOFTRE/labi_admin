package com.labi.modular.biz.controller;

import com.labi.common.constant.Const;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.ProdCatAttrOption;
import com.labi.modular.biz.service.LIProductCatAttrOptionService;
import com.labi.modular.biz.service.LIProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类属性值管理控制器
 */
@Controller
@RequestMapping("/LproductCatAttrOption")
public class LProductCatAttrOptionController extends BaseController {

    @Autowired
    private LIProductCatAttrOptionService productCatAttrOptionService;

    /**
     * 获取商品分类属性值列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<ProdCatAttrOption> list4(ProdCatAttrOption prodCatAttrOption) {

        List<ProdCatAttrOption> productCatAttrOption = productCatAttrOptionService.findProductCatAttrOption(prodCatAttrOption);
        return productCatAttrOption;

    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String,String> saveProdCatAttrOption(ProdCatAttrOption prodCatAttrOption){
        Map<String,String> result = new HashMap<>();
        prodCatAttrOption.setCreatetime(new Date());
        prodCatAttrOption.setOprtime(prodCatAttrOption.getCreatetime());
        prodCatAttrOption.setStatus(Const.ACTIVE);
        Boolean success = productCatAttrOptionService.saveProdCatAttrOption(prodCatAttrOption);
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
    public Map<String,String> updateProdCatAttrOption(ProdCatAttrOption prodCatAttrOption){
        Map<String,String> result = new HashMap<>();
        prodCatAttrOption.setOprtime(new Date());
        prodCatAttrOption.setStatus(Const.ACTIVE);
        Boolean success = productCatAttrOptionService.updateProdCatAttrOption(prodCatAttrOption);
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
    public Map<String,String> deleteProdCatAttrOption(ProdCatAttrOption prodCatAttrOption){
        Map<String,String> result = new HashMap<>();
        prodCatAttrOption.setOprtime(new Date());
        Boolean success = productCatAttrOptionService.deleteProdCatAttrOption(prodCatAttrOption);
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
