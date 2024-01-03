package com.zhouyf.demo1;

import java.lang.reflect.Method;

public class DEMO1 {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.zhouyf.demo1.Dog");
        Method info = clazz.getMethod("info", String.class, int.class);
        String name = info.getName();//获取方法的名称
        System.out.println("方法名称：" + name);
        Class<?> returnType = info.getReturnType();//获取方法的返回类型
        System.out.println("方法的返回类型：" + returnType);
        Class<?>[] parameterTypes = info.getParameterTypes();
        for (Class<?> parameterType : parameterTypes) {
            System.out.println("方法参数类型：" + parameterType.getName());
        }
    }
}
