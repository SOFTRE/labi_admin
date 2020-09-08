package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.model.News;
import com.labi.modular.biz.dao.NewsMapper;
import com.labi.modular.biz.service.INewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
