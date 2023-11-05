package com.zhouyf.service.impl;


import com.zhouyf.pojo.User;
import com.zhouyf.pojo.UserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCreateService {
    @Autowired
    private UserSettingsServiceImpl userSettingsService;

    @Autowired
    private UserServiceImpl userService;

    @Transactional
    public void createUserWithDefaultSettings(User user) {
        userService.save(user);

        UserSettings userSettings = new UserSettings();
        userSettings.setUserId(user.getId());
        userSettings.setTheme("default");
        userSettingsService.save(userSettings);
    }
}
