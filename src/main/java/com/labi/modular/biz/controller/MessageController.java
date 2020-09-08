package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.modular.biz.model.Message;
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

import com.labi.modular.biz.service.IMessageService;
import com.labi.modular.biz.service.IShowCatService;
import com.labi.modular.biz.service.IShowService;

/**
 * 短信消息管理控制器
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    private String PREFIX = "/biz/message/";

    @Autowired
    private IMessageService messageService;//短信消息
    

    /**
     * 跳转到短信消息管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "message.html";
    }

    /**
     * 跳转到添加短信消息管理
     */
    @RequestMapping("/message_add")
    public String showCatAdd() {
        return PREFIX + "message_add.html";
    }

    /**
     * 跳转到修改短信消息管理
     */
    @RequestMapping("/message_update/{messageId}")
    public String showCatUpdate(@PathVariable Integer messageId, Model model) {
        Message message = messageService.selectById(messageId);
        model.addAttribute("item",message);
        LogObjectHolder.me().set(message);
        return PREFIX + "message_edit.html";
    }

    /**
     * 获取短信消息管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	//分页类
    	Page<Message> page = new PageFactory<Message>().defaultPage();
    	
    	Wrapper<Message> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("type", depid);
        }
        
    	//名称
    	if(condition!=null && condition!="") {
    		wrapper.like("title", condition);
    	}
    	
    	return   super.packForBT(messageService.selectMapsPage(page, wrapper));
       // return showCatService.selectList(null);
    }

    /**
     * 新增短信消息管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Message message) {
    	message.setCreateUser(ShiroKit.getUser().getName());
    	message.setCreateTime(new Date());
    	messageService.insert(message);
        return SUCCESS_TIP;
    }

    /**
     * 删除短信消息管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer messageId) {
    	messageService.deleteById(messageId);
        return SUCCESS_TIP;
    }

    /**
     * 修改短信消息管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Message message) {
    	messageService.updateById(message);
        return SUCCESS_TIP;
    }

    /**
     * 短信消息管理详情
     */
    @RequestMapping(value = "/detail/{messageId}")
    @ResponseBody
    public Object detail(@PathVariable("messageId") Integer messageId) {
        return messageService.selectById(messageId);
    }
}
