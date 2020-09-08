package com.labi.modular.system.controller;

import com.labi.common.controller.BaseController;
import com.labi.core.util.CloudSignatureUtil;
import com.labi.modular.system.dao.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 腾讯云
 *
 * @author lyr
 * @Date 2018年5月18日 11:05:41
 */
@Controller
@RequestMapping("/cloud")
public class CloudController extends BaseController {

    /**
     * 获取签名
     */
    @RequestMapping("/signature")
    @ResponseBody
    public String blackboard() throws Exception{
        return CloudSignatureUtil.getUploadSignature();
    }
}
