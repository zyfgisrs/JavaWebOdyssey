package com.zhouyf.config;

import com.zhouyf.pojo.Author;
import com.zhouyf.pojo.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfig {
    @Bean
    public Book bookBean1(){
        return new Book(authorBean1(), "格林童话");
    }

    @Bean
    public Book bookBean2(){
        return new Book(authorBean2(), "安徒生童话");
    }

    @Bean
    public Author authorBean1(){
        return new Author("格林");
    }

    @Bean
    public Author authorBean2(){
        return new Author("安徒生");
    }
}
