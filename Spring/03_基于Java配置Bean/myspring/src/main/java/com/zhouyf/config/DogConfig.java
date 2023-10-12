package com.zhouyf.config;

import com.zhouyf.pojo.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DogConfig {
    @Bean
    public Dog dogBean(){
        return new Dog("Buddy", 3);
    }
}
