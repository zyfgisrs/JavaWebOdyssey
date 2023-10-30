package com.zhouyf.chapter4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDistinct {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 1, 1, 1, 2, 2, 2, 3, 3, 4);
        List<Integer> newList = list.stream().distinct().collect(Collectors.toList());
        newList.forEach(num -> System.out.print(num + " "));
    }
}
