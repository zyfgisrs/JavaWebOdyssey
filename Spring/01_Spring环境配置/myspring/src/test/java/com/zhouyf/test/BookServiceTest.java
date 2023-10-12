package com.zhouyf.test;

import com.zhouyf.pojo.Book;
import com.zhouyf.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class BookServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceTest.class);
    @Autowired
    private BookServiceImpl bookService;


    @Test
    void test(){
        Book book = new Book("三国演义", 12.2);
        LOGGER.info("【LOGGER】插入一本书");
        bookService.insertBook(book);
    }
}
