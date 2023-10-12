package com.zhouyf.test;

import com.zhouyf.pojo.Dog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-pojo.xml")
public class DogTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DogTest.class);


    @Autowired
    private Dog dog;

    @Test
    void test(){
        LOGGER.info("测试Bean的生命周期回调");
        dog.sayHello();
    }
}
