package com.zhouyf.chapter2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class LambdaExample1 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("zhouyf", "tom", "lucy");
        names.forEach(name -> System.out.println(name));

        names.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }
}
