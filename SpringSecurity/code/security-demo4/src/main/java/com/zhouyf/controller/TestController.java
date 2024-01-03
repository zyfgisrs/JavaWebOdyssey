package com.zhouyf.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test1")
    @PreAuthorize("hasAuthority('sys:employee:list')")
    public String test1(){
        return "sys:employee:list";
    }


    @PostMapping ("/test2")
    @PreAuthorize("hasAuthority('site:online:list')")
    public String test2(){
        return "site:online:list";
    }
}
