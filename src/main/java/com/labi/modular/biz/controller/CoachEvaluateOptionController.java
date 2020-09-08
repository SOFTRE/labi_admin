package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.CoachEvaluateOption;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import org.springframework.web.bind.annotation.RequestParam;
import com.labi.modular.biz.service.ICoachEvaluateOptionService;

/**
 * 教练评价管理控制器
 *
 */
@Controller
@RequestMapping("/coachEvaluateOption")
public class CoachEvaluateOptionController extends BaseController {

    private String PREFIX = "/biz/coachEvaluateOption/";

    @Autowired
    private ICoachEvaluateOptionService coachEvaluateOptionService;//教练评价选项
    /**
     * 跳转到教练评价管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "coachEvaluateOption.html";
    }

    /**
     * 跳转到添加教练评价管理
     */
    @RequestMapping("/coachEvaluateOption_add")
    public String coachEvaluateOptionAdd() {
        return PREFIX + "coachEvaluateOption_add.html";
    }

    /**
     * 跳转到修改教练评价管理
     */
    @RequestMapping("/coachEvaluateOption_update/{coachEvaluateOptionId}")
    public String coachEvaluateOptionUpdate(@PathVariable Integer coachEvaluateOptionId, Model model) {
    	CoachEvaluateOption coachEvaluateOption = coachEvaluateOptionService.selectById(coachEvaluateOptionId);
        model.addAttribute("item",coachEvaluateOption);
        LogObjectHolder.me().set(coachEvaluateOption);
        return PREFIX + "coachEvaluateOption_edit.html";
    }

    /**
     * 获取教练评价管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	//分页类
    	Page<CoachEvaluateOption> page = new PageFactory<CoachEvaluateOption>().defaultPage();
    	
    	Wrapper<CoachEvaluateOption> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
    	wrapper.eq("status", Const.ACTIVE);//获取状态正常（A）的数据
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("type", depid);
        }
        
    	//名称
    	if(condition!=null && condition!="") {
    		wrapper.like("name", condition);
    	}
    	
    	return   super.packForBT(coachEvaluateOptionService.selectMapsPage(page, wrapper));
    }

    /**
     * 新增教练评价管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CoachEvaluateOption coachEvaluateOption) {
    	coachEvaluateOption.setCreatetime(new Date());//添加时间
    	coachEvaluateOption.setType(ShiroKit.getUser().getDeptId());//所属公司
    	coachEvaluateOptionService.insert(coachEvaluateOption);
        return SUCCESS_TIP;
    }

    /**
     * 删除教练评价管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer coachEvaluateId) {
    	 
    	//删除
    	CoachEvaluateOption coachEvaluate = new CoachEvaluateOption();
    	coachEvaluate.setId(coachEvaluateId);
    	coachEvaluate.setStatus(Const.DELETE);//修改删除状态
    	coachEvaluateOptionService.updateById(coachEvaluate);
        return SUCCESS_TIP;
    }

    /**
     * 修改教练评价管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CoachEvaluateOption coachEvaluateOption) {
    	coachEvaluateOption.setOprtime(new Date());
    	coachEvaluateOptionService.updateById(coachEvaluateOption);
        return SUCCESS_TIP;
    }

    /**
     * 教练评价管理详情
     */
    @RequestMapping(value = "/detail/{coachEvaluateId}")
    @ResponseBody
    public Object detail(@PathVariable("coachEvaluateId") Integer coachEvaluateId) {
        return coachEvaluateOptionService.selectById(coachEvaluateId);
    }
}
