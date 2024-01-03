package com.zhouyf;


class Box<T extends Number>{
    private T num;

    public Box(T num) {
        this.num = num;
    }

    public void func(){
        long l = num.longValue();
        System.out.println(l);
    }
}

public class Dem02 {
    public static void main(String[] args) {
        Box<Double> integerBox = new Box<>(1.1);
        integerBox.func();
    }
}
