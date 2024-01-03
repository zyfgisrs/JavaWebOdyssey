package com.zhouyf.controller;

import com.zhouyf.domin.dto.LoginParams;
import com.zhouyf.service.impl.UmsSysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UmsSysUserServiceImpl userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginParams loginParams){
        log.info("登录信息========>{}", loginParams);
        String token = userService.login(loginParams);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("token", token);
        result.put("msg", "登录成功");
        return result;
    }
}
