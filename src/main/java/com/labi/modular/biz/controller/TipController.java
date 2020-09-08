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
import com.labi.modular.biz.model.Product;
import com.labi.modular.biz.model.Tip;
import com.labi.modular.biz.service.ITipService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 提示管理控制器
 */
@Controller
@RequestMapping("/tip")
public class TipController extends BaseController {

    private String TIP_FILE_PATH = "tipFile";//详情存储文件

    private String PREFIX = "/biz/tip/";

    @Autowired
    private ITipService tipService;

    /**
     * 跳转到提示管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tip.html";
    }
    /**
     * 获取提示管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Tip> page = new PageFactory<Tip>().defaultPage();
        Wrapper<Tip> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);

        //课程名称
        if (condition != null && condition != "") {
            wrapper.where("instr(title,{0})>0", condition);
        }
        return super.packForBT(tipService.selectMapsPage(page, wrapper));
    }
    /**
     * 跳转到修改提示管理
     */
    @RequestMapping("/tip_update/{tipId}")
    public String tipUpdate(@PathVariable Integer tipId, Model model) {
        //查询提示
        Tip tip = tipService.selectById(tipId);
        model.addAttribute("item", tip);
        LogObjectHolder.me().set(tip);
        return PREFIX + "tip_edit.html";
    }


    /**
     * 跳转到修改提示管理
     */
    @RequestMapping("/tip_edit/{tipId}")
    public String tipTipAdd(@PathVariable Integer tipId, Model model) {
        //查询在线视频
        Tip tip = SpringContextHolder.getBean(ITipService.class).selectById(tipId);
        model.addAttribute("item", tip);
        LogObjectHolder.me().set(tip);
        return PREFIX + "tip_edit.html";
    }


    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Tip tip, String info, HttpServletRequest request) throws Exception {
        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, TIP_FILE_PATH, request);
            //属性设置
            tip.setDesFile(TIP_FILE_PATH + "/" + desFile + ".html");
        }
        tip.setOprtime(new Date());
        tipService.updateById(tip);
        return SUCCESS_TIP;
    }

}
