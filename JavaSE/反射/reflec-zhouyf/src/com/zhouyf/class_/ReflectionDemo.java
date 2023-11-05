package com.zhouyf.class_;

class MyClass implements MyInterface {

}

class AnotherClass {

}

interface MyInterface {

}

public class ReflectionDemo {
    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        Class<?> myClassClass = myClass.getClass();

        if (MyClass.class.isInstance(myClass)) {
            System.out.println("myclass 是 MyClass的实例");
        }

        if (MyInterface.class.isAssignableFrom(myClassClass)) {
            System.out.println("myclass 实现了 MyInterface 接口。");
        }

        try {
            MyInterface myInterface = (MyInterface) myClassClass.cast(myClass);
            System.out.println("类型转换成功");
        } catch (Exception e) {
            System.out.println("类型转换失败");
            throw new RuntimeException(e);
        }

        AnotherClass anotherObject = new AnotherClass();
        if (MyClass.class.isInstance(anotherObject)) {
            System.out.println("anotherObject 是 MyClass 的实例。");
        } else {
            System.out.println("anotherObject 不是 MyClass 的实例。");
        }
    }
}
