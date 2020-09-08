package com.labi.modular.biz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.common.constant.Const;
import com.labi.common.constant.factory.PageFactory;
import com.labi.common.controller.BaseController;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.shiro.ShiroKit;
import com.labi.core.util.SpringContextHolder;
import com.labi.modular.biz.model.BizAgent;
import com.labi.modular.biz.model.BizAgentBalanceRecord;
import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.service.IBizAgentBalanceRecordService;
import com.labi.modular.biz.service.IBizAgentService;
import com.labi.modular.biz.service.IBizUserService;
import com.labi.modular.biz.warpper.BizAgentWarpper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 分销商管理控制器
 */
@Controller
@RequestMapping("/bizAgent")
public class BizAgentController extends BaseController {

    private String PREFIX = "/biz/bizAgent/";

    @Autowired
    private IBizAgentService bizAgentService;

    /**
     * 跳转到分销商管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "bizAgent.html";
    }

    /**
     * 跳转到添加分销商管理
     */
    @RequestMapping("/bizAgent_add")
    public String bizAgentAdd() {
        return PREFIX + "bizAgent_add.html";
    }

    /**
     * 跳转到选择分销商
     */
    @RequestMapping("/choose")
    public String chooseUser() {
        return PREFIX + "chooseAgent.html";
    }

    /**
     * 跳转到修改分销商管理
     */
    @RequestMapping("/bizAgent_update/{bizAgentId}")
    public String bizAgentUpdate(@PathVariable Integer bizAgentId, Model model) {
        BizAgent bizAgent = bizAgentService.selectById(bizAgentId);
        model.addAttribute("item", bizAgent);
        LogObjectHolder.me().set(bizAgent);
        return PREFIX + "bizAgent_edit.html";
    }

    /**
     * 获取分销商管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String beginDate, String endDate) {
        Page<BizAgent> page = new PageFactory<BizAgent>().defaultPage();
        List<Map<String, Object>> result = bizAgentService.selectAllByPage(page, Const.SUCCESS, beginDate, endDate, condition);
        page.setRecords((List<BizAgent>) new BizAgentWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增分销商管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BizAgent bizAgent) {
        bizAgentService.insertAgent(bizAgent);
        return SUCCESS_TIP;
    }

    /**
     * 冻结分销商
     */
    @RequestMapping(value = "/frozen")
    @ResponseBody
    public Object delete(BizAgent bizAgent) {
        bizAgent.setOprtime(new Date());
        bizAgentService.updateById(bizAgent);
        return SUCCESS_TIP;
    }

    /**
     * 修改分销商管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BizAgent bizAgent) {
        bizAgentService.updateAgent(bizAgent);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到分销员收支明细
     */
    @RequestMapping("moneyRecord")
    public String moneyRecord() {
        return PREFIX + "moneyRecord.html";
    }

    /**
     * 分销员收支明细
     *
     * @param bizAgentId
     * @return
     */
    @RequestMapping(value = "/moneyRecord/{bizAgentId}")
    @ResponseBody
    public Object moneyRecord(@PathVariable("bizAgentId") Integer bizAgentId, String condition, String type) {
        Page<BizAgentBalanceRecord> page = new PageFactory<BizAgentBalanceRecord>().defaultPage();

        Wrapper<BizAgentBalanceRecord> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("agent_id", bizAgentId);
        if (StringUtils.isNotBlank(condition)) {
            wrapper.where("instr(remarks,{0})>0", condition);
        }
        if (StringUtils.isNotBlank(type)) {
            wrapper.andNew("type={0}", type);
        }
        return super.packForBT(SpringContextHolder.getBean(IBizAgentBalanceRecordService.class).selectPage(page, wrapper));
    }

    /**
     * 会员关系查看
     */
    @RequestMapping("usernexus/{bizAgentId}")
    public String userNexus(@PathVariable Integer bizAgentId, Model model) {
        model.addAttribute("agentId", bizAgentId);
        return PREFIX + "userNexus.html";
    }

    /**
     * 查询代理商下面的所有会员
     *
     * @param agentId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/usernexus", method = RequestMethod.POST)
    @ResponseBody
    public Object userNexus(Integer agentId) throws Exception {
        //查询代理商下的所有会员
        Wrapper<BizUser> wrapper = new EntityWrapper<>();
        wrapper.orderBy("createtime", false);
        wrapper.eq("agent_id", agentId);
        List<BizUser> users = SpringContextHolder.getBean(IBizUserService.class).selectList(wrapper);
        //查询代理商信息
        BizAgent agent = SpringContextHolder.getBean(IBizAgentService.class).selectById(agentId);
        BizUser agentUser = SpringContextHolder.getBean(IBizUserService.class).selectById(agent.getUserId());
        agentUser.setName(agent.getAgentName());

        Map<Integer, BizUser> map = new HashMap<>();
        users.forEach(u -> {
            map.put(u.getId(), u);
        });
//        Iterator<BizUser> iter = users.iterator();
        //查询下级
        findSubUser(users, agentUser);
        return agentUser;
        //查询全部
//        if(StringUtils.isBlank(userId)){
//            while(iter.hasNext()) {
//                User iterUser = iter.next();
//                String key = iterUser.getRefUserUuid();
//                if(StringUtils.isNotBlank(key)){
//                    User parentUser = map.get(key);
//                    if(parentUser.getChildren() == null){
//                        parentUser.setChildren(new ArrayList<>());
//                        parentUser.getChildren().add(iterUser);
//                    } else {
//                        parentUser.getChildren().add(iterUser);
//                    }
//                }
//            }
//            return JSONObject.toJSONString(map.get("1"));
//        } else {//按用户过滤
//            User user = usersService.findById(userId);
//            if(StringUtils.isNotBlank(user.getRealName())){
//                user.setName(user.getRealName());
//            }
//            //用户的上级
//            User parentUser = findParentUser(users,user);
//            while(!"1".equals(parentUser.getUuid())){
//                parentUser = findParentUser(users,parentUser);
//            }
//            //查询下级
//            findSubUser(users,user);
//            return JSONObject.toJSONString(parentUser);
//        }
    }
//    /**
//     * 查询用户的上级
//     * @param user
//     * @return
//     */
//    private User findParentUser(List<User> users,User user){
//        for(User iterUser  :users) {
//            String key = iterUser.getUuid();
//            if (key.equals(user.getRefUserUuid())) {
//                iterUser.setChildren(new ArrayList<>());
//                iterUser.getChildren().add(user);
//                return iterUser;
//            }
//        }
//        return null;
//    }

    /**
     * 查询用户的下级
     *
     * @param user
     * @return
     */
    private void findSubUser(List<BizUser> users, BizUser user) {
        for (BizUser iterUser : users) {
            Integer key = user.getId();
            if (key.equals(iterUser.getRefUserId())) {
                if (user.getChildren() == null) {
                    user.setChildren(new ArrayList<>());
                    user.getChildren().add(iterUser);
                } else {
                    user.getChildren().add(iterUser);
                }
                findSubUser(users, iterUser);
            }
        }
    }
}
