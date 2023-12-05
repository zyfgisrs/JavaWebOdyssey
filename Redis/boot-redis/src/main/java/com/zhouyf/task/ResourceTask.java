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
