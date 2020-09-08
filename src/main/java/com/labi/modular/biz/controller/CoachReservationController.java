package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.model.CoachReservation;
import com.labi.modular.biz.model.CoachReservationEvaluate;
import com.labi.modular.biz.service.ICoachReservationEvaluateService;
import com.labi.modular.biz.service.ICoachReservationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 预约反馈管理控制器
 */
@Controller
@RequestMapping("/coachReservation")
public class CoachReservationController extends BaseController {

    private String PREFIX = "/biz/coachReservation/";

    Integer evalid = 0;
    @Autowired
    private ICoachReservationService coachReservationService;

    @Autowired
    private ICoachReservationEvaluateService coachReservationEvaluateService;//预约反馈评分

    /**
     * 跳转到预约反馈管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "coachReservation.html";
    }

    /**
     * 跳转到添加预约反馈管理
     */
    @RequestMapping("/coachReservation_add")
    public String coachReservationAdd() {
        return PREFIX + "coachReservation_add.html";
    }

    /**
     * 预约反馈详情
     */
    @RequestMapping("/coachReservation_evaluate/{id}")
    public String coachReservationEvaluate(@PathVariable Integer id) {
        this.evalid = id;
        return PREFIX + "evaluate.html";
    }

    /**
     * 根据预约id查询反馈详情
     *
     * @return
     */
    @RequestMapping("/evaluate_info")
    @ResponseBody
    public Object evaluateListByReservationId() {
        Page<CoachReservationEvaluate> page = new PageFactory<CoachReservationEvaluate>().defaultPage();
        Wrapper<CoachReservationEvaluate> reWrapper = new EntityWrapper<>();
        reWrapper.eq("reservation_id", this.evalid);// 预约id
        return super.packForBT(coachReservationEvaluateService.selectPage(page, reWrapper));
    }

    /**
     * 跳转到修改预约反馈管理
     */
    @RequestMapping("/coachReservation_update/{coachReservationId}")
    public String coachReservationUpdate(@PathVariable Integer coachReservationId, Model model) {
        CoachReservation coachReservation = coachReservationService.selectById(coachReservationId);
        model.addAttribute("item", coachReservation);
        LogObjectHolder.me().set(coachReservation);
        return PREFIX + "coachReservation_edit.html";
    }

    /**
     * 获取预约反馈管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String username, String coachname, String condition, Integer userId, String search) {
        if (StringUtils.isNotBlank(search)) {
            condition = search;
        }

        Page<CoachReservation> page = new PageFactory<CoachReservation>().defaultPage();

        Wrapper<CoachReservation> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("if_feedback", Const.TRUE);
        //所属公司
        Integer depid = ShiroKit.getUser().getDeptId();
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (username != null && username != "") {
            wrapper.like("user_name", username);//用户名称
        }

        if (coachname != null && coachname != "") {
            wrapper.like("coach_name", coachname);//教练名称
        }
        if (condition != null && condition != "") {
            wrapper.and("instr(coach_name,{0})>0", condition);
        }
        return super.packForBT(coachReservationService.selectPage(page, wrapper));
    }

    /**
     * 新增预约反馈管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CoachReservation coachReservation) {

        //所属公司

        //添加时间
        coachReservation.setCreatetime(new Date());
        coachReservationService.insert(coachReservation);
        return SUCCESS_TIP;
    }

    /**
     * 删除预约反馈管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer coachReservationId) {
        coachReservationService.deleteById(coachReservationId);
        return SUCCESS_TIP;
    }

    /**
     * 修改预约反馈管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CoachReservation coachReservation) {
        coachReservationService.updateById(coachReservation);
        return SUCCESS_TIP;
    }

    /**
     * 预约反馈管理详情
     */
    @RequestMapping(value = "/detail/{coachReservationId}")
    @ResponseBody
    public Object detail(@PathVariable("coachReservationId") Integer coachReservationId) {
        return coachReservationService.selectById(coachReservationId);
    }
}
