package com.zhouyf.demo3;


import com.zhouyf.demo2.Person;

public class Test {
    public static void main(String[] args) {
        BigStar wang = new BigStar("王力宏");
        Star proxy = ProxyUtil.createProxy(wang);
        String dance = proxy.dance();
        System.out.println(dance);

        String sing = proxy.sing();
        System.out.println(sing);
    }
}
