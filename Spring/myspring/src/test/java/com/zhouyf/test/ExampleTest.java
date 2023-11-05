package com.zhouyf.test;

import com.zhouyf.config.MapBeanConfig;
import com.zhouyf.pojo.Example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapBeanConfig.class)
public class ExampleTest {
    @Autowired
    private Example example;

    @Test
    public void test(){
        System.out.println(example);
    }
}
