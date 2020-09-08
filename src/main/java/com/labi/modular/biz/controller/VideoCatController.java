package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.Product;
import com.labi.modular.biz.model.VideoCat;
import com.labi.modular.biz.service.IProductService;
import com.labi.modular.biz.service.IVideoCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 视频分类管理控制器
 */
@Controller
@RequestMapping("/videoCat")
public class VideoCatController extends BaseController {

    private String PREFIX = "/biz/videoCat/";

    @Autowired
    private IVideoCatService videoCatService;//视频分类

    @Autowired
    private IProductService productService;//视频

    /**
     * 跳转到视频分类管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "videoCat.html";
    }

    /**
     * 跳转到添加视频分类管理
     */
    @RequestMapping("/videoCat_add")
    public String videoCatAdd() {
        return PREFIX + "videoCat_add.html";
    }

    /**
     * 跳转到修改视频分类管理
     */
    @RequestMapping("/videoCat_update/{videoCatId}")
    public String videoCatUpdate(@PathVariable Integer videoCatId, Model model) {
        VideoCat videoCat = videoCatService.selectById(videoCatId);
        model.addAttribute("item", videoCat);
        LogObjectHolder.me().set(videoCat);
        return PREFIX + "videoCat_edit.html";
    }

    /**
     * 获取视频分类管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        //分页类
        Page<VideoCat> page = new PageFactory<VideoCat>().defaultPage();
        Wrapper<VideoCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        wrapper.eq("status", Const.ACTIVE);//获取状态正常（A）的数据
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司

        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }

        if (condition != null && condition != "") {
            wrapper.like("name", condition);
        }
        return this.packForBT(videoCatService.selectMapsPage(page, wrapper));
    }

    /**
     * 新增视频分类管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(VideoCat videoCat) {
        videoCat.setType(ShiroKit.getUser().getDeptId());//所属公司
        videoCat.setCreatetime(new Date());//创建时间
        videoCatService.insert(videoCat);
        return SUCCESS_TIP;
    }

    /**
     * 删除视频分类管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer videoCatId) {

        //根据分类id查询分类下是否有数据
        Wrapper<Product> wrapper = new EntityWrapper<>();
        wrapper.eq("cat_id", videoCatId);
        wrapper.eq("if_video", Const.TRUE);
        int flg = productService.selectCount(wrapper);
        if (flg > 0) {
            //拒绝删除
            throw new BussinessException(BizExceptionEnum.LABI_SHOW_MSG);
        } else {
            VideoCat videoCat = new VideoCat();
            videoCat.setId(videoCatId);
            videoCat.setStatus(Const.DELETE);//修改删除状态
            videoCatService.updateById(videoCat);
        }
        return SUCCESS_TIP;
    }

    /**
     * 修改视频分类管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(VideoCat videoCat) {
        videoCatService.updateByCatId(videoCat);
        return SUCCESS_TIP;
    }

    /**
     * 视频分类管理详情
     */
    @RequestMapping(value = "/detail/{videoCatId}")
    @ResponseBody
    public Object detail(@PathVariable("videoCatId") Integer videoCatId) {
        return videoCatService.selectById(videoCatId);
    }
}
