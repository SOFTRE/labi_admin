package com.labi.modular.biz.service.impl;

import com.labi.modular.biz.model.Message;
import com.labi.modular.biz.dao.MessageMapper;
import com.labi.modular.biz.service.IMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信消息 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
