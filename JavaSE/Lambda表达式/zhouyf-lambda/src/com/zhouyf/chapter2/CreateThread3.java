package com.zhouyf.chapter2;

public class CreateThread3 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("通过匿名内部类创建线程");
            }
        }).start();
    }
}
