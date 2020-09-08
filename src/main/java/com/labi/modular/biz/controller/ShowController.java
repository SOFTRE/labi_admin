package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.Show;
import com.labi.modular.biz.model.ShowCat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.core.util.DateUtil;
import com.labi.core.util.HtmlUtils;

import org.springframework.web.bind.annotation.RequestParam;

import com.labi.modular.biz.service.IShowCatService;
import com.labi.modular.biz.service.IShowService;

/**
 * 拉比展示管理控制器
 *
 */
@Controller
@RequestMapping("/show")
public class ShowController extends BaseController {

	private String BANNER_FILE_PATH="bannerFile";//banner 详情存储文件
    private String PREFIX = "/biz/show/";

    @Autowired
    private IShowService showService;//拉比展示
    @Autowired
    private IShowCatService showCatService;//拉比展示分类
    /**
     * 跳转到拉比展示管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	//获取拉比展示分类列表
    	Wrapper<ShowCat> catwrapper = new EntityWrapper<>();
        catwrapper.orderBy("createtime",false);
    	catwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=0) {
        	catwrapper.eq("type", depid);
        }
        
    	List<ShowCat> catlist = showCatService.selectList(catwrapper);
    	model.addAttribute("catlist", catlist);//拉比分类list
        return PREFIX + "show.html";
    }

    /**
     * 跳转到添加拉比展示管理
     */
    @RequestMapping("/show_add")
    public String showAdd(Model model) {
    	//获取拉比展示分类列表
    	Wrapper<ShowCat> catwrapper = new EntityWrapper<>();
        catwrapper.orderBy("createtime",false);
    	catwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=0) {
        	catwrapper.eq("type", depid);
        }
        
    	List<ShowCat> catlist = showCatService.selectList(catwrapper);
    	model.addAttribute("catlist", catlist);//拉比分类list
        return PREFIX + "show_add.html";
    }

    /**
     * 跳转到修改拉比展示管理
     */
    @RequestMapping("/show_update/{showId}")
    public String showUpdate(@PathVariable Integer showId, Model model) {
    	//获取拉比展示分类列表
    	Wrapper<ShowCat> catwrapper = new EntityWrapper<>();
        catwrapper.orderBy("createtime",false);
    	catwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
    	List<ShowCat> catlist = showCatService.selectList(catwrapper);
    	model.addAttribute("catlist", catlist);//拉比分类list
    	
        Show show = showService.selectById(showId);
        model.addAttribute("item",show);
        LogObjectHolder.me().set(show);
        return PREFIX + "show_edit.html";
    }

    /**
     * 获取拉比展示管理列表
     * @param condition 名称
     * @param ifRecommend 是否推荐
     * @param ifOnline 是否上线
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,String ifRecommend,String ifOnline,Integer catId, Model model) {
    	
    	//获取拉比展示分类列表
    	Wrapper<ShowCat> catwrapper = new EntityWrapper<>();
        catwrapper.orderBy("createtime",false);
    	catwrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
    	List<ShowCat> catlist = showCatService.selectList(catwrapper);
    	model.addAttribute("catlist", catlist);//拉比分类list
    	
    	//分页类
    	Page<Show> page = new PageFactory<Show>().defaultPage();
    	Wrapper<Show> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
    	wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
    	Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("type", depid);
        }
        
    	//标题参数
    	if(condition!=null && condition!="") {
    		wrapper.like("name", condition);
    	}
    	//分类id
    	if(catId!=null) {
    		wrapper.eq("cat_id", catId);
    	}
    	//是否推荐
    	if(ifRecommend!=null && ifRecommend!="") {
    		wrapper.eq("if_recommend", ifRecommend);
    	}
    	//是否上线
    	if(ifOnline!=null && ifOnline!="") {
    		wrapper.eq("if_online", ifOnline);
    	}
    	Page<Map<String, Object>> showPage = showService.selectMapsPage(page, wrapper);
    	List<Map<String, Object>> list =  showPage.getRecords();
    	//循环拉比展示列表
    	for (Map<String, Object> map : list) {
			//循环拉比分类列表 将id转为分类名称
			for (ShowCat showCat : catlist) {
				if(Integer.parseInt( map.get("catId").toString())==showCat.getId()) {
					map.put("catName", showCat.getName());
				}
			}
			
		}
    	return this.packForBT(showPage);
    	//showPage.setRecords((List<Map<String, Object>>)new ShowWarpper(showPage.getRecords()).warp());
        //return showService.selectList(null);
    }

    /**
     * 新增拉比展示管理
     * @throws Exception 
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Show show,String info,HttpServletRequest request) throws Exception {
    	
    	//生成详情html
    	if(StringUtils.isNotBlank(info)) {
    		//生成文件名称
            String desFile= DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile,BANNER_FILE_PATH, request);
            //属性设置
            show.setDesFile(BANNER_FILE_PATH+"/"+desFile+".html");
        }
    	
    	show.setType(ShiroKit.getUser().getDeptId());//所属公司
    	show.setCreatetime(new Date());//创建时间
        showService.insert(show);
        return SUCCESS_TIP;
    }

    /**
     * 删除拉比展示管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer showId) {
    	
    	Show show = new Show();
    	show.setId(showId);
    	show.setStatus(Const.DELETE);//修改删除状态
    	showService.updateById(show);
        //showService.deleteById(showId);
        return SUCCESS_TIP;
    }

    /**
     * 修改拉比展示管理
     * @throws Exception 
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Show show,String info,HttpServletRequest request) throws Exception {
    	
    	//生成详情html
    	if(StringUtils.isNotBlank(info)) {
    		//生成文件名称
            String desFile= DateUtil.getCurrentDateStr();
            //文件上传
            HtmlUtils.upload(info, desFile,BANNER_FILE_PATH, request);
            //属性设置
            show.setDesFile(BANNER_FILE_PATH+"/"+desFile+".html");
        }
    	
    	
    	show.setOprtime(new Date());
        showService.updateById(show);
        return SUCCESS_TIP;
    }

    /**
     * 拉比展示管理详情
     */
    @RequestMapping(value = "/detail/{showId}")
    @ResponseBody
    public Object detail(@PathVariable("showId") Integer showId) {
        return showService.selectById(showId);
    }
}
