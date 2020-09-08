package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.News;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.core.util.DateUtil;
import com.labi.core.util.HtmlUtils;

import org.springframework.web.bind.annotation.RequestParam;
import com.labi.modular.biz.service.INewsService;

/**
 * 新闻管理控制器
 *
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

    private String NEW_FILE_PATH="newFile";//新闻 详情存储文件
    private String PREFIX = "/biz/news/";

    @Autowired
    private INewsService newsService;//新闻

    /**
     * 跳转到新闻管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "news.html";
    }

    /**
     * 跳转到添加新闻管理
     */
    @RequestMapping("/news_add")
    public String newsAdd() {
        return PREFIX + "news_add.html";
    }

    /**
     * 跳转到修改新闻管理
     */
    @RequestMapping("/news_update/{newsId}")
    public String newsUpdate(@PathVariable Integer newsId, Model model) {
        News news = newsService.selectById(newsId);
        model.addAttribute("item",news);
        LogObjectHolder.me().set(news);
        return PREFIX + "news_edit.html";
    }

    /**
     * 获取新闻管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<News> page = new PageFactory<News>().defaultPage();
        Wrapper<News> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);

        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("gs_type", depid);
        }
        
        //名称
        if(condition!=null && condition!="") {
            wrapper.like("title", condition);
        }
        return super.packForBT(newsService.selectPage(page,wrapper));
        //return newsService.selectList(null);
    }

    /**
     * 新增新闻管理
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(News news,String info,HttpServletRequest request) throws Exception {

        //生成详情html
        if(StringUtils.isNotBlank(info)) {
            //生成文件名称
            String desFile= DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile,NEW_FILE_PATH, request);
            //属性设置
            news.setDesFile(NEW_FILE_PATH+"/"+desFile+".html");
        }
        news.setCreatetime(new Date());
        newsService.insert(news);
        return SUCCESS_TIP;
    }

    /**
     * 删除新闻管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer newsId) {
        newsService.deleteById(newsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改新闻管理
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(News news,String info,HttpServletRequest request) throws Exception {
        //生成详情html
        if(StringUtils.isNotBlank(info)) {
            //生成文件名称
            String desFile= DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile,NEW_FILE_PATH, request);
            //属性设置
            news.setDesFile(NEW_FILE_PATH+"/"+desFile+".html");
        }

        news.setOprtime(new Date());
        newsService.updateById(news);
        return SUCCESS_TIP;
    }

    /**
     * 新闻管理详情
     */
    @RequestMapping(value = "/detail/{newsId}")
    @ResponseBody
    public Object detail(@PathVariable("newsId") Integer newsId) {
        return newsService.selectById(newsId);
    }
}
