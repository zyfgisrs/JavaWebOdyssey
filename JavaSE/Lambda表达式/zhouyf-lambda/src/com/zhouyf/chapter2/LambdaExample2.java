package com.zhouyf.chapter2;

import java.util.HashMap;

public class LambdaExample2 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("tom", 12);
        map.put("zhouyf", 12);
        map.put("lucy", 14);

        map.forEach((name, age) -> System.out.println(name + " is " + age + "years old"));
    }
}
