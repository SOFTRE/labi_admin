package com.labi.modular.biz.controller;

import com.labi.common.constant.Const;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.ProdCat;
import com.labi.modular.biz.service.LIProductCatService;
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
 * 商品分类管理控制器
 */
@Controller
@RequestMapping("/LproductCat")
public class LProductCatController extends BaseController {

    @Autowired
    private LIProductCatService productCatService;

    /**
     * 获取商品分类列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<ProdCat> list2(ProdCat prodCat) {

        List<ProdCat> productCatList = productCatService.findProductCatList(prodCat);

        return productCatList;

    }

    @RequestMapping("/add")
    @ResponseBody
    public Map<String,String> save(ProdCat prodCat){
        Map<String,String> result = new HashMap<>();
        prodCat.setRootId(prodCat.getParentId());
        if (prodCat.getParentId()==Const.UNKNOWN){
            prodCat.setDepth(1);
            prodCat.setPath("[-1],");
        }else {
            prodCat.setDepth(2);
            prodCat.setPath("[-1],"+"["+prodCat.getParentId()+"],");
        }
        prodCat.setStatus(Const.ACTIVE);
        prodCat.setCreatetime(new Date());
        Boolean success = productCatService.saveProdCat(prodCat);
        if (success){
            result.put("code","200");
            result.put("message","插入成功！");
            return result;
        }
        result.put("code","500");
        result.put("message","插入失败！");
        return result;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,String> update(ProdCat prodCat){
        Map<String,String> result = new HashMap<>();
        prodCat.setRootId(prodCat.getParentId());
        if (prodCat.getParentId()==Const.UNKNOWN){
            prodCat.setDepth(1);
            prodCat.setPath("[-1],");
        }else {
            prodCat.setDepth(2);
            prodCat.setPath("[-1],"+"["+prodCat.getParentId()+"],");
        }
        prodCat.setStatus(Const.ACTIVE);
        prodCat.setCreatetime(new Date());
        Boolean success = productCatService.updateProdCat(prodCat);
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
    public Map<String,String> delete(ProdCat prodCat){
        Map<String,String> result = new HashMap<>();
        Boolean success = productCatService.deleteProdCat(prodCat);
        if (success){
            result.put("code","200");
            result.put("message","删除成功！");
            return result;
        }
        result.put("code","500");
        result.put("message","删除失败！");
        return result;
    }

    /**
     * 根据请求的父级设置path和Depth
     */
    private void setPath(ProdCat cat) {
        if (Const.UNKNOWN == cat.getParentId()) {
            cat.setDepth(1);
            cat.setPath("[-1],");
        } else {
            cat.setDepth(2);
            cat.setPath("[-1]," + "[" + cat.getParentId() + "],");
        }
    }

}
