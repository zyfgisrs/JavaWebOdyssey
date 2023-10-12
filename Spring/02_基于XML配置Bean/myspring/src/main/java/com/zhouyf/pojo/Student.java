package com.zhouyf.pojo;

public class Student {
    private String name;

    private String id;


    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        System.out.println("使用构造器");
    }



    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
