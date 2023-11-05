package com.zhouyf.test;

import com.zhouyf.config.HikariDataSourceConfig;
import com.zhouyf.config.TransactionConfig;
import com.zhouyf.pojo.User;
import com.zhouyf.service.impl.UserCreateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HikariDataSourceConfig.class, TransactionConfig.class})
public class UserCreateServiceTest {
    @Autowired
    private UserCreateService userCreateService;


    @Test
    void test(){
        User user = new User();
        user.setId(1000003L);
        user.setUsername("zhouyf");
        user.setPassword("2563085");
        userCreateService.createUserWithDefaultSettings(user);
    }
}
