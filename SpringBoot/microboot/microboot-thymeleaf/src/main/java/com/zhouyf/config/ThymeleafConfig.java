package com.zhouyf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
@Configuration
//标记该类是一个配置类，用于定义Bean或与Spring框架的集成。
public class ThymeleafConfig implements WebMvcConfigurer {
    //实现了 WebMvcConfigurer 接口。这意味着这个类可以用来配置Spring MVC的特定功能。
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.TRADITIONAL_CHINESE);
        //这个方法创建了一个 SessionLocaleResolver 对象，
        //并设置了其默认区域为简体中文，然后返回这个对象。
        return slr;
    }

    public LocaleChangeInterceptor localeChangeInterceptor() {
        //它创建并返回一个 LocaleChangeInterceptor 对象。
        // 这个拦截器用于检测请求参数中的 lang 参数，以动态更改语言设置。
        LocaleChangeInterceptor lcl = new LocaleChangeInterceptor();
        lcl.setParamName("lang");
        return lcl;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());//添加拦截器
    }
    //这个方法是 WebMvcConfigurer 接口的一个方法。
    // 当Spring MVC启动时，它会调用这个方法
    // 这个方法里面的代码会把 LocaleChangeInterceptor 注册到Spring MVC的拦截器中。
    // 这意味着，对于每一个请求，LocaleChangeInterceptor 都会被触发，从而实现了根据请求参数更改语言的功能。
}
