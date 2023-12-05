# 对象序列化

## 相关依赖

```xml
 <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.16.0</version>
    </dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>2.16.0</version>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.16.0</version>
</dependency>
```

## 配置RedisTemplateConfig配置类

```java
package com.zhouyf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisTemplateConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            @Autowired LettuceConnectionFactory factory
            ){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
```

测试类

```java
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
```

Redis客户端查询

```
"{\"@class\":\"com.zhouyf.vo.Book\",\"name\":\"\xe8\xa5\xbf\xe6\xb8\xb8\xe8\xae\xb0\",\"author\":\"\xe5\x90\xb4\xe6\x89\xbf\xe6\x81\xa9\",\"price\":100.0}"
```

