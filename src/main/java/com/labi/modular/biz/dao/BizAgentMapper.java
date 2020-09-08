package com.labi.modular.biz.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.modular.biz.model.BizAgent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分销商 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-22
 */
public interface BizAgentMapper extends BaseMapper<BizAgent> {

    List<Map<String, Object>> selectAllByPage(@Param("page") Page<BizAgent> page, @Param("progress") String progress, @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                              @Param("condition") String condition);

}
