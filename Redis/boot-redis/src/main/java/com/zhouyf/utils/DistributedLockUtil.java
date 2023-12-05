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
