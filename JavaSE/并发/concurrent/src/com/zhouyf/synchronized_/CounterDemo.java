package com.zhouyf.synchronized_;

public class CounterDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++){
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++){
                    Counter.increment();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        System.out.println(Counter.getCount());
    }
}
