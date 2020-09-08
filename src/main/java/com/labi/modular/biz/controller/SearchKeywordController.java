package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.modular.biz.model.SearchKeyword;
import com.labi.modular.biz.service.ISearchKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 搜索推荐词管理控制器
 */
@Controller
@RequestMapping("/searchkeyword")
public class SearchKeywordController extends BaseController {

    private String PREFIX = "/biz/searchkeyword/";

    @Autowired
    private ISearchKeywordService searchkeywordService;//搜索推荐词

    /**
     * 跳转到搜索推荐词管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "searchkeyword.html";
    }

    /**
     * 跳转到添加搜索推荐词管理
     */
    @RequestMapping("/searchkeyword_add")
    public String searchkeywordAdd() {
        return PREFIX + "searchkeyword_add.html";
    }

    /**
     * 跳转到修改搜索推荐词管理
     */
    @RequestMapping("/searchkeyword_update/{searchkeywordId}")
    public String searchkeywordUpdate(@PathVariable Integer searchkeywordId, Model model) {
        SearchKeyword searchkeyword = searchkeywordService.selectById(searchkeywordId);
        model.addAttribute("item", searchkeyword);
        LogObjectHolder.me().set(searchkeyword);
        return PREFIX + "searchkeyword_edit.html";
    }

    /**
     * 获取搜索推荐词管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        //分页类
        Page<SearchKeyword> page = new PageFactory<SearchKeyword>().defaultPage();
        Wrapper<SearchKeyword> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        if (condition != null && condition != "") {
            wrapper.like("keyword", condition);
        }
        return this.packForBT(searchkeywordService.selectMapsPage(page, wrapper));
    }

    /**
     * 新增搜索推荐词管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SearchKeyword searchkeyword) {
        searchkeyword.setCreatetime(new Date());//创建时间
        searchkeywordService.insert(searchkeyword);
        return SUCCESS_TIP;
    }

    /**
     * 删除搜索推荐词管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(SearchKeyword searchkeyword) {
        searchkeywordService.deleteById(searchkeyword);
        return SUCCESS_TIP;
    }

    /**
     * 修改搜索推荐词管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SearchKeyword searchkeyword) {
        searchkeywordService.updateById(searchkeyword);
        return SUCCESS_TIP;
    }
}
