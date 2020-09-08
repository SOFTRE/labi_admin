package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.annotion.Permission;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.dao.OrderPayReportMapper;
import com.labi.modular.biz.pojo.OrderPayReport;
import com.labi.modular.biz.service.IBizUserService;
import com.labi.modular.biz.service.IOrderPayService;
import com.labi.modular.biz.warpper.OrderPayReportWarpper;
import com.labi.modular.system.dao.DeptMapper;
import com.labi.modular.system.model.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 总览信息
 *
 * @author lyr
 * @Date 2017年3月4日23:05:54
 */
@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {

    @Autowired
    IOrderPayService orderPayService;

    @Autowired
    IBizUserService bizUserService;

    @Resource
    private OrderPayReportMapper reportMapper;

    @Autowired
    DeptMapper deptMapper;

    /**
     * 跳转到黑板
     */
    @RequestMapping("")
    public String blackboard(Model model) {
        return "/blackboard.html";
    }

    /**
     * 获取首页总金额
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/totalmoney")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object loadTotalMoney(String type) {
        //所属公司
        Integer depid = ShiroKit.getUser().getDeptId();
        if (depid.intValue() == 0) {
            depid = null;
        }
        return orderPayService.loadTotalMoney(type,depid);
    }

    /**
     * 获取首页总订单数量
     */
    @RequestMapping(value = "/totalnumber")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object loadTotalCount(String type) {
        //所属公司
        Integer depid = ShiroKit.getUser().getDeptId();
        if (depid.intValue() == 0) {
            depid = null;
        }
        return orderPayService.loadTotalNumber(type,depid);
    }


    /**
     * 获取首页用户数量
     */
    @RequestMapping(value = "/totaluser")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object loadUserTotalCount(String type) {
        return bizUserService.loadUserTotalCount(type);
    }


    /**
     * 获取收入柱状图数据
     */
    @RequestMapping(value = "/chartdata")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object loadChartData(String type, String endDate) {
        //所属公司
        Integer depid = ShiroKit.getUser().getDeptId();
        if (depid.intValue() == 0) {
            depid = null;
        }
        Wrapper<Dept> wrapper = null;
        if(depid != null && !"all".equalsIgnoreCase(type)){
            wrapper = new EntityWrapper<>();
            wrapper.eq("id",depid);
        }

        Page<OrderPayReport> page = new PageFactory<OrderPayReport>().defaultPage();
        List<Dept> depts = deptMapper.selectList(wrapper);
        Dept d = new Dept();
        d.setSimplename("总公司");
        d.setId(0);
        depts.add(d);
        for (Dept dept : depts) {
            if("day".equalsIgnoreCase(type)){
                List<Map<String, Object>> result = reportMapper.selectByDay(page, dept.getId(), null, null, endDate);
                dept.setReports((List<OrderPayReport>) new OrderPayReportWarpper(result).warp());
            }
            if("month".equalsIgnoreCase(type)){
                List<Map<String, Object>> result = reportMapper.selectByMonth(page, dept.getId(), null, null, endDate);
                dept.setReports((List<OrderPayReport>) new OrderPayReportWarpper(result).warp());
            }
            if("all".equalsIgnoreCase(type)){
                List<Map<String, Object>> result = reportMapper.selectAll(page, dept.getId(), null, null, null);
                dept.setReports((List<OrderPayReport>) new OrderPayReportWarpper(result).warp());
            }
        }
        return depts;
    }
}
