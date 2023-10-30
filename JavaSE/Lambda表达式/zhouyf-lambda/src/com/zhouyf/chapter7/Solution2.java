package com.zhouyf.chapter7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution2 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 7);
        List<Integer> collect = list.stream().filter(num -> num % 2 == 1).collect(Collectors.toList());
        System.out.println(collect);
    }
}
