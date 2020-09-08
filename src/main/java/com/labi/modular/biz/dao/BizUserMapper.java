package com.labi.modular.biz.dao;

import com.labi.modular.biz.model.BizUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface BizUserMapper extends BaseMapper<BizUser> {

    List<Map<String, Object>> loadUserTotalCount(@Param("type") String type);

    List<Map<String, Object>> loadUserBeforeTotalCount(@Param("type") String type);

}
