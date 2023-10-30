package com.zhouyf.chapter5;

import java.util.*;
import java.util.stream.Collectors;

public class StreamSolution {
    public static void main(String[] args) {
        Course math = new Course("Math", 90);
        Course english = new Course("English", 80);
        Course biology = new Course("Biology", 70);
        Course history = new Course("History", 20);
        Course physics = new Course("Physics", 95);

        Student student1 = new Student("Alice", 21, Arrays.asList(math, english));
        Student student2 = new Student("Bob", 21, Arrays.asList(biology, history));
        Student student3 = new Student("Charlie", 22, Arrays.asList(math, physics, biology));
        Student student4 = new Student("David", 22, Arrays.asList(history, english));
        Student student5 = new Student("Eva", 22, Arrays.asList(physics, biology));
        Student student6 = new Student("Frank", 19, Arrays.asList(math, history));
        Student student7 = new Student("Grace", 19, Arrays.asList(english, biology));
        Student student8 = new Student("Helen", 19, Arrays.asList(math, physics));

        List<Student> students = Arrays.asList(student1, student2, student3, student4,
                student5, student6, student7, student8);

        //找出所有大于20岁的学生
        students.stream().filter(s -> s.getAge() > 20).
                forEach(s -> System.out.println(s.getName()));

        //找出所有学生中分数最高的学科
        Optional<Course> max = students.stream().
                flatMap(student -> student.getCourses().stream()).max((a, b) -> {
                    return a.getScore() - b.getScore();
                });
        max.ifPresent(System.out::println);

        //计算所有学生的平均年龄
        OptionalDouble average = students.stream().mapToDouble(s -> s.getAge()).average();
        average.ifPresent(System.out::println);

        //列出所有参加了math课程的学生
        List<String> studentsMath = students.stream().
                filter(student -> student.getCourses().stream().
                        anyMatch(course -> "Math".equals(course.getCourseName())))
                .map(student -> student.getName()).collect(Collectors.toList());

        System.out.println(studentsMath);

        //找出所有分数都超过60分的学生
        List<String> students60 = students.stream().filter(
                student -> student.getCourses().stream().
                        allMatch(course -> course.getScore() > 60)
                ).map(student -> student.getName()).
                collect(Collectors.toList());
        System.out.println(students60);

        //创建一个按年龄分组的学生的列表
        Map<Integer, List<Student>> groupByAge = students.stream()
                .collect(Collectors.groupingBy(student -> student.getAge()));
        System.out.println(groupByAge);


        Map<Integer, List<String>> NameGroupByAge = students.stream().collect(
                Collectors.groupingBy(
                        s -> s.getAge(),
                        Collectors.mapping(s -> s.getName(), Collectors.toList())
                )
        );
        System.out.println(NameGroupByAge);
    }
}
