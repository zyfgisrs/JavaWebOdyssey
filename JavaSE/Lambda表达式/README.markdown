# Lambda表达式的简介

## 什么是Lambda表达式

Lambda表达式，简单地说，是一种更简洁、更易读的表示匿名函数的方式。它可以没有名字，并且可以被用作参数或返回值。

从技术上讲，Lambda表达式表示的是一个函数式接口的实例，而不是Lambda表达式本身：当Lambda表达式被赋值给一个函数式接口类型的变量或传递给一个期望函数式接口类型的方法时，Java编译器会自动将Lambda表达式转换为该函数式接口的一个匿名实例。

## Lambda表达式的由来和发展

# 基础语法

## Lambda表达式的基本结构

一个lambda表达式主要由三部分组成

- 参数列表
- 箭头符号
- 执行语句（Lambda体）

通常的格式为：

`(parameters) -> expression`

`(parameters) -> expression`

详细解释：

1. 参数列表
   - 可以没有参数或有一个或多个参数。
   - 参数类型可以显式声明，也可以由编译器推断。例如，`(a, b) -> a + b` 和 `(int a, int b) -> a + b` 都是有效的。
   - 在Lambda表达式中，当参数列表只有一个参数，并且其类型可以被编译器推断出来时，可以省略括号。这是为了简化Lambda表达式的写法。例如：`a -> a * a`。
   - 当有多个参数或没有参数时，不能省略括号。例如，`(a, b) -> a + b` 或 `() -> System.out.println("Hello");`。
   - 当你想显式声明参数的类型时，也必须使用括号。例如，`(String s) -> System.out.println(s);`。
2. 箭头符号
   - 用来分隔参数列表和lambda体。
3. Lambda体
   - 如果表达式的主体包含单个语句，则可以不使用大括号。
   - 如果主体包含多个语句，则必须用大括号括起来。

实例：

1. 无参数的Lambda表达式

```java
() -> System.out.println("Hello Lambda!");
```

2. 有一个参数的Lambda表达式

```java
n -> System.out.println(n)
```

3. 有多个参数的Lambda表达式

```java
(int a, int b) -> System.out.println(a + b);
```

4. 使用大括号的Lambda表达式

```java
(String s) -> {
            String upperCase = s.toUpperCase();
            System.out.println(upperCase);
        };
```

# 函数式接口

函数式接口是Java 8及之后版本中引入的一个概念。其核心思想是它只定义了一个抽象方法的接口。因为有且仅有一个抽象方法，所以它可以无缝地与Lambda表达式结合使用。

定义和特点：

1. **单一抽象方法**：函数式接口中只能有一个抽象方法。这是其最核心的特点。

2. **可以有多个非抽象方法**：尽管函数式接口只能有一个抽象方法，但它可以有多个默认方法、静态方法或私有方法。

3. **@FunctionalInterface 注解**：虽然函数式接口不是必须使用`@FunctionalInterface`注解，但使用它是个好习惯。这个注解可以帮助开发者确保接口中不会意外地添加第二个抽象方法。如果试图这样做，编译器会报错。

示例：

```java
@FunctionalInterface
public interface SimpleAction {
    void execute();

    // 可以添加默认方法
    default void doNothing() {
        System.out.println("Doing nothing...");
    }

    // 也可以添加静态方法
    static void staticMethod() {
        System.out.println("Static method inside functional interface.");
    }
}
```

在上面的例子中，`SimpleAction`是一个函数式接口，它有一个抽象方法`execute`，一个默认方法`doNothing`和一个静态方法`staticMethod`。

- 为什么函数式接口与Lambda表达式结合得如此紧密？

由于函数式接口只有一个抽象方法，Lambda表达式可以被看作是这个抽象方法的实现。这种设计使得我们可以使用Lambda表达式的简洁语法为函数式接口创建实例，而不需要显式地定义匿名内部类。

#  Lambda表达式的应用场景

## 集合遍历

```java
public class LambdaExample1 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("zhouyf", "tom", "lucy");
        names.forEach(name -> System.out.println(name));
    }
}
```

```java
public class LambdaExample2 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("tom", 12);
        map.put("zhouyf", 12);
        map.put("lucy", 14);

        map.forEach((name, age) -> System.out.println(name + " is " + age + "years old"));
    }
}
```

`forEach`方法接受一个`BiConsumer`接口实例`action`，遍历过程中会调用`action`对象的方法`accept`：

![image-20231027144946208](assets/image-20231027144946208.png)

`BiConsumer`为函数式接口，因此可以使用Lambda表达式来表示一个实例化的接口。Lambda表达式中的执行语句就是遍历集合元素时的需要自定义的操作逻辑，也就是`accept`方法的方法体。

![image-20231027145311755](assets/image-20231027145311755.png)

因此可以使用匿名内部类的方式：

```java
names.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
```

## 创建线程

### 传统创建线程的方法

- 继承Thread类方式

```java
package com.zhouyf.chapter2;

public class Cat extends Thread{
    @Override
    public void run() {
        System.out.println("通过继承Thread创建线程");
    }
}
```

```java
package com.zhouyf.chapter2;

public class CreateThread1 {
    public static void main(String[] args) {
        new Cat().run();
    }
}
```

- 实现`Runnable`接口方法创建线程

```java
package com.zhouyf.chapter2;

public class Dog implements Runnable{

    @Override
    public void run() {
        System.out.println("通过实现Runnable接口创建线程");
    }
}
```

```java
package com.zhouyf.chapter2;

public class CreateThread2 {
    public static void main(String[] args) {
        Dog dog = new Dog();
        new Thread(dog).start();
    }
}
```

- 匿名内部类创建线程

```java
package com.zhouyf.chapter2;

public class CreateThread3 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("通过匿名内部类创建线程");
            }
        }).start();
    }
}
```

### 使用Lambda表达式创建线程

```java
package com.zhouyf.chapter2;

public class ThreadLambda {
    public static void main(String[] args) {
        new Thread(()-> System.out.println("使用Lambda表达式创建线程")).start();
    }
}
```

# Lambda表达式和匿名内部类的对比

Lambda表达式和匿名内部类都是Java中表示匿名函数的两种方式，但它们之间存在一些重要的差异。让我们通过以下几个方面来对比它们：

1. **语法简洁性**：

   - **Lambda表达式**：提供了一种非常简洁的语法。例如，执行一个简单的操作：`() -> System.out.println("Hello Lambda");`
   - **匿名内部类**：需要更多的代码来实现同样的功能。例如： 

     ```java
     new Thread(new Runnable() {
         @Override
         public void run() {
             System.out.println("Hello Anonymous Inner Class");
         }
     }).start();
     ```

2. **用途**：

   - **Lambda表达式**：主要用于实现函数式接口（只有一个抽象方法的接口）。

   - **匿名内部类**：可以用于实现任何接口，不仅仅是函数式接口。它也可以用于抽象类。


3. **关键字**： 

   - **Lambda表达式**：使用`->`箭头符号。

   - **匿名内部类**：使用`new`关键字。


4. **作用域和变量捕获**： 

   - **Lambda表达式**：与外部作用域的交互更为简单。它可以捕获外部变量，但这些变量必须是事实上的`final`（不一定要显式地声明为`final`，但不能被修改）。

   - **匿名内部类**：也可以捕获外部作用域的变量，但这些变量必须是`final`。


5. **关于`this`关键字**： 

   - **Lambda表达式**：`this`关键字在Lambda表达式内部引用的是包含它的方法的对象。

   - **匿名内部类**：`this`关键字引用的是匿名内部类的实例。


6. **性能**： 

   - **Lambda表达式**：通常更为轻量，因为它不需要生成额外的类文件。在许多情况下，Lambda表达式被转换为静态方法。

   - **匿名内部类**：每个匿名内部类都会产生一个新的类文件。


7. **兼容性**： 

   - **Lambda表达式**：仅在Java 8及更高版本中可用。

   - **匿名内部类**：在较早的Java版本中都可用。


虽然Lambda表达式和匿名内部类在某些场景中可以互换使用，但Lambda表达式提供了一种更简洁、更现代的方式来表示匿名函数，尤其是当你与函数式接口打交道时。但是，在需要实现多个方法的接口或抽象类的情况下，匿名内部类仍然是必要的。

# Lambda表达式捕获外部变量

Lambda表达式捕获外部变量的能力是其功能的一个重要组成部分。当我们在Lambda表达式中引用一个外部的变量，我们实际上是在"捕获"那个变量的值。这种行为有时被称为"闭包"。

## Final和Effectively Final变量

Lambda表达式只能捕获那些是`final`或"effectively final"的变量。"Effectively final"是Java 8中引入的一个概念，指的是那些在初始化后不再改变的变量，即使它们没有被明确标记为`final`。

例如：
```java
int x = 10;
Runnable r = () -> System.out.println(x);  // x is effectively final
// x = 20;  //这将会导致编译错误
```

## 捕获的是值，而不是变量

当Lambda表达式捕获一个变量时，它实际上捕获的是该变量的值，而不是变量本身。这意味着Lambda内部不能修改捕获的变量的值。

例如：
```java
int y = 15;
Runnable r = () -> {
    // y = 25;  // 这将会导致编译错误
    System.out.println(y);
};
```

## 实例变量和静态变量

与局部变量不同，Lambda表达式可以访问和修改包含类的实例变量和静态变量，因为这些变量的生命周期与Lambda的执行不是局部的。

例如：
```java
public class LambdaCaptureExample {
    private int instanceVar = 5;
    private static int staticVar = 50;

    public void testLambdaCapture() {
        Runnable r = () -> {
            instanceVar += 10;
            staticVar += 10;
            System.out.println(instanceVar);
            System.out.println(staticVar);
        };
        r.run();
    }

    public static void main(String[] args) {
        new LambdaCaptureExample().testLambdaCapture();
    }
}
```

## 为什么需要这些限制？

这些限制主要是为了确保数据的完整性和并发安全性。当多个线程共享数据时，如果允许Lambda表达式修改捕获的变量，可能会导致数据不一致和并发问题。

# Stream API

Stream API是Java 8引入的一个新特性，它允许我们以函数式编程方式处理集合数据。Stream API可以使代码更简洁、更易读，并提供了丰富的操作来处理数据。

## 如何获得Stream

集合转化为Stream

```java
List<String> list = Arrays.asList("tom", "lucy", "jim");
Stream<String> stream = list.stream();
```

```java
ArrayList<String> names = new ArrayList<>();
names.add("tom");
names.add("zhouyf");
names.add("lucy");
Stream<String> nameStream = names.stream();
```

## Collectors类

`Collectors` 类是 Java 8 引入的 `java.util.stream` 包中的一个工具类。它提供了一系列用于从流中收集元素的静态工具方法。这些方法大大简化了从流中产生集合或其他累积结果的操作。

### 收集操作

将元素收集为List

```java
List<String> list = Stream.of("a", "b", "c").collect(Collectors.toList());
```

将元素收集为Set

```java
Set<String> set = Stream.of("a", "b", "a").collect(Collectors.toSet());
```

连接流中的字符串。它可以有一个分隔符或前缀和后缀。

```java
String str = Stream.of("a", "b", "c").collect(Collectors.joining(","));
```

### 分组

- 普通分组`groupingBy()`

假设我们有一个Person类

```java
package com.zhouyf.chapter6;

public class Person {
    private String name;

    private int age;

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

现在要按照年龄分组

```java
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
        Map<Integer, List<Person>> groupByAge = persons.stream().collect(Collectors.groupingBy(p -> p.getAge()));
        System.out.println(groupByAge);
    }
}
```

- 多级分组

先按年龄分组，再按姓名的首字母分组

```java
Map<Integer, Map<Character, List<Person>>> group = persons.stream()
        .collect(Collectors.groupingBy(p -> p.getAge(),
                Collectors.groupingBy(p -> p.getName().charAt(0))
        ));
```

- 分组后的下游收集器

按照年龄分组，再统计每个组的人数

```java
Map<Integer, Long> count = persons.stream()
        .collect(Collectors.groupingBy(p -> p.getAge(),
                Collectors.counting()
        ));
```

### 分区

将一组人分为30以下和30岁以上两个组，并以名字的方式来显示

```java
Map<Boolean, List<String>> partition = persons.stream().collect(Collectors.partitioningBy(
        person -> person.getAge() < 30,
        Collectors.mapping(person -> person.getName(), Collectors.toList())
));
```

将一组人分为30以上和30岁以下两组，并计算每一组的平均年龄

```java
Map<Boolean, Double> groupByAge = persons.stream().collect(Collectors.partitioningBy(
        person -> person.getAge() < 30,
        Collectors.averagingDouble(person -> person.getAge())
));
```

### 计数

按照年龄进行分组，然后统计每组的人数

```java
Map<Integer, Long> countByAge = persons.stream().collect(Collectors.groupingBy(
        person -> person.getAge(),
        Collectors.counting()
));
```

### 求和、平均数、最大最小值

### reducing()

按照年龄进行分组，分组后计算每一组的年龄总和

```java
Map<Integer, Integer> collect1 = persons.stream().collect(Collectors.groupingBy(
        person -> person.getAge(),
        Collectors.reducing(
                0,
                p -> p.getAge(),
                (a1, a2) -> a1 + a2
        )
));
```

### mapping()

这个方法常被用在与其他收集器结合的场景中，允许我们在收集结果之前对流中的元素进行转换。它接受两个参数：一个将流元素转换为另一种类型的函数，以及另一个收集器，用于收集转换后的元素。

```java
Map<Integer, String> namesGroupByAge = persons.stream().collect(Collectors.groupingBy(
        person -> person.getAge(),
        Collectors.mapping(
                person -> person.getName(),
                Collectors.joining(",")
        )
));

System.out.println(namesGroupByAge);
```

## 常见的Stream操作

- 中间操作

  ：返回一个新的Stream。

  - `filter`：过滤元素。
  - `map`：转换元素。
  - `sorted`：排序。
  - `distinct`：去重。
  - `flatMap`：转换流的每个元素到另一个流，然后“扁平化”所有的流为一个流。
  - `peek`：主要用于调试。它允许你无副作用地查看流中的元素，然后将相同的元素传递给流的下一个操作。
  - `limit`：从流中获取前N个元素。
  - `skip`：跳过流中的前N个元素。

- 终止操作

  ：返回一个结果或执行一个非Stream操作。

  - `collect`：转换为其他形式。
  - `forEach`：遍历每个元素。
  - `reduce`：将Stream元素组合起来。
  - `count`：计算元素的数量。
  - `anyMatch`：测试流中是否存在至少1个元素是否满足给定的条件。
  - `allMatch`：测试流中的所有元素是否满足给定的条件。
  - `noneMatch`：测试流中是否没有元素满足给定的条件。
  - `findFirst`：返回流中的第一个元素，如果存在的话。
  - `findAny`：返回流中的任何元素，如果存在的话。在并行流中尤其有用，因为它可能返回流中的任何元素。
  - `max`：返回流中的最大值，基于给定的比较器。
  - `min`：返回流中的最小值，基于给定的比较器。

示例：

```java
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
```

### map操作

`map`操作是一个中间操作，它接受一个函数作为参数。这个函数会被应用到每个元素上，并将其转换成一个新的元素。简单地说，`map`就是将Stream中的每一个元素都"映射"到一个新的元素。

```java
public static void main(String[] args) {
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
    List<Integer> newList = list.stream().map(num -> num *2).collect(Collectors.toList());
    newList.forEach(num -> System.out.println(num));
}
```

output：

```
2 4 6 8 10 
```

### distinct操作

`distinct`是`Stream API`的一个中间操作，用于去除流中的重复元素。它依赖于元素的`equals`方法来确定元素是否重复。

```java
package com.zhouyf.chapter4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDistinct {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 1, 1, 1, 2, 2, 2, 3, 3, 4);
        List<Integer> newList = list.stream().distinct().collect(Collectors.toList());
        newList.forEach(num -> System.out.print(num + " "));
    }
}
```

Output：

```
1 2 3 4 
```

- 当使用自定义对象时，确保你已经正确地重写了`equals`和`hashCode`方法，因为`distinct`操作依赖于它们来确定对象是否相等。
- `distinct`操作可能会增加处理时间，尤其是在处理大数据集时，因为它需要保存已经看到的元素以判断新元素是否重复。
- `distinct`是一个简单但非常有用的工具，它可以帮助我们轻松地去除重复的数据。在处理数据时，尤其是从外部源（如数据库、文件等）导入的数据时，这一操作会非常有用。

### reduce操作

在 Java 8 中，`Stream` API 是一个新的抽象，允许我们以声明式的方式处理数据。`reduce` 是其中一个终端操作，允许我们从一个 `Stream` 中取得一个值。它是将 `Stream` 中的元素组合起来，得到一个单一的汇总结果。

- 无初始值

```java
package com.zhouyf.chapter4;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamReduce {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3, 4, 4, 5);
        Optional<Integer> Omin = list.stream().reduce((a, b) -> Math.min(a, b));
        int min = Omin.orElse(0);
        System.out.println(min);
    }
}
```

Output

```
1
```

- 有初始值

```java
public class StreamReduce {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3, 4, 4, 5);
        Integer min = list.stream().reduce(-1, (a, b) -> Math.min(a, b));
        System.out.println(min);
    }
}
```

Output

```
-1
```

## Stream测试

### 第一个任务

当然可以，我会为你设计一个综合性的任务，来测试你对 Java Stream API 的掌握程度。

假设你有以下的一个类结构：

```java
class Student {
    private String name;
    private int age;
    private List<Course> courses;
    // ...构造函数、getters和setters...

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", courses=" + courses +
                '}';
    }
}

class Course {
    private String courseName;
    private int score;
    // ...构造函数、getters和setters...

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", score=" + score +
                '}';
    }
}
```

假设你有一个 `List<Student>`，其中每个 `Student` 对象都有一个与之关联的 `Course` 列表。

现在，执行以下任务：

1. 找出年龄大于 20 的所有学生。
2. 找出所有学生中分数最高的课程。
3. 计算所有学生的平均年龄。
4. 列出参加了 "Math" 课程的所有学生的名字。
5. 找出分数在所有课程中都超过 60 分的学生。
6. 创建一个按年龄分组的学生列表的 map。

```java
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

		//创建一个按年龄分组的学生名字的列表
        Map<Integer, List<String>> NameGroupByAge = students.stream().collect(
                Collectors.groupingBy(
                        s -> s.getAge(),
                        Collectors.mapping(s -> s.getName(), Collectors.toList())
                )
        );
        System.out.println(NameGroupByAge);
    }
}
```

# Lambda表达式练习

**字符串列表转大写**  
给定一个字符串列表，使用lambda表达式将每个字符串转化为大写。

```java
List<String> list = Arrays.asList("ins", "jsdj", "hsdif", "jdso");
List<String> newlist = list.stream().map(s -> s.toUpperCase()).collect(Collectors.toList());
```

*奇数筛选**  

写一个lambda表达式，筛选出一个整数列表中的所有奇数。

```java
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 7);
List<Integer> collect = list.stream().filter(num -> num % 2 == 1).collect(Collectors.toList());
```

**字符串排序**  

使用lambda表达式，根据字符串的长度对一个字符串列表进行排序。

```java
List<String> list = Arrays.asList("dsjo", "sjdsoj", "sjdhi", "ss", "skjdsijf");
List<String> result = list.stream().sorted((a, b) -> a.length() - b.length()).collect(Collectors.toList());
```

**统计**  

使用lambda表达式，计算一个数字列表中所有满足某个条件（例如大于10）的数字的总和。

```java
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
Long sum = list.stream().filter(a -> a > 5).count();
```

**数据转换**  

使用lambda表达式，将一个字符串列表转换为每个字符串的长度的列表。

```java
List<String> list = Arrays.asList("sjsj", "sjdoj", "sj", "sjdfosj", "sjd", 				          "iojsdoifjosi");
List<Integer> lengths = list.stream().map(s -> s.length()).collect(Collectors.toList());
```

**查找**  

编写一个lambda表达式，从一个字符串列表中查找并返回第一个以某个字母（例如“A”）开始的字符串，或返回null。

```java
List<String> list = Arrays.asList("Adjj", "djj", "Adjh");
List<String> list1 = list.stream().filter(str -> str.charAt(0) == 'A').collect(Collectors.toList());
```

**函数操作**  

编写两个lambda表达式：一个将数字翻倍，另一个将数字加3。然后，编写一个组合这两个操作的函数。

```java
Function<Integer, Integer> doubleNumber = n -> n * 2;

Function<Integer, Integer> addThree = n -> n + 3;

Function<Integer, Integer> combine = doubleNumber.andThen(addThree);

Integer apply = combine.apply(34);
System.out.println(apply);
```

**构造映射** 
使用lambda表达式，从一个字符串列表构造一个映射，其中字符串是键，其长度是值。

```java
List<String> list = Arrays.asList("skjdf", "sjdso", "ss", "s");
Map<String, Integer> result = list.stream().collect(Collectors.toMap(
        str -> str,
        str -> str.length()
));
```

**自定义过滤** 
编写一个接受lambda谓词作为参数的方法，该方法将筛选出满足该谓词条件的所有元素。

```java
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
```

在这个示例中，我们定义了一个 `filter` 方法，它接受一个列表和一个 lambda 谓词作为参数。这个方法将筛选出满足谓词条件的所有元素。在 `main` 方法中，我们展示了如何使用这个方法来筛选出一个整数列表中的所有偶数和所有大于5的数字。