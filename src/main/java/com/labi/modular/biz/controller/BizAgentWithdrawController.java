package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.core.shiro.ShiroKit;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.dao.BizUserMapper;
import com.labi.modular.biz.model.BizAgent;
import com.labi.modular.biz.model.BizAgentWithdraw;
import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.service.IBizAgentService;
import com.labi.modular.biz.service.IBizAgentWithdrawService;
import com.labi.modular.biz.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 分销商提现管理控制器
 */
@Controller
@RequestMapping("/bizAgentWithdraw")
public class BizAgentWithdrawController extends BaseController {

    private String PREFIX = "/biz/bizAgentWithdraw/";

    @Autowired
    private IBizAgentWithdrawService bizAgentWithdrawService;

    @Autowired
    private IBizAgentService bizAgentService;


    /**
     * 跳转到分销商提现管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "bizAgentWithdraw.html";
    }

    /**
     * 跳转到同意页面
     */
    @RequestMapping("/tosuccess")
    public String success() {
        return PREFIX + "bizAgentWithdraw_success.html";
    }

    /**
     * 跳转到拒绝页面
     */
    @RequestMapping("/torefuse")
    public String refuse() {
        return PREFIX + "bizAgentWithdraw_refuse.html";
    }


    /**
     * 获取分销商提现管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String progress) {
        Page<BizAgentWithdraw> page = new PageFactory<BizAgentWithdraw>().defaultPage();

        Wrapper<BizAgentWithdraw> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        //所属公司
        Integer depid = ShiroKit.getUser().getDeptId();
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }

        if (StringUtils.isNotBlank(condition)) {
            wrapper.where("instr(name,{0})>0 or instr(phone_num,{1})>0", condition, condition);
        }
        if (StringUtils.isNotBlank(progress)) {
            wrapper.andNew("progress={0}", progress);
        }
        return super.packForBT(bizAgentWithdrawService.selectPage(page, wrapper));
    }

    /**
     * 提现同意
     */
    @RequestMapping(value = "/success")
    @ResponseBody
    public Object success(BizAgentWithdraw withdraw) {
        BizAgentWithdraw old = bizAgentWithdrawService.selectById(withdraw.getId());
        if (!Const.WAIT.equals(old.getProgress()))
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        withdraw.setProgress(Const.SUCCESS);
        withdraw.setOprtime(new Date());
        bizAgentWithdrawService.updateById(withdraw);

        BizAgent agent = bizAgentService.selectById(old.getAgentId());
        SMSUtil.sendWithdrawSuccessMsg(old.getPhoneNum(), agent.getAgentName(),old.getTotalAmount());
        return SUCCESS_TIP;
    }

    /**
     * 提现拒绝
     */
    @RequestMapping(value = "/fail")
    @ResponseBody
    public Object fail(BizAgentWithdraw withdraw) {
        bizAgentWithdrawService.failWithdraw(withdraw);
        return SUCCESS_TIP;
    }
}
