package com.zhouyf.dao;

import java.util.List;

public interface IMessageDao {
    public List<String> findAll();

    public List<String> findOne();
}
