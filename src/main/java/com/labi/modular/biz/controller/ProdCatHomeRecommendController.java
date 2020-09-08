package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.ProdCat;
import com.labi.modular.biz.service.IProdCatService;
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
@RequestMapping("/prodCatHomeRecommend")
public class ProdCatHomeRecommendController extends BaseController {

    private String PREFIX = "/biz/prodCatHomeRecommend/";

    @Autowired
    private IProdCatService prodCatService;

    /**
     * 跳转到商品分类管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prodCatHomeRecommend.html";
    }

    /**
     * 跳转到添加商品分类推荐
     */
    @RequestMapping("/prodCatHomeRecommend_add")
    public String prodCatAdd(Model model) {
        Wrapper<ProdCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        wrapper.eq("parent_id", Const.UNKNOWN);
        wrapper.eq("if_home_recommend", Const.FALSE);
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }
        //查询结果集
        List<ProdCat> cats = prodCatService.selectList(wrapper);
        model.addAttribute("cats", cats);
        return PREFIX + "prodCatHomeRecommend_add.html";
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
        wrapper.orderBy("createtime",false);

        //所属公司
        Integer depid = ShiroKit.getUser().getDeptId();
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }
        //父类id
        if (StringUtils.isNotBlank(catId)) {
            wrapper.eq("parent_id", catId);
        }
        //数据状态
        wrapper.eq("status", Const.ACTIVE);
        wrapper.eq("if_home_recommend", Const.TRUE);
        return super.packForBT(prodCatService.selectPage(page, wrapper));
    }

    /**
     * 跳转到修改商品分类管理
     */
    @RequestMapping("/prodCatHomeRecommend_update/{prodCatId}")
    public String prodCatUpdate(@PathVariable Integer prodCatId, Model model) {
        ProdCat prodCat = prodCatService.selectById(prodCatId);
        model.addAttribute("item", prodCat);

        Wrapper<ProdCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        wrapper.eq("parent_id", Const.UNKNOWN);
        wrapper.andNew("if_home_recommend={0} or id={1}", Const.FALSE, prodCatId);
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }
        //查询结果集
        List<ProdCat> cats = prodCatService.selectList(wrapper);
        model.addAttribute("cats", cats);

        LogObjectHolder.me().set(prodCat);
        return PREFIX + "prodCatHomeRecommend_edit.html";
    }

    /**
     * 新增/修改商品分类推荐
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProdCat prodCat) {
        prodCat.setIfHomeRecommend(Const.TRUE);
        prodCat.setOprtime(new Date());
        prodCatService.updateById(prodCat);
        return SUCCESS_TIP;
    }

    /**
     * 删除商品分类推荐
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer id) {
        ProdCat prodCat = new ProdCat();
        prodCat.setId(id);
        prodCat.setOprtime(new Date());
        prodCat.setIfHomeRecommend(Const.FALSE);
        prodCatService.updateById(prodCat);
        return SUCCESS_TIP;
    }
}
