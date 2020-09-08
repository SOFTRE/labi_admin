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
import com.labi.modular.biz.model.*;
import com.labi.modular.biz.service.*;
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
import java.util.List;

/**
 * 教练管理控制器
 */
@Controller
@RequestMapping("/coach")
public class CoachController extends BaseController {

    private String PREFIX = "/biz/coach/";

    Integer evalid = 0;
    @Autowired
    private ICoachService coachService;//教练
    @Autowired
    private ICoachGradeService coachGradeService;//教练等级
    @Autowired
    private IBizUserService bizUserService;//用户
    @Autowired
    private ICoachCatService coachCatService;//教练分类
    @Autowired
    private ICoachReservationEvaluateService coachReservationEvaluateService;//预约反馈评分

    /**
     * 跳转到教练管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        //查询结果集
        Wrapper<CoachGrade> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//查询正常状态数据 A
        List<CoachGrade> grades = coachGradeService.selectList(wrapper);
        model.addAttribute("grades", grades);
        Wrapper<CoachCat> coachcatwrapper = new EntityWrapper<>();
        coachcatwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        //教练分类结果集
        List<CoachCat> coachcatlist = coachCatService.selectList(coachcatwrapper);
        model.addAttribute("cats", coachcatlist);
        return PREFIX + "coach.html";
    }

    /**
     * 历史评分列表筛选
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/coach_evaluate/{id}")
    public String evaluate(@PathVariable("id") Integer id) {
        this.evalid = id;
        return PREFIX + "evaluate.html";
    }

    /**
     * 根据预约id查询反馈评分详情
     *
     * @param condition
     * @return
     */
    @RequestMapping("/evaluate_info")
    @ResponseBody
    public Object evaluateListByReservationId(String condition) {
        Page<CoachReservationEvaluate> page = new PageFactory<CoachReservationEvaluate>().defaultPage();
        Wrapper<CoachReservationEvaluate> reWrapper = new EntityWrapper<>();
        reWrapper.eq("coach_id", this.evalid);// 预约id
        if (condition != null && condition != "") {
            reWrapper.where("instr(user_name,{0})>0", condition);
        }
        return super.packForBT(coachReservationEvaluateService.selectPage(page, reWrapper));
    }


    /**
     * 跳转到添加教练管理
     */
    @RequestMapping("/coach_add")
    public String coachAdd(Model model) {

        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        //教练等级
        Wrapper<CoachGrade> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }
        //教练等级查询结果集
        List<CoachGrade> gradeslist = coachGradeService.selectList(wrapper);
        model.addAttribute("gradeslist", gradeslist);

        //教练分类
        Wrapper<CoachCat> coachcatwrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        coachcatwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            coachcatwrapper.eq("type", depid);
        }
        //教练分类结果集
        List<CoachCat> coachcatlist = coachCatService.selectList(coachcatwrapper);
        model.addAttribute("coachcatlist", coachcatlist);

        return PREFIX + "coach_add.html";
    }

    /**
     * 跳转到修改教练管理
     */
    @RequestMapping("/coach_update/{coachId}")
    public String coachUpdate(@PathVariable Integer coachId, Model model) {

        Wrapper<CoachGrade> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }
        //查询教练等级结果集
        List<CoachGrade> gradeslist = coachGradeService.selectList(wrapper);
        model.addAttribute("gradeslist", gradeslist);

        //教练分类
        Wrapper<CoachCat> coachcatwrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        coachcatwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            coachcatwrapper.eq("type", depid);
        }
        //教练分类结果集
        List<CoachCat> coachcatlist = coachCatService.selectList(coachcatwrapper);
        model.addAttribute("coachcatlist", coachcatlist);

        Coach coach = coachService.selectById(coachId);

        model.addAttribute("item", coach);
        LogObjectHolder.me().set(coach);
        return PREFIX + "coach_edit.html";
    }

    /**
     * 获取教练管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String telephone, Integer gradeId, Integer catId) {

        Page<Coach> page = new PageFactory<Coach>().defaultPage();

        Wrapper<Coach> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if (depid.intValue() != Const.IS_ADMIN_FLG) {
            wrapper.eq("type", depid);
        }

        //教练名称
        if (condition != null && condition != "") {
            wrapper.where("instr(name,{0})>0", condition);
            //wrapper.like("name", condition);
        }
        //教练电话
        if (telephone != null && telephone != "") {
            wrapper.where("instr(telephone,{0})>0", telephone);
        }
        if (gradeId != null) {
            wrapper.eq("coach_grade_id", gradeId);
        }
        if (catId != null) {
            wrapper.eq("coach_cat_id", catId);
        }
        return super.packForBT(coachService.selectMapsPage(page, wrapper));
        //return coachService.selectList(null);
    }

    /**
     * 新增教练管理
     *
     * @param coach
     * @param info
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Coach coach, String info, HttpServletRequest request) throws Exception {

        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, "coacthFile", request);
            //属性设置
            coach.setDesFile("coacthFile/" + desFile + ".html");
        }

        //电话号码是否存在校验
        Wrapper<Coach> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("telephone", coach.getTelephone());
        int count = coachService.selectCount(wrapper);
        if (count > 0) {
            throw new BussinessException(BizExceptionEnum.COACH_PHONE_EOORO);
        }

        //根据等级id获取教练等级详细信息
        CoachGrade coachGrade = coachGradeService.selectById(coach.getCoachGradeId());
        if (coachGrade != null) {
            coach.setCoachGradeId(coachGrade.getId());//教练等级id
            coach.setCoachGradeName(coachGrade.getName());//教练等级名称
        }

        //根据用户id查询用户信息
        BizUser bizUser = bizUserService.selectById(coach.getUserId());
        if (Const.TRUE.equalsIgnoreCase(bizUser.getIfCoach())) {
            throw new BussinessException(BizExceptionEnum.COACH_EOORO);
        }

        //根据分类id查询教练分类信息
        CoachCat coachCat = coachCatService.selectById(coach.getCoachCatId());
        if (coachCat != null) {
            coach.setCoachCatName(coachCat.getName());
        }

        coach.setTotalScore(0d);
        coach.setSaleNum(0);
        coach.setScore(0d);
        coach.setScoreNum(0);
        coach.setIfRecommend(coach.getIfRecommend());
        coach.setCreatetime(new Date());//创建时间
        coach.setType(ShiroKit.getUser().getDeptId());//所属公司

        coachService.addCoach(coach, bizUser);
        return SUCCESS_TIP;
    }

    /**
     * 删除教练管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer coachId) {

        //获取教练详细
        Coach coach = coachService.selectById(coachId);

        //根据用户id查询用户信息
        BizUser bizUser = bizUserService.selectById(coach.getUserId());
        if (bizUser != null) {
            bizUser.setIfCoach(Const.FALSE);
            bizUserService.updateById(bizUser);
        }
        coachService.deleteById(coachId);
        return SUCCESS_TIP;
    }

    /**
     * 冻结/解冻
     */
    @RequestMapping(value = "/frozen")
    @ResponseBody
    public Object frozen(String frozen, Integer coachId) {

        //获取教练详细
        Coach coach = coachService.selectById(coachId);
        coach.setIfFrozen(frozen);
        coachService.updateById(coach);
        return SUCCESS_TIP;
    }

    /**
     * 推荐/取消推荐
     */
    @RequestMapping(value = "/recommend")
    @ResponseBody
    public Object recommend(String recommend, Integer coachId) {
        //获取教练详细
        Coach coach = coachService.selectById(coachId);
        coach.setIfRecommend(recommend);
        coachService.updateById(coach);
        return SUCCESS_TIP;
    }

    /**
     * 修改教练管理
     *
     * @param coach
     * @param info
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Coach coach, String info, HttpServletRequest request) throws Exception {

        //生成详情html
        if (StringUtils.isNotBlank(info)) {
            info = StringEscapeUtils.unescapeHtml(info);
            //生成文件名称
            String desFile = DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile, "coacthFile", request);
            //属性设置
            coach.setDesFile("coacthFile/" + desFile + ".html");
        }

        //电话号码是否存在校验
        Wrapper<Coach> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("telephone", coach.getTelephone());
        wrapper.ne("id", coach.getId());
        int count = coachService.selectCount(wrapper);
        if (count > 0) {
            throw new BussinessException(BizExceptionEnum.COACH_PHONE_EOORO);
        }

        //根据id获取教练等级详细信息
        CoachGrade coachGrade = coachGradeService.selectById(coach.getCoachGradeId());
        if (coachGrade != null) {
            coach.setCoachGradeId(coachGrade.getId());//教练等级id
            coach.setCoachGradeName(coachGrade.getName());//教练等级名称
        }

        //根据分类id查询教练分类信息
        CoachCat coachCat = coachCatService.selectById(coach.getCoachCatId());
        if (coachCat != null) {
            coach.setCoachCatName(coachCat.getName());
        }

        coachService.updateById(coach);
        return SUCCESS_TIP;
    }

    /**
     * 教练管理详情
     */
    @RequestMapping(value = "/detail/{coachId}")
    @ResponseBody
    public Object detail(@PathVariable("coachId") Integer coachId) {
        return coachService.selectById(coachId);
    }

    /***
     * 检测电话号码是否存在
     * @param phone
     * @return
     */
    @RequestMapping(value = "/check/phone")
    @ResponseBody
    public Object isPhone(String phone) {

        Wrapper<Coach> wrapper = new EntityWrapper<>();
        wrapper.eq("telephone", phone);
        return coachService.selectOne(wrapper);
    }


    public static void main(String[] args) {
        System.out.println(0d);
    }
}
