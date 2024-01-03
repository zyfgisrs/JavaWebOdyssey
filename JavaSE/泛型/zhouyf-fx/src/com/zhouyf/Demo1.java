package com.zhouyf;

import java.util.ArrayList;
import java.util.List;

public class Demo1 {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("ssss");
        new Demo1().show(strings);//编译错误
    }

    public void show(List<? extends Object> s) {
        for (Object o : s) {
            System.out.println(o);
        }
    }
}
