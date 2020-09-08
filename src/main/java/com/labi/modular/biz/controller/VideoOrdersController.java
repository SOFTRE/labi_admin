package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.ProdOrders;
import com.labi.modular.biz.service.IProdOrdersService;
import com.labi.modular.biz.warpper.ProdOrdersWarpper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 视频订单管理控制器
 */
@Controller
@RequestMapping("/videoOrders")
public class VideoOrdersController extends BaseController {

    private String PREFIX = "/biz/videoOrders/";

    @Autowired
    private IProdOrdersService prodOrdersService;

    /**
     * 跳转到视频订单管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "videoOrders.html";
    }


    /**
     * 获取视频订单管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String progress,Integer userId,String search) {
        Page<ProdOrders> page = new PageFactory<ProdOrders>().defaultPage();
        if(StringUtils.isNotBlank(search)){
            condition = search;
        }
        List<Map<String, Object>> result = prodOrdersService.selectVideoOrdersByPage(page, progress, condition,userId);
        page.setRecords((List<ProdOrders>) new ProdOrdersWarpper(result).warp());
        return super.packForBT(page);
    }
}
