package com.zhouyf.chapter3;

public class Lambda1 {
    public static void main(String[] args) {
        int x = 1;
        Runnable r = () -> System.out.println(x);
//        x = 10;
    }
}
