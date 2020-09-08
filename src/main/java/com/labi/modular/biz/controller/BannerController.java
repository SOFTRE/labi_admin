package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.modular.biz.model.Banner;
import com.labi.modular.biz.model.Coach;
import com.labi.modular.biz.model.Course;
import com.labi.modular.biz.model.CourseCat;
import com.labi.modular.biz.model.CourseOrder;
import com.labi.modular.biz.model.ProdVideo;
import com.labi.modular.biz.model.Product;
import com.labi.modular.biz.model.Show;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;

import org.springframework.web.bind.annotation.RequestParam;
import com.labi.modular.biz.service.IBannerService;
import com.labi.modular.biz.service.ICoachService;
import com.labi.modular.biz.service.ICourseCatService;
import com.labi.modular.biz.service.ICourseOrderService;
import com.labi.modular.biz.service.ICourseService;
import com.labi.modular.biz.service.IProdVideoService;
import com.labi.modular.biz.service.IProductService;
import com.labi.modular.biz.service.IShowService;

/**
 * banner管理控制器
 */
@Controller
@RequestMapping("/banner")
public class BannerController extends BaseController {

    private String PREFIX = "/biz/banner/";

    @Autowired
    private IBannerService bannerService;

    @Autowired
    private ICoachService coachService;//教练
    @Autowired
    private IProductService productService;//商品
    @Autowired
    private IProdVideoService prodVideoService;//在线视频
    @Autowired
    private ICourseCatService courseCatService;//课程分类
    @Autowired
    private ICourseService courseService;//课程
    @Autowired
    private IShowService showService;//拉比展示
    /**
     * 跳转到banner管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "banner.html";
    }

    /**
     * 跳转到添加banner管理
     */
    @RequestMapping("/banner_add")
    public String bannerAdd() {
        return PREFIX + "banner_add.html";
    }

    /**
     * 跳转到修改banner管理
     */
    @RequestMapping("/banner_update/{bannerId}")
    public String bannerUpdate(@PathVariable Integer bannerId, Model model) {
    	//课程分类
    	 Wrapper<CourseCat> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
    	 wrapper.eq("status", Const.ACTIVE);//数据有效标识 A
    	 List<CourseCat> listcourseCat=courseCatService.selectList(wrapper);
    	 model.addAttribute("listcourseCat", listcourseCat);
    	 
    	//banner详情
        Banner banner = bannerService.selectById(bannerId);
        String goto_info="";
        //根据类型和外键id查询
    	if("product".equals(banner.getType())) {
    		//商品
    		Product product= productService.selectById(banner.getGotoInfo());
    		if(product!=null) {
    			goto_info= product.getName();
    		}
    	}else if("video".equals(banner.getType())) {
    		//视频
    		ProdVideo prodvideo = prodVideoService.selectById(banner.getGotoInfo());
    		if(prodvideo!=null) {
    			goto_info= prodvideo.getName();
    		}
    	}else if("course".equals(banner.getType())) {
    		//课程 
    		Course course = courseService.selectById(banner.getGotoInfo());
    		if(course!=null) {
    			goto_info= course.getName();
    		}
    	}else if("coach".equals(banner.getType())) {
    		//教练
    		Coach coach = coachService.selectById(banner.getGotoInfo());
    		if(coach!=null) {
    			goto_info= coach.getName();
    		}
    	}else if("show".equals(banner.getType())) {
    		//拉比展示
    		Show show = showService.selectById(banner.getGotoInfo());
    		if(show!=null) {
    			goto_info=show.getTitle();
    		}
    	}
    	banner.setInfoDes(goto_info);
    	
        model.addAttribute("item", banner);
        LogObjectHolder.me().set(banner);
        return PREFIX + "banner_edit.html";
    }

    /**
     * 获取banner管理列表
     * @param condition 名称
     * @param position 所属模块位置
     * @param type 类型
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,String position,String type) {
        Page<Banner> page = new PageFactory<Banner>().defaultPage();
        //List<Map<String, Object>> result = bannerService.getBnnerList(page, "");

        Wrapper<Banner> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime",false);
        Integer depid = ShiroKit.getUser().getDeptId();//所属公司
        if(depid!=Const.IS_ADMIN_FLG) {
        	wrapper.eq("gs_type", depid);
        }
        
        //名称
        if(condition!=null && condition!="") {
            wrapper.like("name", condition);
        }
        //位置
        if(position!=null && position!="") {
            wrapper.eq("position", position);
        }
        //类型
        if(type!=null && type!="") {
            wrapper.eq("type", type);
        }
        return super.packForBT(bannerService.selectPage(page,wrapper));
//		page.setRecords(bannerService.getBnnerList(page, ""));
//		return ;
    }

    /**
     * 新增banner管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Banner banner) {
    	
    	//如果所属模块不是首页  banner位置默认1
    	if(banner.getPosition()!=1) {
    		banner.setIndexes(1);//banner位置顶部
    	}
    	
    	//所属公司
    	banner.setGsType(ShiroKit.getUser().getDeptId());
        //添加时间
        banner.setCreatetime(new Date());
        bannerService.insert(banner);
        return SUCCESS_TIP;
    }

    /**
     * 删除banner管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer bannerId) {
        bannerService.deleteById(bannerId);
        return SUCCESS_TIP;
    }

    /**
     * 修改banner管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Banner banner) {
    	
    	//如果所属模块不是首页  banner位置默认1
    	if(banner.getPosition()!=1) {
    		banner.setIndexes(1);//banner位置顶部
    	}
    	
    	banner.setOprtime(new Date());//操作时间参数
        bannerService.updateById(banner);
        return SUCCESS_TIP;
    }

    /**
     * banner管理详情
     */
    @RequestMapping(value = "/detail/{bannerId}")
    @ResponseBody
    public Object detail(@PathVariable("bannerId") Integer bannerId) {
    	Banner banner = bannerService.selectById(bannerId);
    	String goto_info="";
    	if("product".equals(banner.getType())) {
    		//商品
    		goto_info=  banner.getGotoInfo()==null?"":productService.selectById(banner.getGotoInfo()).getName();
    	}else if("video".equals(banner.getType())) {
    		//视频
    		goto_info=  banner.getGotoInfo()==null?"":prodVideoService.selectById(banner.getGotoInfo()).getName();
    	}else if("course".equals(banner.getType())) {
    		//课程 
    		goto_info=  banner.getGotoInfo()==null?"":courseService.selectById(banner.getGotoInfo()).getName();
    	}else if("coach".equals(banner.getType())) {
    		goto_info=  banner.getGotoInfo()==null?"":coachService.selectById(banner.getGotoInfo()).getName();
    	}
    	banner.setInfoDes(goto_info);
        return banner;
    }
    
    /**
     * 
     * @param Keyword 搜索关键字
     * @param type 所属模块
     * @return
     */
    @RequestMapping(value = "/geturl")
    @ResponseBody
    public Object geturl(String Keyword, String  type) {
    	
    	 if(type.equals("coach")) {
    		//教练 
    		 Wrapper<Coach> wrapper = new EntityWrapper<>();
             wrapper.orderBy("createtime",false);
    		 if(Keyword!=null && Keyword!="") {
    			 wrapper.like("name", Keyword);
    		 }
    		return  coachService.selectList(wrapper);
    	 }else if(type.equals("product")) {
    		 //商品
    		 Wrapper<Product> wrapper = new EntityWrapper<>();
             wrapper.orderBy("createtime",false);
    		 if(Keyword!=null && Keyword!="") {
    			 wrapper.like("name", Keyword);
    		 }
    		return  productService.selectList(wrapper);
    	 }else if(type.equals("video")) {
    		 //视频 
//    		 Wrapper<ProdVideo> wrapper = new EntityWrapper<>(); 
//    		 if(Keyword!=null && Keyword!="") {
//    			 wrapper.like("name", Keyword);
//    		 }
//    		return  prodVideoService.selectList(wrapper);
    		 Wrapper<Product> wrapper = new EntityWrapper<>();
             wrapper.orderBy("createtime",false);
    		 if(Keyword!=null && Keyword!="") {
    			 wrapper.like("name", Keyword);
    		 }
    		return  productService.selectList(wrapper);
    	 }else if(type.equals("course")) {
    		 //课程
    		 Wrapper<Course> wrapper = new EntityWrapper<>();
             wrapper.orderBy("createtime",false);
    		 if(Keyword!=null && Keyword!="") {
    			 wrapper.like("name", Keyword);
    		 }
    		return  courseService.selectList(wrapper);
    	 }else if(type.equals("show")) {
    		//拉比展示
    		 Wrapper<Show> wrapper = new EntityWrapper<>();
             wrapper.orderBy("createtime",false);
    		 if(Keyword!=null && Keyword!="") {
    			 wrapper.like("title", Keyword);
    		 }
    		 return  showService.selectList(wrapper);
    	 }
    	return "";
    }
    public static void main(String[] args) {
		boolean flg=false;
		if(flg) {
			System.out.println("a");
		}else {
			System.out.println("b");
		}
	}
}
