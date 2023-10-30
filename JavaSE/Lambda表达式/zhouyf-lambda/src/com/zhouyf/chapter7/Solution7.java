package com.zhouyf.chapter7;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution7 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("skjdf", "sjdso", "ss", "s");
        Map<String, Integer> result = list.stream().collect(Collectors.toMap(
                str -> str,
                str -> str.length()
        ));

        System.out.println(result);
    }
}
