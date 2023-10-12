package com.zhouyf.pojo;

public class Person {
    private String name;
    private int age;

    public void setName(String name) {
        System.out.println("调用了setName方法");
        this.name = name;
    }

    public void setAge(int age) {
        System.out.println("调用了setAge方法");
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
