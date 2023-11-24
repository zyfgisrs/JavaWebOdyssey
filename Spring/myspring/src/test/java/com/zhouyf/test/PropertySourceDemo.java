package com.zhouyf.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertyResolver;

import java.util.HashMap;

public class PropertySourceDemo {
    public final static Logger LOGGER = LoggerFactory.getLogger(PropertySourceDemo.class);

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhouyf");
        map.put("age", 12);
        MapPropertySource source = new MapPropertySource("url", map);
        Object age = source.getProperty("age");
        LOGGER.info("age = {}", age);
    }




}
