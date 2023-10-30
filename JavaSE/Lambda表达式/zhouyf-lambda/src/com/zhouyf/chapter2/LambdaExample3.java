package com.zhouyf.chapter2;

public class LambdaExample3 {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Thread using lambda expression");
        }).start();
    }
}
