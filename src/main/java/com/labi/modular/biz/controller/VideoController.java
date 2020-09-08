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
import com.labi.modular.biz.model.ProdVideo;
import com.labi.modular.biz.model.Product;
import com.labi.modular.biz.model.VideoCat;
import com.labi.modular.biz.service.IProdVideoService;
import com.labi.modular.biz.service.IProductService;
import com.labi.modular.biz.service.IVideoCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 在线课程管理控制器
 */
@Controller
@RequestMapping("/video")
public class VideoController extends BaseController {

    private String PRODUCT_FILE_PATH = "productFile";//详情存储文件

    private String PREFIX = "/biz/video/";

    @Autowired
    private IProductService productService;


    @Autowired
    private IVideoCatService videoCatService;

    /**
     * 跳转到在线课程管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        Wrapper<VideoCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("seq_num", true);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        //查询结果集
        List<VideoCat> cats = videoCatService.selectList(wrapper);
        model.addAttribute("cats", cats);
        return PREFIX + "video.html";
    }

    /**
     * 跳转到添加在线课程管理
     */
    @RequestMapping("/video_add")
    public String productAdd(Model model) {
        Wrapper<VideoCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);
        List<VideoCat> cats = SpringContextHolder.getBean(IVideoCatService.class).selectList(wrapper);
        model.addAttribute("cats", cats);
        return PREFIX + "video_add.html";
    }

    /**
     * 跳转到修改在线课程管理
     */
    @RequestMapping("/video_update/{productId}")
    public String productUpdate(@PathVariable Integer productId, Model model) {
        Wrapper<VideoCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);
        List<VideoCat> cats = SpringContextHolder.getBean(IVideoCatService.class).selectList(wrapper);
        model.addAttribute("cats", cats);
        //查询在线课程
        Product product = productService.selectById(productId);
        model.addAttribute("item", product);
        LogObjectHolder.me().set(product);
        return PREFIX + "video_edit.html";
    }

    /**
     * 跳转到添加在线课程管理
     */
    @RequestMapping("/prod_video_add")
    public String productVideoEdit() {
        return PREFIX + "prod_video_add.html";
    }

    /**
     * 跳转到修改在线课程管理
     */
    @RequestMapping("/prod_video_edit/{videoId}")
    public String productVideoAdd(@PathVariable Integer videoId, Model model) {
        //查询在线视频
        ProdVideo vodeo = SpringContextHolder.getBean(IProdVideoService.class).selectById(videoId);
        model.addAttribute("item", vodeo);
        LogObjectHolder.me().set(vodeo);
        return PREFIX + "prod_video_edit.html";
    }

    /**
     * 新增在线课程管理
     */
    @RequestMapping(value = "/video/add")
    @ResponseBody
    public Object addVideo(ProdVideo video) throws Exception {
        video.setCreatetime(new Date());
        video.setStatus(Const.ACTIVE);
        SpringContextHolder.getBean(IProdVideoService.class).insert(video);
        return SUCCESS_TIP;
    }

    /**
     * 新增在线课程管理
     */
    @RequestMapping(value = "/video/update")
    @ResponseBody
    public Object editVideo(ProdVideo video) throws Exception {
        video.setOprtime(new Date());
        SpringContextHolder.getBean(IProdVideoService.class).updateById(video);
        return SUCCESS_TIP;
    }

    /**
     * 删除商品管理
     */
    @RequestMapping(value = "/video/delete")
    @ResponseBody
    public Object delete(Integer videoId) {
        ProdVideo video = new ProdVideo();
        video.setId(videoId);
        video.setOprtime(new Date());
        video.setStatus(Const.DELETE);
        SpringContextHolder.getBean(IProdVideoService.class).updateById(video);
        return SUCCESS_TIP;
    }

    /**
     * 获取在线课程管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer catId, String ifRecommend) {
        Page<Product> page = new PageFactory<Product>().defaultPage();
        Wrapper<Product> wrapper = new EntityWrapper<>();
        if (StringUtils.isBlank(page.getOrderByField())) {
            wrapper.orderBy("createtime", false);
        }
        //所属公司
        Integer depid = ShiroKit.getUser().getDeptId();
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }
        if (catId != null) {
            wrapper.eq("cat_id", catId);
        }
        if (StringUtils.isNotBlank(ifRecommend)) {
            wrapper.eq("if_recommend", ifRecommend);
        }
        //课程名称
        if (condition != null && condition != "") {
            wrapper.where("instr(name,{0})>0", condition);
        }
        wrapper.eq("status", Const.ACTIVE);//数据状态
        wrapper.eq("if_video", Const.TRUE);//视频
        return super.packForBT(productService.selectMapsPage(page, wrapper));
    }

    /**
     * 获取在线课程管理列表
     */
    @RequestMapping(value = "/videos")
    @ResponseBody
    public Object videoList(Integer productId, String condition) {
        if (productId == null) {
            return null;
        }
        Page<ProdVideo> page = new PageFactory<ProdVideo>().defaultPage();
        Wrapper<ProdVideo> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        if (condition != null && condition != "") {
            wrapper.where("instr(name,{0})>0", condition);
        }
        wrapper.eq("prod_id", productId);
        wrapper.eq("status", Const.ACTIVE);
        return super.packForBT(SpringContextHolder.getBean(IProdVideoService.class).selectMapsPage(page, wrapper));
    }

    /**
     * 新增在线课程管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Product product, String info, HttpServletRequest request) throws Exception {
        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, PRODUCT_FILE_PATH, request);
            //属性设置
            product.setDesFile(PRODUCT_FILE_PATH + "/" + desFile + ".html");
        }
        product.setSaleNum(0);
        product.setStatus(Const.ACTIVE);
        product.setIfRecommend(Const.FALSE);
        product.setCreatetime(new Date());
        product.setIfVideo(Const.TRUE);
        product.setType(ShiroKit.getUser().getDeptId());//所属公司
        productService.insert(product);
        return SUCCESS_TIP;
    }

    /**
     * 修改在线课程管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Product product, String info, HttpServletRequest request) throws Exception {
        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, PRODUCT_FILE_PATH, request);
            //属性设置
            product.setDesFile(PRODUCT_FILE_PATH + "/" + desFile + ".html");
        }
        product.setOprtime(new Date());
        productService.updateById(product);
        return SUCCESS_TIP;
    }

}
