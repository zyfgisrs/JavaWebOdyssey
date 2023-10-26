package com.zhouyf.config;

import com.zhouyf.vo.Dept;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeptConfig {
    @Bean
    public Dept createDept() {
        return new Dept();
    }
}
