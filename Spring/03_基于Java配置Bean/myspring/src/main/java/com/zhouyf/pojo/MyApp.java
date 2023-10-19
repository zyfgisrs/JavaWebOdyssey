package com.zhouyf.pojo;

import org.springframework.beans.factory.annotation.Value;

public class MyApp {
    @Value("${myapp.message}")
    private String message;

    public void start(){
        System.out.println(message);
    }
}
