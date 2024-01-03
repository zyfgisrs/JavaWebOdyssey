package com.zhouyf.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ZhouyfDemo {
    public static void main(String[] args) {
        AtomicLong num = new AtomicLong(100L);
        System.err.println(num.compareAndSet(200L, 300L));
        System.err.println(num);
        System.out.println(num.compareAndSet(100L, 300L));
        System.out.println(num);
    }
}
