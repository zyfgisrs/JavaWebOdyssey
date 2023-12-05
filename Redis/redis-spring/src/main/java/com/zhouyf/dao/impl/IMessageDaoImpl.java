package com.zhouyf.dao.impl;

import com.zhouyf.dao.IMessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IMessageDaoImpl implements IMessageDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMessageDaoImpl.class);

    @Override
    public List<String> findAll() {
        LOGGER.info("【MessageDAO数据层调用】调用findAll()方法查询消息表全部数据");
        return List.of("java", "c++", "rust");
    }

    @Override
    public List<String> findOne() {
        LOGGER.info("【MessageDAO数据层调用】调用findOne()方法查询消息表全部数据");
        return List.of("php");
    }
}
