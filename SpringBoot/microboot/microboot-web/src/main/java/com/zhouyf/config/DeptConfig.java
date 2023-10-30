package com.zhouyf.config;

import com.zhouyf.vo.Dept;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties

public class DeptConfig {
    @Bean
    public Dept createDept() {
        return new Dept();
    }
}
