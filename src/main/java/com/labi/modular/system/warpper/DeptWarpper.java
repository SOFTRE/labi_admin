package com.labi.modular.system.warpper;

import com.labi.common.constant.factory.ConstantFactory;
import com.labi.core.util.ToolUtil;
import com.labi.common.warpper.BaseControllerWarpper;

import java.util.Map;

/**
 * 公司列表的包装
 *
 * @author lyr
 * @date 2017年4月25日 18:10:31
 */
public class DeptWarpper extends BaseControllerWarpper {

    public DeptWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        Integer pid = (Integer) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}
