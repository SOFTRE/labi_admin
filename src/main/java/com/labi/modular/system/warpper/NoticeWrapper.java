package com.labi.modular.system.warpper;

import com.labi.common.constant.factory.ConstantFactory;
import com.labi.common.warpper.BaseControllerWarpper;

import java.util.Map;

/**
 * 公司列表的包装
 *
 * @author lyr
 * @date 2017年4月25日 18:10:31
 */
public class NoticeWrapper extends BaseControllerWarpper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Integer creater = (Integer) map.get("creater");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
