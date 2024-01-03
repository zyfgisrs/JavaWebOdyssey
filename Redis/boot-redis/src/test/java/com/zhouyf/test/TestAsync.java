package com.zhouyf.test;

import com.zhouyf.StartDistributedLockApplication;
import com.zhouyf.server.AsyncRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest(classes = StartDistributedLockApplication.class)
@ExtendWith(SpringExtension.class)
public class TestAsync {

    @Autowired
    private AsyncRedisService asyncRedisService;


    @Test
    void test() throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringCompletableFuture = this.asyncRedisService.asyncSetAndGet("zhouyf", "周杨凡");
        log.info("操作的结果为：{}", stringCompletableFuture.get());
    }
}
