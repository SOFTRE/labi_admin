package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.ProblemMapper;
import com.labi.modular.biz.model.Problem;
import com.labi.modular.biz.service.IProblemService;
import org.springframework.stereotype.Service;

/**
 * 常见问题 服务实现类
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements IProblemService {

}
