package com.zhouyf.test;

import com.zhouyf.StartRedisApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = StartRedisApplication.class)
@ExtendWith(SpringExtension.class)
public class RedisPipelineTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void test(){
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (int i = 0; i < 1000; i++) {
                    connection.stringCommands().set(("key" + i).getBytes(), ("value" + i).getBytes());
                }
                return null; // 必须返回null
            }
        });
    }
}
