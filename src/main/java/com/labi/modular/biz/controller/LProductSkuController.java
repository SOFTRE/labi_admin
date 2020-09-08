package com.labi.modular.biz.controller;

import com.alibaba.fastjson.JSONObject;
import com.labi.common.constant.Const;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.ProdAttr;
import com.labi.modular.biz.model.ProdSku;
import com.labi.modular.biz.service.LIProductService;
import com.labi.modular.biz.service.LIProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品管理控制器
 */
@Controller
@RequestMapping("/LproductSku")
public class LProductSkuController extends BaseController {

    @Autowired
    private LIProductSkuService productSkuService;
    @Autowired
    private LIProductService productService;

    @RequestMapping(value = "/attrs")
    @ResponseBody
    public Object list(Integer productId) {
        List<ProdAttr> prodAttrs = productService.findProductListAttr(productId);
        List<ProdSku> prodSkus = productSkuService.findProductListSku(productId);
        JSONObject result = new JSONObject();
        result.put("prodSkus",prodSkus);
        result.put("prodAttrs",prodAttrs);
        return result;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String,String> add(ProdSku prodSku){
        Map<String,String> result = new HashMap<>();
        prodSku.setCreatetime(new Date());
        prodSku.setOprtime(prodSku.getCreatetime());
        prodSku.setStatus(Const.ACTIVE);
        Boolean success = productSkuService.saveProductSku(prodSku);
        if (success){
            result.put("code","200");
            result.put("message","新增成功");
            return result;
        }
        result.put("code","500");
        result.put("message","新增失败");
        return result;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,String> update(ProdSku prodSku){
        Map<String,String> result = new HashMap<>();
        prodSku.setOprtime(new Date());
        prodSku.setStatus(Const.ACTIVE);
        Boolean success = productSkuService.updateProductSku(prodSku);
        if (success){
            result.put("code","200");
            result.put("message","更新成功");
            return result;
        }
        result.put("code","500");
        result.put("message","更新失败");
        return result;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,String> delete(ProdSku prodSku){
        Map<String,String> result = new HashMap<>();
        prodSku.setOprtime(new Date());
        prodSku.setStatus(Const.DELETE);
        Boolean success = productSkuService.deleteProductSku(prodSku);
        if (success){
            result.put("code","200");
            result.put("message","删除成功");
            return result;
        }
        result.put("code","500");
        result.put("message","删除失败");
        return result;
    }

}
