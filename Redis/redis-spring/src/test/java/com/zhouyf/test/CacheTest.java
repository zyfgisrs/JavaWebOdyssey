package com.zhouyf.test;

import com.zhouyf.StartRedisApplication;
import com.zhouyf.service.IMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ContextConfiguration(classes = StartRedisApplication.class)
@ExtendWith(SpringExtension.class)
public class CacheTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(CacheTest.class);

    @Autowired
    private IMessageService iMessageService;

    @Test
    void test(){
        List<String> list1 = iMessageService.list1();
        List<String> list2 = iMessageService.list2();
        LOGGER.debug("【测试类中】{}", list1);
        LOGGER.debug("【测试类中】{}", list2);
    }
}
