package com.zhouyf.pojo;

public class Dog {
    private String name;


    public void init(){
        System.out.println("DogBean初始化");
    }


    public void destroy(){
        System.out.println("DogBean销毁");
    }

    public void sayHello(){
        System.out.println("Hello from " + this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
