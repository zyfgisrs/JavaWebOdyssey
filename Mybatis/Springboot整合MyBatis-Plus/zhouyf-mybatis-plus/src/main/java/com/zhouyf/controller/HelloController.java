package com.zhouyf.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试hello")
@RestController
public class HelloController {

    @GetMapping("/hello")
    @Operation(method = "hello")
    public String hello(){
        return "hello";
    }
}
