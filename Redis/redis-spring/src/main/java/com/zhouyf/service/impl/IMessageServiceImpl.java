package com.zhouyf.service.impl;

import com.zhouyf.dao.IMessageDao;
import com.zhouyf.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IMessageServiceImpl implements IMessageService {
    private final static Logger LOGGER = LoggerFactory.getLogger(IMessageServiceImpl.class);
    @Autowired
    private IMessageDao messageDao;

    @Override
    public List<String> list1() {
        return messageDao.findOne();
    }

    @Override
    public List<String> list2() {
        return messageDao.findAll();
    }
}
