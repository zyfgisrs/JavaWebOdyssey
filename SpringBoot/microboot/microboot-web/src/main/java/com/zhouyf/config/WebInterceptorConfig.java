package com.zhouyf.config;

import com.zhouyf.interceptor.DefaultHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getDefaultHandlerInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor getDefaultHandlerInterceptor() {
        return new DefaultHandlerInterceptor();
    }
}



