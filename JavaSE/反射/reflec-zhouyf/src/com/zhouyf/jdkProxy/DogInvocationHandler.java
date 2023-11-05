package com.zhouyf.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DogInvocationHandler implements InvocationHandler {

    private Object target;

    public DogInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before eating");
        Object result = method.invoke(target, args);
        System.out.println("after eating");
        return result;
    }
}
