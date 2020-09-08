package com.labi.modular.system.service.impl;

import com.labi.modular.system.dao.NoticeMapper;
import com.labi.modular.system.model.Notice;
import com.labi.modular.system.service.INoticeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 站内消息 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
