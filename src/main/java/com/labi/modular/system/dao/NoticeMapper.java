package com.labi.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.modular.system.model.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * <p>
  * 通知表 Mapper 接口
 * </p>
 *
 * @author lyr
 * @since 2017-07-11
 */
public interface NoticeMapper extends BaseMapper<Notice> {
    List<Map<String, Object>> list(@Param("condition") String condition,@Param("type")Integer type);
}