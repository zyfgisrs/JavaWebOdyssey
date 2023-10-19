package com.zhouyf.test;

import com.zhouyf.config.MyAppConfig;
import com.zhouyf.pojo.MyApp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MyAppConfig.class)
public class MyAppTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(MyAppTest.class);
    @Autowired
    private MyApp myApp;


    @Test
    void test(){
        LOGGER.info("测试Bean配置.....");
        myApp.start();
    }
}
