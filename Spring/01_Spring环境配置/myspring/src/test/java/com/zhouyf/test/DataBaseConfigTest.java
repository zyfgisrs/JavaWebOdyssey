package com.zhouyf.test;

import com.zhouyf.config.DataBaseConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Properties;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class DataBaseConfigTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseConfigTest.class);

    @Autowired
    private DataBaseConfig dataBaseConfig;

    @Test
    void test() {
        Properties connectionProps = dataBaseConfig.getConnectionProps();
        LOGGER.info("url:{}", connectionProps.get("url"));
        LOGGER.info("usrname:{}", connectionProps.get("usrname"));
        LOGGER.info("password:{}", connectionProps.get("password"));
    }
}
