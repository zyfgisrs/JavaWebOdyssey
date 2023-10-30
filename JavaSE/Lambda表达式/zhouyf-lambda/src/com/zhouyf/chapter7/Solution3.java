package com.zhouyf.chapter7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution3 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("dsjo", "sjdsoj", "sjdhi", "ss", "skjdsijf");
        List<String> result = list.stream().sorted((a, b) -> a.length() - b.length()).collect(Collectors.toList());
        System.out.println(result);
    }
}
