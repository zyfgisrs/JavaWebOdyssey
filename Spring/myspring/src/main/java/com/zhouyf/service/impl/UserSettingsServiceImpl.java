package com.zhouyf.service.impl;

import com.zhouyf.pojo.UserSettings;
import com.zhouyf.service.UserSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsServiceImpl implements UserSettingsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void save(UserSettings userSettings) {
        String sql = "INSERT INTO usersettings (userId, theme) VALUES (?, ?)";
        int effectedRows = jdbcTemplate.update(sql,
                userSettings.getUserId(), userSettings.getTheme());
        if(effectedRows > 0){
            LOGGER.info("用户设置保存成功");
        }
    }
}
