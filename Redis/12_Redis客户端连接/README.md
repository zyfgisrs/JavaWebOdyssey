# lettuce依赖

```xml
<dependencies>
    <dependency>
        <groupId>io.lettuce</groupId>
        <artifactId>lettuce-core</artifactId>
        <version>6.3.0.RELEASE</version>
    </dependency>
</dependencies>
```

# 连接Redis服务器

测试连接

```java
package com.zhouyf.test;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRedisClient {
        private static final String REDIS_HOST = "192.168.136.128";

        private static final int REDIS_PORT = 6379;

        private static final String REDIS_USERNAME = "default";

        private static final String REDIS_PASSWORD = "12345";

        private static final int DATABASE_INDEX = 0;

        private static final Logger LOGGER = LoggerFactory.getLogger(TestRedisClient.class);


    public static void main(String[] args) {
        RedisURI redisURI = RedisURI.Builder.redis(REDIS_HOST, REDIS_PORT)
                .withAuthentication(REDIS_USERNAME, REDIS_PASSWORD)
                .withDatabase(DATABASE_INDEX)
                .build();
        RedisClient redisClient = RedisClient.create(redisURI);
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            LOGGER.debug("获取redis连接 {}", redisClient);

            // 使用连接执行命令
            RedisCommands<String, String> commands = connection.sync();
            String pingResponse = commands.ping();
            LOGGER.debug("Redis PING 响应: {}", pingResponse);
        } catch (Exception e) {
            LOGGER.error("连接Redis时发生异常", e);
        }
    }
}
```

`DEBUG com.zhouyf.test.TestRedisClient -- 获取redis连接 io.lettuce.core.RedisClient@5049d8b2`

`DEBUG com.zhouyf.test.TestRedisClient -- Redis PING 响应: PONG`

