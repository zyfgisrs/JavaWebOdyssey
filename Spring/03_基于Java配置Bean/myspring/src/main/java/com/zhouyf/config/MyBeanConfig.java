package com.zhouyf.config;

import com.zhouyf.pojo.MyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class MyBeanConfig {
    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

   @Bean
    public List<String> nameList() {
        return List.of("张三", "李四", "王五");
    }

    @Bean
    public Map<String, Integer> scoresMap1() {
        return Map.of("张三", 85, "李四", 90, "王五", 95);
    }

    @Bean
    public Map<String, Integer> scoresMap2() {
        return Map.of("张三", 85, "李四", 90, "王五", 95);
    }

}
