package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.core.util.DateUtil;
import com.labi.core.util.HtmlUtils;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.model.ProdAttr;
import com.labi.modular.biz.model.ProdCat;
import com.labi.modular.biz.model.Product;
import com.labi.modular.biz.service.IProdAttrService;
import com.labi.modular.biz.service.IProdCatService;
import com.labi.modular.biz.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品管理控制器
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    private String PRODUCT_FILE_PATH = "productFile";//详情存储文件

    private String PREFIX = "/biz/product/";

    @Autowired
    private IProductService productService;

    @Autowired
    private IProdCatService prodCatService;

    /**
     * 跳转到商品管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        Wrapper<ProdCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("seq_num", true);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        wrapper.eq("parent_id", Const.UNKNOWN);
        //查询结果集
        List<ProdCat> cats = prodCatService.selectList(wrapper);
        model.addAttribute("cats", cats);
        return PREFIX + "product.html";
    }

    /**
     * 跳转到添加商品管理
     */
    @RequestMapping("/product_add")
    public String productAdd(Model model) {
        Wrapper<ProdCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);
        wrapper.eq("parent_id", Const.UNKNOWN);
        //查询结果集
        List<ProdCat> cats = SpringContextHolder.getBean(IProdCatService.class).selectList(wrapper);
        model.addAttribute("cats", cats);
        return PREFIX + "product_add.html";
    }

    /**
     * 跳转到添加商品SKU管理
     */
    @RequestMapping("/skus")
    public String productSku() {
        return PREFIX + "product_sku.html";
    }

    /**
     * 跳转到修改商品管理
     */
    @RequestMapping("/product_update/{productId}")
    public String productUpdate(@PathVariable Integer productId, Model model) {
        Wrapper<ProdCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);
        wrapper.eq("parent_id", Const.UNKNOWN);
        //查询一级分类
        List<ProdCat> cats = SpringContextHolder.getBean(IProdCatService.class).selectList(wrapper);
        model.addAttribute("cats", cats);
        //查询商品
        Product product = productService.selectById(productId);

        //查询子分类
        ProdCat cat = SpringContextHolder.getBean(IProdCatService.class).selectById(product.getCatId());
        //如果是一级分类
        if (cat.getParentId() == -1) {
            model.addAttribute("catParentId", cat.getId());

            Wrapper<ProdCat> wrapperSub = new EntityWrapper<>();
            wrapper.orderBy("createtime", false);
            wrapperSub.eq("status", Const.ACTIVE);
            wrapperSub.eq("parent_id", cat.getParentId());
            List<ProdCat> subCats = SpringContextHolder.getBean(IProdCatService.class).selectList(wrapperSub);
            model.addAttribute("subCats", subCats);
            model.addAttribute("subCats", new ArrayList<>());
            product.setCatId(-1);
        } else {
            Wrapper<ProdCat> wrapperSub = new EntityWrapper<>();
            wrapper.orderBy("createtime", false);
            wrapperSub.eq("status", Const.ACTIVE);
            wrapperSub.eq("parent_id", cat.getParentId());
            List<ProdCat> subCats = SpringContextHolder.getBean(IProdCatService.class).selectList(wrapperSub);
            model.addAttribute("subCats", subCats);
            model.addAttribute("catParentId", cat.getParentId());
        }
        model.addAttribute("item", product);
        LogObjectHolder.me().set(product);
        return PREFIX + "product_edit.html";
    }

    /**
     * 根据商品ID查询商品属性
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "attrs", method = RequestMethod.POST)
    @ResponseBody
    public List<ProdAttr> loadGoodsAttrList(Integer productId) {
        Wrapper<ProdAttr> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);

        wrapper.eq("status", Const.ACTIVE);
        wrapper.eq("prod_id", productId);
        return SpringContextHolder.getBean(IProdAttrService.class).selectList(wrapper);
    }

    /**
     * 获取商品管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String cartIds, String ifRecommend) {
        Page<Product> page = new PageFactory<Product>().defaultPage(); // ?
        Wrapper<Product> wrapper = new EntityWrapper<>(); //好像是映射类，用于简单的sql操作
        if(StringUtils.isBlank(page.getOrderByField())){
            wrapper.orderBy("createtime", false); //按照时间降序排序
        }

        Integer depid = ShiroKit.getUser().getDeptId();// 所属公司 Security 中的 ThreadContext.getSubject();
        if (depid.intValue() != Const.IS_ADMIN_FLG) { // 如果user的部门id不是管理员，
            wrapper.eq("type", depid);
        }
        if (StringUtils.isNotBlank(cartIds)) {
            cartIds = ",".concat(cartIds).concat(",");
            wrapper.and("instr({0},concat(',',cat_id,','))>0", cartIds);
        }
        if (StringUtils.isNotBlank(ifRecommend)) {
            wrapper.eq("if_recommend ", ifRecommend);
        }
        if (condition != null && condition != "") {
            wrapper.and("instr(name,{0})>0", condition);
        }
        wrapper.eq("status", Const.ACTIVE);
        wrapper.eq("if_video", Const.FALSE);
        return super.packForBT(productService.selectMapsPage(page, wrapper));
    }

    /**
     * 新增商品管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Product product, String attrValueArrStr, String info, HttpServletRequest request) throws Exception {
        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, PRODUCT_FILE_PATH, request);
            //属性设置
            product.setDesFile(PRODUCT_FILE_PATH + "/" + desFile + ".html");
        }
        productService.insertProduct(product, attrValueArrStr);
        return SUCCESS_TIP;
    }

    /**
     * 删除商品管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String ids) throws Exception {
        productService.deleteBatch(ids);
        return SUCCESS_TIP;
    }

    /**
     * 上下架商品管理
     */
    @RequestMapping(value = "/shelf")
    @ResponseBody
    public Object shelf(@RequestParam Integer productId, String shelf) {
        Product product = new Product();
        product.setId(productId);
        product.setOprtime(new Date());
        product.setIfShelf(shelf);
        productService.updateById(product);
        return SUCCESS_TIP;
    }
    /**
     * 是否推荐
     */
    @RequestMapping(value = "/recommend")
    @ResponseBody
    public Object recommend(@RequestParam Integer productId, String ifRecommend) {
        Product product = new Product();
        product.setId(productId);
        product.setOprtime(new Date());
        product.setIfRecommend(ifRecommend);
        productService.updateById(product);
        return SUCCESS_TIP;
    }

    /**
     * 修改商品管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Product product, String attrValueArrStr, String info, HttpServletRequest request) throws Exception {
        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, PRODUCT_FILE_PATH, request);
            //属性设置
            product.setDesFile(PRODUCT_FILE_PATH + "/" + desFile + ".html");
        }
        productService.updateProduct(product, attrValueArrStr);
        return SUCCESS_TIP;
    }
}
