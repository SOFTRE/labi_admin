package com.labi.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.labi.common.node.ZTreeNode;
import com.labi.modular.system.model.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 公司表 Mapper 接口
 * </p>
 *
 * @author lyr
 * @since 2017-07-11
 */
public interface DeptMapper extends BaseMapper<Dept> {


    /**
     * 获取ztree的节点列表
     *
     * @return
     * @date 2017年2月17日 下午8:28:43
     */
    List<ZTreeNode> tree();

    List<Map<String, Object>> list(@Param("condition") String condition);

}