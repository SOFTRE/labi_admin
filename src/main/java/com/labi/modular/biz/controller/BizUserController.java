package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.service.IBizUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户管理控制器
 */
@Controller
@RequestMapping("/user")
public class BizUserController extends BaseController {

    private String PREFIX = "/biz/user/";

    @Autowired
    private IBizUserService userService;

    /**
     * 跳转到用户管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到分销商添加用户选择页
     */
    @RequestMapping("/choose")
    public String chooseUser() {
        return PREFIX + "chooseUser.html";
    }

    /**
     * 跳转到教练添加用户选择页
     */
    @RequestMapping("/choosecoach")
    public String chooseCoachUser() {
        return "/biz/coach/" + "chooseUser.html";
    }

    /**
     * 跳转到会员详情
     */
    @RequestMapping("/userdtl")
    public String userUpdate() {
        return PREFIX + "userdtl.html";
    }
    /**
     * 跳转到添加用户管理
     */
    @RequestMapping("/user_add")
    public String userAdd() {
        return PREFIX + "user_add.html";
    }
    /**
     * 获取用户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String agentStatus, String ifCoach) {
        Page<BizUser> page = new PageFactory<BizUser>().defaultPage();
        Wrapper<BizUser> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        //名称
        if (StringUtils.isNotBlank(condition)) {
            wrapper.where("instr(name,{0})>0 or instr(phone_num,{1})>0", condition, condition);
        }
        if (StringUtils.isNotBlank(agentStatus)) {
            wrapper.andNew("agent_status={0}", agentStatus);
        }
        if (StringUtils.isNotBlank(ifCoach)) {
            wrapper.andNew("if_coach={0}", ifCoach);
        }
        return super.packForBT(userService.selectPage(page, wrapper));
    }

}
