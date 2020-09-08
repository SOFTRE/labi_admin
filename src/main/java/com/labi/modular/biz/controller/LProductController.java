package com.labi.modular.biz.controller;

import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.*;
import com.labi.modular.biz.service.LIProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理控制器
 */
@Controller
@RequestMapping("/Lproduct")
public class LProductController extends BaseController {

    @Autowired
    private LIProductService productLService;


    /**
     * 获取商品管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Product> list(Product product) {

        List<Product> productList = productLService.findProductList(product);

        return productList;

    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public void add(Product product,String attrValueArrStr){
        productLService.saveProduct(product,attrValueArrStr);

    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,String> update(Product product,String attrValueArrStr){
        Map<String,String> result = new HashMap<>();
        Boolean success = productLService.updateProduct(product,attrValueArrStr);
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
    public Map<String,String> delete(@RequestParam String ids){
        Map<String,String> result = new HashMap<>();
       Boolean success = productLService.deleteByIds(ids);
       if (success){
           result.put("code","200");
           result.put("message","success");
           return result;
       }
       result.put("code","500");
       result.put("message","error");
       return result;
    }

}
