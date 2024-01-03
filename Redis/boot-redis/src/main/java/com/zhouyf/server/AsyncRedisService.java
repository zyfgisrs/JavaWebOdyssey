package com.zhouyf.server;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncRedisService {
    final RedisTemplate<String, String> redisTemplate;


    public AsyncRedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public CompletableFuture<String> asyncSetAndGet(String key, String value) {
        //CompletableFuture.supplyAsync执行一个异步任务
        return CompletableFuture.supplyAsync(() -> {
            // 使用 SessionCallback 来确保操作在同一个Redis连接中执行
            List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(RedisOperations operations) {
                    operations.multi();//开始一个Redis事务的命令
                    operations.opsForValue().set(key, value);
                    operations.opsForValue().get(key);
                    // 命令被添加到队列中，并不会被立即执行
                    return operations.exec();
                    //使用exec()命令来原子性执行事务
                }
            });

            // 从事务结果中获取并返回最后一个操作的结果
            return (String) txResults.get(txResults.size() - 1);
        });
    }
}
