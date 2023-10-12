package com.zhouyf.test;

import com.zhouyf.pojo.Bird;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class BirdTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BirdTest.class);

    @Qualifier("lazyBirdBean")
    @Autowired
    @Lazy
    private Bird lazyBird;

    @Qualifier("birdBean")
    @Autowired
    private Bird bird;

    @Test
    void test() {
        LOGGER.info("非延迟化Bean请求");
        bird.sayHello();
        LOGGER.info("非延迟化Bean请求结束");
        LOGGER.info("迟化Bean请求");
        lazyBird.sayHello();
        LOGGER.info("迟化Bean请求结束");
    }

}
