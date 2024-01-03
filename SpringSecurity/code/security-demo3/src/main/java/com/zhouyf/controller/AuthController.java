package com.zhouyf.controller;

import com.zhouyf.domin.dto.LoginParams;
import com.zhouyf.service.impl.UmsSysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final UmsSysUserService userService;

    public AuthController(UmsSysUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginParams loginParams){
        String token = userService.login(loginParams);
        return token;
    }
}
