package com.zhouyf.chapter5;

import java.util.List;

public class Student {
    private String name;

    private int age;

    private List<Course> courses;


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", courses=" + courses +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Student(String name, int age, List<Course> courses){
        this.name = name;
        this.age = age;
        this.courses = courses;
    }
}
