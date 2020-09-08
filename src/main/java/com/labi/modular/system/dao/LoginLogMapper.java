package com.labi.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.labi.modular.system.model.LoginLog;
import com.labi.modular.system.model.OperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 登录记录 Mapper 接口
 * </p>
 *
 * @author lyr
 * @since 2017-07-11
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {
    /**
     * 获取操作日志
     *
     * @author lyr
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getOperationLogs(@Param("page") Page<OperationLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("logType") String logType, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    /**
     * 获取登录日志
     *
     * @author lyr
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getLoginLogs(@Param("page") Page<LoginLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("deptid") Integer depid, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}