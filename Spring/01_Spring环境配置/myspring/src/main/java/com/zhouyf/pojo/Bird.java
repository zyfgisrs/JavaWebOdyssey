package com.zhouyf.pojo;

public class Bird {
    private String name;

    public void sayHello() {
        System.out.println("sayHello from " + this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("正在进行属性注入...");
        this.name = name;
    }
}
