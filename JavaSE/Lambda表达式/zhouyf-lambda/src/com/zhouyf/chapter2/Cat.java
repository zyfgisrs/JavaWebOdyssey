package com.zhouyf.chapter2;

public class Cat extends Thread{
    @Override
    public void run() {
        System.out.println("通过继承Thread创建线程");
    }
}
