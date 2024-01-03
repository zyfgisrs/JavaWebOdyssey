package com.zhouyf.config;

import com.zhouyf.web.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtilsConfig {

    @Bean
    public JwtUtils jwtUtils(){
        return new JwtUtils();
    }
}
