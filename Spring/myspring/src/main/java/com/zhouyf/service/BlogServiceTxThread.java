package com.zhouyf.service;

import java.util.concurrent.CountDownLatch;

public interface BlogServiceTxThread {
    void add(CountDownLatch latch);
}
