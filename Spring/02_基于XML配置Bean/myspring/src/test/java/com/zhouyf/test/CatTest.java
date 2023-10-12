package com.zhouyf.test;

import com.zhouyf.pojo.Cat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class CatTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(CatTest.class);

    @Qualifier("catSingle")
    @Autowired
    private Cat cat1;

    @Qualifier("catSingle")
    @Autowired
    private Cat cat2;

    @Qualifier("catSingle")
    @Autowired
    private Cat cat3;

    @Qualifier("catProto")
    @Autowired
    private Cat cat4;

    @Qualifier("catProto")
    @Autowired
    private Cat cat5;

    @Test
    void test(){
        LOGGER.info("single对象:{}", cat1.hashCode());
        LOGGER.info("single对象:{}", cat2.hashCode());
        LOGGER.info("single对象:{}", cat3.hashCode());
        LOGGER.info("proto对象:{}", cat4.hashCode());
        LOGGER.info("proto对象:{}", cat5.hashCode());
    }
}
