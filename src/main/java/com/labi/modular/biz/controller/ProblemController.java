package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.modular.biz.model.Problem;
import com.labi.modular.biz.service.IProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 常见问题管理控制器
 */
@Controller
@RequestMapping("/problem")
public class ProblemController extends BaseController {

    private String PREFIX = "/biz/problem/";

    @Autowired
    private IProblemService problemService;//常见问题

    /**
     * 跳转到常见问题管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "problem.html";
    }

    /**
     * 跳转到添加常见问题管理
     */
    @RequestMapping("/problem_add")
    public String problemAdd() {
        return PREFIX + "problem_add.html";
    }

    /**
     * 跳转到修改常见问题管理
     */
    @RequestMapping("/problem_update/{problemId}")
    public String problemUpdate(@PathVariable Integer problemId, Model model) {
        Problem problem = problemService.selectById(problemId);
        model.addAttribute("item", problem);
        LogObjectHolder.me().set(problem);
        return PREFIX + "problem_edit.html";
    }

    /**
     * 获取常见问题管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        //分页类
        Page<Problem> page = new PageFactory<Problem>().defaultPage();
        Wrapper<Problem> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        if (condition != null && condition != "") {
            wrapper.like("problem", condition);
        }
        return this.packForBT(problemService.selectMapsPage(page, wrapper));
    }

    /**
     * 新增常见问题管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Problem problem) {
        problem.setCreatetime(new Date());//创建时间
        problemService.insert(problem);
        return SUCCESS_TIP;
    }

    /**
     * 删除常见问题管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Problem problem) {
        problemService.deleteById(problem);
        return SUCCESS_TIP;
    }

    /**
     * 修改常见问题管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Problem problem) {
        problemService.updateById(problem);
        return SUCCESS_TIP;
    }
}
