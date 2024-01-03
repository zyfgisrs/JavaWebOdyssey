package com.zhouyf.service;

import com.zhouyf.model.User;
import com.zhouyf.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IUserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    //INSERT INTO `user` ( id, username, password, phone, info, status, balance, create_time, update_time )
        // VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
    void testSaveById(){
        User user = new User();
        user.setUsername("lilei");
        user.setPassword("dshfhsj");
        user.setBalance(8000);
        user.setInfo("是一名学生");
        user.setPhone("15365116735");
        user.setUpdateTime(LocalDate.of(2021, 2,3));
        user.setStatus(1);
        user.setCreateTime(LocalDate.of(2020, 7,8));
        boolean save = userService.save(user);
        System.out.println(save);
    }

    @Test
    //SELECT id,username,password,phone,info,status,balance,create_time,update_time FROM `user`
    void testQueryByName(){
        List<User> list = userService.list();
        for (User user : list) {
            System.out.println(list);
        }
    }
}