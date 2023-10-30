package com.zhouyf.chapter6;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorExample1 {
    public static void main(String[] args) {
        List<String> list = Stream.of("a", "b", "c").collect(Collectors.toList());
        System.out.println(list);

        String str = Stream.of("a", "b", "c").collect(Collectors.joining(","));
        System.out.println(str);
    }
}
