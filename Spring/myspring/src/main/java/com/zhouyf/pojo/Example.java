package com.zhouyf.pojo;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Data
public class Example {
    @Value("#{T(com.zhouyf.utils.MapUtil).createMap()}")
    private Map<String, String> map1;

    @Value("#{{'name':'zyf', 'age':'12'}}")
    private Map<String, String> map2;

    @Value("#{mapBean}")
    private Map<String, String> map3;

    @Value("#{&mapBean}")
    private FactoryBean<Map<String, String>> factoryBean;

    @Value("#{1 + 12}")
    private Integer age;
}
