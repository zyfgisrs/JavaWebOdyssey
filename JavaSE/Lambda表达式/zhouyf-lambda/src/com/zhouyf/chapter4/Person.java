package com.zhouyf.chapter4;

public class Person {
    private String name;

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
