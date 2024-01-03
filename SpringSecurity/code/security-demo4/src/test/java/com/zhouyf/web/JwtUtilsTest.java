package com.zhouyf.web;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;


@SpringBootTest
class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;
    @Test
    void test(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhouyf");
        String token = jwtUtils.createToken(map);
        System.out.println(token);
    }


    @Test
    void test1(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhouyf");
        map.put("age", 11);
        String token = jwtUtils.createToken(map);
        Claims claims = jwtUtils.parseTokens(token);
        System.out.println(claims);
    }

}