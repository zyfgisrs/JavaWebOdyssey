package com.zhouyf.threadfactory;


import java.lang.reflect.Constructor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ZhouThreadFactory implements ThreadFactory {
    private static final String TITLE = "zhouyf-";
    private static final AtomicInteger count = new AtomicInteger(0);

    private ZhouThreadFactory() {
    }

    private final static ZhouThreadFactory INSTANCE = new ZhouThreadFactory();

    public static ZhouThreadFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Thread newThread(Runnable r) {
        try {
            Class<?> clazz = Class.forName("java.lang.Thread");
            Constructor<?> constructor = clazz.getConstructor(Runnable.class, String.class);
            return (Thread) constructor.newInstance(r, TITLE + count.getAndIncrement());
        } catch (Exception e) {
            return null;
        }
    }
}

class test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ZhouThreadFactory.getInstance().newThread(() -> {
                System.out.println(Thread.currentThread().getName());
            }).start();
        }
    }
}