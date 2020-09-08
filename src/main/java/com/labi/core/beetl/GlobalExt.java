package com.labi.core.beetl;

import com.labi.config.properties.CloudProperties;
import com.labi.core.util.SpringContextHolder;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lyr
 * @date 2018/5/16 0016
 */
public class GlobalExt implements WebRenderExt {

    @Override
    public void modify(Template template, GroupTemplate arg1, HttpServletRequest arg2, HttpServletResponse arg3) {
        CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);
        template.binding("filePreFix",cloudProperties.getBucketFileUrl());
        template.binding("imgPreFix", cloudProperties.getBucketImgUrl());
    }
}