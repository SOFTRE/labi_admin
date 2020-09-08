package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.annotion.Permission;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.dao.OrderPayMapper;
import com.labi.modular.biz.dao.OrderPayReportMapper;
import com.labi.modular.biz.model.OrderPay;
import com.labi.modular.biz.warpper.OrderPayWarpper;
import com.labi.modular.system.dao.DeptMapper;
import com.labi.modular.system.model.Dept;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 报表管理的控制器
 *
 * @author lyr
 * @Date 2017年4月5日 19:45:36
 */
@Controller
@RequestMapping("/reportdtl")
public class OrderPayReportDelController extends BaseController {

    private static String PREFIX = "/biz/report/";

    @Resource
    private OrderPayReportMapper reportMapper;

    @Resource
    private OrderPayMapper orderPayMapper;

    @Resource
    private DeptMapper deptMapper;

    /**
     * 跳转到报表管理的首页
     */
    @RequestMapping("")
    public String index(Model model) {
        List<Dept> depts = deptMapper.selectList(null);
        Dept dept = new Dept();
        dept.setSimplename("总公司");
        dept.setId(0);
        depts.add(0, dept);
        model.addAttribute("depts", depts);
        return PREFIX + "orderPayDtl.html";
    }

    /**
     * 查询报表明细列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginDate, @RequestParam(required = false) String endDate, @RequestParam(required = false) String businessType, @RequestParam(required = false) Integer deptId) {
        Page<OrderPay> page = new PageFactory<OrderPay>().defaultPage();
        if (deptId == null) {
            deptId = ShiroKit.getUser().getDeptId();//所属公司
            if (deptId == Const.IS_ADMIN_FLG) {
                deptId = null;
            }
        }
        List<Map<String, Object>> result = orderPayMapper.selectList(page, deptId, businessType, beginDate, endDate);
        page.setRecords((List<OrderPay>) new OrderPayWarpper(result).warp());
        return super.packForBT(page);
    }

}
