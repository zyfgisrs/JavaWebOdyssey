# Pipeline流水线

Redis Pipeline（流水线）是一种用于提高数据库操作效率的技术，尤其在执行大量命令时表现突出。在Redis中，Pipeline的核心思想是将多个命令打包，然后一次性发送给服务器，而不是发送一个命令，等待回应后再发送下一个。这样做的主要目的是减少网络传输所带来的延迟。

- 当需要插入或更新大量数据时，使用Pipeline可以显著减少操作时间。
- 在对性能要求高的场景，比如实时系统或高频交易系统中，Pipeline可以减少网络延迟的影响。

## 范例

```java
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
```

