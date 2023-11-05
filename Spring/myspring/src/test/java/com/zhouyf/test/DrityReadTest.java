package com.zhouyf.test;

import com.zhouyf.config.HikariDataSourceConfig;
import com.zhouyf.config.TransactionConfig;
import com.zhouyf.service.impl.BlogServiceDirtyRead;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TransactionConfig.class, HikariDataSourceConfig.class})
public class DrityReadTest {

    @Autowired
    private BlogServiceDirtyRead blogServiceDirtyRead;

    @Test
    void test() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);  // 你启动了两个线程，所以计数为2

        blogServiceDirtyRead.add(latch);

        latch.await();  // 等待所有线程完成
    }
}
