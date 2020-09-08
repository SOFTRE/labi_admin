package com.labi.modular.biz.warpper;

import java.util.Map;

import com.labi.common.constant.factory.ConstantFactory;
import com.labi.common.warpper.BaseControllerWarpper;

/**
 * 拉比展示列表的包装类
 *
 * @author lyr
 * @date 2017年4月5日22:56:24
 */
public class ShowWarpper extends BaseControllerWarpper {

    public ShowWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("catName", ConstantFactory.me().getShowCatName(Integer.parseInt(map.get("catId").toString())));
    }

}
