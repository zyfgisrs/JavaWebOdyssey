package com.zhouyf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhouyf.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class UserTest {
    @Autowired
    private UserMapper userMapper;


    @Test
    void test() {
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
        //查询名字中带o以及balance大于等于1000的数据
    void test1() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username", "info", "phone")
                .like("username", "o")
                .gt("balance", 1000.0);
        List<User> users = userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }


    @Test
        //更新username为jack的balance为2500
    void test2() {
        User user = new User();
        user.setBalance(2500);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", "jack");
        int update = userMapper.update(user, wrapper);
        System.out.println(update);
    }


    @Test
        //将jack,tom,rose的balance减200
    void test3() {
        List<String> usernames = List.of("rose", "tom", "jack");
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>().
                setSql("balance = balance - 200").
                in("username", usernames);
        int update = userMapper.update(null, wrapper);
        System.out.println(update);
    }

    @Test
        //将jack,tom,rose的balance减200
    void test4() {
        List<String> usernames = List.of("rose", "tom", "jack");
        int amount = 200;
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<User>().
                in(User::getUsername, usernames);
        int update = userMapper.updateBalanceById(wrapper, amount);
    }
}
