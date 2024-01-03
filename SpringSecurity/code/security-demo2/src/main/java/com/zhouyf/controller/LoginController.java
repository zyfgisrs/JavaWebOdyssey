package com.zhouyf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }


}
