package com.labi.common.constant.dictmap;

import com.labi.common.constant.dictmap.base.AbstractDictMap;

/**
 * 公司的映射
 *
 * @author lyr
 * @date 2017-05-06 15:01
 */
public class DeptDict extends AbstractDictMap {

    @Override
    public void init() {
        put("deptId", "公司名称");
        put("num", "公司排序");
        put("pid", "上级名称");
        put("simplename", "公司简称");
        put("fullname", "公司全称");
        put("tips", "备注");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("deptId", "getDeptName");
        putFieldWrapperMethodName("pid", "getDeptName");
    }
}
