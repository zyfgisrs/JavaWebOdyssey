package com.zhouyf.chapter4;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamReduce {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3, 4, 4, 5);
        Integer min = list.stream().reduce(-1, (a, b) -> Math.min(a, b));
        System.out.println(min);
    }
}
