package com.zhouyf.config;

import com.zhouyf.interceptor.IdempotentInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.handlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor handlerInterceptor() {
        return new IdempotentInterceptor();
    }
}
