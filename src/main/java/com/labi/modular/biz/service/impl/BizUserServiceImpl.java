package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.BizUserMapper;
import com.labi.modular.biz.dao.OrderPayMapper;
import com.labi.modular.biz.model.BizUser;
import com.labi.modular.biz.service.IBizUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class BizUserServiceImpl extends ServiceImpl<BizUserMapper, BizUser> implements IBizUserService {

    @Resource
    private BizUserMapper bizUserMapper;

    @Override
    public Map<String, Object> loadUserTotalCount(String type) {
        Map<String, Object> map = bizUserMapper.loadUserTotalCount(type).get(0);
        if(StringUtils.isNotBlank(type)){
            Object totalMoney = map.get("totalCount");
            Object totalMoney1 = bizUserMapper.loadUserBeforeTotalCount(type).get(0).get("totalCount");
            BigDecimal proportion = new BigDecimal(totalMoney.toString()).divide( new BigDecimal(totalMoney1.toString()),10, RoundingMode.CEILING).multiply(new BigDecimal(100));
            map.put("proportion",proportion);
        } else {
            map.put("proportion",100);
        }
        return map;
    }
}
