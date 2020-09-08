package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.log.LogObjectHolder;
import com.labi.modular.biz.model.*;
import com.labi.modular.biz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 热推商品管理控制器
 */
@Controller
@RequestMapping("/productHot")
public class ProductHotController extends BaseController {

    private String PRODUCT_FILE_PATH = "productFile";//详情存储文件

    private String PREFIX = "/biz/product/hot/";

    @Autowired
    private IProductService productService;//商品
    @Autowired
    private IProductHotService productHotService;//热推商品
    @Autowired
    private ICourseService courseService;//课程
    @Autowired
    private INewsService newsService;
    @Autowired
    private IShowService showService;//拉比展示
    @Autowired
    private ICoachService coachService;//教练

    /**
     * 跳转到商品管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "product_hot.html";
    }

    /**
     * 热推商品选择
     */
    @RequestMapping("/productHot_add")
    public String productAdd(Model model) {
        return PREFIX + "chooseProduct.html";
    }

    /**
     * 商品添加
     *
     * @param model
     * @return
     */
    @RequestMapping("/hot_add")
    public String hotAdd(Model model) {
        return PREFIX + "product_hot_add.html";
    }

    /**
     * 跳转至热推商品选择页面
     *
     * @return
     */
    @RequestMapping("/chooseProduct")
    public String chooseCoachUser() {
        return PREFIX + "chooseProduct.html";
    }

    /**
     * 获取热推商品管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String productType) {

        Page<ProductHot> page = new PageFactory<ProductHot>().defaultPage();
        Wrapper<ProductHot> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        //名称
        if (condition != null && condition != "") {
            wrapper.like("name", condition);
        }
        if (productType != null && productType != "") {
            wrapper.eq("product_type", productType);
        }
        return super.packForBT(productHotService.selectPage(page, wrapper));
    }

    /***
     * 新增热门推荐
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProductHot hot) throws Exception {

        //添加之前判断该商品是否已存在
        Wrapper<ProductHot> wrapper = new EntityWrapper<>();
        wrapper.eq("product_id", hot.getProductId());
        wrapper.eq("product_type", hot.getProductType());
        Integer flg = productHotService.selectCount(wrapper);
        //教练(coach) 商品(product) 课程(course) 视频课程(video)
        if ("coach".equalsIgnoreCase(hot.getProductType())) {
            Coach coach = coachService.selectById(hot.getProductId());
            hot.setDes(coach.getDes());
            hot.setImg(coach.getImg());
        }
        if ("product".equalsIgnoreCase(hot.getProductType()) || "video".equalsIgnoreCase(hot.getProductType())) {
            Product product = productService.selectById(hot.getProductId());
            hot.setDes(product.getDes());
            hot.setImg(product.getThumbImg());
        }
        if ("course".equalsIgnoreCase(hot.getProductType())) {
            Course course = courseService.selectById(hot.getProductId());
            hot.setDes(course.getDes());
            hot.setImg(course.getImg());
        }
        if ("news".equalsIgnoreCase(hot.getProductType())) {
            News news = newsService.selectById(hot.getProductId());
            hot.setImg(news.getImg());
        }
        if (flg > 0) {
            throw new BussinessException(BizExceptionEnum.PRODUCT_HOT_ADD_EOORO);
        } else {
            hot.setCreatetime(new Date());
            productHotService.insert(hot);
        }
        return SUCCESS_TIP;
    }

    /**
     * @param Keyword     搜索关键字
     * @param productType 所属模块
     * @return
     */
    @RequestMapping(value = "/geturl")
    @ResponseBody
    public Object geturl(String Keyword, String productType) {

        if (productType.equals("coach")) {
            //教练
            Wrapper<Coach> wrapper = new EntityWrapper<>();
            if (Keyword != null && Keyword != "") {
                wrapper.eq("name", Keyword);
            }
            return coachService.selectList(wrapper);
        } else if (productType.equals("product")) {
            //商品
            Wrapper<Product> wrapper = new EntityWrapper<>();
            if (Keyword != null && Keyword != "") {
                wrapper.eq("name", Keyword);
            }
            return productService.selectList(wrapper);
        } else if (productType.equals("video")) {
            //视频
            Wrapper<Product> wrapper = new EntityWrapper<>();
            if (Keyword != null && Keyword != "") {
                wrapper.eq("name", Keyword);
            }
            return productService.selectList(wrapper);
        } else if (productType.equals("course")) {
            //课程
            Wrapper<Course> wrapper = new EntityWrapper<>();
            if (Keyword != null && Keyword != "") {
                wrapper.eq("name", Keyword);
            }
            return courseService.selectList(wrapper);
        } else if (productType.equals("show")) {
            //拉比展示
            Wrapper<Show> wrapper = new EntityWrapper<>();
            if (Keyword != null && Keyword != "") {
                wrapper.eq("title", Keyword);
            }
            return showService.selectList(wrapper);
        } else if (productType.equals("news")) {
            //拉比展示
            Wrapper<News> wrapper = new EntityWrapper<>();
            if (Keyword != null && Keyword != "") {
                wrapper.eq("title", Keyword);
            }
            return newsService.selectList(wrapper);
        }
        return "";
    }

    /**
     * 跳转到修改商品管理
     */
    @RequestMapping("/productHot_update/{productId}")
    public String productUpdate(@PathVariable Integer productId, Model model) {

        ProductHot product = productHotService.selectById(productId);
        model.addAttribute("item", product);
        LogObjectHolder.me().set(product);
        return PREFIX + "product_hot_edit.html";
    }


    /**
     * 删除热推商品
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String ids) throws Exception {
        String[] idstr = ids.split(",");
        for (String str : idstr) {
            System.out.println(str);
            productHotService.deleteById(Integer.valueOf(str));
        }
        return SUCCESS_TIP;
    }


    /**
     * 修改商品管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProductHot hot) throws Exception {
        //教练(coach) 商品(product) 课程(course) 视频课程(video)
        if ("coach".equalsIgnoreCase(hot.getProductType())) {
            Coach coach = coachService.selectById(hot.getProductId());
            hot.setDes(coach.getDes());
            hot.setImg(coach.getImg());
        }
        if ("product".equalsIgnoreCase(hot.getProductType()) || "video".equalsIgnoreCase(hot.getProductType())) {
            Product product = productService.selectById(hot.getProductId());
            hot.setDes(product.getDes());
            hot.setImg(product.getThumbImg());
        }
        if ("course".equalsIgnoreCase(hot.getProductType())) {
            Course course = courseService.selectById(hot.getProductId());
            hot.setDes(course.getDes());
            hot.setImg(course.getImg());
        }
        if ("news".equalsIgnoreCase(hot.getProductType())) {
            News news = newsService.selectById(hot.getProductId());
            hot.setImg(news.getImg());
        }
        productHotService.updateById(hot);
        return SUCCESS_TIP;
    }
}
