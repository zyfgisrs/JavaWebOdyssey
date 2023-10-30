package com.zhouyf.chapter4;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMap {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> newList = list.stream().map(num -> num * 2).collect(Collectors.toList());
        newList.forEach(num -> System.out.print(num + " "));
    }
}
