package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;

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
import com.labi.modular.biz.model.ProdEcReason;
import com.labi.modular.biz.service.IProdEcReasonService;

/**
 * 退货原因管理控制器
 */
@Controller
@RequestMapping("/prodEcReason")
public class ProdEcReasonController extends BaseController {

    private String PREFIX = "/biz/prodEcReason/";

    @Autowired
    private IProdEcReasonService prodEcReasonService; //退货问题
    /**
     * 跳转到退货原因管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prodEcReason.html";
    }

    /**
     * 跳转到添加退货原因管理
     */
    @RequestMapping("/prodEcReason_add")
    public String bannerAdd() {
        return PREFIX + "prodEcReason_add.html";
    }

    /**
     * 跳转到修改退货原因管理
     */
    @RequestMapping("/prodEcReason_update/{prodEcReasonId}")
    public String bannerUpdate(@PathVariable Integer prodEcReasonId, Model model) {
    	//问题详情
    	ProdEcReason prodEcReason = prodEcReasonService.selectById(prodEcReasonId);
        model.addAttribute("item", prodEcReason);
        LogObjectHolder.me().set(prodEcReason);
        return PREFIX + "prodEcReason_edit.html";
    }

    /**
     * 获取退货原因管理列表
     * @param condition 名称
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<ProdEcReason> page = new PageFactory<ProdEcReason>().defaultPage();
        Wrapper<ProdEcReason> wrapper = new EntityWrapper<>();
        wrapper.eq("status", Const.ACTIVE);//数据有效标识 A
        wrapper.orderBy("seq_num",false);
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("gs_type", depid);
        }
        
        //名称
        if(condition!=null && condition!="") {
            wrapper.like("name", condition);
        }
        return super.packForBT(prodEcReasonService.selectPage(page,wrapper));
    }

    /**
     * 新增退货原因管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProdEcReason prodEcReason) {
        //添加时间
    	prodEcReason.setCreatetime(new Date());
    	prodEcReasonService.insert(prodEcReason);
        return SUCCESS_TIP;
    }

    /**
     * 删除退货原因管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer prodEcReasonId) {
    	//根据id查询
    	ProdEcReason prodEcReason= prodEcReasonService.selectById(prodEcReasonId);
    	prodEcReason.setStatus(Const.DELETE);
    	prodEcReasonService.updateById(prodEcReason);
        return SUCCESS_TIP;
    }

    /**
     * 修改退货原因管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProdEcReason prodEcReason) {
    	prodEcReason.setOprtime(new Date());//操作时间参数
    	prodEcReasonService.updateById(prodEcReason);
        return SUCCESS_TIP;
    }

    /**
     * 退货原因管理详情
     */
    @RequestMapping(value = "/detail/{prodEcReasonId}")
    @ResponseBody
    public Object detail(@PathVariable("prodEcReasonId") Integer prodEcReasonId) {
    	ProdEcReason prodEcReason = prodEcReasonService.selectById(prodEcReasonId);
        return prodEcReason;
    }
    
    
    public static void main(String[] args) {
    	
	}
}
