package com.zhouyf.test;

import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

public class EnvironmentProperty {
    public static void main(String[] args) {
        PropertyResolver resolver = new StandardEnvironment();
        String version = resolver.resolvePlaceholders("${java.specification.version}");
        String name = resolver.resolvePlaceholders("${name}");
        String age = resolver.resolvePlaceholders("${age}");
        System.out.println("java.specification.version = "  + version);
        System.out.println("name = "  + name);
        System.out.println("age = "  + age);
    }
}
