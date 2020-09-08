package com.labi.modular.biz.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.BizAgent;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分销商 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-22
 */
public interface IBizAgentService extends IService<BizAgent> {

    List<Map<String, Object>> selectAllByPage(Page<BizAgent> page, String progress, String beginDate, String endDate, String condition);

    boolean insertAgent(BizAgent agent);

    boolean updateAgent(BizAgent agent);

    boolean updateAgentApplyFail(Integer agentId, String remarks);

    boolean updateAgentApplySuccess(Integer agentId);
}
