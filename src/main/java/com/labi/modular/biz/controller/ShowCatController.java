package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.modular.biz.model.Show;
import com.labi.modular.biz.model.ShowCat;
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
import com.labi.modular.biz.service.IShowCatService;
import com.labi.modular.biz.service.IShowService;

/**
 * 拉比展示分类管理控制器
 *
 */
@Controller
@RequestMapping("/showCat")
public class ShowCatController extends BaseController {

    private String PREFIX = "/biz/showCat/";

    @Autowired
    private IShowCatService showCatService;//分类
    
    @Autowired
    private IShowService showService;//拉比展示

    /**
     * 跳转到拉比展示分类管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "showCat.html";
    }

    /**
     * 跳转到添加拉比展示分类管理
     */
    @RequestMapping("/showCat_add")
    public String showCatAdd() {
        return PREFIX + "showCat_add.html";
    }

    /**
     * 跳转到修改拉比展示分类管理
     */
    @RequestMapping("/showCat_update/{showCatId}")
    public String showCatUpdate(@PathVariable Integer showCatId, Model model) {
        ShowCat showCat = showCatService.selectById(showCatId);
        model.addAttribute("item",showCat);
        LogObjectHolder.me().set(showCat);
        return PREFIX + "showCat_edit.html";
    }

    /**
     * 获取拉比展示分类管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	//分页类
    	Page<ShowCat> page = new PageFactory<ShowCat>().defaultPage();
    	
    	Wrapper<ShowCat> wrapper = new EntityWrapper<>();
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
    	
    	return   super.packForBT(showCatService.selectMapsPage(page, wrapper));
       // return showCatService.selectList(null);
    }

    /**
     * 新增拉比展示分类管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ShowCat showCat) {
    	showCat.setCreatetime(new Date());//添加时间
    	showCat.setType(ShiroKit.getUser().getDeptId());//所属公司
        showCatService.insert(showCat);
        return SUCCESS_TIP;
    }

    /**
     * 删除拉比展示分类管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer showCatId) {
    	//根据分类id查询分类下是否有数据
    	Wrapper<Show> wrapper = new EntityWrapper<>();
    	wrapper.eq("cat_id", showCatId);
    	wrapper.eq("status", Const.ACTIVE);
    	int flg =  showService.selectCount(wrapper);
    	if(flg>0) {
    		//拒绝删除
    		throw new BussinessException(BizExceptionEnum.LABI_SHOW_MSG);
    	}else {
    		//删除
    		ShowCat showCat = new ShowCat();
    		showCat.setId(showCatId);
    		showCat.setStatus(Const.DELETE);//修改删除状态
    		showCatService.updateById(showCat);
    	}
        //showCatService.deleteById(showCatId);
        return SUCCESS_TIP;
    }

    /**
     * 修改拉比展示分类管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ShowCat showCat) {
    	showCat.setOprtime(new Date());
        showCatService.updateById(showCat);
        return SUCCESS_TIP;
    }

    /**
     * 拉比展示分类管理详情
     */
    @RequestMapping(value = "/detail/{showCatId}")
    @ResponseBody
    public Object detail(@PathVariable("showCatId") Integer showCatId) {
        return showCatService.selectById(showCatId);
    }
}
