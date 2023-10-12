package com.zhouyf.test;

import com.zhouyf.config.DogConfig;
import com.zhouyf.pojo.Dog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DogConfig.class)
public class DogTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(DogTest.class);

    @Autowired
    private Dog dog;

    @Test
    void test(){
        LOGGER.info("测试Bean配置");
        System.out.println(dog);
    }
}
