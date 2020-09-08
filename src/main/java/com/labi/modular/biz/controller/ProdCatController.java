package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.log.LogObjectHolder;
import com.labi.modular.biz.model.ProdCat;
import com.labi.modular.biz.model.Product;
import com.labi.modular.biz.service.IProdCatService;
import com.labi.modular.biz.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 商品分类管理控制器
 */
@Controller
@RequestMapping("/prodCat")
public class ProdCatController extends BaseController {

    private String PREFIX = "/biz/prodCat/";

    @Autowired
    private IProdCatService prodCatService;

    @Autowired
    private IProductService productService;

    /**
     * 跳转到商品分类管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prodCat.html";
    }

    /**
     * 跳转到添加商品分类管理
     */
    @RequestMapping("/prodCat_add")
    public String prodCatAdd(Model model) {
        Wrapper<ProdCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        wrapper.eq("parent_id", Const.UNKNOWN);
        //查询结果集
        List<ProdCat> cats = prodCatService.selectList(wrapper);
        model.addAttribute("cats", cats);
        return PREFIX + "prodCat_add.html";
    }

    /**
     * 获取商品分类管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String catId) {
        Page<ProdCat> page = new Page<>((1), 1000);
        page.setOpenSort(false);
        Wrapper<ProdCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);

        //父类id
        if (StringUtils.isNotBlank(catId)) {
            wrapper.eq("parent_id", catId);
        }

        //数据状态
        wrapper.eq("status", Const.ACTIVE);
        return prodCatService.selectPage(page, wrapper);
    }

    /**
     * 新增商品分类管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProdCat prodCat) throws Exception {
        prodCat.setRootId(prodCat.getParentId());
        setPath(prodCat);
        prodCat.setStatus(Const.ACTIVE);//数据状态
        prodCat.setCreatetime(new Date());//添加时间
        prodCatService.insert(prodCat);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改商品分类管理
     */
    @RequestMapping("/prodCat_update/{prodCatId}")
    public String prodCatUpdate(@PathVariable Integer prodCatId, Model model) {
        ProdCat prodCat = prodCatService.selectById(prodCatId);
        model.addAttribute("item", prodCat);

        Wrapper<ProdCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        wrapper.eq("parent_id", Const.UNKNOWN);
        //查询结果集
        List<ProdCat> cats = prodCatService.selectList(wrapper);
        model.addAttribute("cats", cats);

        LogObjectHolder.me().set(prodCat);
        return PREFIX + "prodCat_edit.html";
    }

    /**
     * 修改商品分类管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProdCat prodCat) {
        prodCat.setRootId(prodCat.getParentId());
        setPath(prodCat);
        prodCatService.updateByCatId(prodCat);
        return SUCCESS_TIP;
    }

    /**
     * 删除商品分类管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer id) {
        //根据分类id查询分类下是否有数据
        Wrapper<Product> wrapper = new EntityWrapper<>();
        wrapper.eq("cat_id", id);
        wrapper.eq("if_video", Const.FALSE);
        int flg = productService.selectCount(wrapper);
        if (flg > 0) {
            //拒绝删除
            throw new BussinessException(BizExceptionEnum.LABI_SHOW_MSG);
        } else {
            ProdCat prodCat = new ProdCat();
            prodCat.setId(id);
            prodCat.setStatus(Const.DELETE);//修改删除状态
            prodCat.setOprtime(new Date());
            prodCatService.updateById(prodCat);
        }
        return SUCCESS_TIP;
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
