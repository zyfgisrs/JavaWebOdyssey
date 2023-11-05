package com.zhouyf.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class CreateAnimal {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你要创建的动物");
        String animal = scanner.nextLine();
        String className = "com.zhouyf.example." + animal;
        System.out.println("请输入动物的名字");
        String name = scanner.nextLine();

        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(String.class);
        Object o = constructor.newInstance(name);
        System.out.println(o);
    }
}
