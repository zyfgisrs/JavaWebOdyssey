# Redis分布锁的实现

## 配置线程池

```java
package com.zhouyf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);  // 核心线程数
        executor.setMaxPoolSize(20);   // 最大线程数
        executor.setQueueCapacity(100);  // 队列大小
        executor.setThreadNamePrefix("MyExecutor-"); // 线程名称前缀
        executor.initialize();
        return executor;
    }
}
```

## 分布式锁工具类

```java
package com.zhouyf.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DistributedLockUtil {


    final StringRedisTemplate stringRedisTemplate;

    public DistributedLockUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 分布式锁的处理操作方法，当前抢占到资源的线程可以实现数据的锁定
     * @param key 锁的唯一标识符
     * @param userId 用户ID，用于标识锁的持有者
     * @param timeout 锁有效时间
     * @param timeUnit 锁的有效时间单位
     * @return 是否拿到锁
     */
    public boolean lock(String key, String userId, Long timeout, TimeUnit timeUnit){
        String value = userId + ":" + System.currentTimeMillis();
        //setIfAbsent 方法相当于 Redis 的 SETNX 命令
        //如果设置成功（表示锁被成功获取），方法返回 true。
        if(Boolean.TRUE.equals(this.stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit))){
            return true;
        }

        String executor = this.stringRedisTemplate.opsForValue().get(key);
        if(StringUtils.hasLength(executor)){
            String oldValue = this.stringRedisTemplate.opsForValue().get(key);
            if(StringUtils.hasLength(oldValue) && oldValue.startsWith(userId)){
                this.stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
                return true;
            }
        }
        return false;
    }

    public void unlock(String key, String userId){
        String value = this.stringRedisTemplate.opsForValue().get(key);
        log.debug("资源操作完毕，释放掉锁资源：{}", value);

        if(StringUtils.hasLength(value) && value.startsWith(userId)){
            this.stringRedisTemplate.opsForValue().getOperations().delete(key);
        }
    }

}
```

## 任务

```java
package com.zhouyf.task;

import com.zhouyf.utils.DistributedLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ResourceTask {
    private static final String BUSINESS_KEY = "zhouyf-resource";

    final DistributedLockUtil distributedLockUtil;

    public ResourceTask(DistributedLockUtil distributedLockUtil) {
        this.distributedLockUtil = distributedLockUtil;
    }

    @Async("taskExecutor")
    public void handle(String userid) {
        //操作之前先要获取到分布式锁，锁的方法返回true才可以执行资源处理
        if (this.distributedLockUtil.lock(BUSINESS_KEY, userid, 3L, TimeUnit.SECONDS)) {
            log.debug("【{}】获取锁，开始进行业务处理。", Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("【{}】进行业务处理完毕，释放锁。", Thread.currentThread().getName());
            this.distributedLockUtil.unlock(BUSINESS_KEY, userid);
        } else {
            log.debug("【{}】未获取到锁，无法进行资源处理。", Thread.currentThread().getName());
        }
    }
}
```

## 测试

```java
package com.zhouyf.test;

import com.zhouyf.StartDistributedLockApplication;
import com.zhouyf.task.ResourceTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = StartDistributedLockApplication.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Slf4j
public class TestResourceTask {
    @Autowired
    private ResourceTask resourceTask;

    @Test
    void test(){
        for(int x = 0; x < 10; x++){
            this.resourceTask.handle("zhouyf" + x);
        }
    }
}
```

执行结果

```
com.zhouyf.task.ResourceTask             : 【MyExecutor-6】获取锁，开始进行业务处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-2】未获取到锁，无法进行资源处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-1】未获取到锁，无法进行资源处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-3】未获取到锁，无法进行资源处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-7】未获取到锁，无法进行资源处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-9】未获取到锁，无法进行资源处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-10】未获取到锁，无法进行资源处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-5】未获取到锁，无法进行资源处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-8】未获取到锁，无法进行资源处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-4】未获取到锁，无法进行资源处理。
com.zhouyf.task.ResourceTask             : 【MyExecutor-6】进行业务处理完毕，释放锁。
com.zhouyf.utils.DistributedLockUtil     : 资源操作完毕，释放掉锁资源：zhouyf5:1701764347654
```

