package com.zhouyf.chapter6;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsPartitioningBy {
    public static void main(String[] args) {
        Person p1 = new Person("tom", 12);
        Person p2 = new Person("lucy", 12);
        Person p3 = new Person("zhouyf", 12);
        Person p4 = new Person("jim", 32);
        Person p5 = new Person("mike", 32);
        Person p6 = new Person("russ", 50);
        List<Person> persons = Arrays.asList(p1, p2, p3, p4, p5, p6);

        Map<Integer, Integer> collect = persons.stream().collect(Collectors.groupingBy(
                person -> person.getAge(),
                Collectors.summingInt(p -> p.getAge())
        ));

        Map<Integer, Integer> collect1 = persons.stream().collect(Collectors.groupingBy(
                person -> person.getAge(),
                Collectors.reducing(
                        0,
                        p -> p.getAge(),
                        (a1, a2) -> a1 + a2
                )
        ));

        System.out.println(collect1);
    }
}
