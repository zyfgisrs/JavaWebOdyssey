package com.zhouyf.config;

import com.zhouyf.pojo.Author;
import com.zhouyf.pojo.Book;
import com.zhouyf.pojo.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@EnableAspectJAutoProxy()
@Configuration
@ComponentScan(basePackages = {"com.zhouyf.service", "com.zhouyf.aspect"})
public class BookConfig {
    @Bean
    public Book bookBean(){
        return new Book(authorBean(), "格林童话");
    }

    @Bean
    public Author authorBean(){
        return new Author("格林");
    }

    @Bean
    public Message getMessage() throws ParseException {
        return new Message("巴以冲突", "zhouyf",
                new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-01"));
    }
}
