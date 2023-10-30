package com.zhouyf.chapter7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution1 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("ins", "jsdj", "hsdif", "jdso");
        List<String> newlist = list.stream().map(s -> s.toUpperCase()).collect(Collectors.toList());
        System.out.println(newlist);
    }
}
