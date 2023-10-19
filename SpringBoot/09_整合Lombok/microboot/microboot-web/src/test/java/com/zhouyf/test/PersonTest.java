package com.zhouyf.test;

import com.zhouyf.vo.Person;

public class PersonTest {
    public static void main(String[] args) {
        Person p = new Person().name("zhouyf").age(20);
        System.out.println(p);
    }
}
