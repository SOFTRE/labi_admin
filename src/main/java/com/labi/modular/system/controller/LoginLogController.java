package com.labi.modular.system.controller;

import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.annotion.Permission;
import com.labi.common.annotion.log.BussinessLog;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.system.dao.DeptMapper;
import com.labi.modular.system.dao.LoginLogMapper;
import com.labi.modular.system.model.LoginLog;
import com.labi.modular.system.model.OperationLog;
import com.labi.modular.system.warpper.LogWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 日志管理的控制器
 *
 * @author lyr
 * @Date 2017年4月5日 19:45:36
 */
@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {

    private static String PREFIX = "/system/log/";

    @Resource
    private LoginLogMapper logMapper;

    @Resource
    private DeptMapper deptMapper;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("depts", deptMapper.selectList(null));
        return PREFIX + "login_log.html";
    }

    /**
     * 查询登录日志列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName,@RequestParam(required = false) Integer deptId) {
        Page<LoginLog> page = new PageFactory<LoginLog>().defaultPage();
        Integer deptid = ShiroKit.getUser().getDeptId();//所属公司
        if(deptid==Const.IS_ADMIN_FLG) {
            deptid = deptId;
        }
        List<Map<String, Object>> result = logMapper.getLoginLogs(page, beginTime, endTime, logName,deptid, page.getOrderByField(), page.isAsc());
        page.setRecords((List<LoginLog>) new LogWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 清空日志
     */
    @BussinessLog("清空登录日志")
    @RequestMapping("/delLoginLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from sys_login_log");
        return super.SUCCESS_TIP;
    }
}
