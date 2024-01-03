package com.zhouyf.demo3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyUtil {
    public static Star createProxy(Star s) {
        Star star = (Star)Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(), new Class[]{Star.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if(method.getName().equals("sing")){
                            System.out.println("准备场地，收钱");
                        } else if (method.getName().equals("dance")) {
                            System.out.println("化妆，准备跳舞，收费");
                        }
                        return method.invoke(s, args);
                    }
                });
        return star;
    }
}
