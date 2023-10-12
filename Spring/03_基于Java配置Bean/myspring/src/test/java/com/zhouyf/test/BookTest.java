package com.zhouyf.test;

import com.zhouyf.config.BookConfig;
import com.zhouyf.pojo.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookConfig.class)
public class BookTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookTest.class);

    @Autowired
    @Qualifier("bookBean1")
    private Book book1;

    @Autowired
    @Qualifier("bookBean2")
    private Book book2;

    @Test
    void test(){
        LOGGER.info("测试Bean配置");
        System.out.println(book1);
        System.out.println(book2);
    }
}
