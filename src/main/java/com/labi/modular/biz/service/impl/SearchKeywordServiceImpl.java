package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.SearchKeywordMapper;
import com.labi.modular.biz.model.SearchKeyword;
import com.labi.modular.biz.service.ISearchKeywordService;
import org.springframework.stereotype.Service;

/**
 * 搜索推荐词 服务实现类
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class SearchKeywordServiceImpl extends ServiceImpl<SearchKeywordMapper, SearchKeyword> implements ISearchKeywordService {

}
