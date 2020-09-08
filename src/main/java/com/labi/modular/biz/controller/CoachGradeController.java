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
import com.labi.core.util.DateUtil;
import com.labi.core.util.HtmlUtils;
import com.labi.modular.biz.model.Coach;
import com.labi.modular.biz.model.CoachGrade;
import com.labi.modular.biz.service.ICoachGradeService;
import com.labi.modular.biz.service.ICoachService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 教练等级管理控制器
 */
@Controller
@RequestMapping("/coachGrade")
public class CoachGradeController extends BaseController {

    private String PREFIX = "/biz/coachGrade/";

    @Autowired
    private ICoachGradeService coachGradeService;//教练分类
    @Autowired
    private ICoachService coachService;//教练

    /**
     * 跳转到教练等级管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "coachGrade.html";
    }

    /**
     * 跳转到添加教练等级管理
     */
    @RequestMapping("/coachGrade_add")
    public String coachGradeAdd() {
        return PREFIX + "coachGrade_add.html";
    }

    /**
     * 跳转到修改教练等级管理
     */
    @RequestMapping("/coachGrade_update/{coachGradeId}")
    public String coachGradeUpdate(@PathVariable Integer coachGradeId, Model model) {
        CoachGrade coachGrade = coachGradeService.selectById(coachGradeId);
        model.addAttribute("item", coachGrade);
        LogObjectHolder.me().set(coachGrade);
        return PREFIX + "coachGrade_edit.html";
    }

    /**
     * 获取教练等级管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        Page<CoachGrade> page = new PageFactory<CoachGrade>().defaultPage();

        Wrapper<CoachGrade> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//查询正常状态数据 A
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }

        if (condition != null && condition != "") {
            wrapper.like("name", condition);
        }
        return super.packForBT(coachGradeService.selectMapsPage(page, wrapper));
        //return coachGradeService.selectList(null);
    }

    /**
     * 新增教练等级管理
     *
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CoachGrade coachGrade, String info, HttpServletRequest request) throws Exception {
        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, "coacthFile", request);
            //属性设置
            coachGrade.setDesFile("coacthFile/" + desFile + ".html");
        }
        coachGrade.setCreatetime(new Date());//创建日期
        coachGrade.setType(ShiroKit.getUser().getDeptId());//所属公司
        coachGradeService.insert(coachGrade);
        return SUCCESS_TIP;
    }

    /**
     * 删除教练等级管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer coachGradeId) {
        //根据分类id查询分类下是否有数据
        Wrapper<Coach> wrapper = new EntityWrapper<>();
        wrapper.eq("coach_grade_id", coachGradeId);
        int flg = coachService.selectCount(wrapper);
        if (flg > 0) {
            //拒绝删除
            throw new BussinessException(BizExceptionEnum.LABI_SHOW_MSG);
        } else {
            CoachGrade coachGrade = new CoachGrade();
            coachGrade.setId(coachGradeId);
            coachGrade.setStatus(Const.DELETE);//D 删除
            coachGradeService.updateById(coachGrade);
        }
        //coachGradeService.deleteById(coachGradeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改教练等级管理
     *
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CoachGrade coachGrade, String info, HttpServletRequest request) throws Exception {
        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            info = StringEscapeUtils.unescapeHtml(info);
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, "coacthFile", request);
            //属性设置
            coachGrade.setDesFile("coacthFile/" + desFile + ".html");
        }
        coachGrade.setOprtime(new Date());
        coachGradeService.updateById(coachGrade);
        Wrapper<Coach> wrapper = new EntityWrapper<>();
        wrapper.eq("coach_grade_id", coachGrade.getId());
        Coach coach = new Coach();
        coach.setCoachGradeName(coachGrade.getName());
        coachService.update(coach, wrapper);

        return SUCCESS_TIP;
    }

    /**
     * 教练等级管理详情
     */
    @RequestMapping(value = "/detail/{coachGradeId}")
    @ResponseBody
    public Object detail(@PathVariable("coachGradeId") Integer coachGradeId) {
        return coachGradeService.selectById(coachGradeId);
    }
}
