package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.labi.common.constant.Const;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.model.ProdCat;
import com.labi.modular.biz.model.ProdCatAttr;
import com.labi.modular.biz.model.ProdCatAttrOption;
import com.labi.modular.biz.service.IProdCatAttrOptionService;
import com.labi.modular.biz.service.IProdCatAttrService;
import com.labi.modular.biz.service.IProdCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 商品分类属性属性管理控制器
 *
 */
@Controller
@RequestMapping("/prodCatAttr")
public class ProdCatAttrController extends BaseController {

    private String PREFIX = "/biz/prodCatAttr/";

    @Autowired
    private IProdCatAttrService prodCatAttrService;
    /**
     * 跳转到添加商品分类属性管理
     */
    @RequestMapping(value = "/add",method =RequestMethod.GET)
    public String prodCatAdd() {
        return PREFIX + "prodCatAttr_add.html";
    }
    /**
     * 跳转到修改商品分类属性管理
     */
    @RequestMapping(value = "/update/{attrId}",method =RequestMethod.GET)
    public String prodCatUpdate(@PathVariable Integer attrId, Model model) {
        ProdCatAttr attr = prodCatAttrService.selectById(attrId);
        model.addAttribute("item", attr);
        LogObjectHolder.me().set(attr);
        return PREFIX + "prodCatAttr_edit.html";
    }
    /**
     /**
     * 根据商品分类属性id查询属性
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object attrs(Integer catId) {
        Wrapper<ProdCatAttr> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        wrapper.eq("status", Const.ACTIVE);
        wrapper.eq("cat_id", catId);
        return prodCatAttrService.selectList(wrapper);
    }

    /**
     * 根据子级分类ID查询所有属性和属性值
     * @param catId
     * @return
     */
    @RequestMapping(value="attrs", method = RequestMethod.POST)
    @ResponseBody
    public List<ProdCatAttr> loadAttrAndOption(Integer catId){
        ProdCat cat = SpringContextHolder.getBean(IProdCatService.class).selectById(catId);
        Wrapper<ProdCatAttr> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        wrapper.where("(cat_id={0} or cat_id={1} )", catId,cat.getParentId());
        wrapper.eq("status", Const.ACTIVE);
        List<ProdCatAttr> attrs = prodCatAttrService.selectList(wrapper);
        for(ProdCatAttr attr : attrs){
            Wrapper<ProdCatAttrOption> wrapperOption = new EntityWrapper<>();
            wrapper.orderBy("createtime",false);
            wrapperOption.eq("status", Const.ACTIVE);
            wrapperOption.eq("attr_id", attr.getId());
            attr.setOptions(SpringContextHolder.getBean(IProdCatAttrOptionService.class).selectList(wrapperOption));
        }
        return attrs;
    }
    /**
     * 新增商品分类属性属性管理
     */
    @RequestMapping(value = "/add",method =RequestMethod.POST)
    @ResponseBody
    public Object add(ProdCatAttr prodCatAttr) {
    	
        prodCatAttr.setCreatetime(new Date());
        prodCatAttr.setStatus(Const.ACTIVE);
        prodCatAttrService.insert(prodCatAttr);
        return SUCCESS_TIP;
    }

    /**
     * 删除商品分类属性属性管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(ProdCatAttr prodCatAttr) {
        prodCatAttr.setOprtime(new Date());
        prodCatAttr.setStatus(Const.DELETE);
        prodCatAttrService.updateById(prodCatAttr);
        return SUCCESS_TIP;
    }

    /**
     * 修改商品分类属性属性管理
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(ProdCatAttr prodCatAttr) {
        prodCatAttr.setOprtime(new Date());
        prodCatAttrService.updateById(prodCatAttr);
        return SUCCESS_TIP;
    }
}
