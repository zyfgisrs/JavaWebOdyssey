# `HASH` 数据类型

```java
package com.zhouyf.test;

import com.zhouyf.utils.RedisConnectionPoolUtils;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class HashTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HashTest.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtils.getConnection();
        RedisAsyncCommands<String, String> commands = connection.async();
        commands.flushall();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "zhouyf");
        map.put("age", "11");
        map.put("id", "0001");
        LOGGER.debug("【Hash数据】: 设置一组Hash数据{}", commands.hset("member", map).get());
        LOGGER.debug("【Hash数据】: name = {}", commands.hget("member", "name").get());
        LOGGER.debug("【Hash数据】: age = {}", commands.hget("member", "age").get());
        LOGGER.debug("【Hash数据】: id = {}", commands.hget("member", "id").get());
    }
}

```

# List数据类型

```java
package com.zhouyf.test;

import com.zhouyf.utils.RedisConnectionPoolUtils;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class ListTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HashTest.class);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtils.getConnection();
        RedisAsyncCommands<String, String> commands = connection.async();
        commands.flushall();
        RedisFuture<Long> future1 = commands.lpush("list1", "java", "c++", "go", "rust");
        LOGGER.debug("左边push数据:{}", future1.get());
        LOGGER.debug("list1:{}", commands.lrange("list1", 0, -1).get());
        RedisFuture<Long> future2 = commands.rpush("list2", "php", "javascript","rust");
        LOGGER.debug("右边push数据:{}", future2.get());
        LOGGER.debug("list2:{}", commands.lrange("list2", 0, -1).get());
        LOGGER.debug("list1右边删除数据:{}", commands.rpop("list1").get());
        LOGGER.debug("list1:{}", commands.lrange("list1", 0, -1).get());
    }
}
```

# SET数据类型

```java
package com.zhouyf.test;

import com.zhouyf.utils.RedisConnectionPoolUtils;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class SetTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetTest.class);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtils.getConnection();
        RedisAsyncCommands<String, String> commands = connection.async();
        commands.flushall();
        RedisFuture<Long> sadd1 = commands.sadd("language1", "java", "c++", "go", "php");
        LOGGER.debug("创建language1数据: {}", sadd1.get());
        RedisFuture<Long> sadd2 = commands.sadd("language2", "rust", "c", "go", "php");
        LOGGER.debug("创建language2数据: {}", sadd2.get());
        //获取set集合成员数
        LOGGER.debug("language1中的成员数:{}", commands.scard("language1").get());

        //交集
        LOGGER.debug("language1和language2交集:{}",commands.sinter("language1", "language2").get());

        //并集
        LOGGER.debug("language1和language2并集:{}",commands.sunion("language1", "language2").get());
    }
}
```

# ZSET数据类型

```java
package com.zhouyf.test;

import com.zhouyf.utils.RedisConnectionPoolUtils;
import io.lettuce.core.Range;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ZSetTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZSetTest.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtils.getConnection();
        RedisAsyncCommands<String, String> commands = connection.async();
        commands.flushall();
        commands.zadd("studentScores", 75, "Alice");
        commands.zadd("studentScores", 85, "Bob");
        commands.zadd("studentScores", 90, "Charlie");
        commands.zadd("studentScores", 88, "Eve");
        commands.zadd("studentScores", 92, "Frank");
        LOGGER.debug("Alice的分数:{}", commands.zscore("studentScores", "Alice").get());
        LOGGER.debug("Eve的分数:{}", commands.zscore("studentScores", "Eve").get());

        RedisFuture<List<String>> studentScores = commands.zrangebyscore("studentScores", Range.create(80, 100));
        List<String> list = studentScores.get();
        LOGGER.debug("80-100分的学生:{}", list);
    }
}
```

# 位数据

```java
package com.zhouyf.test;

import com.zhouyf.utils.RedisConnectionPoolUtils;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class BitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BitTest.class);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtils.getConnection();
        RedisAsyncCommands<String, String> commands = connection.async();
        commands.flushall();
        RedisFuture<Long> setbit1 = commands.setbit("zhouyf:bit", 1, 1);
        RedisFuture<Long> setbit2 = commands.setbit("zhouyf:bit", 5, 1);
        LOGGER.debug("第1位为{}", commands.getbit("zhouyf:bit", 1).get());
        LOGGER.debug("第5位为{}", commands.getbit("zhouyf:bit", 5).get());
    }
}
```

# hyperloglog

```java
package com.zhouyf.test;

import com.zhouyf.utils.RedisConnectionPoolUtils;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HyperloglogTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HyperloglogTest.class);

    public static void main(String[] args) {
        StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtils.getConnection();
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<Long> future = commands.pfadd("hll", "user1", "user2", "user3");
        commands.pfcount("hll").thenAccept(count -> {
            LOGGER.debug("用户的个数为：{}", count);
        });
    }
}
```

