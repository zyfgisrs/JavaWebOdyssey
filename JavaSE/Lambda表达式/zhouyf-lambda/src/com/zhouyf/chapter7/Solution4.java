package com.zhouyf.chapter7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution4 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Long sum = list.stream().filter(a -> a > 5).count();
        System.out.println(sum);
    }
}
