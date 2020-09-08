package com.labi.modular.biz.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.labi.common.controller.BaseController;
import com.labi.core.util.KdniaoTrackQueryAPI;
import com.labi.modular.biz.model.OrderLogistics;
import com.labi.modular.biz.service.IOrderLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 物流控制器
 */
@Controller
@RequestMapping("/logistics")
public class OrderLogisticsController extends BaseController {

    @Autowired
    private IOrderLogisticsService orderLogisticsService;

    /**
     * 新增物流信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(OrderLogistics orderLogistics) {
        orderLogistics.setCreatetime(new Date());
        orderLogisticsService.insert(orderLogistics);
        return SUCCESS_TIP;
    }

    /**
     * 查看物流信息
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/detail/{orderId}", method = RequestMethod.POST)
    @ResponseBody
    public String wuliu(@PathVariable("orderId") Integer orderId) {
        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
        try {
            OrderLogistics orderLogistics = orderLogisticsService.selectByOrderId(orderId);
            String result = api.getOrderTracesByJson(orderLogistics.getLogisticsCode(), orderLogistics.getLogisticsNo());
            JSONObject jsonObject = JSONObject.parseObject(result);
            boolean success = jsonObject.getBoolean("Success");
            if (success) {
                JSONArray array = jsonObject.getJSONArray("Traces");
                JSONObject obj = new JSONObject();
                obj.put("arr", array.toString());
                obj.put("lo", orderLogistics);
                return obj.toJSONString();
            }
            return JSONObject.toJSONString(new String[]{"error"});
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
}
