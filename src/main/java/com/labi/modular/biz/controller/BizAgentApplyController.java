package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.BizAgent;
import com.labi.modular.biz.service.IBizAgentService;
import com.labi.modular.biz.warpper.BizAgentWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 分销商管理控制器
 */
@Controller
@RequestMapping("/bizAgentApply")
public class BizAgentApplyController extends BaseController {

    private String PREFIX = "/biz/bizAgentApply/";

    @Autowired
    private IBizAgentService bizAgentService;

    /**
     * 跳转到分销商管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "bizAgentApply.html";
    }

    /**
     * 跳转到拒绝页面
     */
    @RequestMapping("/refuse")
    public String bizAgentApplyRefuse() {
        return PREFIX + "bizAgent_Apply_refuse.html";
    }

    /**
     * 获取分销商管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String progress, String beginDate, String endDate) {

        Page<BizAgent> page = new PageFactory<BizAgent>().defaultPage();
        List<Map<String, Object>> result = bizAgentService.selectAllByPage(page, progress, beginDate, endDate, condition);
        page.setRecords((List<BizAgent>) new BizAgentWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 分销商申请同意
     */
    @RequestMapping(value = "/success")
    @ResponseBody
    public Object success(Integer agentId) {
        bizAgentService.updateAgentApplySuccess(agentId);
        return SUCCESS_TIP;
    }

    /**
     * 分销商申请拒绝
     */
    @RequestMapping(value = "/fail")
    @ResponseBody
    public Object fail(Integer agentId, String remarks) {
        bizAgentService.updateAgentApplyFail(agentId, remarks);
        return SUCCESS_TIP;
    }
}
