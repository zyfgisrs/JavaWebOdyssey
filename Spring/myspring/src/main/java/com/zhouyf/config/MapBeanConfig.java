package com.zhouyf.config;

import com.zhouyf.pojo.Example;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MapBeanConfig {
    @Bean("mapBean")
    public MapFactoryBean mapFactoryBean(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "zyf");
        hashMap.put("age", "12");
        MapFactoryBean mapFactoryBean = new MapFactoryBean();
        mapFactoryBean.setSourceMap(hashMap);
        return mapFactoryBean;
    }

    @Bean
    public Example example(){
        return new Example();
    }
}
