package com.zhouyf.chapter2;

public class Dog implements Runnable{

    @Override
    public void run() {
        System.out.println("通过实现Runnable接口创建线程");
    }
}
