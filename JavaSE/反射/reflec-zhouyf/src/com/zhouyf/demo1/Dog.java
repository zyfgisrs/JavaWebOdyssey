package com.zhouyf.demo1;

public class Dog {
    private String name;


    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public Dog(String name) {
        this.name = name;
    }

    private Dog(int age){
        this.age = age;
    }

    public void show(){
        System.out.println(this.name + " : " + this.age);
    }


    public String info(String id, int num){
        return this.name + id + num;
    }

}
