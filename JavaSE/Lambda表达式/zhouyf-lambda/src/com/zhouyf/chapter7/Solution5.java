package com.zhouyf.chapter7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution5 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Adjj", "djj", "Adjh");
        List<String> list1 = list.stream().filter(str -> str.charAt(0) == 'A').collect(Collectors.toList());
        System.out.println(list1);
    }
}
