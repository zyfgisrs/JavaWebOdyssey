package com.zhouyf;

import java.util.List;

public class Demo3 {
    public static void main(String[] args) {

    }


    public void func1(List<? super Children> list){
        for (Object o : list) { //只能使用Object接收
            System.out.println(o);
        }

        list.add(new Children());
        list.add(new Grandson());//可以添加Children或者Childre的子类
    }


    public void func2(List<? extends Children> list){
        for (Parent o : list) {
            System.out.println(o);
        }

        for (Children o : list) {
            System.out.println(o);
        }

//        list.add(new Parent());//错误
//        list.add(new Children());//错误
//        list.add(new Grandson());//错误
//        list.add(new Object());//错误
    }
}

class Parent{

}

class Children extends Parent{

}

class Grandson extends Children{}
