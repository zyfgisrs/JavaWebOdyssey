# RedisTemplate

## 配置类

```java
package com.zhouyf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisTemplateConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(
            @Autowired LettuceConnectionFactory factory
            ){
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        return template;
    }
}
```

## 测试

```java
package com.zhouyf.test;

import com.zhouyf.StartRedisApplication;
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

public class TemplateTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(TemplateTest.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void test() {
        redisTemplate.opsForValue().set("name", "zhouyf");
        LOGGER.info("【普通数据】name = {}", redisTemplate.opsForValue().get("name"));

        redisTemplate.opsForHash().put("member:zyf", "name", "zhouyf");
        redisTemplate.opsForHash().put("member:zyf", "age", "24");
        redisTemplate.opsForHash().put("member:zyf", "id", "0001");
        LOGGER.info("【member:zyf】name = {}", redisTemplate.opsForHash().get("member:zyf","name"));
        LOGGER.info("【member:zyf】age = {}", redisTemplate.opsForHash().get("member:zyf","age"));
        LOGGER.info("【member:zyf】id = {}", redisTemplate.opsForHash().get("member:zyf","id"));
    }
}
```

```
INFO com.zhouyf.test.TemplateTest -- 【普通数据】name = zhouyf
INFO com.zhouyf.test.TemplateTest -- 【member:zyf】name = zhouyf
INFO com.zhouyf.test.TemplateTest -- 【member:zyf】age = 24
INFO com.zhouyf.test.TemplateTest -- 【member:zyf】id = 0001
```

