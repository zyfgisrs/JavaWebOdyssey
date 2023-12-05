package com.zhouyf.test;

import com.zhouyf.StartRedisApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = StartRedisApplication.class)
@ExtendWith(SpringExtension.class)

public class TestLettuceConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestLettuceConnection.class);

    @Autowired
    private LettuceConnectionFactory factory;//连接工厂

    @Test
    void test(){
        RedisConnection connection = this.factory.getConnection();
        RedisCommands commands = connection.commands();
        String ping = commands.ping();
        LOGGER.debug("ping返回：{}", ping);

    }
}
