package com.labi.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.labi.common.annotion.Permission;
import com.labi.common.annotion.log.BussinessLog;
import com.labi.common.constant.Dict;
import com.labi.common.constant.factory.ConstantFactory;
import com.labi.common.controller.BaseController;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.common.node.ZTreeNode;
import com.labi.core.log.LogObjectHolder;
import com.labi.core.util.ToolUtil;
import com.labi.modular.system.dao.DeptMapper;
import com.labi.modular.system.model.Dept;
import com.labi.modular.system.service.IDeptService;
import com.labi.modular.system.warpper.DeptWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 公司控制器
 *
 * @author lyr
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/system/dept/";

    @Resource
    DeptMapper deptMapper;

    @Resource
    IDeptService deptService;

    /**
     * 跳转到公司管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加公司
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改公司
     */
    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptMapper.selectById(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        return PREFIX + "dept_edit.html";
    }

    /**
     * 获取公司的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptMapper.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 新增公司
     */
    @BussinessLog(value = "添加公司", key = "simplename", dict = Dict.DeptDict)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Dept dept) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //完善pids,根据pid拿到pid的pids
        deptSetPids(dept);
        return this.deptMapper.insert(dept);
    }

    /**
     * 获取所有公司列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.deptMapper.list(condition);
        return super.warpObject(new DeptWarpper(list));
    }

    /**
     * 公司详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        return deptMapper.selectById(deptId);
    }

    /**
     * 修改公司
     */
    @BussinessLog(value = "修改公司", key = "simplename", dict = Dict.DeptDict)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        deptSetPids(dept);
        deptMapper.updateById(dept);
        return SUCCESS_TIP;
    }

    /**
     * 删除公司
     */
    @BussinessLog(value = "删除公司", key = "deptId", dict = Dict.DeleteDict)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer deptId) {
        Wrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + deptId + "]%");
        int flg = deptMapper.selectCount(wrapper);
        if (flg > 0) {
            //拒绝删除
            throw new BussinessException(BizExceptionEnum.LABI_SHOW_MSG);
        } else {
            //缓存被删除的公司名称
            LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

            deptService.deleteDept(deptId);
        }

        return SUCCESS_TIP;
    }

    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("[0],");
        } else {
            int pid = dept.getPid();
            Dept temp = deptMapper.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }
}
