package com.zhouyf.random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random(888);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.printf("【%s】线程生成的随机数:[%d]\n", Thread.currentThread().getName(), random.nextInt(10));
            }, i + "").start();
        }

        TimeUnit.MILLISECONDS.sleep(400);
        Random random1 = new Random(888);
        for (int i = 0; i < 10; i++) {
            System.out.print(random1.nextInt(10));
        }
        System.out.println();


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                ThreadLocalRandom current = ThreadLocalRandom.current();
                System.out.printf("【%s】线程生成的随机数:[%d]\n",
                        Thread.currentThread().getName(), current.nextInt(10));
            }, i + "").start();
        }
    }
}
