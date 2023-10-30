package com.zhouyf.chapter7;

import java.util.function.Function;

public class Solution6 {
    public static void main(String[] args) {
        Function<Integer, Integer> doubleNumber = n -> n * 2;

        Function<Integer, Integer> addThree = n -> n + 3;

        Function<Integer, Integer> combine = doubleNumber.andThen(addThree);

        Integer apply = combine.apply(34);
        System.out.println(apply);
    }
}
