package com.zhouyf.test;

import com.zhouyf.pojo.Animal;
import com.zhouyf.pojo.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class AnimalTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalTest.class);

    @Autowired
    private Animal animal;

    @Test
    void test() {
        System.out.println(animal);
    }
}
