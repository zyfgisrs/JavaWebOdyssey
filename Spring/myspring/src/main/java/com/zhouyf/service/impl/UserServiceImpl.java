package com.zhouyf.service.impl;

import com.zhouyf.pojo.User;
import com.zhouyf.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (id, username, password) VALUES (?, ?, ?)";
        int effectedRows = jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getPassword());
        if(effectedRows > 0){
            LOGGER.info("用户创建成功");
        }
    }
}
