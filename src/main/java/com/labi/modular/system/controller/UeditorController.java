package com.labi.modular.system.controller;

import com.labi.common.controller.BaseController;
import com.labi.core.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 腾讯云
 *
 * @author lyr
 * @Date 2018年5月18日 11:05:41
 */
@Controller
@RequestMapping("/ueditor")
public class UeditorController extends BaseController {

    /**
     * 获取签名
     */
    @RequestMapping("/config")
    @ResponseBody
    public String blackboard(HttpServletRequest request) throws Exception{
        String rootPath = request.getSession().getServletContext().getRealPath("/")+"\\static\\js\\plugins\\";
        return new ActionEnter( request, rootPath ).exec();
    }
}
