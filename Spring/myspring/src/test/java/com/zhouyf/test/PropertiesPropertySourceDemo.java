package com.zhouyf.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertiesPropertySourceDemo {
    public final static Logger LOGGER = LoggerFactory.getLogger(PropertiesPropertySourceDemo.class);
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/test/resources/source.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Properties properties = new Properties();
        properties.load(inputStream);
        PropertiesPropertySource source1 = new PropertiesPropertySource("source", properties);
        Map<String, Object> map = Map.of("id", "123", "address", "changchun");
        MapPropertySource source2 = new MapPropertySource("map", map);
        MutablePropertySources source = new MutablePropertySources();
        source.addLast(source1);
        source.addLast(source2);
        PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(source);
        String name = resolver.resolvePlaceholders("${name}"); //存在
        String name1 = resolver.resolvePlaceholders("${name1}");//不存在
        System.out.println(name);
        System.out.println(name1);
        String name2 = resolver.resolveRequiredPlaceholders("${name}"); //存在
//        String name3 = resolver.resolveRequiredPlaceholders("${name1}");
//         不存在的话将会报错
        System.out.println(name2);
    }
}
