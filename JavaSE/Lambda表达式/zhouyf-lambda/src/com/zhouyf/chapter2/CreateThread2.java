package com.zhouyf.chapter2;

public class CreateThread2 {
    public static void main(String[] args) {
        Dog dog = new Dog();
        new Thread(dog).start();
    }
}
