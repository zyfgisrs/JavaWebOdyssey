
package com.zhouyf.test;

import com.zhouyf.StartDistributedLockApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@SpringBootTest(classes = StartDistributedLockApplication.class)
@ExtendWith(SpringExtension.class)
public class TestRedisTemplate {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void test(){
        this.redisTemplate.opsForValue().set("zhouyf", "zyf");
        log.info("【Redis数据】zhouyf = {}", this.redisTemplate.opsForValue().get("zhouyf"));
    }
}
