package com.zhouyf.test;

import com.zhouyf.config.BookConfig;
import com.zhouyf.pojo.Author;
import com.zhouyf.pojo.Book;
import com.zhouyf.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookConfig.class)
public class BookServiceTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookServiceTest.class);


    @Autowired
    private BookService bookService;

    @Test
    public void test(){
        bookService.remove();
        bookService.show();
        bookService.add(new Book(new Author("鲁迅"), "朝花夕拾"));
    }
}
