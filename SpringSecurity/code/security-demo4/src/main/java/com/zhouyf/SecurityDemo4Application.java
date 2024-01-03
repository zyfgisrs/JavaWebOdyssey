package com.zhouyf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.zhouyf.mapper")
public class SecurityDemo4Application {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemo4Application.class, args);
    }

}
