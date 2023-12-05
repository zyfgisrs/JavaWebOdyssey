# Redis数据库连接池

## 依赖配置

`commons-pool2`是Apache Commons提供的一个高效、可伸缩的对象池框架，用于管理对象的池化（Pooling），以便可以重用这些对象。这个框架是Java语言编写的，主要用于管理那些创建成本高昂、需要重用的资源，比如数据库连接、线程和其他类似的重资源。

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>2.12.0</version>
</dependency>
```

## 构建连接池

```java
package com.zhouyf.utils;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class RedisConnectionPoolUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConnectionPoolUtils.class);
    private static final String REDIS_HOST = "192.168.136.128";

    private static final int REDIS_PORT = 6379;

    private static final String REDIS_USERNAME = "default";

    private static final String REDIS_PASSWORD = "12345";

    private static final int DATABASE_INDEX = 0;

    private static final int MAX_IDLE = 2;

    private static final int MIN_IDLE = 2;

    private static final int MAX_TOTAL = 2;

    private static RedisClient client = null;

    private static GenericObjectPool<StatefulRedisConnection<String, String>> pool = null;


    //// 构建对象池的静态方法。
    private static synchronized void initializePool() {
        RedisURI redisURI = RedisURI.Builder.redis(REDIS_HOST, REDIS_PORT).withAuthentication(REDIS_USERNAME, REDIS_PASSWORD).
                withDatabase(DATABASE_INDEX).build();

        // 创建 Redis 客户端
        client = RedisClient.create(redisURI);
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(MAX_IDLE);
        config.setMinIdle(MIN_IDLE);
        config.setMaxTotal(MAX_TOTAL);
        // 创建一个通用对象池，用于管理 Redis 连接。
        pool = ConnectionPoolSupport.createGenericObjectPool(() -> client.connect(), config);
    }

    // 提供一个静态方法从池中借用 Redis 连接。
    public static StatefulRedisConnection<String, String> getConnection() {
        if (pool == null) {
            initializePool();
        }

        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RedisConnectionException("Error borrowing connection from the pool", e);
        }
    }

    public static void returnConnection(StatefulRedisConnection<String, String> connect) {
        if (connect != null) {
            pool.returnObject(connect);
        }
        LOGGER.debug("归还连接");
    }

    public static synchronized void closePool() {

        if (pool != null) {
            pool.close();
            pool = null;
        }
        if (client != null) {
            client.shutdown();
            client = null;
        }
        LOGGER.debug("关闭连接池");
    }

}
```

- 通过管理连接的生命周期，`returnConnection` 方法帮助维护连接池的稳定性和可靠性，防止因连接过多而导致的系统崩溃或性能下降。
- 通过归还连接，连接池可以重用这些已经建立的连接，而不是每次需要时都创建新连接。这减少了创建和销毁连接的开销。

## 测试连接池

```java
package com.zhouyf.test;

import com.zhouyf.utils.RedisConnectionPoolUtils;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisPoolTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisPoolTest.class);

    public static void main(String[] args) throws Exception {
        StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtils.getConnection();
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future = commands.set("id", "0001");
        LOGGER.debug("设置数据的处理结果: {}", future.get());
        RedisFuture<String> id = commands.get("id");
        LOGGER.debug("id为:{}", id.get());
        RedisConnectionPoolUtils.returnConnection(connection);
        RedisConnectionPoolUtils.closePool();
    }
}
```

```
DEBUG com.zhouyf.test.RedisPoolTest -- 设置数据的处理结果: OK
DEBUG com.zhouyf.test.RedisPoolTest -- id为:0001
DEBUG com.zhouyf.utils.RedisConnectionPoolUtils -- 归还连接
DEBUG com.zhouyf.utils.RedisConnectionPoolUtils -- 关闭连接池
```

