package com.zhouyf.test;

import com.zhouyf.StartRedisApplication;
import com.zhouyf.vo.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = StartRedisApplication.class)
@ExtendWith(SpringExtension.class)

public class TemplateSerializerTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(TemplateSerializerTest.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void test1() {
        Book book = new Book("西游记", "吴承恩", 100);
        redisTemplate.opsForValue().set("book1", book);
    }

    @Test
    void test2() {
        Book book = (Book) redisTemplate.opsForValue().get("book1");
        LOGGER.info("【书】name = {}, author = {}, price = {}", book.getName(), book.getAuthor(), book.getPrice());
    }
}
