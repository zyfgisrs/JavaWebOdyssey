package com.zhouyf.jdkProxy;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        Dog dog = new Dog();
        DogInvocationHandler dogInvocationHandler = new DogInvocationHandler(dog);
        Action action = (Action) Proxy.newProxyInstance(
                Action.class.getClassLoader(),
                //一个Class对象数组，每一个元素都是你希望代理的接口。
                new Class[]{Action.class},
                //一个InvocationHandler实例，当代理的方法被调用时，会关联到这个处理器上
                dogInvocationHandler
        );

        action.eat();
    }
}
