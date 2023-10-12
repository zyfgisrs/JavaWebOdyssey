package com.zhouyf.test;

import com.zhouyf.pojo.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-pojo.xml")
public class PersonTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonTest.class);

    @Autowired
    private Person person;

    @Test
    void test(){
        LOGGER.info("测试Bean配置");
        System.out.println(person);
    }
}
