package com.zhouyf.service.impl;

import com.zhouyf.pojo.Message;
import com.zhouyf.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private Message message;

    @Override
    public void echo() {
        System.out.println("【消息推送】" + message);
    }
}
