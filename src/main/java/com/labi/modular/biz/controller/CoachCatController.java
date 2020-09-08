package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.Banner;
import com.labi.modular.biz.model.Coach;
import com.labi.modular.biz.model.CoachCat;
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
 * 教练分类管理控制器
 */
@Controller
@RequestMapping("/coachCat")
public class CoachCatController extends BaseController {

    private String PREFIX = "/biz/coachCat/";

    @Autowired
    private IBannerService bannerService;

    @Autowired
    private ICoachService coachService;//教练
    @Autowired
    private IProductService productService;//商品
    @Autowired
    private IProdVideoService prodVideoService;//在线视频
    @Autowired
    private ICourseCatService courseCatService;//课程分类
    @Autowired
    private ICourseService courseService;//课程
    @Autowired
    private ICoachCatService coachCatService;//教练分类

    /**
     * 跳转到教练分类管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "coachCat.html";
    }

    /**
     * 跳转到添加教练分类管理
     */
    @RequestMapping("/coachCat_add")
    public String coachCatAdd() {
        return PREFIX + "coachCat_add.html";
    }

    /**
     * 跳转到修改教练分类管理
     */
    @RequestMapping("/coachCat_update/{coachcatId}")
    public String coachCatUpdate(@PathVariable Integer coachcatId, Model model) {
        //教练分类详情
        CoachCat coachCat = coachCatService.selectById(coachcatId);
        model.addAttribute("item", coachCat);
        LogObjectHolder.me().set(coachCat);
        return PREFIX + "coachCat_edit.html";
    }

    /**
     * 获取教练分类管理列表
     *
     * @param condition 名称
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<CoachCat> page = new PageFactory<CoachCat>().defaultPage();
        //List<Map<String, Object>> result = bannerService.getBnnerList(page, "");

        Wrapper<CoachCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }

        //名称
        if (condition != null && condition != "") {
            wrapper.like("name", condition);
        }


        return super.packForBT(coachCatService.selectPage(page, wrapper));
//		page.setRecords(bannerService.getBnnerList(page, ""));
//		return ;
    }

    /**
     * 新增教练分类管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CoachCat coachCat) {

        //所属公司
        coachCat.setType(ShiroKit.getUser().getDeptId());
        //添加时间
        coachCat.setCreatetime(new Date());
        coachCatService.insert(coachCat);
        return SUCCESS_TIP;
    }

    /**
     * 删除教练分类管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer coachCatId) {
        coachCatService.deleteById(coachCatId);
        return SUCCESS_TIP;
    }

    /**
     * 修改教练分类管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CoachCat coachCat) {
        coachCat.setOprtime(new Date());//操作时间参数
        coachCatService.updateById(coachCat);

        Wrapper<Coach> wrapper = new EntityWrapper<>();
        wrapper.eq("coach_cat_id", coachCat.getId());
        Coach coach = new Coach();
        coach.setCoachCatName(coachCat.getName());
        coachService.update(coach, wrapper);
        return SUCCESS_TIP;
    }

    /**
     * 教练分类管理详情
     */
    @RequestMapping(value = "/detail/{bannerId}")
    @ResponseBody
    public Object detail(@PathVariable("bannerId") Integer bannerId) {
        Banner banner = bannerService.selectById(bannerId);
        String goto_info = "";
        if ("product".equals(banner.getType())) {
            //商品
            goto_info = banner.getGotoInfo() == null ? "" : productService.selectById(banner.getGotoInfo()).getName();
        } else if ("video".equals(banner.getType())) {
            //视频
            goto_info = banner.getGotoInfo() == null ? "" : prodVideoService.selectById(banner.getGotoInfo()).getName();
        } else if ("course".equals(banner.getType())) {
            //课程
            goto_info = banner.getGotoInfo() == null ? "" : courseService.selectById(banner.getGotoInfo()).getName();
        } else if ("coach".equals(banner.getType())) {
            goto_info = banner.getGotoInfo() == null ? "" : coachService.selectById(banner.getGotoInfo()).getName();
        }
        banner.setInfoDes(goto_info);
        return banner;
    }

}
