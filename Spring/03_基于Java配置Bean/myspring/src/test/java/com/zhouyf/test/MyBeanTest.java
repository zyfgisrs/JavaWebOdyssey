package com.zhouyf.test;

import com.zhouyf.config.MyBeanConfig;
import com.zhouyf.pojo.MyBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MyBeanConfig.class)
public class MyBeanTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBeanConfig.class);

    @Autowired
    private MyBean myBean;

    @Test
    void test(){
        LOGGER.info("测试bean配置");
        System.out.println(myBean);
    }

}
