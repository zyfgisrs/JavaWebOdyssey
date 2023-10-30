package com.zhouyf.chapter4;

import java.util.ArrayList;

public class StreamPerson {
    public static void main(String[] args) {
        ArrayList<Person> peoples = new ArrayList<>();
        String[] names = {"zhouyf", "tom", "lucy", "mike", "lining", "xiaoming", "bob", "james"};
        int[] ages = {12, 23, 34, 33, 11, 44, 42, 66};
        for (int i = 0; i < names.length; i++) {
            peoples.add(new Person(names[i], ages[i]));
        }
        peoples.stream().filter(p -> p.getAge() > 20).sorted((p1, p2) -> {
            return p1.getName().compareTo(p2.getName());
        }).map(p -> p.getName()).forEach(name -> System.out.println(name));
    }
}
