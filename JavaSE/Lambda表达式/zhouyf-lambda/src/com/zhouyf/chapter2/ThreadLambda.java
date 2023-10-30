package com.zhouyf.chapter2;

public class ThreadLambda {
    public static void main(String[] args) {
        new Thread(()-> System.out.println("使用Lambda表达式创建线程")).start();
    }
}
