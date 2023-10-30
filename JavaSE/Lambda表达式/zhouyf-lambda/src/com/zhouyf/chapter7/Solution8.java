package com.zhouyf.chapter7;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Solution8 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        Predicate<Integer> evenPredicate = n -> n % 2 == 0;

        List<Integer> evenNumbers = filter(list, evenPredicate);
        System.out.println(evenNumbers);

        Predicate<Integer> greaterThanFive = n -> n > 5;

        List<Integer> greaterThanFives = (List<Integer>) filter(list, greaterThanFive);
        System.out.println(greaterThanFives);
    }


    public static <T> List<T> filter(List<T> list, Predicate<T> predicate){
        return list.stream().filter(predicate).
                collect(Collectors.toList());
    }
}
