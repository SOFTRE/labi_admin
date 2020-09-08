package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.core.util.DateUtil;
import com.labi.core.util.TimeUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.labi.modular.biz.model.CoachTime;
import com.labi.modular.biz.service.ICoachCatService;
import com.labi.modular.biz.service.ICoachTimeService;
import com.labi.modular.biz.warpper.BizCoachTimeWarpper;

/**
 * 上课时间管理控制器
 *
 */
@Controller
@RequestMapping("/coachTime")
public class CoachTimeController extends BaseController {

	private String PREFIX = "/biz/coachTime/";

	int nextflg = 0;
	@Autowired
	private ICoachTimeService coachTimeService;// 教练上课时间
	@Autowired
	private ICoachCatService coachCatService;// 教练类别

	/**
	 * 跳转到上课时间管理首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "coachTime.html";
	}

	/**
	 * 跳转到添加上课时间管理
	 */
	@RequestMapping("/coachTime_add")
	public String coachTimeAdd(Model model) {
		// Integer depid = ShiroKit.getUser().getDeptId();//所属公司
		// //教练类别
		// Wrapper<CoachCat> wrapper = new EntityWrapper<>();
		// wrapper.orderBy("createtime",false);
		// wrapper.eq("status", Const.ACTIVE);//查询状态为A的数据
		// if(depid!=Const.IS_ADMIN_FLG) {
		// wrapper.eq("type", depid);
		// }
		// List<CoachCat> coachCatList = coachCatService.selectList(wrapper);
		// model.addAttribute("coachCatList", coachCatList);

		return PREFIX + "coachTime_add.html";
	}

	@RequestMapping("/coaclist")
	public String coachlist() {
		return PREFIX + "coachUser.html";
	}

	/**
	 * 跳转到修改上课时间管理
	 */
	@RequestMapping("/coachTime_update/{coachTimeId}")
	public String coachTimeUpdate(@PathVariable Integer coachTimeId, Model model) {

		CoachTime coachTime = coachTimeService.selectById(coachTimeId);
		model.addAttribute("item", coachTime);
		LogObjectHolder.me().set(coachTime);

		Calendar now = Calendar.getInstance();
		// 当前时间往后6天日期
		// Map<Integer, String> dates = Maps.newLinkedHashMap();
		// dates.put(29, "2018-07-31");
		Map<Integer, String> datalist = DateUtil.getDayTime();

		// 小时集合
		Map<Integer, String> hhMap = TimeUtil.setTime();

		List<CoachTime> listcoachtime1 = new ArrayList<CoachTime>();
		List<CoachTime> listcoachtime2 = new ArrayList<CoachTime>();
		List<CoachTime> listcoachtime3 = new ArrayList<CoachTime>();
		List<CoachTime> listcoachtime4 = new ArrayList<CoachTime>();
		List<CoachTime> listcoachtime5 = new ArrayList<CoachTime>();
		List<CoachTime> listcoachtime6 = new ArrayList<CoachTime>();
		List<CoachTime> listcoachtime7 = new ArrayList<CoachTime>();

		Iterator<Map.Entry<Integer, String>> it = datalist.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			i++;
			Map.Entry<Integer, String> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());

			// 根据日期查询当前教练可预约时间
			Wrapper<CoachTime> wrapper = new EntityWrapper<>();
			Integer tomonth = now.get(Calendar.MONTH) + 1;
			Integer today = now.get(Calendar.DAY_OF_MONTH);
			String strmonth = tomonth < 10 ? "0" + tomonth : String.valueOf(tomonth);
			String strday = today < 10 ? "0" + tomonth : String.valueOf(today);
			String day = now.get(Calendar.YEAR) + "-" + strmonth + "-" + strday;
			if (day.equals(entry.getValue())) {
				wrapper.between("start_time",
						entry.getValue() + " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":00",
						entry.getValue() + " 23:59:59");
			} else {
				wrapper.between("start_time", entry.getValue(), entry.getValue() + " 23:59:59");
			}
			wrapper.eq("coach_id", coachTime.getCoachId());
			List<CoachTime> listcoachtimeexist = coachTimeService.selectList(wrapper);

			for (Integer in : hhMap.keySet()) {
				CoachTime timehhmm = new CoachTime();
				String str = hhMap.get(in);// 得到每个key多对用value的值
				timehhmm.setHhmm(str);
				timehhmm.setHhmmKey(String.valueOf(in));
				timehhmm.setDayTime(entry.getValue());
				System.out.println(entry.getValue());
				timehhmm.setWeek(DateUtil.getWeekStr(DateUtil.parseDate(entry.getValue())));

				for (CoachTime coachTime2 : listcoachtimeexist) {
					String stat = str.substring(0, 5) + ":00";
					String end = str.substring(6, 11) + ":00";
					if (coachTime2.getStartTime().getTime() == DateUtil.parseTime(entry.getValue() + " " + stat)
							.getTime()
							|| coachTime2.getEndTime().getTime() == DateUtil.parseTime(entry.getValue() + " " + end)
									.getTime()) {
						timehhmm.setFlg(1);
						break;
					}

				}
				if (i == 1) {
					listcoachtime1.add(timehhmm);
				} else if (i == 2) {
					listcoachtime2.add(timehhmm);
				} else if (i == 3) {
					listcoachtime3.add(timehhmm);
				} else if (i == 4) {
					listcoachtime4.add(timehhmm);
				} else if (i == 5) {
					listcoachtime5.add(timehhmm);
				} else if (i == 6) {
					listcoachtime6.add(timehhmm);
				} else if (i == 7) {
					listcoachtime7.add(timehhmm);
				}
			}
		}
		// model.addAttribute("datalist",datalist);
		model.addAttribute("listcoachtime1", listcoachtime1);
		model.addAttribute("listcoachtime2", listcoachtime2);
		model.addAttribute("listcoachtime3", listcoachtime3);
		model.addAttribute("listcoachtime4", listcoachtime4);
		model.addAttribute("listcoachtime5", listcoachtime5);
		model.addAttribute("listcoachtime6", listcoachtime6);
		model.addAttribute("listcoachtime7", listcoachtime7);
		return PREFIX + "coachTime_edit.html";
	}

	/**
	 * 获取上课时间管理列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String coachName) {
		// 分页类
		Page<CoachTime> page = new PageFactory<CoachTime>().defaultPage();

		Integer depid = ShiroKit.getUser().getDeptId();// 所属公司
		if (depid.intValue() == Const.IS_ADMIN_FLG) {
			depid = null;
		}

		// 参数说明 ： 分页类 教练名称 教练所属公司
		List<Map<String, Object>> result = coachTimeService.getCoachTimeListByName(page, coachName, depid);
		page.setRecords((List<CoachTime>) new BizCoachTimeWarpper(result).warp());
		return super.packForBT(page);
	}

	/**
	 * 新增上课时间管理
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(CoachTime coachTime, Integer userId) {

		// 教练时间段是否已经存在
		Wrapper<CoachTime> wrapper = new EntityWrapper<>();
		if (coachTime.getStartTime() != null) {
			wrapper.eq("start_time", coachTime.getStartTime());
		}
		if (coachTime.getEndTime() != null) {
			wrapper.eq("end_time", coachTime.getEndTime());
		}
		wrapper.eq("coach_id", userId);
		int flg = coachTimeService.selectCount(wrapper);
		if (flg > 0) {
			throw new BussinessException(BizExceptionEnum.COACH_TIME_EOORO);
		}

		System.out.println("userID " + userId);
		coachTime.setCoachId(userId);
		coachTime.setCreatetime(new Date());
		coachTimeService.insert(coachTime);
		return SUCCESS_TIP;
	}

	/***
	 * 
	 * @param coachId
	 *            教练id
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param cssflg
	 *            0添加 1删除
	 * @return
	 */
	@RequestMapping(value = "/addtime")
	@ResponseBody
	public Object addTime(Integer coachId, String startTime, String endTime, String cssflg) {
		// 添加
		if ("0".equals(cssflg)) {
			// 教练时间段是否已经存在
			Wrapper<CoachTime> wrapper = new EntityWrapper<>();
			wrapper.eq("start_time", startTime);
			wrapper.eq("end_time", endTime);
			wrapper.eq("coach_id", coachId);
			int flg = coachTimeService.selectCount(wrapper);
			if (flg > 0) {
				throw new BussinessException(BizExceptionEnum.COACH_TIME_EOORO);
			} else {
				CoachTime coachTime = new CoachTime();
				coachTime.setCoachId(coachId);// 教练id
				coachTime.setStartTime(DateUtil.parseTime(startTime));// 开始时间
				coachTime.setEndTime(DateUtil.parseTime(endTime));// 结束时间
				coachTime.setCreatetime(new Date());// 添加时间
				Boolean inflg = coachTimeService.insert(coachTime);
				System.out.println(inflg);
			}
		} else {
			// 删除
			Wrapper<CoachTime> wrapper = new EntityWrapper<>();
			wrapper.eq("start_time", startTime);
			wrapper.eq("end_time", endTime);
			wrapper.eq("coach_id", coachId);
			Boolean delflg = coachTimeService.delete(wrapper);
			System.err.println(delflg);
		}
		System.out.println(coachId + startTime + endTime);
		return SUCCESS_TIP;
	}

	@RequestMapping(value = "/checktime")
	@ResponseBody
	public Object checkTime(Integer coachId, String startTime, String endTime) {
		// 教练时间段是否已经存在
		Wrapper<CoachTime> wrapper = new EntityWrapper<>();
		wrapper.eq("start_time", startTime);
		wrapper.eq("end_time", endTime);
		wrapper.eq("coach_id", coachId);
		int flg = coachTimeService.selectCount(wrapper);
		if (flg > 0) {
			SUCCESS_TIP.setCode(300);
		}
		return SUCCESS_TIP;
	}

	/**
	 * 删除上课时间管理
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam Integer coachTimeId) {
		coachTimeService.deleteById(coachTimeId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改上课时间管理
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(CoachTime coachTime) {
		System.out.println("sss" + coachTime.getEndTime());
		coachTimeService.updateById(coachTime);
		return SUCCESS_TIP;
	}

	/**
	 * 上课时间管理详情
	 */
	@RequestMapping(value = "/detail/{coachTimeId}")
	@ResponseBody
	public Object detail(@PathVariable("coachTimeId") Integer coachTimeId) {
		return coachTimeService.selectById(coachTimeId);
	}

	public static void main(String[] args) {

		Date date = new Date();
		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
		String currSun = dateFm.format(DateUtil.parseDate("2018-08-01"));
		System.out.println(currSun);

		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate("2018-8-3"));
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        System.out.println(weekDays[w]);
	}
}
