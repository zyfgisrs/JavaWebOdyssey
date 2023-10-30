package com.zhouyf.chapter6;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsExample2 {
    public static void main(String[] args) {
        Person p1 = new Person("tom", 12);
        Person p2 = new Person("lucy", 13);
        Person p3 = new Person("zhouyf", 34);
        Person p4 = new Person("jim", 32);
        Person p5 = new Person("mike", 31);
        Person p6 = new Person("russ", 31);
        List<Person> persons = Arrays.asList(p1, p2, p3, p4, p5, p6);
        Map<Integer, Long> count = persons.stream()
                .collect(Collectors.groupingBy(p -> p.getAge(),
                        Collectors.counting()
                ));
        System.out.println(count);
    }
}
