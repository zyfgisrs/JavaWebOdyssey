# 1 JDK新特新

- Java Record
- `Switch`开关表达式
- Text Block文本块
- sealed 密封类

## 1.1 Java Record

- Record是一种特殊的类型的Java类。可以用来创建不可变类，语法简短。`Jackson2.12` 支持Record类。
- Java record定义的语法类似于类和接口的定义，但是使用了关键字`record`来表示。一个Java record定义通常由字段定义和构造方法定义组成。
- 同时，`Java record`只是一种语法糖，它并不会改变Java的基本语言特性和规则，因此使用`Java record`定义的类仍然可以被当作普通类来使用。
- Java 中的 record 类型可以被视为一种数据载体，因为它们提供了一种方便的方式来存储和传输数据。
- 语言级别`Lombok`，避免样本代码。
  - 带有全部参数得构造方法。
  - public访问器。
  - `toString()`   `hashCode()`  `equals()`
  - 无get\set方法。没有遵循Bean得命名范式。
  - `final`属性，不可修改。
  - 不能声明实例属性，能声明static成员。

### 1.2.1 **创建Record**

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230321190136873.png" alt="image-20230321190136873" style="zoom:50%;" />



```java
public record Student(Integer id, String name, String email, Integer age) {
}
```

测试类

```java
import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void test() {
        //创建Record对象
        Student lisi = new Student(1001, "lisi", "lisi@qq.com", 20);
        System.out.println(lisi);

        //获取属性，只读，没有set get方法,提供访问器
        Integer id = lisi.id();
        String name = lisi.name();
        System.out.println("id=" + id);
        System.out.println("name=" + name);
    }
}
```

输出

```properties
Student[id=1001, name=lisi, email=lisi@qq.com, age=20]
id=1001
name=lisi
```



### 1.2.2 定义方法

```java
import java.util.Optional;

public record Student(Integer id, String name, String email, Integer age) {
    //实例方法
    public String concat(){
        return String.format("姓名是%s,年龄是%d",name,age);
    }

    //静态方法
    public static String emailToUpperCase(String email){
        return Optional.ofNullable(email).orElse("no email").toUpperCase();
    }
}
```

测试

```java
import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void test() {
        //创建Record对象
        Student lisi = new Student(1001, "lisi", "lisi@qq.com", 20);
        String concat = lisi.concat();
        System.out.println(concat);

        String email = Student.emailToUpperCase("lisi@qq.com");
        System.out.println(email);
    }
}
```

输出

```
姓名是lisi,年龄是20
LISI@QQ.COM
```

### 1.2.3 构造方法

- 紧凑型构造方法没有任何参数，**甚至没有括号**。
- 规范构造方法是以所有成员作为参数（推荐）。
- 定制构造方法是自定义参数个数。

### 1.2.4 实现接口

PrintInterface接口

```java
public interface PrintInterface {
    void print();
}
```

`ProductRecord`类

```java
import java.util.StringJoiner;

public record ProductRecord(Integer id, String name, Integer qty) implements PrintInterface{
    @Override
    public void print() {
        StringJoiner joiner = new StringJoiner("-");
        StringJoiner s = joiner.add(id.toString()).add(name).add(qty.toString());
        System.out.println("商品信息:" + s);
    }
}
```

测试类

```java
import org.junit.jupiter.api.Test;

class ProductRecordTest {
    @Test
    void test01() {
        ProductRecord tv = new ProductRecord(1001, "电视", 1);
        tv.print();
    }
}
```

输出

```
商品信息:1001-电视-1
```

### 1.2.5 Local Record

- Record可以作为局部变量使用。在代码中定义并使用Record，下面定义一个`SaleRecord`。

```java
@Test
void test02() {
    record SaleRecord(String saleId, String production, Double price){};

    SaleRecord saleRecord = new SaleRecord("S001", "显示器", 1300.01);

    System.out.println(saleRecord);
}
```

### 1.2.6 嵌套Record

StudentRecord类

```java
public record StudentRecord(String name, Integer age, TeacherRecord teacherRecord) {
}

public record TeacherRecord(String name, Integer age) {
}
```

测试类

```JAVA
import org.junit.jupiter.api.Test;
class StudentRecordTest {
    @Test
    void test01() {
        TeacherRecord li = new TeacherRecord("李老师", 46);
        StudentRecord tom = new StudentRecord("tom", 15, li);
        System.out.println(tom);
    }
}
```

输出

```java
StudentRecord[name=tom, age=15, teacherRecord=TeacherRecord[name=李老师, age=46]]
```

### 1.2.7 instanceof判断Record

`instanceof` 运算符用于检查一个对象是否是某个类的实例，其语法如下：

```java
object instanceof class
```

其中 object 是要检查的对象，class 是要检查的类。如果 object 是 class 的一个实例，则返回 true；否则返回 false。



**Record类型使用instanceof**

定义一个Person Record类

```java
public record Person(String name, Integer age) {
}
```

定义SomeService类，有一个业务方法，判断person是否成年

```java
public class SomeService {
    //定义业务方法
    public boolean isEligible(Object obj){
        //判断person是否成年
        if(obj instanceof Person(String name, Integer age)){
            return age >= 18;
        }
        return false;
    }
}
```

或者可以这么写

```java
public class SomeService {
    //定义业务方法
    public boolean isEligible(Object obj) {
        //判断person是否成年
        if (obj instanceof Person(String name, Integer age) p) {
            return p.age() >= 18;
        }
        return false;
    }
}
```

单元测试

```java
import org.junit.jupiter.api.Test;

class PersonTest {
    @Test
    void test01() {
        Person p = new Person("张三", 20);
        SomeService someService = new SomeService();
        boolean eligible = someService.isEligible(p);
        System.out.println("eligible = " + eligible);
    }
}
```

输出

```
eligible = true
```



### 1.2.8 反射相关

判断是否为Record类型

`record.getclass.isRecord()`

```java
void test02() {
    TeacherRecord li = new TeacherRecord("李老师", 46);
    StudentRecord tom = new StudentRecord("tom", 15, li);
    boolean record = tom.getClass().isRecord();
    System.out.println("record = " + record);
}
```

输出

```
record = true
```



获取Record属性的类型

`getClass().getRecordComponents()`

```java
@Test
void test02() {
    TeacherRecord li = new TeacherRecord("李老师", 46);
    StudentRecord tom = new StudentRecord("tom", 15, li);
    RecordComponent[] recordComponents = tom.getClass().getRecordComponents();
    for (RecordComponent recordComponent : recordComponents) {
        System.out.println("recordComponent = " + recordComponent);
    }
}
```

输出

```properties
recordComponent = java.lang.String name
recordComponent = java.lang.Integer age
recordComponent = TeacherRecord teacherRecord
```



## 1.2 switch开关表达式

- 支持箭头表达式
- 支持yield返回值
- 支持Java Record

### 1.2.1 箭头表达式

```java
package com.zyf.switch_;

/**
 * @author:zhouyangfan
 * @date:2023/3/22
 * @Description:演示switch箭头表达式
 **/
public class Example {
    public static void main(String[] args) {
        int number = 3;
        String str = "";
        switch (number) {
            case 1 -> str = "one";
            case 2 -> str = "two";
            case 3 -> str = "three";
            //都不匹配，执行default
            default -> throw new RuntimeException("无效数字");
        }
        System.out.println("str = " + str);
    }
}
```

输出：

```properties
str = three
```



### 1.2.2 yield

在Java 12中，`switch`语句可以使用`yield`语句来返回一个值，这个值可以被直接赋值给一个变量或者用于方法调用的参数。`yield`语句的作用是**终止**`switch`语句的执行，并将控制权返回到`switch`语句的**调用者**，同时返回一个值。

```java
package com.zyf.switch_;

/**
 * @author:zhouyangfan
 * @date:2023/3/22
 * @Description:
 **/
public class Yield_ {
    public static void main(String[] args) {
        Integer number = 5;
        String str = switch (number) {
            case 1:
                yield "one";
            case 2:
                yield "two";
            case 3:
                yield "three";
            default:
                //抛出非法状态异常
                throw new IllegalStateException("Unexpected value: " + number);
        };
        System.out.println("str = " + str);
    }
}
```

代码块的使用，可以使代码更加简洁、易于理解和维护，尤其是在处理多个分支的情况下，可以减少代码的重复和冗余。

```java
@Test
public void test01() {
    Integer number = 2;
    String str = switch (number) {
        case 1 -> {
            System.out.println("数字为one");
            yield "one";
        }
        case 2 -> {
            System.out.println("数字为two");
            yield "two";
        }
        case 3 -> {
            System.out.println("数字为three");
            yield "three";
        }
        default -> {
            //抛出非法状态异常
            throw new IllegalStateException("Unexpected value: " + number);
        }
    };
    System.out.println("str = " + str);
}
```

输出：

```
数字为two
str = two
```

注：箭头表达式与`case ：`不能混合使用



### 1.2.3 Switch-Record

定义了三个Record类型的图形类

```java
package com.zyf.switch_.shape;

public record Circle(Double radius) {
}

record Square(Double squareSide) {
}

record Rectangle(Double rectangleLength, Double rectangleWidth){

}
```

定了一个ShapeService类，其中calculateAreaOfShape可以计算出图形的面积

```java
package com.zyf.switch_.shape;

/**
 * @author:zhouyangfan
 * @date:2023/3/22
 * @Description:计算图形的面积
 **/
public class ShapeService {
    public Double calculateAreaOfShape(Object shape) {
        Double area = switch (shape) {
            case Square(Double squareSide) -> {
                System.out.println("正方形的边长为：" + squareSide);
                yield squareSide * squareSide;
            }
            case Rectangle(Double rectangleLength, Double rectangleWidth) -> {
                System.out.println("长方形的长为：" + rectangleLength + ",宽为：" + rectangleWidth);
                yield rectangleLength * rectangleWidth;
            }
            case Circle(Double radius) -> {
                System.out.println("圆形的半径为：" + radius);
                yield Math.PI * radius * radius;
            }
            default -> {
                throw new IllegalStateException("无法计算");
            }
        };
        return area;
    }
}
```

单元测试

```java
package com.zyf.switch_.shape;

/**
 * @author:zhouyangfan
 * @date:2023/3/22
 * @Description:计算图形的面积
 **/
public class ShapeService {
    public Double calculateAreaOfShape(Object shape) {
        return switch (shape) {
            case Square(Double squareSide) -> {
                System.out.println("正方形的边长为：" + squareSide);
                yield squareSide * squareSide;
            }
            case Rectangle(Double rectangleLength, Double rectangleWidth) -> {
                System.out.println("长方形的长为：" + rectangleLength + ",宽为：" + rectangleWidth);
                yield rectangleLength * rectangleWidth;
            }
            case Circle(Double radius) -> {
                System.out.println("圆形的半径为：" + radius);
                yield Math.PI * radius * radius;
            }
            default -> {
                throw new IllegalStateException("无法计算");
            }
        };
    }
}
```

输出

```properties
圆形的半径为：1.1
circleArea = 3.8013271108436504
========================
长方形的长为：2.2,宽为：2.1
rectangleArea = 4.620000000000001
========================
正方形的边长为：1.6
squareArea = 2.5600000000000005
```



## 1.3 Text Block

- Java 文本块是 JDK 15 中的新功能，它允许在代码中包含多行字符串而无需使用转义序列。通常，当需要在字符串中使用多行文本时，您需要使用转义字符（如`\n`）来表示换行符，并将字符串拆分为多行。
- 使用 Java 文本块，您可以将多行文本包含在三个双引号之间的块中，而无需使用转义字符。这使得代码更加易读和易于维护，尤其是在需要包含大量文本的情况下，如 HTML、XML 或 SQL 查询等。

```java
String html = """
<!DOCTYPE html>
<html>
    <head>
        <title>My Page</title>
    </head>
    <body>
        <h1>Hello, world!</h1>
    </body>
</html>
""";
```

**使用 Java 文本块，可以将 HTML 页面的结构清晰地表示为一个多行字符串，而不需要使用转义字符。**



注意点：

```java
String name = """李四""";

报错：Illegal text block start: missing new line after opening quotes
在开头的三个双引号后面没有换行符，就会出现该错误。
```

文本块与字符串的相同点：

1. 都表示一组字符序列。
2. 都可以通过索引访问其中的字符。
3. 都支持字符串操作方法，如substring()、length()等。

文本块与字符串的不同点同点：

1. 定义方式不同：文本块使用三个双引号（"""）来定义，字符串使用双引号（"）来定义。
3. 文本块中的特殊字符不需要转义，例如，可以直接使用反斜杠（\）和双引号（"）而无需转义，而字符串需要。
4. 文本块在编译时被解析，而字符串在运行时被解析。

格式化文本块

第一种方法：`String.format`

```java
package com.zyf.textblock;

/**
 * @author:zhouyangfan
 * @date:2023/3/22
 * @Description:格式化文本块
 **/
public static void main(String[] args) {
    String name = "Tom";
    int age = 25;

    String textBlock = """
            Hello, my name is %s and I am %d years old.
            This is a text block.
            """;

    String formatted = String.format(textBlock, name, age);
    System.out.println("formatted = " + formatted);
}
```

输出

```
formatted = Hello, my name is Tom and I am 25 years old.
This is a text block.
```



第二种方法`formatted`

```java
public static void main(String[] args) {
    String name = "Tom";
    int age = 25;

    String textBlock = """
            Hello, my name is %s and I am %d years old.
            This is a text block.
            """.formatted(name, age);
    System.out.println(textBlock);
}
```

输出

```java
Hello, my name is Tom and I am 25 years old.
This is a text block.
```



`\`的作用

```java
public void test01() {
    String textBlock = """
            Hello, my name is zyf,\
            I am 28 years old
            """;
    System.out.println(textBlock);
}
```

输出

```
Hello, my name is zyf, I am 28 years old
```

## 1.4 var

- var是一个保留字，不是关键字
- 方法内声明的局部变量，必须有初始值
- 每次声明一个变量，不可复合声明多个变量
- var动态类型是编译器根据变量所赋的值来推断类型
- var代替显示类型，代码简洁，减少不必要的排版，混乱

## 1.5 密闭类sealed

Java 中的密封类（Sealed class）是从 Java 15 版本开始引入的一种新特性，它可以让开发者更加精确地控制类的继承。



作用：

1. 约束继承：与普通类不同，密封类可以限制哪些类可以继承它。只有在密封类中声明的类才能继承它，这样可以有效地控制继承层次结构，减少继承带来的不可预期的问题。
2. 提高安全性：由于只有特定的类能够继承密封类，因此可以提高代码的安全性和可维护性。开发者可以更加精确地控制类的继承，避免意外的错误和安全漏洞。
3. 改进设计：使用密封类可以改进代码的设计和组织结构，因为开发者可以更加精确地控制类的继承。密封类的引入也可以鼓励开发者更好地设计类的继承结构，使代码更加可读和易于维护。

定义：

```java
sealed class 类名 permits 子类1, 子类N列表{}
//permits为限制子类的类名
```

# 2. SprintBoot基础

SpringBoot特性：

1. 简化配置：Spring Boot采用了“约定优于配置”的原则，大大简化了应用程序的配置过程。开发人员不再需要手动配置各种繁琐的XML文件，而是通过一些默认配置，可以快速地搭建起一个简单的Spring应用。

2. 内嵌服务器：Spring Boot内置了Tomcat、Jetty、Undertow等常用的Web服务器，因此可以非常方便地构建出一个可执行的、独立的Web应用程序。

3. 自动配置：Spring Boot能够根据应用程序的依赖关系和代码结构，自动为应用程序配置各种组件。例如，当应用程序引入了Spring Data JPA依赖时，Spring Boot会自动配置数据库连接池和事务管理器等组件。

4. 起步依赖：Spring Boot提供了一系列“起步依赖”，可以让开发人员非常方便地引入一些常用的框架和库。例如，使用spring-boot-starter-web起步依赖，就可以快速地引入Spring MVC、Tomcat和其他相关组件。

5. Actuator：Spring Boot提供了一个强大的Actuator模块，可以帮助开发人员监控和管理应用程序。通过访问Actuator端点，开发人员可以查看应用程序的健康状况、性能指标、配置信息等等。

6. 易于测试：Spring Boot提供了一些测试支持，包括自动配置的测试环境、内存数据库等。开发人员可以非常方便地编写集成测试和单元测试。

7. 生态丰富：Spring Boot拥有非常丰富的生态系统，涵盖了各种框架、库、工具等等。这些生态系统的组件都与Spring Boot高度兼容，可以非常方便地集成在应用程序中。

   

约定大于配置：

- "约定大于配置"（Convention Over Configuration）是一种软件开发的思想，它强调在开发过程中，应该尽量采用一些默认约定，减少配置的工作量。这种思想可以提高开发效率，减少出错的机会。
- 在Spring Boot中，采用约定大于配置的思想，通过一些默认约定和自动配置，尽可能地减少开发人员的配置工作。例如，当我们引入了Spring Boot的Web组件时，框架会自动为我们配置Tomcat或Jetty等Web服务器，并将默认的端口设置为8080。我们只需要编写Controller类，并将其标记为@Controller或@RestController，就可以使用HTTP协议提供RESTful服务，无需手动配置大量的XML文件和注解。
- 采用约定大于配置的思想，Spring Boot可以大幅简化Spring应用程序的开发过程。开发人员只需要专注于业务逻辑的实现，而无需关心框架和组件的具体配置。这样可以提高开发效率，并且减少由于配置错误引起的问题。

开箱即用：

- "开箱即用"（Out of the Box）是指软件产品在发布后，可以直接运行，而无需进行额外的配置或安装。在Spring Boot中，开箱即用是指Spring Boot应用程序在启动时，自动加载了许多默认配置和自动配置，开发人员无需进行额外的配置即可快速启动应用程序。

- Spring Boot内置了Tomcat、Jetty、Undertow等常用的Web服务器，开发人员只需要编写业务代码，无需手动配置Web服务器，就可以快速构建出一个可执行的Web应用程序。

- Spring Boot还内置了一系列常用的框架和库，如Spring Data JPA、Spring Data Redis、Spring Security等，这些组件可以帮助开发人员快速搭建出一个功能完善的应用程序。开发人员只需要通过在Maven或Gradle中引入相关的起步依赖，Spring Boot就会自动为应用程序配置相应的组件。

- Spring Boot还提供了一些自动配置的功能，可以根据应用程序的依赖关系和代码结构，自动为应用程序配置各种组件。例如，当应用程序引入了Spring Data JPA依赖时，Spring Boot会自动配置数据库连接池和事务管理器等组件。这样，开发人员无需手动配置这些组件，即可快速地搭建出一个功能完善的应用程序。

- 综上所述，Spring Boot的"开箱即用"特性可以大幅降低应用程序的开发难度和成本，让开发人员可以更加专注于业务逻辑的实现，而无需过多关注底层的配置和组件的实现。

  

**Spring与SpringBoot的关系**

- Spring Boot是一个基于Spring框架的开发框架，它旨在简化Spring应用程序的开发和部署。Spring Boot提供了许多开箱即用的特性，例如自动配置、快速开发、内置Web服务器等，可以让开发人员更加专注于业务逻辑的实现而非框架配置。
- Spring Boot可以看作是Spring框架的一种扩展，它使用Spring框架的核心功能，并在其基础上添加了一些额外的特性。使用Spring Boot可以大大简化Spring应用程序的配置和部署，并加速开发过程。

**SpringBoot和SpringCloud的关系**

- Spring Cloud是一个基于Spring Boot的分布式应用程序开发框架，它提供了一些工具和服务，可以帮助开发人员构建和管理分布式应用程序。Spring Cloud提供了诸如服务发现、配置中心、负载均衡等功能，可以帮助开发人员构建高可用、可扩展的分布式应用程序。
- Spring Boot是Spring Cloud的基础，Spring Cloud构建在Spring Boot之上。开发人员可以使用Spring Boot快速搭建独立的应用程序，然后使用Spring Cloud来实现应用程序的分布式部署、服务发现、负载均衡等功能。

## 2.1 学习环境

- JDK：`JDK19`
- `Maven：3.6`
- `IDEA2022`
- 数据库：`MySQL5`以上
- 文本工具：Sublime

## 2.2 脚手架

​       Spring Initializr脚手架是一种快速生成Spring Boot项目基础结构的工具，它可以通过命令行或IDE插件的方式使用。它基于Spring Initializr Web应用程序，允许用户在本地运行和使用Spring Initializr服务。

- Spring Initializr脚手架的web地址：https://start.spring.io/


- 阿里云脚手架：https://start.aliyun.com/


### 2.1.1 WEB脚手架创建项目

1. 登录https://start.spring.io/

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230322171929978.png" alt="image-20230322171929978" style="zoom:50%;" />

2. 选择配置，导入依赖。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323125423080.png" alt="image-20230323125423080" style="zoom:33%;" />

3. 下载zip文件，解压，导入到IDEA中。

4. 文件目录显示：

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230322172142185.png" alt="image-20230322172142185" style="zoom: 50%;" />

5. 入口类Boot02Application，启动SpringBoot

![image-20230322173603294](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230322173603294.png)

6. 配置文件application.properties，static目录存放静态资源，templates目录存放模板（视图技术）

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230322173906035.png" alt="image-20230322173906035" style="zoom:50%;" />

### 2.1.2 IDEA内置脚手架创建SpringBoot项目

1. File -> New -> Project
2. 选择Spring Initializr，填写配置信息

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230322174938169.png" alt="image-20230322174938169" style="zoom:25%;" />

3. 选择项目依赖

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230322174957005.png" alt="image-20230322174957005" style="zoom:25%;" />

4. 从项目依赖可以看出，Spring Boot是Spring的应用程序，springboot3依赖的是Spring6

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230322180116672.png" alt="image-20230322180116672" style="zoom:33%;" />



## 2.3 代码结构

### 2.3.1 单一模块

一个工程一个模块的完整功能实现：以Order订单业务为例。

1. **创建一个SpringBoot项目。**
2. **根包为com.zyf.order，启动类在order根包下**。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323140422015.png" alt="image-20230323140422015" style="zoom:33%;" />

Boot04Application启动类

```java
package com.zyf.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//有@SpringBootApplication注解的类是启动类
//通常放在根目录下
@SpringBootApplication
public class Boot04Application {
    public static void main(String[] args) {
        SpringApplication.run(Boot04Application.class, args);
    }

}
```

3. **在根包下创建controller、service、repository（持久层）和model（领域模型）**。
4. **service类下创建inter包（接口）和impl包（接口实现），model层中包含entity和dto**

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323141332819.png" alt="image-20230323141332819" style="zoom: 33%;" />

### 2.3.2 多个模块

1. 创建项目，根包为crm。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323142516600.png" alt="image-20230323142516600" style="zoom: 33%;" />

2. 创建两个模块分别为activity和sale模块，在crm下建立这两个包。
3. 分别在这两个包下创建controller层、respository层、service层

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323150539530.png" alt="image-20230323150539530" style="zoom:33%;" />

以上是推荐的所模块的代码结构，把启动类放在根包的下面

### 2.3.3 starter启动器

#### 2.3.3.1 启动项目

1. 创建一个SpringBoot项目，添加控制器HelloController

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323152518849.png" alt="image-20230323152518849" style="zoom:33%;" />

2. 编写控制器

```java
 * @Description:简单的控制器
 **/
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "欢迎使用SpringBoot3";
    }
}
```

- `@Controller` 注解通常用于创建基于视图的 Spring MVC 控制器。这些控制器处理传入的 HTTP 请求并生成 HTTP 响应。在处理请求时，它们通常会返回一个逻辑视图名称，该名称将被解析为一个实际的视图。这个视图通常是一个Thymeleaf 模板。

- `@RestController` 注解用于创建基于 REST 的 Spring MVC 控制器，它将处理传入的 HTTP 请求并生成 HTTP 响应。与 `@Controller` 不同的是，`@RestController` 控制器会将响应直接发送到客户端，而不是解析为视图。这通常使用 Spring 中的消息转换器来完成。通常情况下，`@RestController` 控制器返回的是 JSON 或 XML 数据。

- 因此，`@Controller` 和 `@RestController` 的主要区别在于它们处理请求的方式以及返回响应的方式。如果您正在开发基于视图的 Web 应用程序，则应使用 `@Controller` 注解。如果您正在开发基于 REST 的 Web 应用程序，则应使用 `@RestController` 注解。

3. 启动项目，点击启动类的run。

4. 输出日志

![image-20230323152855592](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323152855592.png)

5. 从日志信息可以看出，启动了一个tomcat服务器，版本为10.1.5，端口号为8080，没有地址。
6. 浏览器中访问服务器：http://localhost:8080/hello，可以看到控制器返回的内容显示在了浏览器上。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323154552059.png" alt="image-20230323154552059" style="zoom:50%;" />

#### 2.3.3.2 pom文件中的starter

父项目

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.0.4</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

当前项目的坐标

```xml
<groupId>com.zyf</groupId>
<artifactId>Boot06</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

JDK版本

```xml
<properties>
    <java.version>19</java.version>
</properties>
```

依赖列表

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>


    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

- 带有starter的叫做启动器（启动依赖，spring-boot-starter-xxx 是spring官方推出的启动器
- xxx-starter：非spring推出的，由其他的组织提供的

#### 2.3.3.3 starter

- 在 Spring Boot 中，Starter 是一种约定俗成的方式，它可以帮助开发者快速地添加**依赖项**和**配置信息**以构建某种类型的应用程序。
- Starter 实际上是一个 Maven 或 Gradle 依赖项和依赖版本，可以提供所需的所有库和依赖项，以及一些常用的默认配置，以便我们可以更轻松地构建特定类型的应用程序。
- 每个 Starter 都会集成一组常用的依赖项，这些依赖项可以快速启动特定类型的应用程序。例如，Spring Boot 中有许多官方 Starter，如 `spring-boot-starter-web`、`spring-boot-starter-data-jpa`、`spring-boot-starter-test` 等。Starter 包含了所有构建特定类型应用程序所需的依赖项和默认配置信息。在使用 Starter 时，我们只需要添加相应的依赖项，Spring Boot 就会自动进行依赖项解析和自动配置。
- 除了官方 Starter 之外，开发者还可以编写自己的 Starter，并将其发布到 Maven 中央仓库或私有仓库中。这些自定义 Starter 可以帮助开发者更好地组织和管理应用程序中的依赖项和配置信息，并且可以方便地在多个应用程序之间共享和重用。

可以看到spring-boot-starter-web启动器包含了其他五个依赖，json、tomcat服务器等。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323161039481.png" alt="image-20230323161039481" style="zoom:50%;" />

### 2.3.4 parent父项目

`Spring Boot Starter Parent` 是 Spring Boot 中的一个特殊的 Maven 或 Gradle 依赖项，它提供了一组默认的依赖项和插件，以及一些默认的配置，帮助开发者更轻松地构建 Spring Boot 应用程序。

父项目提供一下功能：

1. 提供了 Spring Boot 的版本号，确保我们所使用的 Spring Boot 的依赖项和插件版本是兼容的。
2. 提供了一些常用的依赖项，如 Spring Framework、Spring Boot Starter Web、Spring Boot Starter Data JPA 等，使得我们可以轻松地添加这些依赖项，并避免版本冲突。
3. 提供了一些默认的配置信息，如日志、数据源等，默认配置符合 Spring Boot 的最佳实践，可以让我们的应用程序更加稳定和高效。

父项目的父项目：`spring-boot-dependencies`

- 包含了许多常用的第三方库的版本信息。这个 POM 中的版本信息通常是 Spring Boot 推荐使用的版本，这些版本都经过了充分的测试和验证，可以与 Spring Boot 的其他版本兼容。
- 通过在 Maven 或 Gradle 项目中引入 `spring-boot-dependencies` POM，我们可以省去手动指定这些库的版本号的麻烦，并且可以确保这些库的版本与 Spring Boot 的版本兼容。
- `spring-boot-dependencies` POM 中包含了许多库的版本信息，包括但不限于以下内容：
  1. Spring Framework 的核心模块，如 spring-core、spring-beans、spring-context 等。
  2. Spring Boot 的核心 Starter
  3. 数据库驱动程序，如 MySQL、PostgreSQL、Oracle 等。
  4. 消息队列，如 RabbitMQ、Kafka 等。
  5. 缓存，如 Redis、Ehcache 等。
  6. 测试框架，如 JUnit、Mockito、Spring Test 等。

## 2.4 核心注解

 @SpringBootApplication：

- @SpringBootApplication是一个组合注解：包含了三个注解的功能：@Configuration、@EnableAutoConfiguration 和 @ComponentScan
- @Configuration：声明该类是一个配置类，用于定义 Spring Bean。自定义配置类。
- @EnableAutoConfiguration：自动配置 Spring 应用程序。Spring Boot 会根据 classpath 中的 jar 包和项目中的配置，自动配置 Spring Bean 和应用程序的行为。
- @ComponentScan：组件扫描器，自动扫描该类所在包以及子包下的所有 Spring 组件，包括 Spring Bean、Controller、Service 等。
- **SpringBoot约定：启动类，作为扫描包的根（起点）。**

@SpringBootTest作用：

1. 加载Spring应用程序上下文：使用@SpringBootTest注解标记的测试类将创建一个Spring应用程序上下文，这样测试中所需的所有Bean和配置都将加载到上下文中。这样测试可以使用Spring的依赖注入和自动配置等特性。
2. 配置Spring应用程序上下文：@SpringBootTest允许指定应用程序的配置类，以便可以轻松地使用测试专用的配置类，或者使用实际的应用程序配置。
3. 启动嵌入式Web服务器：如果应用程序中包含嵌入式Web服务器，使用@SpringBootTest注解将启动服务器，使得可以进行集成测试。
4. 管理测试的生命周期：使用@SpringBootTest注解，Spring Boot可以管理测试生命周期，确保在运行测试时正确创建和销毁上下文。



为什么要把启动类放在根包下：

- Spring Boot的启动类通常被放置在应用程序的根包下，这样做是为了确保组件扫描和自动配置能够正确地工作。
- 具体来说，Spring Boot框架在启动时会扫描启动类所在的包及其子包下的所有组件，例如@Service、@Controller和@Repository等注解标记的组件，并将它们自动注册到Spring上下文中。如果启动类不在根包下，那么Spring Boot框架可能无法扫描到其他包中的组件，从而导致应用程序无法正常启动。
- Spring Boot框架还会根据启动类的位置来确定默认的配置路径，例如application.properties或application.yml等文件的位置。如果启动类不在根包下，那么Spring Boot框架可能无法找到这些默认的配置文件，从而导致配置无法生效。



@SpringBootApplication注解包含了@Configuration，相当于启动类可以作为SpringConfig配置类。因此，这里我们@Bean注解来测试@SpringBootApplication注解中的@Configuration注解：

启动类中添加一个自定义类Date Bean

```java
package com.zyf.pk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Boot06Application {
    @Bean
    public Date createDate() {
        return new Date();
    }

    public static void main(String[] args) {
				SpringApplication.run(Boot06Application.class, args);
    }
}
```

HelloController类，将Date类注入到控制类中，并将date返回到浏览器

```java
package com.zyf.pk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author:zhouyangfan
 * @date:2023/3/23
 * @Description:简单的控制器
 **/
@RestController
public class HelloController {
    @Autowired
    private Date date;

    @GetMapping("/hello")
    public String hello() {
        return "欢迎使用SpringBoot3" + date;
    }
}
```

浏览器显示：

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230323172212692.png" alt="image-20230323172212692" style="zoom: 50%;" />

可以看到，date bean对象已经成功注入到ioc容器中。



### 2.4.1 启动类的run方法

- 启动类中，run方法返回的是**org.springframework.context.ConfigurableApplicationContext**应用程序上下文对象，是Spring容器中的一个对象，负责管理和协调应用程序中所有的Bean组件

- 通过访问应用程序上下文对象，您可以获取应用程序中的Bean组件，以及管理和配置这些组件。可以使用`getBean()`方法获取某个Bean组件的实例，或者使用`registerShutdownHook()`方法注册一个应用程序关闭钩子，以便在应用程序关闭时清理资源。

下面这个例子，使用上下文对象的`getBean`方法在容器中获取date bean。

```java
package com.zyf.pk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Boot06Application {
    @Bean
    public Date createDate() {
        return new Date();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext cts = SpringApplication.run(Boot06Application.class, args);
        Date dateBean = cts.getBean(Date.class);
        System.out.println("dateBean = " + dateBean);
    }
}
```

输出：

```
dateBean = Thu Mar 23 17:21:42 CST 2023
```

## 2.5 SpringBoot运行方式

- 开发工具，IDEA中执行main方法
- Maven插件 `mvn spring-boot:run`
- 打包成可执行 JAR 文件运行，`java -jar <jar文件名>.jar`
- 使用 Docker 运行：将 Spring Boot 项目打包成 Docker 镜像，然后在 Docker 容器中运行项目

### 2.5.1 jar和sprintboot jar区别

JAR（Java Archive）是Java平台中一种常见的打包格式，用于将一组Java类和相关资源文件打包成一个可执行的二进制文件。它可以包含Java类文件、资源文件、配置文件、库文件等等。当您使用Java开发应用程序时，可以将您的代码和相关依赖项打包到一个JAR文件中，并将其分发给其他开发者或用户。这个JAR文件可以在任何支持Java运行环境的机器上运行。

Spring Boot JAR文件包含应用程序的所有依赖项，并且它可以作为一个**可执行文件**直接运行，而不需要部署到一个Web服务器上。因此，它通常被称为可执行JAR（executable JAR）。

## 2.6 外部化配置

### 2.6.1 配置文件基础

配置文件的两种格式：

- properties   `key=value`
- yml     `key:[空格]值`

Java Properties 文件以键值对的形式存储数据，每个键值对都由一个键和一个对应的值组成，它们之间用等号或分隔。

YAML（"YAML Ain't Markup Language"）是一种人类友好的数据序列化格式，经常用于配置文件。YAML文件使用缩进和换行符来表示数据结构，而不使用标签、属性或其他可读性差的字符，因此它比许多其他数据格式更容易阅读和编写。

#### 2.6.1.1 配置文件格式

yml的基本语法规则：

- 大小写敏感
- 使用缩进表示层级关系
- 缩进只可以使用**空格**，不允许使用Tab键
- 缩进的空格数目不重要，相同层级的元素左对齐即可
- `#`字符表示注释，只支持单行注释。`#`放在注释行的第一个字符。
- 建议yml编写只用小写和空格。

yml支持三种数据格式：

- 对象：键值对的集合。
- 数组：一组按次序排列的值。
- 标量：单个的、不可再分的值（数字、字符串，布尔值）。

#### 2.6.1.2 application文件

在Spring Boot项目中，`application.properties`或`application.yml`文件是用于配置应用程序的文件之一。这个文件包含了应用程序需要的配置信息，比如端口号、数据库连接信息、日志级别等。当应用程序启动时，Spring Boot会自动读取这个文件并将配置信息加载到应用程序中。

#### 2.6.1.3 properties配置application

**application文件推荐放在resources目录下，并且使用application命名**

需求：在application.properties提供应用程序的name，owner，port基本信息，使用@Value()读取这些数据，显示给用户。

1. 创建application配置文件

```properties
app.name=zyf
app.age=23
app.hobby=football
```

2. 创建service类SomeService使用@Value注解读取application配置文件中的数据配置到属性上

```java
package com.zyf.exer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

/**
 * @author:zhouyangfan
 * @date:2023/3/23
 * @Description:service类
 **/
@Service
public class SomeService {
    @Value("${app.name}")
    private String name;

    @Value("${app.age}")
    private int age;
    @Value("${app.hobby}")
    private String hobby;

    public void printInfo() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        String info = stringJoiner.add(name).add(String.valueOf(age)).add(hobby).toString();
        System.out.println("info = " + info);
    }
}
```

3. 创建测试类，进行测试：

```java
package com.zyf.exer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SomeServiceTest {
    @Autowired
    private SomeService someService;

    @Test
    void test01() {
        someService.printInfo();
    }
}
```

4. 输出

```properties
info = zyf 23 football
```

#### 2.6.1.4 yml配置application

对于2.6.1.2 只改动配置文件：创建yml文，名为application.yml

```yaml
#编写配置
#层级关系需要换行加空格
app:
  name: zyf
  age: 23
  hobby: football
```

单元测试结果

```
info = zyf 23 football
```

#### 2.6.1.5 Environment

- Spring Boot的`Environment`是一个接口，它提供了访问应用程序上下文中配置属性的方法。在Spring Boot应用程序中，`Environment`提供了许多有用的方法来检索各种配置属性，如**配置文件、系统环境变量、命令行参数、属性文件**等。
- Spring Boot的`Environment`接口的主要实现是`StandardEnvironment`。`StandardEnvironment`包含了一些默认的配置属性，比如系统环境变量、JVM系统属性等。此外，Spring Boot还提供了一些其他的实现类，如`MockEnvironment`用于测试环境。
- 在Spring Boot应用程序中，可以通过`@Autowired`注解将`Environment`对象注入到Bean中，然后使用它来访问应用程序上下文中的配置属性。

application.yml配置类

```yml
#编写配置
#层级关系需要换行加空格
app:
  name: zyf
  age: 23
  hobby: football
  height: 183.5
```

创建EnviService类，将Environment接口对象注入到容器中，使用environment对象来访问应用程序上下文中配置属性的方法。

```java
package com.zyf.exer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author:zhouyangfan
 * @date:2023/3/24
 * @Description:environment接口
 **/
@Service
public class EnviService {
    @Autowired
    private Environment environment;

    public void printInfo() {
        String name = environment.getProperty("app.name");
        System.out.println(name);

        if (environment.containsProperty("app.age")) {
            System.out.println("存在age属性");
        }

        //读取key的值，转成期望类型，同时提供默认值
        Double height = environment.getProperty("app.height", Double.class, 180.0);
        System.out.println(height);
    }
}
```

单元测试类

```java
package com.zyf.exer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EnviServiceTest {
    @Autowired
    private EnviService enviService;
    @Test
    void test01() {
        enviService.printInfo();
    }
}
```

输出

```
zyf
存在age属性
183.5
```



#### 2.6.1.6 组织多配置文件

每个框架独立一个配置文件，最后将多个文件集中到application。

创建redis配置文件redis.yml和mysql配置文件db.yml，放到conf目录下

```yml
spring:
  redis:
    host: 192.68.1.10
    port: 6379
    password: 123456
```

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/db
    username: root
    password: 123456
```

将redis.yml和db.yml导入进application中

```yml
#导入其他配置文件,多个文件使用逗号作为分隔符
spring:
  config:
    import: conf/db.yml,conf/redis.yml
```

创建一个MultiFileConfigurationService类，用来读取输出配置的信息

```java
package com.zyf.exer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author:zhouyangfan
 * @date:2023/3/24
 * @Description:读取多文件配置信息
 **/
@Service
public class MultiFileConfigurationService {
    @Value("${spring.redis.host}")
    private String redisHostName;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    public void print() {
        System.out.println("redisHostName = " + redisHostName);
        System.out.println("jdbcUrl = " + jdbcUrl);
    }
}
```

单元测试类

```java
package com.zyf.exer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MultiFileConfigurationServiceTest {
    @Autowired
    private MultiFileConfigurationService multiFileConfigurationService;

    @Test
    void tset01() {
        multiFileConfigurationService.print();
    }
}
```

输出

```
redisHostName = 192.68.1.10
jdbcUrl = jdbc:mysql://localhost:3306/db
```

#### 2.6.1.7 多环境配置

Spring Profiles 是 Spring 框架的一个特性，它允许你根据不同的环境或用例定义应用程序的不同配置。Profiles 可以用于根据活动的 profile 激活或禁用特定的 bean 或配置。

Profiles 可以在各种场景中发挥作用，例如：

- 为不同的环境（如开发、测试、生产）定义不同的数据库配置。
- 为不同的环境配置不同的日志记录设置。

要为不同的环境配置不同的属性，使用以下命名规则：

- `application-{profile}.properties` 或 `application-{profile}.yml`：其中 `{profile}` 是要激活的 profile 名称。
- 推荐使用dev表示开发，test表示测试，prod表示生产 ，feature表示特性。

注：SpringBoot会加载application以及application-{profile}两类文件，不是指单独加载application-{profile}。



创建两个环境application-dev和application-test，与application放在同一目录下，分别表示开发环境和测试环境：

application-dev

```yml
myapp:
  memo: 这是开发环境配置


#指定环境名称
spring:
  config:
    activate:
      on-profile: dev
```

application-test

```yml
myapp:
  memo: 这是测试环境配置


#指定环境名称
spring:
  config:
    activate:
      on-profile: test
```

在application.yml文件中 激活使用的环境配置文件，这里激活的测试环境

```yml
  #激活环境
  profiles:
    active: test
```

创建一个多环境配置服务类，用来输出环境的备注memo

```java
package com.zyf.exer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author:zhouyangfan
 * @date:2023/3/24
 * @Description:多环境配置类
 **/
@Service
public class MultiEnvironmentConfigurationService {
    @Value("${myapp.memo}")
    private String memo;

    //输出备注信息
    public void printMemo() {
        System.out.println("memo = " + memo);
    }
}
```

单元测试

```java
package com.zyf.exer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MultiEnvironmentConfigurationServiceTest {
    @Autowired
    private MultiEnvironmentConfigurationService multiEnvironmentConfigurationService;

    @Test
    void test01() {
        multiEnvironmentConfigurationService.printMemo();
    }
}
```

输出

```
memo = 这是测试环境配置
```

可以看到，成功激活了测试环境。



### 2.6.2 配置Bean

使用@ConfigurationProperties注解可以方便地将配置文件中的属性值映射到Java类中，从而简化代码，并且可以提高可读性和可维护性。

#### 2.6.2.1 简单属性绑定

编写一个AppBean类，有三个属性：name、age和hobby

使用@Component和@ConfigurationProperties将配置项绑定到Bean的属性

```java
package com.zyf.exer.pk01;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author:zhouyangfan
 * @date:2023/3/24
 * @Description:配置类
 **/
@Component
//prefix表示配置文件中多个key的公共开始部分
@ConfigurationProperties(prefix = "app")
public class AppBean {
    //key名称与属性名相同
    private String name;

    private int age;

    private String hobby;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "AppBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}
```

配置文件

```yml
myapp:
  name: zyf
  age: 23
  hobby: football
```

单元测试

```java
package com.zyf.exer.pk01;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AppBeanTest {
    @Autowired
    private AppBean appBean;
    @Test
    void test01() {
        System.out.println(appBean.toString());
    }
}
```

输出

```
AppBean{name='zyf', age=23, hobby='football'}
```



**使用@ConfigurationProperties注解的注意点：**

1. 配置文件前缀需要与Java类的前缀一致（注解中的prefix属性与配置文件中的公共部分）。
2. 需要在Spring Boot应用程序中启用@ConfigurationProperties注解才能生效。可以通过在主类上添加@EnableConfigurationProperties注解来启用。
3. Java类需要有默认的构造函数，否则会抛出异常。
4. Java类的属性需要有getter和setter方法，否则无法将属性值注入到该类中。
5. 配置文件中的属性名需要与Java类中的属性名一致，或者使用@Value注解指定属性名。
6. 可以通过@ConfigurationProperties注解的ignoreInvalidFields属性设置为true，忽略无效的属性值。
7. 可以通过@ConfigurationProperties注解的ignoreUnknownFields属性设置为true，忽略未知的属性名。
8. Bean的static属性不支持。
9. 使用@ConfigurationProperties注解的bean必须要有无参构造器。
10. springboot默认生成代理Bean，@Configuration(proxyBeanMethods = false)配置生成普通bean。

#### 2.6.2.2 嵌套Bean

需求：Bean中包含了其他Bean作为属性，将配置文件的配置项绑定到Bean以及引用类型的成员。Bean的定义无特殊要求。

NestAppBean中有四个字段，分别为name、age、hobby和Security类型的security，其中Security类中有两个String类型的字段分别为userName和password。

Security类

```java
package com.zyf.exer.pk02;

/**
 * @author:zhouyangfan
 * @date:2023/3/25
 * @Description:Security类，作为NestAppBean的一个字段
 **/

public class Security {
    private String userName;

    private String password;

    public Security(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Security() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Security{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
```

NestAppBean

```java
package com.zyf.exer.pk02;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author:zhouyangfan
 * @date:2023/3/25
 * @Description:Bean作为属性
 **/
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "myapp")
public class NestAppBean {
    private String name;
    private Integer age;
    private String hobby;
    private Security security;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    @Override
    public String toString() {
        return "NestAppBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hobby='" + hobby + '\'' +
                ", security=" + security +
                '}';
    }
}
```

application配置文件

```yml
#编写配置
#层级关系需要换行加空格
myapp:
  name: zyf
  age: 23
  hobby: football
  security:
    userName: root
    password: 123456
```

单元测试类

```java
package com.zyf.exer.pk02;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NestAppBeanTest {
    @Autowired
    private NestAppBean nestAppBean;

    @Test
    void test01() {
        System.out.println(nestAppBean.toString());
    }
}
```

输出：

```
NestAppBean{name='zyf', age=23, hobby='football', security=Security{userName='root', password='123456'}}
```

#### 2.6.2.3 启用和扫描注解

`@EnableConfigurationProperties`是一个Spring Boot注解，它允许将@ConfigurationProperties注解的类与Spring容器中的bean关联起来。将`@EnableConfigurationProperties`注解放在Spring Boot应用程序的启动类上是常见的做法。

```java
package com.zyf.exer;

import com.zyf.exer.pk02.NestAppBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({NestAppBean.class})
public class Boot01Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot01Application.class, args);
    }
}
```

这样可以保证Spring将`NestAppBean`类添加到Spring容器中。而NestAppBean类不需要在添加@Configuration注解。



`@ConfigurationPropertiesScan`是Spring Boot 2.2版本引入的一个注解，用于自动扫描和注册使用`@ConfigurationProperties`注解的类。与`@EnableConfigurationProperties`注解相比，它更加方便，因为它无需显式地指定需要添加到Spring容器中的配置属性类。

使用`@ConfigurationPropertiesScan`注解，您可以告诉Spring Boot在应用程序启动时自动扫描指定的包或类，并将所有使用`@ConfigurationProperties`注解的类添加到Spring容器中。

```java
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.zyf.exer.pk02")
public class Boot01Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot01Application.class, args);
    }
}
```

#### 2.6.2.4 绑定第三方对象

假设Security是第三方对象，我们无法在Security代码中添加注解，因此我们使用方法（SpringConfig）来配置Security

编写配置文件：

```yml
security: 
  userName: zyf
  password: zyf123
```

编写Config配置类

```java
package com.zyf.exer.pk03;

import com.zyf.exer.pk02.Security;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:zhouyangfan
 * @date:2023/3/25
 * @Description:配置类
 **/
@Configuration
public class Config {
    @Bean
    @ConfigurationProperties(prefix = "security")
    public Security createSecurity(){
        return new Security();
    }
}
```

单元测试

```java
package com.zyf.exer.pk03;

import com.zyf.exer.pk02.Security;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ConfigTest {
    @Autowired
    private Security security;
    @Test
    void test01() {
        System.out.println(security);
    }
}
```

输出

```
Security{userName='zyf', password='zyf123'}
```

#### 2.6.2.5 集合属性注入

需求：Teacher类中有4个属性，分别为String类型的name属性，列表`List<Student> students`属性，`Map<String, Course> courses`属性。Student类中有一个name属性和一个age属性，Course类中有两个属性分别为courseName和courseId。

Teacher类

```java
package com.zyf.exer.pk04;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author:zhouyangfan
 * @date:2023/3/25
 * @Description:teacher Bean类
 **/
@ConfigurationProperties(prefix = "teacher")
public class Teacher {
    private String name;

    private List<Student> students;

    private Map<String,Course> courses;

    private String[] paper;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public void setCourses(Map<String, Course> courses) {
        this.courses = courses;
    }

    public String[] getPaper() {
        return paper;
    }

    public void setPaper(String[] paper) {
        this.paper = paper;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", students=" + students +
                ", courses=" + courses +
                ", paper=" + Arrays.toString(paper) +
                '}';
    }
}
```

Student类

```java
package com.zyf.exer.pk04;

/**
 * @author:zhouyangfan
 * @date:2023/3/25
 * @Description:student类
 **/
public class Student {
    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

Course类

```java
package com.zyf.exer.pk04;

/**
 * @author:zhouyangfan
 * @date:2023/3/25
 * @Description:Course
 **/
public class Course {
    private String courseName;

    private Integer courseId;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", courseId=" + courseId +
                '}';
    }
}
```

在启动类中配置扫包

```java
package com.zyf.exer;

import com.zyf.exer.pk02.NestAppBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.zyf.exer.pk04")
public class Boot01Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot01Application.class, args);
    }
}
```

application配置文件

```yml
teacher:
  name: zhouyangfan
  paper:
    - 线性代数论文
    - 微积分论文
  students:
    - name: jack
      age: 17
    - name: tom
      age: 18
  courses:
    cour01:
      courseName: 概率论
      courseId: 1
    cour02:
      courseName: 几何
      courseId: 2
```

单元测试类

```java
package com.zyf.exer.pk04;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeacherTest {
    @Autowired
    private Teacher teacher;

    @Test
    void test01() {
        System.out.println(teacher.toString());
    }
}
```

输出：

```java
Teacher{name='zhouyangfan', students=[Student{name='jack', age=17}, Student{name='tom', age=18}], courses={cour01=Course{courseName='概率论', courseId=1}, cour02=Course{courseName='几何', courseId=2}}, paper=[线性代数论文, 微积分论文]}
```

可以看到，所有的属性已经都成功注入到teacher bean中。

#### 2.6.2.6 指定数据源文件

application做配置是经常使用的，除此以外我们能够指定某个文件作为数据源，@PropertySource是主力，用以加载properties文件。

创建User类，其中有两个字段，分别为String类型的name和int类型的age

```java
package com.zyf.exer.pk05;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author:zhouyangfan
 * @date:2023/3/25
 * @Description:User Bean
 **/
@Configuration
@PropertySource(value = "classpath:/conf/user-config.properties")
@ConfigurationProperties(prefix = "app")
public class User {
    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

数据源文件配置

```properties
app.name=zyf
app.age=24
```

单元测试

```java
package com.zyf.exer.pk05;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {
    @Autowired
    private User user;

    @Test
    void test01() {
        System.out.println(user.toString());
    }
}
```

输出

```java
User{name='zyf', age=24}
```

#### 2.6.2.7 配置bean总结

```
@ConfigurationProperties
位置：
		1.在类上面，需要有源代码
		2.方法上面，使用第三方对象，配和@Bean注解
```

数据来源：

- application文件（yml或properties）
- 指定数据来源Properties(value = “xxx.properties”)

配合@ConfigurationProperties的注解

1. @Configuration
2. @ConfigurationPropertiesScan
3. @EnableConfigurationProperties（推荐使用）

注意：

1. Bean类中有无参构造方法
2. 属性有set方法
3. 静态属性无法进行注入
4. 使用构造方法也能进行配置bean，无需set方法，但不推荐。

#### 2.6.2.8  @ImportResource

将对象注入到Spring容器，可以通过如下方式：

- 传统的XML配置文件
- JavaConfig技术：@Configuration与@Bean
- 创建对象的注解：@Controller、@Service、@Repository、@Component

如果需要xml提供的bean声明，`@ImportResource`加载xml注册Bean

需求：使用XML配置Spring容器。声明Bean

1. 创建PersonBean类，对象由容器管理。

```java
package com.zyf.exer.pk06;

/**
 * @author:zhouyangfan
 * @date:2023/3/26
 * @Description:PersonBean
 **/
public class PersonBean {
    private String name;

    private Integer age;

    public PersonBean() {
    }

    public PersonBean(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

2. resource目录下创建applicationContext.xml容器管理

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="personBean" class="com.zyf.exer.pk06.PersonBean">
        <property name="name" value="zyf"/>
        <property name="age" value="13"/>
     </bean>
</beans>
```

使用@ImportResource注解将xml配置中的bean注入到容器中，测试

```java
package com.zyf.exer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:/applicationContext.xml")
public class Boot01Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Boot01Application.class, args);
        boolean personBean = run.containsBean("personBean");
        System.out.println(personBean);
    }
}
```

输出true，说明PersonBean已经被容器管理。

## 2.7 AOP

- Aspect：表示切面，开发自己编写功能增强代码的地方，这些代码会通过动态代理加入到原有的业方法中。@Aspect注解表示当前类是切面类。切面类是一个普通类。
- Joinpoint：表示连接点，连接切面和目标对象。或是一个方法名称，一个包名，类名。这个位置执行切面中的功能代码。
- Pointcut：筛选出的连接点。一个类中的所有方法都可以是JointPoint，具体哪个方法要增加功能，这个方法就是Pointcut。
- Advice：advice 是以切面（Aspect）的形式存在的。一个切面包括一个或多个 advice，以及定义在哪些Joinpoint（连接点）上执行 advice 的 Pointcut（切点）。Spring 框架提供了以下五种类型的 advice：
  1. Before advice（前置通知）：在目标对象的方法调用之前执行代码块。
  2. After returning advice（返回通知）：在目标对象的方法成功执行并返回之后执行代码块。
  3. After throwing advice（异常通知）：在目标对象的方法抛出异常时执行代码块。
  4. After advice（后置通知）：在目标对象的方法调用之后执行代码块，无论是否抛出异常。
  5. Around advice（环绕通知）：在目标对象的方法调用前后执行代码块，可以控制是否执行目标对象的方法调用，以及修改返回值或抛出异常。

**主要包括五个注解（来自aspectj框架）**：

- @Before：在切点方法之前执行。
- @After：在切点方法之后执行。
- @AfterReturning：切点方法返回后执行
- @AfterThrowing：切点方法抛异常执行
- @Around：属于环绕增强，能控制切点执行前，执行后。功能最强的注解。

**AOP maven依赖**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

定义业务接口SomeService

```java
package com.zyf.aop.service;

public interface SomeService {
    void query(Integer id);

    void save(String name, Integer age);

}
```

定义业务实现类SomeServiceImpl

```java
package com.zyf.aop.service;

import org.springframework.stereotype.Service;

/**
 * @author:zhouyangfan
 * @date:2023/3/27
 * @Description:业务实现类
 **/
@Service
public class SomeServiceImpl implements SomeService {
    @Override
    public void query(Integer id) {
        System.out.println(">>>SomeService的业务方法query<<<");
    }

    @Override
    public void save(String name, Integer age) {
        System.out.println(">>>SomeService的业务方法save<<<");
    }
}
```

定义切面类（@Aspect）

```java
package com.zyf.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * @author:zhouyangfan
 * @date:2023/3/27
 * @Description:
 **/
@Component
@Aspect
public class LogAspect {
    @Before("execution(* com.zyf.aop.service..*.*(..))")
    public void sysLog(JoinPoint joinPoint) {
        StringJoiner log = new StringJoiner("|", "{", "}");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.add(formatter.format(LocalDateTime.now()));

        //当前执行业务方法名称
        String methodName = joinPoint.getSignature().getName();
        log.add(methodName);

        //方法的参数
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.add(arg == null ? "-" : arg.toString());
        }

        System.out.println("日志：" + log);
    }
}
```

单元测试类

```JAVA
package com.zyf.aop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SomeServiceImplTest {
    @Autowired
    private SomeService someService;

    @Test
    void test01() {
        someService.query(100);
        someService.save("zyf", 23);
    }
}
```

输出

```java
日志：{2023-03-27 16:42:29|query|100}
>>>SomeService的业务方法query<<<
日志：{2023-03-27 16:42:29|save|zyf|23}
>>>SomeService的业务方法save<<<
```

# 3. 自动配置

自动配置：从类路径中去搜索相关的jar，根据jar的内容，尝试创建所需要的对象。如果有mybatis.jar，尝试创建DataSource（根据配置文件中url，username，password）连接数据库。还需要创建SqlSessionFactory，Dao接口的代理对象，这些开发人员不需要写一行代码，就能使用MyBatis框架了。

## 3.1 自动配置类

- Spring Boot的自动配置类是一组用于自动化配置应用程序的类。
- Spring Boot自动配置类通常位于org.springframework.boot.autoconfigure包下，它们根据类路径中存在的类和配置属性自动配置Spring应用程序。
- Spring Boot的自动配置类通常以`XXXXAutoConfiguration`命名，其中`XXXX`代表所要自动配置的功能。例如，DataSourceAutoConfiguration用于自动配置数据源，WebMvcAutoConfiguration用于自动配置Web应用程序，SecurityAutoConfiguration用于自动配置安全等等。

## 3.2 imports文件

imports文件的位置：

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328132839365.png" alt="image-20230328132839365" style="zoom:50%;" />

**imports文件内容为自动配置类的列表**

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328133041681.png" alt="image-20230328133041681" style="zoom: 33%;" />

第三方库的自动配置列表imports文件也能找到，如MyBatis

![image-20230328133739646](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328133739646.png)

![image-20230328133849917](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328133849917.png)

SpringBoot遍历imports文件列表，加载各个配置类。

## 3.3 启用自动配置注解

@EnableAutoConfiguration：启用自动配置，加载相关的自动配置类(XXXAutoConfiguration)。

@EnableAutoConfiguration注解的元注解中的@Import注解`@Import({AutoConfigurationImportSelector.class})`它导入了一个名为 `AutoConfigurationImportSelector` 的选择器，它是 Spring Boot 自动配置机制的核心。这个选择器会查找类路径上的 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 文件，加载其中定义的自动配置类，并将这些自动配置类中的 Bean 注册到 Spring 容器中。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328135334807.png" alt="image-20230328135334807" style="zoom:50%;" />

SpringBoot自动配置需要starter依赖和配置类

下图为MyBatis的自动配置类MybatisAutoConfiguration：

![image-20230328141358185](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328141358185.png)

条件注解：`@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})`在这个特定的注解中，它检查是否存在 `SqlSessionFactory.class` 和 `SqlSessionFactoryBean.class` 两个类。如果这两个类都在类路径中存在，那么带有这个注解的 bean 将被配置和加载。如果其中任何一个类都不在类路径上，那么带有这个注解的 bean 将不会被加载。

`@EnableConfigurationProperties({MybatisProperties.class})`绑定Bean，加载application配置文件中的配置项。可以看到MybatisProperties类中，@ConfigurationProperties注解参数前缀为mybatis，下面的属性都是可以配置的MyBatis参数，可以写在application.yml文件中，以mybatis开头。

![image-20230328142842125](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328142842125.png)

# 4. 数据库访问

## 4.1 DataDource

**DataSourceAutoConfiguration自动配置类可以自动配置数据源**

![image-20230328145906325](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328145906325.png)

`@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})`表明自动配置需要DataSource和EmbeddedDatabaseType类

`@EnableConfigurationProperties({DataSourceProperties.class})`注解用于启用`@ConfigurationProperties`注解的DataSourceProperties类，将DataSourceProperties类自动注入到DataSourceAutoConfiguration自动配置类中。

**DataSourceProperties类**

![image-20230328150307056](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328150307056.png)

可以看到DataSourceProperties类有@ConfigurationProperties注解，配置项的前缀为spring.datasource。

```
private ClassLoader classLoader;
private boolean generateUniqueName = true;
private String name;
private Class<? extends DataSource> type;
private String driverClassName;
private String url;
private String username;
private String password;
private String jndiName;
private EmbeddedDatabaseConnection embeddedDatabaseConnection;
private Xa xa = new Xa();
private String uniqueName;
```

`@Import({DataSourcePoolMetadataProvidersConfiguration.class})`注解导入数据库连接池。SpringBoot支持多种数据库连接池，优先使用HikariCP，其次是Tomcat pooling，再次是DBCP2，如果以上都没有，最后会使用Oracle UCP连接池。当项目中starter依赖了spring-boot-starter-jdbc或者spring-boot-starter-data-jpa默认添加HikariCP连接池依赖，也就是默认使用HikariCP连接池。

## 4.2 JdbcTemplate

JdbcTemplate是Spring Framework中提供的一个持久化工具，它是一个简单但功能强大的类，可用于执行SQL语句并与关系型数据库进行交互。它为开发人员提供了一种方便且直接的方式来执行常规的CRUD（创建、读取、更新和删除）操作，而无需处理与底层数据访问层（例如JDBC）相关的复杂性。

JdbcTemplateAutoConfiguration自动配置了JdbcTemplate对象，并对JdbcTemplate做了简单地初始设置。

### 4.2.1 环境搭建

SpringBoot能够自动执行DDL，DML脚本，两个脚本文件名称默认是schema.sql和data.sql。脚本文件在类路径中自动加载。

自动执行脚本还涉及到spring.sql.init.mode配置项

- always：总是执行数据库初始化脚本。
- never：禁用数据库初始化。

环境准备

- 创建数据库（Blog）和表（article）
- 创建Spring Boot工程
- Maven依赖
- JDK20
- Starter依赖：Lombok、MySQL Driver，JDBC API

数据库和表

schema.sql文件：

```sql
CREATE TABLE `article`(
    `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` INT(11) NOT NULL COMMENT '作者id',
    `title` VARCHAR(100) NOT NULL COMMENT '文章标题',
    `summary` VARCHAR(200) DEFAULT NULL COMMENT '文章概要',
    `read_count` INT(11) UNSIGNED zerofill NOT NULL COMMENT '阅读读数',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
)ENGINE =InnoDB AUTO_INCREMENT = 1 default charset =utf8mb4;
```

data.sql文件

```mysql
INSERT INTO `article` VALUES ('1', '2101', 'SpringBoot 核心注解',
                              '核心注解的主要作用','00000008976',
                              '2023-01-16 12:11:12',
                              '2023-01-16 12:11:19');

INSERT INTO `article` VALUES ('2', '356752', 'JVM调优',
                              'HotSpot虚拟机详解','00000000026',
                              '2023-01-16 12:15:27',
                              '2023-01-16 12:15:30');
```

pom.xml

```xml
    <dependencies>
<!--        JdbcTemplate : 连接池：HikariCP-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

<!--        MySQL驱动-->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>

<!--        Lombok依赖-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

<!--        junit测试-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

在application.properties中配置数据源

```
#配置数据源
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/blog
spring.datasource.username=root
spring.datasource.password=zyf2563085

#设置执行数据库脚本
spring.sql.init.mode=always
```

运行项目后，创建了article表，其中添加了两条数据。

![image-20230328215533458](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230328215533458.png)

执行完数据库相关操作的脚本后，将spring.sql.init.mode修改为never

```properties
spring.sql.init.mode=never
```

### 4.2.2 JdbcTemplate访问MySQL

![image-20230329090840610](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230329090840610.png)

`spring-boot-starter-jdbc`是Spring Boot中的一个起步**依赖**，针对Spring Boot应用的，提供了对JDBC的全套支持，包括自动配置数据源、提供JdbcTemplate和事务管理等功能。

![image-20230329091019918](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230329091019918.png)

`spring-jdbc`是Spring框架中与JDBC相关的核心组件之一，提供了一系列的JDBC操作支持，如执行SQL查询、更新和批处理等。它的设计目的是让开发者更加方便地在Spring应用中使用JDBC。

SpringBoot自动将JdbcTemplate对象注入IoC容器中，我们可以直接调用JdbcTemplate的方法执行查询，更新，删除的SQL。归纳起来有以下几种方法：

- execute方法：可以用于执行任何SQL语句，常用来执行DDL语句。
- update、batchUpdate方法：用于执行新增、修改与删除等语句。
- query和queryForXXX方法：用于执行查询相关的语句。
- call方法：用于执行数据库存储过程和函数相关的语句。

1. 数据库（blog）和表（article）准备

2. 创建持久类

```java
package com.zyf.jdbctpl01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author:zhouyangfan
 * @date:2023/3/29
 * @Description:实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePO {
    private Integer id;
    private Integer userId;
    private String title;
    private String summary;
    private Integer readCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```

3. 单元测试中，引入JdbcTemplate对象，测试聚合函数

```java
package com.zyf.jdbctpl01;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author:zhouyangfan
 * @date:2023/3/29
 * @Description:测试JdbcTemplate
 **/
@SpringBootTest
public class TestJdbcTemplate {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testCount() {
        String sql = "select count(*) as ct from article";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println("记录的行数 = " + count);
    }
}
```

4. 输出结果

```
记录的行数 = 2
```

### 4.2.3 JdbcTemplate查询

#### 4.2.3.1 BeanPropertyRowMapper类：

`BeanPropertyRowMapper`是Spring Framework中的一个实用工具类，它实现了`RowMapper`接口，用于将`ResultSet`的数据映射到Java对象中。

`BeanPropertyRowMapper`的主要作用是将`ResultSet`中的一行数据映射到指定类型的Java对象中。具体来说，它通过反射机制，将`ResultSet`中的列名与Java对象的属性名进行匹配（完全匹配或者驼峰），然后自动将数据填充到Java对象中，最后返回一个包含Java对象的List集合。

#### 4.2.3.2  测试“?”占位符

```java
@Test
void testQuery() {
    String sql = "select * from article where id = ?";
    ArticlePO articlePO = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ArticlePO.class), 1);
    System.out.println("articlePO = " + articlePO);
}
```

输出

```
articlePO = ArticlePO(id=1, userId=2101, title=SpringBoot 核心注解, summary=核心注解的主要作用, readCount=8976, createTime=2023-01-16T12:11:12, updateTime=2023-01-16T12:11:19)
```

queryForObject查询，若没有查询到数据，则会报错。

#### 4.2.3.3 自定义RowMapper

RowMapper是一个函数式接口，可以自定义映射关系，返回一个pojo对象。

```java
@FunctionalInterface
public interface RowMapper<T> {
    @Nullable
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
```

重写RowMapper中的方法来自定义RowMapper

```java
@Test
void testRowMapper() {
    String sql = "select * from article where id = ?";
    ArticlePO articlePO = jdbcTemplate.
    queryForObject(sql, (rs, rownum) -> {
        var id = rs.getInt("id");
        var userId = rs.getInt("user_id");
        var title = rs.getString("title");
        var summary = rs.getString("summary");
        var readCount = rs.getInt("read_count");
        var createTime = new Timestamp(rs.getTimestamp("create_time").getTime()).toLocalDateTime();
        var updateTime = new Timestamp(rs.getTimestamp("create_time").getTime()).toLocalDateTime();
        return new ArticlePO(id,userId,title,summary,readCount,createTime,updateTime);
    }, 1);
    System.out.println("articlePO = " + articlePO);
}
```

输出

```
articlePO = ArticlePO(id=1, userId=2101, title=SpringBoot 核心注解, summary=核心注解的主要作用, readCount=8976, createTime=2023-01-16T12:11:12, updateTime=2023-01-16T12:11:12)
```

#### 4.2.3.3 多行查询queryForList

queryForList查不到数据不会报错

```java
@Test
void testQueryList() {
    String sql = "SELECT * FROM article ORDER BY 'id';";
    //一个list成员是一个记录，Map是列名
    List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql);
    listMap.forEach(el -> {
        el.forEach((field, value) -> {
            System.out.println("字段名称" + field + ",列值" + value);
        });
        System.out.println("===================");
    });
}
```

输出

```xml
字段名称id,列值1
字段名称user_id,列值2101
字段名称title,列值SpringBoot 核心注解
字段名称summary,列值核心注解的主要作用
字段名称read_count,列值8976
字段名称create_time,列值2023-01-16T12:11:12
字段名称update_time,列值2023-01-16T12:11:19
===================
字段名称id,列值2
字段名称user_id,列值356752
字段名称title,列值JVM调优
字段名称summary,列值HotSpot虚拟机详解
字段名称read_count,列值26
字段名称create_time,列值2023-01-16T12:15:27
字段名称update_time,列值2023-01-16T12:15:30
```

### 4.2.4 JdbcTemplate增删改

**修改id为1的记录的title**

```java
@Test
void testUpdate() {
    String sql = "UPDATE article SET title = ? WHERE id = ?";
    int rows = jdbcTemplate.update(sql, "Java编程实战", 1);
    System.out.println("rows = " + rows);
}
```

输出

```
rows = 1
```

**表中添加一条记录**

```java
@Test
void testUpdate01() {
    String sql = "INSERT INTO `article` (`id`, `user_id`, `title`, " +
            "`summary`, `read_count`, `create_time`, `update_time`) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    int rows = jdbcTemplate.update(sql, 3,
            3882, "MySQL是怎样运行的", "MySQL底层原理", "00000009087",
            "2023-01-17 12:11:14", "2023-01-17 12:11:30");
    System.out.println("rows = " + rows);
}
```

删除表中一条记录

```java
@Test
void testdel() {
    String sql = "DELETE FROM `article` WHERE id = ?";
    int rows = jdbcTemplate.update(sql, 3);
    System.out.println("rows = " + rows);
}
```

### 4.2.5 使用命名参数

**NamedParameterJdbcTemplate**是Spring Framework提供的JdbcTemplate的扩展，它提供了一种使用命名参数而不是位置参数的方式来执行SQL查询。NamedParameterJdbcTemplate可以使SQL查询更易于编写和维护，因为它允许您使用具有描述性名称的参数来替换查询中的占位符。

```java
@Autowired
private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
@Test
void namejdbctemplate() {
    String sql = "INSERT INTO `article` (`id`, `user_id`, `title`, " +
            "`summary`, `read_count`, `create_time`, `update_time`) " +
            "VALUES (:id, :uid, :title, :summary, :rc, :ct, :ut)";
    HashMap<String, Object> map = new HashMap<>();
    map.put("id", 3);
    map.put("uid", 133);
    map.put("title", "操作系统");
    map.put("summary", "理解操作系统");
    map.put("rc", 13);
    map.put("ct","2023-01-13 16:03:12");
    map.put("ut","2023-01-13 16:03:12");
    int rows = namedParameterJdbcTemplate.update(sql, map);
    System.out.println("rows = " + rows);
}
```

使用BeanPropertySqlParameterSourc将Java对象中的属性映射到命名参数上。

```java
@Test
void namejdbctemplate01() {
    String sql = "INSERT INTO `article` (`id`, `user_id`, `title`, " +
            "`summary`, `read_count`, `create_time`, `update_time`) " +
            "VALUES (:id, :userId, :title, :summary, :readCount, " +
            ":createTime, :updateTime)";
    ArticlePO articlePO = new ArticlePO(4, 234, "Spring技术", "手写Spring",
            278, LocalDateTime.of(2023, 1, 23, 13, 12, 16),
            LocalDateTime.of(2023, 1, 23, 13, 12, 23));
    BeanPropertySqlParameterSource beanArticlePO = new BeanPropertySqlParameterSource(articlePO);
    int rows = namedParameterJdbcTemplate.update(sql, beanArticlePO);
    System.out.println("rows = " + rows);
}
```

### 4.2.6 多表查询

多表查询的关注的是查询结果映射为Java Object。常用两种方案：

1. 将查询结果转为Map，列名为key，列值是value。这种方式比较通用，适合查询任何表。
2. 根据查询结果中包含的列，创建相对的实体类。属性和查询结果的列对应。将查询结果自定义RowMapper、ResultSetExtractor映射为实体类对象。

创建新的表article_detail，存储文章内容，与article表是一对一关系。

```sql
CREATE TABLE `article_detail`(
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '注解',
	`article_id` INT(11) NOT NULL COMMENT '文章ID',
	`content` TEXT NOT NULL COMMENT '文章内容',
	PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
```

插入数据

```mysql
INSERT INTO article_detail (`id`, `article_id`,`content`) VALUES (123, 1, 'java编程基础');
```

创建一个与article_detai表对应的持久类

```java
package com.zyf.jdbctpl01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:zhouyangfan
 * @date:2023/3/29
 * @Description: ArticleDetailPO类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailPO {
    private Integer id;
    private Integer articleId;
    private String content;
}
```

需求：查询某个文章的全部属性，包括文章内容

1. 创建一个实体类ArticleMainPO，将ArticlePO作为成员变量

```java
package com.zyf.jdbctpl01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author:zhouyangfan
 * @date:2023/3/29
 * @Description:实体类ArticleMainPO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleMainPO {
    private Integer id;
    private Integer userId;
    private String title;
    private String summary;
    private Integer readCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //一对一
    private ArticleDetailPO detail;
}
```

2. 编写代码

```java
void test01() {
    String sql = """
            select m.*, d.id as detail_id, d.article_id, d.content
            from article m join article_detail d
            on m.id = d.article_id
            where m.id = :id
            """;
    HashMap<String, Object> para = new HashMap<>();
    para.put("id", 1);
    List<ArticleMainPO> query = namedParameterJdbcTemplate.query(sql, para, (rs, num) -> {
        var id = rs.getInt("id");
        var userId = rs.getInt("user_id");
        var title = rs.getString("title");
        var summary = rs.getString("summary");
        var readCount = rs.getInt("read_count");
        var createTime = new Timestamp(rs.getTimestamp("create_time").getTime()).toLocalDateTime();
        var updateTime = new Timestamp(rs.getTimestamp("create_time").getTime()).toLocalDateTime();
        var content = rs.getString("content");
        var detailId = rs.getInt("detail_id");
        var articleId = rs.getInt("article_id");
        return new ArticleMainPO(id, userId, title, summary, readCount, createTime, updateTime, new ArticleDetailPO(detailId, articleId, content));
    });
    for (ArticleMainPO articleMainPO : query) {
        System.out.println(articleMainPO);
    }
}
```

3. 输出

```
ArticleMainPO(id=1, userId=2101, title=Java编程实战, summary=核心注解的主要作用, readCount=8976, createTime=2023-01-16T12:11:12, updateTime=2023-01-16T12:11:12, detail=ArticleDetailPO(id=123, articleId=1, content=java编程基础))
```

## 4.3 Mybatis

环境搭建：

1. 创建项目SpringBoot项目
2. maven依赖：LomBok、MySQL Driver、MyBatis Framework
3. 创建实体类Article

```java
package com.zyf.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author:zhouyangfan
 * @date:2023/3/29
 * @Description:实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePO {
    private Integer id;
    private Integer userId;
    private String title;
    private String summary;
    private Integer readCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```

4. 数据源和mybatis配置

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/blog
spring.datasource.username=root
spring.datasource.password=zyf2563085


#配置mybatis
mybatis.configuration.map-underscore-to-camel-case=true

#配置日志
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

### 4.3.1 单表增删改查

**mapper包下新建ArticleMapper接口，test包下新建MyBatisTest测试类**

启动类中使用@MapperScan注解扫描Mapper接口并注册到Spring容器中。

```java
package com.zyf.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = "com.zyf.mybatis.mapper")
@SpringBootApplication
public class BootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMybatisApplication.class, args);
    }
}

```

```java
package com.zyf.mybatis.mapper;

import com.zyf.mybatis.model.ArticlePO;
import org.apache.ibatis.annotations.*;

public interface ArticleMapper {
    @Select("""
            select id, user_id, title, summary, read_count, create_time, update_time 
            from blog.article where id = #{articleId};
            """)
    //查询结果ResultSet和PO对象的属性映射
    @Results(id = "BaseArticleMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "title", property = "title"),
            @Result(column = "summary", property = "summary"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    ArticlePO selectById(@Param("articleId") Integer id);

    //添加insert
    @Insert("""
            insert into article(user_id, title, summary, 
            read_count, create_time, update_time)
            values(#{userId}, #{title}, #{summary}, 
            #{readCount}, #{createTime}, #{updateTime})
            """)
    int insertArticle(ArticlePO articlePO);

    //update
    @Update("""
            update article set read_count = #{readCount} where id = #{id};
            """)
    int updateReadCount(Integer id, Integer readCount);

    @Delete("""
            delete from blog.article where id = #{id}
            """)
    int deleteArticle(Integer id);
}
```

```java
package com.zyf.mybatis;

import com.zyf.mybatis.mapper.ArticleMapper;
import com.zyf.mybatis.model.ArticlePO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author:zhouyangfan
 * @date:2023/3/30
 * @Description:测试类
 **/
@SpringBootTest
public class MyBatisTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void testQuery() {
        ArticlePO articlePO = articleMapper.selectById(1);
        System.out.println("articlePO = " + articlePO);
    }

    @Test
    void testInsert() {
        ArticlePO articlePO = new ArticlePO();
        articlePO.setTitle("Tomcat开发");
        articlePO.setSummary("使用tomcat服务器");
        articlePO.setUserId(19);
        articlePO.setReadCount(1234);
        articlePO.setCreateTime(LocalDateTime.of(2023,1,1,12,12,1));
        articlePO.setUpdateTime(LocalDateTime.of(2023,1,1,12,12,13));
        int rows = articleMapper.insertArticle(articlePO);
        System.out.println("rows = " + rows);
    }

    @Test
    void testUpdate() {
        int rows = articleMapper.updateReadCount(6, 1235);
        System.out.println("rows = " + rows);
    }

    @Test
    void testDelete(){
        int rows = articleMapper.deleteArticle(6);
        System.out.println("rows = " + rows);
    }
}
```

总结：

1. @MapperScan将Mapper接口注入到Spring容器中；
2. @Select注解查询，@Insert注解增加记录，@Update注解修改和@Delete注解删除
3. @Results注解用于将查询结果映射到Java对象的属性上。
   - @Results注解通常用于在@Select注解中指定查询结果映射规则
   - @Result注解必须放在@Results注解内部，且不能单独使用；
   - 如果Java对象属性的名称和查询结果中的列名相同，则可以省略@Result注解中的property和column参数；
   - 如果查询结果中的列名和Java对象属性的名称不同，则需要使用@Result注解指定映射规则。

### 4.3.2 结果映射

将查询结果中的列和实体bean的属性对应关系。

- @Results和@Result，定义一个映射，id为该映射的名称。value为该映射对应值。
- @ResultMap
  1. 先通过@Results定义列的映射关系，@ResultMap可以来复用已定义的映射关系。
  2. 在xml中定义`<resultMap id = "xxx">`，在代码中使用`@ResultMap(value=“xml中的id”)`

编写一个ArticleDao接口，定义一个方法queryById根据id查询记录

```java
package com.zyf.mybatis.mapper;

import com.zyf.mybatis.model.ArticlePO;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;


public interface ArticleDao {
    @Select("""
            select id, user_id, title, summary, read_count, create_time, update_time
            from blog.article where id = #{id}
            """)
    @ResultMap(value = "ArticleMapper")
    ArticlePO queryById(Integer id);
}
```

resultMap的配置文件，resultMap的id为ArticleMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zyf.mybatis.mapper.ArticleDao">
    <resultMap id="ArticleMapper" type="com.zyf.mybatis.model.ArticlePO">
        <id column="id" property="id"/>
        <result column="user_id" property="userId"/>
        <result column="read_count" property="readCount"/>
        <result column="create_time" property="createTime"/>
        <result column="update_time" property="updateTime"/>
    </resultMap>
</mapper>
```

单元测试类

```java
package com.zyf.mybatis.mapper;

import com.zyf.mybatis.model.ArticlePO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleDaoTest {
    @Autowired
    private ArticleDao articleDao;

    @Test
    void testQuery() {
        ArticlePO articlePO = articleDao.queryById(1);
        System.out.println("articlePO = " + articlePO);
    }
}
```

输出

```java
articlePO = ArticlePO(id=1, userId=2101, title=Java编程实战, summary=核心注解的主要作用, readCount=8976, createTime=2023-01-16T12:11:12, updateTime=2023-01-16T12:11:19)
```

### 4.3.3 SQLProvider

​         我们可以直接在方法上面直接编写SQL语句。使用Text Block编写长的SQL显得不够简洁。MyBatis提供了SQL提供者功能。将SQL以方法的形式定义在单独的类中。Mapper接口通过引用SQL提供者中的方法名称，表示要执行的SQL。

SQL提供者有四类：

1. @SelectProvider
2. @InsertProvider
3. @UpdateProvider
4. @DeleteProvider

**提供者类的静态方法**

```java
public static String selectById(){
			return "xxxxxxSQL语句";    //返回SQL语句
}
```

**Mapper接口方法上的注解**

```java
@SelectProvider(type=提供者类, method="方法名称")
```

示例：

1. 创建一个提供者类，然后编写一个提供者方法，返回查询语句

```java
package com.zyf.mybatis.provider;

/**
 * @author:zhouyangfan
 * @date:2023/3/30
 * @Description:提供者类
 **/
public class SqlProvider {
    public static String selectById(){
        return "select id, user_id, title, summary, read_count, create_time, update_time" +
               " from blog.article where id = #{id}";
    }
}
```

2. 创建Dao接口

```java
package com.zyf.mybatis.mapper;

import com.zyf.mybatis.model.ArticlePO;
import com.zyf.mybatis.provider.SqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;

public interface ArticleProviderDao {

    @SelectProvider(type = SqlProvider.class, method = "selectById")
    @ResultMap(value = "articleRM")
    ArticlePO selectById(@Param("id") Integer id);
}

```

3. ResultMap映射

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zyf.mybatis.mapper.ArticleProviderDao">
    <resultMap id="articleRM" type="com.zyf.mybatis.model.ArticlePO">
        <id column="id" property="id"/>
        <result column="user_id" property="userId"/>
        <result column="read_count" property="readCount"/>
        <result column="create_time" property="createTime"/>
        <result column="update_time" property="updateTime"/>
    </resultMap>
</mapper>
```

4. 测试类

```java
package com.zyf.mybatis.mapper;

import com.zyf.mybatis.model.ArticlePO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleProviderDaoTest {
    @Autowired
    private ArticleProviderDao articleProviderDao;

    @Test
    void testSQLProvider() {
        ArticlePO articlePO = articleProviderDao.selectById(1);
        System.out.println("articlePO = " + articlePO);
    }
}
```

5. 执行结果

```
articlePO = ArticlePO(id=1, userId=2101, title=Java编程实战, summary=核心注解的主要作用, readCount=8976, createTime=2023-01-16T12:11:12, updateTime=2023-01-16T12:11:19)
```

### 4.3.4 一对一查询

- @One 注解用于实现一对一的关联查询。当我们需要在一个实体中关联另一个实体时，可以使用 @One 注解来完成。
- 我们使用注解表示article和article_details的一对一关系。

文章细节类ArticleDetail

```java
package com.zyf.bootmybatis01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:zhouyangfan
 * @date:2023/3/31
 * @Description:文章细节类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetail {
    private Integer id;
    private Integer articleId;
    private String content;
}
```

文章类Article，其中包含一对一关系`ArticleDetail articleDetail`

```java
package com.zyf.bootmybatis01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author:zhouyangfan
 * @date:2023/3/31
 * @Description:ArticlePO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer id;
    private Integer userId;
    private String title;
    private String summary;
    private Integer readCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //一对一的关系
    private ArticleDetail articleDetail;
}
```

Mapper一对一查询

```java
package com.zyf.bootmybatis01.mapper;


import com.zyf.bootmybatis01.model.Article;
import com.zyf.bootmybatis01.model.ArticleDetail;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

public interface ArticleOneToOneMapper {
    //一对一查询
    //查询文章的详情
    @Select("""
            select id, article_id, content from article_detail
            where article_id = #{articleId}
            """)
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "article_id", property = "articleId"),
            @Result(column = "content", property = "content")
    })
    ArticleDetail selectDetail(Integer articleId);

    //查询文章属性包含详情
    @Select("""
            select id, user_id, title, summary, read_count, create_time, update_time
            from blog.article where id = #{id}
            """)
    @Results(value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "title", property = "title"),
            @Result(column = "summary", property = "summary"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "id", property = "articleDetail",
            one = @One(select = "com.zyf.bootmybatis01.mapper.ArticleOneToOneMapper.selectDetail",
                            fetchType = FetchType.LAZY))
    })
    Article selectAllArticle(Integer id);

}
```

测试类

```java
import com.zyf.bootmybatis01.model.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleOneToOneMapperTest {
    @Autowired
    private ArticleOneToOneMapper articleOneToOneMapper;

    @Test
    void testOneToOne() {
        Article article = articleOneToOneMapper.selectAllArticle(2);
        System.out.println("article = " + article);
    }
}
```

输出

```java
article = Article(id=2, userId=356752, title=JVM调优, summary=HotSpot虚拟机详解, readCount=26, createTime=2023-01-16T12:15:27, updateTime=2023-01-16T12:15:30, articleDetail=ArticleDetail(id=124, articleId=2, content=使用jvm工具进行调优))
```

### 4.4.4 一对多查询

- 使用@Many注解来完成一对多的查询

1. 创建comment评论表和评论实体类Comment

```mysql
CREATE TABLE `comment`(
	`id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
	`article_id` INT NOT NULL COMMENT '文章编号',
	`content` VARCHAR(100) NOT NULL COMMENT '评论内容',
	PRIMARY KEY (`id`)
)ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT charset=utf8mb4;
```

```JAVA
package com.zyf.bootmybatis01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:zhouyangfan
 * @date:2023/3/31
 * @Description:  评论类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    private Integer articleId;
    private String content;
}
```

ArticlePO类，其中包含了一对多的关系`List<Comment> comments`

```java
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author:zhouyangfan
 * @date:2023/3/31
 * @Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePO {
    private Integer id;
    private Integer userId;
    private String title;
    private String summary;
    private Integer readCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //多个评论
    private List<Comment> comments;
}
```

Mapper类（一对多查询）

```java
package com.zyf.bootmybatis01.mapper;

import com.zyf.bootmybatis01.model.ArticlePO;
import com.zyf.bootmybatis01.model.Comment;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ArticleCommentMapper {
    //查询评论
    @Select("""
            select id, article_id, content from blog.comment 
            where article_id = #{articleId}
            """)
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "article_id", property = "articleId"),
            @Result(column = "content", property = "content")
    })
    List<Comment> selectComments(Integer articleId);


    @Select("""
            select id, user_id, title, summary, read_count, create_time, update_time
            from blog.article where id = #{id}
            """)
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "title", property = "title"),
            @Result(column = "summary", property = "summary"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "id", property = "comments",
                    many = @Many(select = "com.zyf.bootmybatis01.mapper.ArticleCommentMapper.selectComments",
                    fetchType = FetchType.LAZY))
    })
    ArticlePO selectArticle(Integer id);

}
```

测试类

```java
package com.zyf.bootmybatis01.mapper;

import com.zyf.bootmybatis01.model.ArticlePO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleCommentMapperTest {
    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Test
    void testOneToMany() {
        ArticlePO articlePO = articleCommentMapper.selectArticle(1);
        System.out.println("articlePO = " + articlePO);
    }
}
```

输出

```java
articlePO = ArticlePO(id=1, userId=2101, title=Java编程实战, summary=核心注解的主要作用, readCount=8976, createTime=2023-01-16T12:11:12, updateTime=2023-01-16T12:11:19, comments=[Comment(id=1, articleId=1, content=写得好), Comment(id=2, articleId=1, content=写的妙), Comment(id=3, articleId=1, content=质量好)])
```

### 4.4.5 MyBatis相关配置

```yml
mybatis:
  configuration:
    #驼峰命名
    map-underscore-to-camel-case: true
    #日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    #延迟加载
    lazy-loading-enabled: true
    #启用缓存
    cache-enabled: true
  #主配置文件
  config-location: classpath:/sql-config.xml
```



## 4.4 连接池

```yml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/blog
    username: root
    password: zyf2563085
    hikari:
      #是否自动提交事务
      auto-commit: true
      maximum-pool-size: 10
      #不建议设置最小连接
      minimum-idle: 10
      connection-test-query: select 1
      #连接超时时间，如果在连接没有可用的情况下超过此时间将会抛出异常
      connection-timeout: 20000
      #MySQL数据库相关的配置
      data-source-properties:
        cachePrepStmts: true
        dataSource.cachePrepStmts: true
        #连接池大小默认，官方推荐250-500
        dataSource.prepStmtCacheSize: 250
        #单条语句最大长度默认256，官方推荐2048
        dataSource.prepStmtCacheSqlLimit: 2048
        #新版本MySQL支持服务器端准备
        #开启后性能显著提升
        dataSource.useServerPrepStmts: true

```

## 4.5 声明式事务

声明式事务的方式：

- XML配置文件：全局配置
- @Transaction注解驱动：和代码一起提供，比较直观。和代码的耦合度比较高。【Spring团队建议只使用@Transaction注解具体类（以及具体类的方法），而不是注释接口。当然，可以将@Transaction注解放在接口（或接口方法上），但这只有在使用基于接口的代理时才能正常工作】

方法的可见性：

公共方法应用@Transaction注解。如果使用@Transaction注释了受保护的、私有的或包可见的方法，则不会引发错误，但注释的方法不会显示配置的事务设置，事务不生效。如果需要受保护的、私有的方法具有事务考虑使用AspectJ。而不是基于代理的机制。

### 4.5.1 事务注解

mapper类

```java
package com.zyf.boottran.mapper;

import com.zyf.boottran.model.Article;
import com.zyf.boottran.model.ArticleDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface ArticleMapper {

    //添加新的文章属性
    @Insert("""
            INSERT INTO `article` (user_id, title, summary, read_count, create_time, update_time)
            VALUES(#{userId}, #{title}, #{summary}, #{readCount}, #{createTime}, #{updateTime})
            """)
    //可选的配置
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insertArticle(Article article);


    //添加文章内容
    @Insert("""
            INSERT INTO `article_detail` (article_id, content)
            VALUES(#{articleId}, #{content})
            """)
    int insertDetails(ArticleDetail articleDetail);

}
```

service接口和实现类

```java
package com.zyf.boottran.service;

import com.zyf.boottran.model.Article;

public interface ArticleService {

    boolean postNewArticle(Article article, String content);
}
```

```java

import com.zyf.boottran.mapper.ArticleMapper;
import com.zyf.boottran.model.Article;
import com.zyf.boottran.model.ArticleDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:zhouyangfan
 * @date:2023/4/3
 * @Description:
 **/
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public boolean postNewArticle(Article article, String content) {
        int rows = articleMapper.insertArticle(article);
        //添加文章内容
        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setArticleId(article.getId());
        articleDetail.setContent(content);
        int detailsRows = articleMapper.insertDetails(articleDetail);
        return rows >= 1 && detailsRows >= 1;
    }
}
```

postNewArticle方法没有配置事务

测试类，content设为null，让添加article_detail报错。

```
package com.zyf.boottran.service;

import com.zyf.boottran.model.Article;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ArticleServiceImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImplTest.class);
    @Autowired
    private ArticleServiceImpl articleService;

    @Test
    void test01() {
        Article article = new Article();
        article.setUserId(344);
        article.setTitle("Linux操作系统");
        article.setSummary("linux系统基本操作");
        article.setReadCount(1456);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
//        String content = "本书介绍了Linux操作系统的基本命令";
        String content = null;
        String res = articleService.postNewArticle(article, content) ? "成功" : "失败";
        LOGGER.info("添加书籍{}", res);
    }
}
```

输出报错信息，而article成功插入，可见该业务没有被Spring事务所管理。

```
Caused by: java.sql.SQLIntegrityConstraintViolationException: Column 'content' cannot be null
```

当我们在postNewArticle方法配置事务，再此运行测试。可以发现事务发生了回滚，第一个sql插入操作也没有被提交。

### 4.5.2 无效的事务

- Spring事务处理是AOP的环绕通知，只有通过代理对象调用具有事务的方法才能生效。类中有A方法，调用带有事务的B方法。调用A方法事务无效。
- protected，private方法默认是没有事务功能的。
- 具有事务的方法中子线程的事务无效。

```java
package com.zyf.boottran.service;

import com.zyf.boottran.mapper.ArticleMapper;
import com.zyf.boottran.model.Article;
import com.zyf.boottran.model.ArticleDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author:zhouyangfan
 * @date:2023/4/3
 * @Description:
 **/
@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    private ArticleMapper articleMapper;

    @Transactional//事务控制注解
    @Override
    public boolean postNewArticle(Article article, String content) {
        int rows = articleMapper.insertArticle(article);
        //添加文章内容
        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setArticleId(article.getId());
        articleDetail.setContent(content);
        int detailsRows = articleMapper.insertDetails(articleDetail);
        return rows >= 1 && detailsRows >= 1;
    }

    @Transactional
    @Override
    public boolean threadPostNewArticle(Article article, String content) {
        LOGGER.info("【{}】父线程开启",Thread.currentThread().getName());
        new Thread(()->{
            LOGGER.info("【{}】子线程开启",Thread.currentThread().getName());
            int rows = articleMapper.insertArticle(article);
            //添加文章内容
            ArticleDetail articleDetail = new ArticleDetail();
            articleDetail.setArticleId(article.getId());
            articleDetail.setContent(content);
            int detailsRows = articleMapper.insertDetails(articleDetail);
            LOGGER.info("【{}】子线程结束",Thread.currentThread().getName());
        },"子线程").start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
```

测试

```java
@Test
void test01() {
    Article article = new Article();
    article.setUserId(344);
    article.setTitle("Linux操作系统");
    article.setSummary("linux系统基本操作");
    article.setReadCount(1456);
    article.setCreateTime(LocalDateTime.now());
    article.setUpdateTime(LocalDateTime.now());
    String content = null;
    boolean b = articleService.threadPostNewArticle(article, content);
}
```

输出信息如下，可以看待子线程并没有被Spring事务所管理，数据库中数据显示并没有进行回滚。

```
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@105da21f] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@2108428857 wrapping com.mysql.cj.jdbc.ConnectionImpl@6b864efc] will not be managed by Spring
```

# 5. Web

- SpringBoot可以使用嵌入式Tomcat、Jetty、Undertow或者Netty创建一个自包含的HTTP服务器。一个SpringBoot的Web应用能够自己独立运行，不依赖需要安装的Tomcat，Jetty等。

SpringBoot可以创建两种类型的Web应用：

- 基于Servlet体系的Spring Web MVC应用
- 使用spring-boot-starter-webflux模块来构建响应式，非阻塞的Web应用程序。

**Spring WebFlux是一个单独体系内容。当前学习的是Spring Web MVC应用开发。**

![image-20230408155653472](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408155653472.png)

## 5.1 高效构建Web应用

### 5.1.1 构建Web应用

1. 创建一个SpringBoot项目，依赖选择spring-web（包含了Spring MVC，Restful，Tomcat），视图技术Thymeleaf，Lombok插件。包名com.zyf.quickweb。

![image-20230408160745177](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408160745177.png)

![image-20230408160823609](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408160823609.png)

2. 创建controller，并创建Contronller

```java
package com.zyf.quickweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * @author:zhouyangfan
 * @date:2023/4/8
 * @Description:控制器
 **/
@Controller
public class QuickController {
    
    @RequestMapping("/exam/quick")
    public String quick(Model model){
        model.addAttribute("title","Web开发");
        model.addAttribute("time", LocalDateTime.now());
        return "quick";
    }
}
```

3. 创建视图，在templates包中创建quick.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div>
        <h3>视图技术</h3>
        <div th:text="${title}"></div>
        <div th:text="${time}"></div>
    </div>

</body>
</html>
```

4. 启动服务器，发送请求，结果：

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408162138424.png" alt="image-20230408162138424" style="zoom:50%;" />

编写SpringMVC的应用分为三步：

1. 编写请求页面（在浏览器直接模拟请求）
2. 编写Controller
3. 编写视图页面

返回json给前端：

```java
package com.zyf.quickweb.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author:zhouyangfan
 * @date:2023/4/8
 * @Description:json
 **/
@Controller
public class JSONViewController {
    @RequestMapping("exam/json")
    public void jsonView(HttpServletResponse response) throws IOException {
        String json = "{\"name\": \"tom\", \"age\":24}";
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
        writer.close();
    }
}
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408165412320.png" alt="image-20230408165412320" style="zoom:50%;" />



**SpringMVC框架支持控制方法返回对象，由框架将对象使用jackson转为json，并输出**

1. 首先定义model

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private Integer age;
}
```

2. 控制器中编写方法，使用@ResponseBody注解

```java
package com.zyf.quickweb.controller;

import com.zyf.quickweb.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author:zhouyangfan
 * @date:2023/4/8
 * @Description:控制类
 **/
@Controller
public class JacksonViewController {
    
    @ResponseBody
    @RequestMapping("/exam/jackson")
    public User getUserJson(){
        User user = new User();
        user.setName("张三");
        user.setAge(24);
        return user;
    }
}
```

3. 进行测试，可以看到回显了json。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408165316420.png" alt="image-20230408165316420" style="zoom:50%;" />

- `@ResponseBody`注解是Spring框架中的一个注解，它用于指示方法的返回值应该被序列化为HTTP响应体并发送回客户端。在Spring MVC控制器中使用此注解来处理HTTP请求并将响应发送回客户端。当一个带有`@ResponseBody`注解的方法被调用时，Spring会自动将方法的返回值序列化为一个可以发送回客户端的格式，例如JSON。
- User对象会被Jackson工具库的ObjectMapper对象转为json字符串最后由HttpServletResponse输出。

定制控制器的以及方法总结：

1. URI请求对应控制器的方法的映射RequestMapping(value=“请求的uri地址”)
2. 请求的方法对应RequestMapping(method=“xxx”)
3. 客户端请求参数传递到控制器方法的形参
4. 控制方法的返回为响应的数据

### 5.1.2 @RequestMapping注解

RequestMapping：请求映射

```java
public @interface RequestMapping {
    String name() default "";

    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};

    RequestMethod[] method() default {};

    String[] params() default {};

    String[] headers() default {};

    String[] consumes() default {};

    String[] produces() default {};
}
```

@RequestMapping是Spring框架中一个非常重要的注解，**用于将HTTP请求映射到具体的Controller方法上**。

在Spring MVC中，我们可以通过@RequestMapping注解来定义一个Controller的方法，指定这个方法可以处理哪些URL请求。RequestMapping注解有很多属性可以用来指定请求映射的细节，比如请求的URL路径、HTTP请求方法、请求头、请求参数等等。

下面是一些常用的@RequestMapping注解的属性：

- value或path属性：指定请求的URL路径。例如：@RequestMapping("/user")，表示处理以"/user"开头的请求。
- method属性：指定HTTP请求方法。例如：@RequestMapping(value = "/user", method = RequestMethod.GET)，表示处理以GET方式请求"/user"的请求。
- params属性：指定请求参数。例如：@RequestMapping(value = "/user", params = "id=1")，表示处理请求参数id=1的请求。
- headers属性：指定请求头。例如：@RequestMapping(value = "/user", headers = "Content-Type=text/plain")，表示处理请求头Content-Type为text/plain的请求。

除了@RequestMapping注解之外，Spring还提供了其他的注解来处理特定类型的请求，如@GetMapping、@PostMapping、@PutMapping、@DeleteMapping等，它们都是@RequestMapping注解的变体，更加简洁易读：

1. @GetMapping：表示get请求方式的@RequestMapping
2. @PostMapping：表示post请求方式的@RequestMapping
3. @PutMapping：表示put请求方式的@RequestMapping
4. @DeleteMapping：表示delete请求方式的@ResquestMapping

### 5.1.3 @GetMapping和@PostMapping

```java
@RequestMapping(
    method = {RequestMethod.GET}
)
public @interface GetMapping {
	.....
}
```

`method = {RequestMethod.GET}`用来处理HTTP GET请求。

```JAVA
@RequestMapping(
    method = {RequestMethod.POST}
)
public @interface PostMapping {
		.....
}
```

`method = {RequestMethod.POST}`，用来处理HTTP POST请求

### 5.1.4 给项目加上favicon图标

favicon.ico是网站的缩略标志，可以显示在浏览器标签、地址左边和收藏夹，是展示网站个性的logo标志。

图片生成器网站：https://quanxin.org/favicon

## 5.2 SpringMVC

### 5.2.1 控制器Controller

- 控制器负责处理请求参数，处理业务逻辑（调用service层），返回响应结果。

- 控制器是一种普通Bean，使用@Controller或者@RestController注释。
- @Controller被声明为@Component，所以就是一个@Bean，@Controller源码如下：

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {
    @AliasFor(
        annotation = Component.class
    )
    String value() default "";
}
```

### 5.2.2 @Controller与@RestController区别

- `@Controller` 是一个传统的注解，它用于定义处理HTTP请求的类，其方法可以返回视图、模型和其他数据类型。通常在使用`@Controller`注解时，需要使用`@ResponseBody`注解才能返回JSON数据。
- `@RestController` 是Spring4.0之后引入的新注解，它组合了`@Controller`和`@ResponseBody`注解的功能。使用`@RestController`注解的类中的所有方法都将默认返回JSON格式的数据。因此，如果需要在Spring中构建RESTful Web服务，则推荐使用`@RestController`注解，因为它可以更方便地处理JSON数据。

@RestController源码：

```JAVA
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestController {
    @AliasFor(
        annotation = Controller.class
    )
    String value() default "";
}
```

### 5.2.3 匹配请求路径到控制器方法

SpringMVC支持多种策略。匹配请求的路径到控制器方法。

- AntPathMatcher
- PathPatternParser（推荐）

#### 5.2.3.1 通配符

- `？`：一个字符
- `*`：0个或多个字符
- `**`：表示多个路径或者字符

测试通配符`？`

```java
package com.zyf.quickweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:zhouyangfan
 * @date:2023/4/8
 * @Description:
 **/
@RestController
public class ExamPathController {

    @GetMapping("/exam/test?1")
    public String path1(HttpServletRequest request) {
        return "path请求:" + request.getRequestURI();
    }
}
```

![image-20230408201122813](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408201122813.png)

通配符测试`*`

```java
package com.zyf.quickweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:zhouyangfan
 * @date:2023/4/8
 * @Description:测试*
 **/
@RestController
public class ExamPathController {

    @GetMapping("/exam/test*")
    public String path1(HttpServletRequest request, String name, Integer age) {
        return "path请求:" + request.getRequestURI()
               + " name:" + name + " age:" + age;
    }
}
```

![image-20230408202159215](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408202159215.png)

测试通配符`**`：凡事以/test开头的路径都是正确的

```java
package com.zyf.quickweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:zhouyangfan
 * @date:2023/4/8
 * @Description:测试**
 **/
@RestController
public class ExamPathController {

    @GetMapping("/test/**")
    public String path1(HttpServletRequest request, String name, Integer age) {
        return "path请求:" + request.getRequestURI()
               + " name:" + name + " age:" + age;
    }
}
```

![image-20230408203228044](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408203228044.png)

#### 5.2.3.2 RESTFul的支持路径变量

- {变量名}
- {myname:[a-z]+}
- {*myname}

```java
@GetMapping("/order/{*id}")
public String path1(HttpServletRequest request, @PathVariable("id") String id) {
    System.out.println(id);
    return "path请求:" + request.getRequestURI()
           + " id:" + id;
}
```

![image-20230408204907437](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408204907437.png)

![image-20230408204926713](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408204926713.png)

```java
@GetMapping("/order/{filename:\\w+}.html")
public String path1(HttpServletRequest request, @PathVariable("filename") String filename) {
    System.out.println(filename);
    return "path请求:" + request.getRequestURI()
           + " filename:" + filename;
}
```

![image-20230408205442223](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408205442223.png)

### 5.2.4 控制方法参数类型

![1f5c40fb8ed7b8aa73d9016f715cf3c](C:\Users\ZHOUYA~1\AppData\Local\Temp\WeChat Files\1f5c40fb8ed7b8aa73d9016f715cf3c.jpg)

常用的参数列表：

![image-20230408211002616](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408211002616.png)

控制器方法接收请求，是通过控制器方法的形参接收请求参数的，形参列表接收参数的方式：

- 请求参数与形参一一对应，适用简单类型。形参可以有合适的数据类型，比如：String，Integer，int等。
- 对象类型，控制器方法形参是对象，请求的多个参数名与属性名相对应。
- @RequestParam注解，将查询参数，form表单数据解析到方法参数，解析multipart文件上传。
- @RequestBody，接受前端传递的json格式参数。
- HttpServletRequest接收参数。
- @ResquestHeader，从请求header中获取某项值。

SpringMVC中的接口HandlerMethodArgumentResolver（处理器方法参数解析器）解析参数需要的值。

#### 5.2.4.1 接受对象类型参数：

1. 创建pojo Order订单类

```java
package com.zyf.quickweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author:zhouyangfan
 * @date:2023/4/9
 * @Description:pojo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private String shopId;
    private String productName;
    private Double amount;
}
```

2. 编写控制器，接受order对象

```java
package com.zyf.quickweb.controller;

import com.zyf.quickweb.pojo.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:zhouyangfan
 * @date:2023/4/9
 * @Description:Order控制器
 **/
@RestController
public class OrderController {

    @GetMapping("/order/test")
    public String OrderTest(Order order) {
        return "接收的order对象:" + order;
    }
}
```

3. 发送请求

![image-20230409141226036](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409141226036.png)

可以看到请求参数中没有amount参数，最终接收的order对象中amount属性为null

![image-20230409141345075](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409141345075.png)

#### 5.2.4.2 @RequestParam注解

required = true时，必须接收到此参数，否则报错

required = false, defaultValue =“xx” ，可以不接收参数，默认为xx

```java
@GetMapping("/order/test01")
public String OrderTest01(@RequestParam(name = "id", required = true)
        String id,
        @RequestParam(name = "shopId", required = true)
        String shopId,
        @RequestParam(name = "productName", required = false, defaultValue = "商品")
        String productName,
        @RequestParam(name = "amount", required = true) Double amount
) {
    Order order = new Order();
    order.setId(id);
    order.setShopId(shopId);
    order.setProductName(productName);
    order.setAmount(amount);
    return "接收到的order对象：" + order;
}
```

![image-20230409143513609](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409143513609.png)

```
MissingServletRequestParameterException: Required request parameter 'id' for method parameter type String is not present
```

![image-20230409143537681](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409143537681.png)

可以看到productName默认值为商品

![image-20230409143637666](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409143637666.png)

#### 5.2.4.3 @RequestHeader

```java
@GetMapping("/order/test01")
public String OrderTest01(@RequestParam(name = "id", required = true)
        String id,
        @RequestParam(name = "shopId", required = true)
        String shopId,
        @RequestParam(name = "productName", required = false, defaultValue = "商品")
        String productName,
        @RequestParam(name = "amount", required = true) Double amount,
        @RequestHeader("accept") String accept
) {
    Order order = new Order();
    order.setId(id);
    order.setShopId(shopId);
    order.setProductName(productName);
    order.setAmount(amount);
    return "接收到的order对象：" + order + "请求头: " + accept;
}
```

![image-20230409144346659](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409144346659.png)

#### 5.2.4.4 @RequestBody

@RequestBody注解接收json格式的参数，将值可以赋给对象

编写控制方法

```java
@PostMapping("/order/test02")
public String OrderTest02(@RequestBody Order order) {
    return "接收到的order对象：" + order;
}
```

测试

![image-20230409145854347](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409145854347.png)

#### 5.2.4.5 IO读取请求参数

Reader或InputStream读取请求体的数据，适合post请求体的各种数据。具有广泛性。

编写控制器方法：

```java
@PostMapping("/reader/test")
public String ReaderTest(Reader reader){
    StringBuilder content = new StringBuilder();
    BufferedReader bufferedReader = new BufferedReader(reader);
    String line = "";
    try {
        while ((line = bufferedReader.readLine()) != null){
            content.append(line);
            content.append("\n");
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }finally {
        try {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(reader != null){
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    return content.toString();
}
```

测试：发送body

![image-20230409151811837](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409151811837.png)

#### 5.2.4.6 接收数组参数

编写控制器，接收数组参数

```java
@GetMapping("/strArr/test")
public String getList(String[] strArr){
    return Arrays.toString(strArr);
}
```

![image-20230409152651574](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409152651574.png)

![image-20230409152723728](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409152723728.png)

### 5.2.5 控制方法返回值

![image-20230409134010034](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409134010034.png)

#### 5.2.5.1 模型Model

许多实际项目中（视图），后台要从控制层直接返回前端所需要的数据，这时Model大家族就派上用场了，Model就是SpringMVC中的M，用来传输数据。

在SpringMVC中，Model和ModelAndView都是用来封装数据的对象，但它们之间有一些区别。

1. Model对象是一个接口，用来封装数据。控制器方法可以通过Model对象添加数据，然后返回视图名称或者视图路径，视图会从Model对象中获取数据并展示在页面中。
2. ModelAndView对象是一个封装了模型数据和视图名称的对象。控制器方法可以通过ModelAndView对象设置模型数据和视图名称，然后将其返回。视图解析器会根据视图名称找到对应的视图，并将模型数据传递给视图进行渲染。
3. 在使用ModelAndView对象时，除了设置模型数据和视图名称之外，还可以设置其他参数，比如设置响应状态码、设置响应头信息等。
4. 使用Model对象时，控制器方法必须返回视图名称或者视图路径，视图解析器会根据这个视图名称找到对应的视图。而使用ModelAndView对象时，控制器方法返回的就是一个ModelAndView对象，其中包含了模型数据和视图名称。
5. 总体来说，ModelAndView相比于Model更加灵活，可以在一个对象中封装模型数据和视图名称，并且可以设置其他参数。但是在使用简单的场景下，使用Model也可以实现数据的传递和展示。

在使用Spring MVC时，控制器处理完业务逻辑之后，需要将处理结果传递给视图层进行展示，而ModelAndView就是用来封装这个处理结果的。它包含一个Model对象和一个View对象，其中Model对象用于存储控制器处理后的数据，而View对象则指定展示这些数据的视图模板。

一般来说，控制器处理业务逻辑后会将数据存入Model对象中，然后将View对象指定为需要展示的模板文件。在Spring MVC中，视图层使用模板引擎来渲染模板文件，并将控制器传递过来的数据填充到模板中，最终生成HTML或其他格式的响应内容。因此，可以说ModelAndView是在配合模板引擎的情况下使用的。

1. 编写控制器

```java
package com.zyf.quickweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author:zhouyangfan
 * @date:2023/4/10
 * @Description:测试Model和ModelAndView
 **/
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","zyf");
        model.addAttribute("age",19);
        return "hello";
    }

    @GetMapping("/hello1")
    public ModelAndView hello(ModelAndView model){
        model.addObject("name","zyf");
        model.addObject("age",19);
        model.setViewName("hello");
        return model;
    }
}
```

2. 编写html接收model中的数据

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <span th:text="${name}"></span><br>
    <span th:text="${age}"></span>
</body>
</html>
```

3. 发送请求

![image-20230410192237590](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410192237590.png)

![image-20230410192251246](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410192251246.png)

#### 5.2.5.2 JSON

json已经在5.1中做过了，一般用在ajax或者前后端分离的项目中。

#### 5.2.5.3 ResponseEntity

ResponseEntity包含了HttpStatus Code和应答数据的结合体。因为有HTTP Code能表达标准的语义：200成功，404没有发现等。

```java
package com.zyf.quickweb.controller;

import com.zyf.quickweb.pojo.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:zhouyangfan
 * @date:2023/4/10
 * @Description:测试ResponseEntity自定义响应状态码
 **/
@RestController
public class TestController {
    @GetMapping("/status")
    public ResponseEntity hello() {
        Order order = new Order();
        order.setId("22");
        order.setShopId("acc211");
        order.setAmount(2222.2);
        order.setProductName("TV");
        ResponseEntity<Order> orderResponseEntity =
                new ResponseEntity<Order>(order, HttpStatus.FORBIDDEN);
        return orderResponseEntity;
    }
}
```

![image-20230410194704586](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410194704586.png)

可以看到状态码为自定义的403

#### 5.2.5.4 Map List

```java
@GetMapping("/list")
public List<Object> getList() {
    List<Object> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add("tom");
    list.add(new User("zyf",22));
    return list;
}
```

![image-20230410201957866](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410201957866.png)

```java
@GetMapping("/map")
public Map<String, Object> getMap() {
    HashMap<String, Object> map = new HashMap<>();
    map.put("name", "tom");
    map.put("age", 19);
    map.put("sex", '男');
    return map;
}
```

![image-20230410202030127](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410202030127.png)

### 5.2.6 Java Bean Validation

#### 5.2.6.1 普通验证

- Java Bean Validation是一个验证框架，它提供了一种标准化的方式来验证JavaBean中的数据。它允许你使用注解在JavaBean的字段或方法上声明验证约束，然后在运行时验证这些约束。

- SpringBoot使用Java Bean Validation验证域模型的属性值是否符合预期，如果验证失败，立即返回错误信息。Java Bean Validation将验证规则从controller、service集中到Bean对象。一个地方控制所有的验证。

![e50963e8b0a71870bbe381fcfbd7296](C:\Users\ZHOUYA~1\AppData\Local\Temp\WeChat Files\e50963e8b0a71870bbe381fcfbd7296.jpg)

![968ccac996e12586e14d43e41e056c3](C:\Users\ZHOUYA~1\AppData\Local\Temp\WeChat Files\968ccac996e12586e14d43e41e056c3.jpg)

- @NotBlank注解来自org.hibernate.validator.constraints包，用于验证字符串是否为空、长度为零或只包含空格。如果字符串不符合这些条件，则该注解会引发一个验证异常。
- @NotNull注解来自javax.validation.constraints包，用于验证参数或字段是否为null。如果参数或字段为null，则该注解会引发一个空指针异常。

范例：

1. 编写pojo

```java
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Integer id;

    @NotNull(message = "文章必须有标题")
    @Size(min=1, max = 25 , message = "标题必须1个字以上，20字以下")
    private String title;

    @NotBlank(message = "文章必须有副标题")
    @Size(min=8, max = 60 , message = "父标题必须30个字以上")
    private String summary;

    @DecimalMin(value = "0", message = "已读最小值为0")
    private Integer readCount;

    @Email(message = "邮箱各式不正确")
    private String email;
}
```

2. 编写控制层

```java
package com.zyf.quickweb.controller;

import com.zyf.quickweb.pojo.Article;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:zhouyangfan
 * @date:2023/4/10
 * @Description:Article控制层
 **/
@RestController
public class ArticleController {

    @PostMapping("/article/add")
    public Map<String, Object> addArticle(@Validated @RequestBody Article article, BindingResult bindingResult) {

        //service方法处理文章的业务
        HashMap<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < fieldErrors.size(); i++) {
                FieldError fieldError = fieldErrors.get(i);
                map.put(i + "-" + fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return map;
    }

}
```

3. 接口测试

![image-20230410160540744](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410160540744.png)

#### 5.2.6.2 分组验证

对于添加Article的操作，不需要id值，系统生成会生成主键id，但修改Article的操作，必要要接收传入的id值，因此单纯在Article中限定id字段是不正确的操作，这里引出分组验证的技术。

1. 编写Article类

```java
package com.zyf.quickweb.pojo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:zhouyangfan
 * @date:2023/4/10
 * @Description:文章Bean
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    public static interface AddArticleGroup {
    }


    public static interface EditArticleGroup {
    }


    @NotNull(message = "修改操作，文章id必须有值", groups = {EditArticleGroup.class})
    @Min(value = 1, message = "id必须大于0", groups = {AddArticleGroup.class})
    private Integer id;

    @NotNull(message = "文章必须有标题", groups = {EditArticleGroup.class, AddArticleGroup.class})
    @Size(min = 1, max = 25, message = "标题必须1个字以上，20字以下")
    private String title;

    @NotBlank(message = "文章必须有副标题", groups = {EditArticleGroup.class, AddArticleGroup.class})
    @Size(min = 8, max = 60, message = "父标题必须30个字以上")
    private String summary;

    @DecimalMin(value = "0", message = "已读最小值为0", groups = {EditArticleGroup.class, AddArticleGroup.class})
    private Integer readCount;

    @Email(message = "邮箱各式不正确", groups = {EditArticleGroup.class, AddArticleGroup.class})
    private String email;
}
```

2. 编写控制器

```java
package com.zyf.quickweb.controller;

import com.zyf.quickweb.pojo.Article;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:zhouyangfan
 * @date:2023/4/10
 * @Description:Article控制层
 **/
@RestController
public class ArticleController {

    @PostMapping("/article/add")
    public Map<String, Object> addArticle(@Validated(Article.AddArticleGroup.class) @RequestBody Article article, BindingResult bindingResult) {

        //service方法处理文章的业务
        HashMap<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < fieldErrors.size(); i++) {
                FieldError fieldError = fieldErrors.get(i);
                map.put(i + "-" + fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return map;
    }

    @PostMapping("/article/edit")
    public Map<String, Object> editArticle(@Validated(Article.EditArticleGroup.class) @RequestBody Article article, BindingResult bindingResult) {

        //service方法处理文章的业务
        HashMap<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < fieldErrors.size(); i++) {
                FieldError fieldError = fieldErrors.get(i);
                map.put(i + "-" + fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return map;
    }

}
```

3. 测试：

![image-20230410162747499](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410162747499.png)

![image-20230410162642486](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410162642486.png)

#### 5.2.6.3 验证自动配置类

ValidationAutoConfiguration自动配置类，创建了LocalValidatorFactoryBean工厂Bean，当有class路径中有hibernate.validator。能够创建hiberate的约束进行验证。

## 5.3 SpringMVC请求流程

SpringMVC处理用户的请求与访问一个Servlet是类似的，请求发送类Servlet，执行doService方法，最后响应结果给浏览器完成一次请求处理。

### 5.3.1 DispatcherServlet

![image-20230413200631761](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413200631761.png)

DispatcherServlet负责接收所有对Controller的请求，调用开发者编写的controller进行业务逻辑处理，再将controller方法的返回值经过视图处理响应给浏览器。

DispatcherServlet职责：

1. 是一个门面，接受请求，控制请求的处理过程。
2. 访问其他控制器，这些控制器处理业务逻辑。
3. 创建合适的视图，将2中得到的业务结果放到视图，响应给用户。
4. 解耦其他组件，所有组件只与DispatcherServlet交互。彼此之间没有关联。
5. 实现ApplicationContextAware，每个DispatcherServlet都拥有自己的WebApplicationContext，它继承了ApplicationContext。WebApplicationContext包含了Web相关的Bean对象，比如开发人员注释的@Controller的类，视图解析器，视图对象等等。DispatcherServlet访问容器中Bean对象。
6. Servlet+Spring IoC组合。

### 5.3.2 SpringMVC的请求流程

1. Tomcat接收到一个请求后，会交给DispatcherServlet进行处理。
2. DispatcherServlet会根据请求的path，调用HandlerMapping处理器映射器，找到对应的Handler以及拦截器，一并返回给DispatcherServlet。
3. DispatcherServlet调用HandlerAdapter处理器适配器。
4. 处理器适配器经过适配调用具体的处理器，来执行Handler方法。
5. 在执行方法前会解析方法的参数，比如解析@RequestParam、@RequestHeader、@PathVarible等注解。
6. 解析这些注解就是从请求中获取对应的数据，比如请求头，请求的paramters，然后把数据传给对应的参数。
7. 有了参数之后就开始执行方法。
8. 执行方法后就会得到方法的返回值，SpringMVC会对方法的返回值进行解析。
9. 如果方法中加上@ResponseBody，那么就把返回值直接返回给浏览器。
10. 在这个过程中可能需要把一个对象转成json字符串才能返回给浏览器。
11. 如果方法没有加上@ResponseBody注解，就会进行视图解析，把数据模型填充到视图里面，把解析之后的html数据返回给浏览器。

## 5.4 SpringMVC自动配置

### 5.4.1 SpringMVC自动配置

web MVC的自动配置类：WebMvcAutoConfiguration类是Spring Boot中的一个自动配置类，主要用于配置Spring MVC的相关配置，包括视图解析器、静态资源处理、消息转换器等。

```java
@AutoConfiguration(after = { DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
      ValidationAutoConfiguration.class })
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@ImportRuntimeHints(WebResourcesRuntimeHints.class)
public class WebMvcAutoConfiguration {
}
```

### 5.4.2 DispatcherServlet自动配置

**DispatcherServlet自动配置类：DispatcherServletAutoConfiguration**

```java
public class DispatcherServletAutoConfiguration {

	/**
	 * The bean name for a DispatcherServlet that will be mapped to the root URL "/".
	 */
	public static final String DEFAULT_DISPATCHER_SERVLET_BEAN_NAME = "dispatcherServlet";

	/**
	 * The bean name for a ServletRegistrationBean for the DispatcherServlet "/".
	 */
	public static final String DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME = "dispatcherServletRegistration";

	@Configuration(proxyBeanMethods = false)
	@Conditional(DefaultDispatcherServletCondition.class)
	@ConditionalOnClass(ServletRegistration.class)
	@EnableConfigurationProperties(WebMvcProperties.class)
	protected static class DispatcherServletConfiguration {

		@Bean(name = DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
		public DispatcherServlet dispatcherServlet(WebMvcProperties webMvcProperties) {
			DispatcherServlet dispatcherServlet = new DispatcherServlet();
			dispatcherServlet.setDispatchOptionsRequest(webMvcProperties.isDispatchOptionsRequest());
			dispatcherServlet.setDispatchTraceRequest(webMvcProperties.isDispatchTraceRequest());
			dispatcherServlet.setThrowExceptionIfNoHandlerFound(webMvcProperties.isThrowExceptionIfNoHandlerFound());
			dispatcherServlet.setPublishEvents(webMvcProperties.isPublishRequestHandledEvents());
			dispatcherServlet.setEnableLoggingRequestDetails(webMvcProperties.isLogRequestDetails());
			return dispatcherServlet;
		}

		@Bean
		@ConditionalOnBean(MultipartResolver.class)
		@ConditionalOnMissingBean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
		public MultipartResolver multipartResolver(MultipartResolver resolver) {
			// Detect if the user has created a MultipartResolver but named it incorrectly
			return resolver;
		}

	}
}
```

DispatcherServletRegistrationBean

```JAVA
@Bean(name = DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME)
@ConditionalOnBean(value = DispatcherServlet.class, name = DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
public DispatcherServletRegistrationBean dispatcherServletRegistration(DispatcherServlet dispatcherServlet,
    WebMvcProperties webMvcProperties, ObjectProvider<MultipartConfigElement> multipartConfig) {
  DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(dispatcherServlet,
      webMvcProperties.getServlet().getPath());
  registration.setName(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
  //配置Servlet启动顺序，对于SpringMVC中的DispatcherServlet来说，通常会将其设置为1，以确保在应用程序启动时就完成SpringMVC的初始化工作。
  registration.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
  multipartConfig.ifAvailable(registration::setMultipartConfig);
  return registration;
}
```

WebMvcProperties类：可以看到前缀为`spring.mvc`

```JAVA
@ConfigurationProperties(prefix = "spring.mvc")
public class WebMvcProperties {
}
```

中央调度器的配置

```properties
#中央控制器的访问路径
spring.mvc.servlet.path=/zyf
#加载顺序，值越小越先加载
spring.mvc.servlet.load-on-startup=0
#配置解析日期格式，使用配置的格式，日期参数才能正确地被springmvc处理
spring.mvc.format.date-time=yyyy-MM-dd HH:mm:ss
```



映射器、适配器等在WebMvcConfigurationSupport类中配置。WebMvcAutoConfiguration中内部类EnableWebMvcConfiguration是WebMvcConfigurationSupport的子类。

```java
@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(WebProperties.class)
	public static class EnableWebMvcConfiguration extends DelegatingWebMvcConfiguration implements ResourceLoaderAware {
    .....
	}
```

### 5.4.3 Web服务器自动配置

**ServletWebServerFactoryAutoConfiguration类：**

ServletWebServerFactoryAutoConfiguration类的主要作用是为应用程序提供一个ServletWebServerFactory bean实例，它可以创建并配置Servlet容器。该类使用Spring Boot的自动配置机制，根据应用程序中的配置和类路径中的库自动选择合适的Servlet容器。

导入Tomcat、Jetty和Undertow内嵌服务器

```
@Import({ ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistrar.class,
		ServletWebServerFactoryConfiguration.EmbeddedTomcat.class,
		ServletWebServerFactoryConfiguration.EmbeddedJetty.class,
		ServletWebServerFactoryConfiguration.EmbeddedUndertow.class })
```

ServletWebServerFactoryConfiguration：

```java
class ServletWebServerFactoryConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ Servlet.class, Tomcat.class, UpgradeProtocol.class })
	@ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
	static class EmbeddedTomcat {

		@Bean
		TomcatServletWebServerFactory tomcatServletWebServerFactory(
				ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers,
				ObjectProvider<TomcatContextCustomizer> contextCustomizers,
				ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers) {
			TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
			factory.getTomcatConnectorCustomizers().addAll(connectorCustomizers.orderedStream().toList());
			factory.getTomcatContextCustomizers().addAll(contextCustomizers.orderedStream().toList());
			factory.getTomcatProtocolHandlerCustomizers().addAll(protocolHandlerCustomizers.orderedStream().toList());
			return factory;
		}

	}

	/**
	 * Nested configuration if Jetty is being used.
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ Servlet.class, Server.class, Loader.class, WebAppContext.class })
	@ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
	static class EmbeddedJetty {

		@Bean
		JettyServletWebServerFactory jettyServletWebServerFactory(
				ObjectProvider<JettyServerCustomizer> serverCustomizers) {
			JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
			factory.getServerCustomizers().addAll(serverCustomizers.orderedStream().toList());
			return factory;
		}

	}

	/**
	 * Nested configuration if Undertow is being used.
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ Servlet.class, Undertow.class, SslClientAuthMode.class })
	@ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
	static class EmbeddedUndertow {

		@Bean
		UndertowServletWebServerFactory undertowServletWebServerFactory(
				ObjectProvider<UndertowDeploymentInfoCustomizer> deploymentInfoCustomizers,
				ObjectProvider<UndertowBuilderCustomizer> builderCustomizers) {
			UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
			factory.getDeploymentInfoCustomizers().addAll(deploymentInfoCustomizers.orderedStream().toList());
			factory.getBuilderCustomizers().addAll(builderCustomizers.orderedStream().toList());
			return factory;
		}

		@Bean
		UndertowServletWebServerFactoryCustomizer undertowServletWebServerFactoryCustomizer(
				ServerProperties serverProperties) {
			return new UndertowServletWebServerFactoryCustomizer(serverProperties);
		}

	}

}
```

Web服务器配置信息在ServerProperties类中

```java
@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
public class ServerProperties {
}
```

### 5.4.4 修改服务器配置

修改配置

```properties
server.port=8001
server.servlet.context-path=/api
server.servlet.encoding.charset=UTF-8
server.servlet.encoding.force=false
```

【启动SpringBoot】：打印日志，可以看到端口号与路径已成功修改

```properties
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8001 (http) with context path '/api'
```

![image-20230416143448312](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230416143448312.png)配置Tomcat

```properties
#配置tomcat日志
server.tomcat.accesslog.enabled=true
server.tomcat.accesslog.directory=D:/exam
server.tomcat.accesslog.prefix=my_log
server.tomcat.accesslog.file-date-format=.yyyy-MM-dd
server.tomcat.accesslog.suffix=.log
#tomcat最大连接数
server.tomcat.max-connections=8000
```

日志输出：

![image-20230416144618357](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230416144618357.png)

### 5.4.5 自动配置总结

![SpringMVC可配置项](https://img-blog.csdnimg.cn/ef7a16eed0474834823ef9ff4aff60e0.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RsaGp3MTQxMg==,size_16,color_FFFFFF,t_70#pic_center)

## 5.5 Servlets, Filters, and Listeners

### 5.5.1 Servlet

#### 5.5.1.1 使用注解创建Servlet

创建一个控制类，继承HttpServlet类，加上@WebServlet注解

```java
package com.zyf.servlet.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author:zhouyangfan
 * @date:2023/4/16
 * @Description:HelloServlet
 **/
@WebServlet(urlPatterns = "/helloServlet", name = "HelloServlet")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("这是SpringBoot中的一个Servlet");
        out.flush();
        out.close();
    }
}
```

在启动类上加上扫描器

```java
package com.zyf.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages = "com.zyf.servlet")//扫描器，扫描WebServlet注解
@SpringBootApplication
public class ServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletApplication.class, args);
    }

}
```

发送请求

![image-20230416202036263](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230416202036263.png)

#### 5.5.1.2 使用编程方式创建Servlet

创建一个LoginServlet控制类，继承HttpServlet类

```java
package com.zyf.servlet.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author:zhouyangfan
 * @date:2023/4/16
 * @Description:loginServlet
 **/

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("这是登录Servlet");
        out.flush();
        out.close();
    }
}
```

config包下编写配置类

```java
package com.zyf.servlet.config;

import com.zyf.servlet.control.LoginServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:zhouyangfan
 * @date:2023/4/16
 * @Description:Servlet配置类
 **/
@Configuration
public class ServletConfig {
    @Bean
    public ServletRegistrationBean getServletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new LoginServlet());
        servletRegistrationBean.addUrlMappings("/login");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }
}
```

启动服务器，发送请求

![image-20230416203200859](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230416203200859.png)

### 5.5.2 Filter

#### 5.5.2.1 使用注解创建过滤器

创建一个过滤器

```java
package com.zyf.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * @author:zhouyangfan
 * @date:2023/4/16
 * @Description:过滤器
 **/
@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String s = ((HttpServletRequest) request).getRequestURI().toString();
        System.out.println("过滤器执行了 URI : " + s);
        filterChain.doFilter(request, response);
    }
}
```

发送请求

![image-20230416204538886](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230416204538886.png)

控制台输出

```
过滤器执行了 URI : /helloServlet
```

#### 5.5.2.2 使用编码方式创建过滤器

不使用注解

```java
package com.zyf.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * @author:zhouyangfan
 * @date:2023/4/16
 * @Description:过滤器
 **/
//@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String s = ((HttpServletRequest) request).getRequestURI().toString();
        System.out.println("编码方式过滤器执行了 URI : " + s);
        filterChain.doFilter(request, response);
    }
}
```

编写配置类

```java
@Bean
public FilterRegistrationBean getFilterRegistrationBean(){
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new LogFilter());
    filterRegistrationBean.addUrlPatterns("/*");
    return filterRegistrationBean;
}
```

发送请求，控制台中输出了过滤器执行的结果：

```
编码方式过滤器执行了 URI : /helloServlet
```

#### 5.5.2.3 定义Filter执行顺序

控制Filter执行顺序有两种途径

1. 对比过滤器名称，按字典顺序排序，AuthFilter -> LogFilter （注解）
2. FilterRegistrationBean登记Filter，设置order顺序，数值越小，先执行。（编程方式配置）

使用注解创建两个过滤器

```java
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String s = ((HttpServletRequest) request).getRequestURI().toString();
        System.out.println("AuthFilter过滤器执行了 URI : " + s);
        filterChain.doFilter(request, response);
    }
}

@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String s = ((HttpServletRequest) request).getRequestURI().toString();
        System.out.println("LogFilter过滤器执行了 URI : " + s);
        filterChain.doFilter(request, response);
    }
}
```

发送请求：可以看到按照名字顺序来执行

```
AuthFilter过滤器执行了 URI : /helloServlet
LogFilter过滤器执行了 URI : /helloServlet
```

修改过滤器名字

```JAVA
@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String s = ((HttpServletRequest) request).getRequestURI().toString();
        System.out.println("LogFilter过滤器执行了 URI : " + s);
        filterChain.doFilter(request, response);
    }
}


@WebFilter(urlPatterns = "/*")
public class YuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String s = ((HttpServletRequest) request).getRequestURI().toString();
        System.out.println("YuthFilter过滤器执行了 URI : " + s);
        filterChain.doFilter(request, response);
    }
}
```

发送请求，控制台输出：

```
LogFilter过滤器执行了 URI : /helloServlet
YuthFilter过滤器执行了 URI : /helloServlet
```

使用order来进行排序：

```java
@Bean
public FilterRegistrationBean getFilterRegistrationBean1(){
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new LogFilter());
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.setOrder(1);
    return filterRegistrationBean;
}

@Bean
public FilterRegistrationBean getFilterRegistrationBean2(){
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new AuthFilter());
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.setOrder(2);
    return filterRegistrationBean;
}
```

输出：

```
LogFilter过滤器执行了 URI : /helloServlet
AuthFilter过滤器执行了 URI : /helloServlet
```

#### 5.5.2.4 使用框架中的Filter

SpringBoot中有许多已经定义好的Filter，这些Filter实现了一些功能，如果我们需要使用它们，可以通过FilterRegistrationBean注册Bean对象。

`CommonsRequestLoggingFilter`是Spring Framework中提供的一个过滤器，用于记录HTTP请求的详细信息，例如请求方法、请求URL、请求参数、请求头等。它可以方便地进行请求日志记录和调试。

配置CommonsRequestLoggingFilter

```java
@Bean
public FilterRegistrationBean getFilterRegistrationBean3(){
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    CommonsRequestLoggingFilter commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();
    commonsRequestLoggingFilter.setIncludeQueryString(true);
    filterRegistrationBean.setFilter(commonsRequestLoggingFilter);
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.setOrder(0);
    return filterRegistrationBean;
}
```

修改web日志等级

```properties
logging.level.web=debug
```

发送请求

![image-20230416215054273](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230416215054273.png)

控制台：

```properties
CommonsRequestLoggingFilter      : Before request [GET /helloServlet?name=zyf]
LogFilter过滤器执行了 URI : /helloServlet
AuthFilter过滤器执行了 URI : /helloServlet
CommonsRequestLoggingFilter      : After request [GET /helloServlet?name=zyf]
```

### 5.5.3 Listener

在Servlet中，常用的监听器包括：

1. ServletContextListener：用于在Web应用程序启动时加载Servlet上下文，并将其存储在ServletContext中。这样，所有的Servlet和JSP都可以共享该上下文中的对象。
2. HttpSessionListener：用于在Web应用程序中创建和销毁HttpSession时进行相应的处理。HttpSession是在客户端和服务器之间维护状态的机制，可以在其中存储与用户相关的数据。
3. ServletRequestListener：用于在Web应用程序中创建和销毁ServletRequest时进行相应的处理。ServletRequest是代表客户端请求的对象，可以在其中存储与请求相关的数据。
4. HttpSessionAttributeListener：用于在HttpSession中添加、删除或修改属性时进行相应的处理。这样，可以在属性添加、删除或修改时执行特定的操作，例如记录日志或发送电子邮件通知。

## 5.6 WebMvcConfigurate

WebMvcConfigurer作为配置类，采用JavaBean的形式来代替xml文件配置来进行个性化的定制。WebMvcConfigurer就是JavaConfig形式的SpringMVC的配置文件。

WebMvcConfigurer是一个接口，需要自定义对象，实现接口并覆盖某个方法。

WebMvcConfigurer中的方法

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230417095216101.png" alt="image-20230417095216101" style="zoom:50%;" />

### 5.6.1 页面跳转控制器

SpringBoot中使用页面视图，例如Thymeleaf。要跳转显示某个页面，必须通过Controller对象。也就是我们需要创建一个Controller，转发到一个视图才行。如果我们现在需要显示你页面，可以无需这个控制器。addViewControllers()完成从请求到视图的跳转。

1. 首先编写MvcSettings类实现了WebMvcConfigurer接口，重写addViewControllers方法

```java
package com.zyf.webmvcconfig.mvc.settings;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:SpringMVC的配置类，使用JavaConfig的方式配置SpringMVC，代替原来的XML文件
 **/
@Configuration
public class MvcSettings implements WebMvcConfigurer {
    //从视图跳转控制器，从请求直达视图页面（无需controller）
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //配置页面控制
        //addViewController("请求的url")
        //setViewName("目标视图")
        registry.addViewController("/welcome").setViewName("index");
    }
}
```

2. 视图

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
    <h3>
        欢迎各位小伙伴！
    </h3>
</body>
</html>
```

3. 发送请求

![image-20230417101141937](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230417101141937.png)

4. 重点：

```java
addViewController("请求的url")
setViewName("目标视图") //视图的名称
```

### 5.6.2 数据格式化

- Formatter是一个数据转换接口，将一种数据转换为另外一种数据。与`Formatter<T>`功能类型相似的还有`Converter<S, T>`。这里研究`Formatter<T>`。
- `Formatter<T>`只能将String类型转为其他数据类型。这点在Web应用中适用广泛
- 因为Web请求的所有参数都是String，我们需要把String转为Integer，Long，Date等。

Spring中内置了`Formatter<T>`:

1. DateFormatter：String和Date之间的解析与格式化
2. InetAddressFormatter：String和InetAddress之间的解析与格式化
3. PercentStyleFormatter：String与Number之间的解析与格式化，带货币符合
4. NumberFormat：String和Number之间的解析与格式化

- 我们在使用@DateTimeFormat，@NumberFormat注解时，就是通过Formatter<T>解析String类型的我们期望的Date或Number。
- `Formatter<T>`也是Spring的扩展点，我们除磷特殊格式的请求参数时，能够自定义合适的Formatter<T>，将请求的String数据转换为我们的某个对象，使用这个对象更方便我们的后续编码。

```java
public interface Formatter<T> extends Printer<T>, Parser<T> {

}
```

Parser接口：将String类型的对象转换为T类型的对象。

```java
@FunctionalInterface
public interface Parser<T> {
	T parse(String text, Locale locale) throws ParseException;

}
```



需求将前端请求的“123;12;12;AK;12”类型的数据解析到指定类中。

首先定义一个数据模型DeviceInfo

```java
package com.zyf.webmvcconfig.mvc.model;

import lombok.Data;

/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:DeviceInfo
 **/
@Data
public class DeviceInfo {
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
}
```

首先定义一个转换器，实现数据模型的转换功能，这里要实现`Formatter`接口的两个方法

```java
package com.zyf.webmvcconfig.mvc.formatter;

import com.zyf.webmvcconfig.mvc.model.DeviceInfo;
import org.springframework.format.Formatter;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.util.Locale;
import java.util.StringJoiner;

/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:转换器
 **/
public class DeviceFormatter implements Formatter<DeviceInfo> {

    @Override
    public DeviceInfo parse(String text, Locale locale) throws ParseException {
        DeviceInfo deviceInfo = null;
        if (!StringUtils.isEmpty(text)) {
            String[] items = text.split(";");
            deviceInfo = new DeviceInfo();
            deviceInfo.setItem1(items[0]);
            deviceInfo.setItem2(items[1]);
            deviceInfo.setItem3(items[2]);
            deviceInfo.setItem4(items[3]);
            deviceInfo.setItem5(items[4]);
            return deviceInfo;
        }
        return null;
    }

    @Override
    public String print(DeviceInfo object, Locale locale) {
        StringJoiner stringJoiner = new StringJoiner("#");
        stringJoiner.add(object.getItem1()).add(object.getItem2()).add(object.getItem3())
                .add(object.getItem4()).add(object.getItem5());
        return stringJoiner.toString();
    }
}
```

将转换器配置到SpringMVC中，实现addFormatters方法

```java
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:SpringMVC的配置类，使用JavaConfig的方式配置SpringMVC，代替原来的XML文件
 **/
@Configuration
public class MvcSettings implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DeviceFormatter());
    }
}
```

编写一个控制器来测试数据转换功能

```java
package com.zyf.webmvcconfig.mvc.controller;

import com.zyf.webmvcconfig.mvc.model.DeviceInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:DeviceController控制器类
 **/
@RestController
public class DeviceController {

    @GetMapping("/device")
    public String addDeviceInfo(@RequestParam("device") DeviceInfo info) {
        return "接收的设配信息" + info.toString();
    }
}
```

进行测试：可以看到已经成功将“123;12;12;AK;12”转换为一个对象。

![image-20230417110030353](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230417110030353.png)

### 5.6.3 拦截器

HandlerInterceptor接口和他的实现类称为拦截器，时SpringMVC的一种对象，拦截器时SpringMVC框架的对象，与Servelt与无关。拦截器能够预先处理发给Controller的请求。可以决定是否被Controller处理。用户请求是先由DispatcherServlet接收后，在Controller之前执行的拦截器对象。

- 框架中预定义的拦截器
- 自定义拦截器

根据拦截器的特点，类似权限验证，记录日志，过滤字符，登录token处理都可以使用拦截器。

拦截器定义的步骤：

1. 声明类实现HandlerInterceptor接口，重写三个方法。
2. 登录拦截器。

#### 5.6.3.1 配置一个拦截器

范例：在SpringMVC中配置一个拦截器

1. 编写一个控制类：

```java
package com.zyf.webmvcconfig.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:ArticleController控制类
 **/
@RestController
public class ArticleController {

    @GetMapping("/article/edit")
    public String editArticle() {
        return "修改文章";
    }

    @GetMapping("/article/add")
    public String addArticle() {
        return "添加文章";
    }

    @GetMapping("/article/query")
    public String queryArticle() {
        return "查询文章";
    }
}
```

2. 创建拦截器，实现HandlerInterceptor接口，重写preHandle方法

```java
package com.zyf.webmvcconfig.mvc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:拦截器
 **/
public class AuthInterceptor implements HandlerInterceptor {
    private final String name = "zyf";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        String userName = request.getParameter("name");
        if (userName.equals(name) && (
                requestURI.startsWith("/article/add") ||
                requestURI.startsWith("/article/edit") ||
                requestURI.startsWith("/article/query")
        )) {
            System.out.println("请求被拦截");
        return false;
        }
return true;
    }
}
```

3. 注册拦截器

```java
package com.zyf.webmvcconfig.mvc.settings;

import com.zyf.webmvcconfig.mvc.formatter.DeviceFormatter;
import com.zyf.webmvcconfig.mvc.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:SpringMVC的配置类，使用JavaConfig的方式配置SpringMVC，代替原来的XML文件
 **/
@Configuration
public class MvcSettings implements WebMvcConfigurer {
    //从视图跳转控制器，从请求直达视图页面（无需controller）
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //配置页面控制
        //addViewController("请求的url")
        //setViewName("目标视图")
        registry.addViewController("/welcome").setViewName("index");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DeviceFormatter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new AuthInterceptor());
        interceptorRegistration.addPathPatterns("/article/*").excludePathPatterns("/article/query");
    }
}
```

请求query查询文章，没有被拦截

![image-20230417133233083](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230417133233083.png)

请求add增加文章，被拦截，控制台输出

```
/article/add
请求被拦截
```

#### 5.6.3.2 配置多个拦截器

```java
public void addInterceptors(InterceptorRegistry registry) {
    InterceptorRegistration interceptorRegistration = registry.addInterceptor(new AuthInterceptor());
    interceptorRegistration.order(2).addPathPatterns("/article/*").excludePathPatterns("/article/query");


    interceptorRegistration = registry.addInterceptor(new LoginInterceptor());
    interceptorRegistration.order(1).addPathPatterns("/**");
}
```

多个拦截器的执行顺序用order方法来控制，值越小越先执行。

## 5.7 文件上传

### 5.7.1 MultipartResolver

- SpringBoot3实现了文件上传处理。
- SpringBoot3提供了文件上传的接口MultipartResolver，用于解析文件上传的请求，它的内部实现类StandardServletMultipartResolver。
- StandardServletMultipartResolver内部封装了读取POST请求体中的请求数据，也就是文件内容。现在只需要在Controller方法中加入形参`@RequestParam MultipartFile`。MultipartFile表示上传的文件，提供了方便的方法保存到文件磁盘。

MultipartFile API：

| 方法                  | 作用                                    |
| --------------------- | --------------------------------------- |
| getName()             | 获取参数名称                            |
| getOriginalFilename() | 获取上传原始文件名称                    |
| isEmpty()             | 上传的文件大小                          |
| getInputStream()      | 文件的InputStream，可用于读取文件的内容 |
| transferTo(File dest) | 保存上传文件到目标dest                  |

5.7.1 文件上传范例：

1. 配置文件

```properties
#请求上传文件总的最大
spring.servlet.multipart.max-request-size=50MB
#请求上传文件单个的最大
spring.servlet.multipart.max-file-size=5MB
```

2. 前端页面

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230417153421139.png" alt="image-20230417153421139" style="zoom:50%;" />

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
    <h3>项目首页，上传文件成功！！！！</h3>
</body>
</html>
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>上传文件</title>
</head>
<body>
      <div>
        <h3>上传文件</h3>
        <form action="uploadFile" enctype="multipart/form-data" method="post">
          选择文件:<input type="file" name="upfile"><br><br>
          <input type="submit" value="上传">
        </form>
      </div>
</body>
</html>
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>error</title>
</head>
<body>
  <h3>文件上传错误</h3>
</body>
</html>
```

3. 创建文件上传controller

```java
package com.zyf.uploadfiles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:
 **/
@Controller
public class UploadFileController {

    //上传文件
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("upfile") MultipartFile multipartFile) {
        System.out.println("开始处理上传文件");
        Map<String, Object> info = new HashMap<>();
        try {
            if (!multipartFile.isEmpty()) {
                info.put("上传文件的参数名称", multipartFile.getName());
                info.put("内容类型", multipartFile.getContentType());
                var ext = "unknow";
                var filename = multipartFile.getOriginalFilename();
                if (filename.indexOf(".") > 0) {
                    ext = filename.substring(filename.lastIndexOf(".") + 1);
                }
                var newFileName = UUID.randomUUID().toString() + "." + ext;
                var path = "E://upload//" + newFileName;
                multipartFile.transferTo(new File(path));
                info.put("文件的名称", newFileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Set<String> keys = info.keySet();
            for (String key : keys) {
                Object o = info.get(key);
                System.out.println(key + " : " + o);
            }
        }
        return "redirect:/index.html";
    }
}
```

### 5.7.2 Servlet规范

- 在Servlet3.0规范中，定义了jakarta.servlet.http.Part接口处理multipart/form-data POST请求中接收到的表单数据。有了Part对象，其write()方法将上传文件保存到本地服务器磁盘目录。
- SpringBoot3使用的Servlet规范是基于5的，所以上传文件使用的就是Part接口。StandardServletMultipartResolver对Part接口进行的封装，实现了基于Servlet规范的文件上传。

```java
package com.zyf.uploadfiles.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:
 **/
@Controller
public class PartController {

    @PostMapping("/files")
    public String uploadFilePart(HttpServletRequest request) {
        try {
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                part.write(fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/index.html";
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        System.out.println(contentDisposition);
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            //trim() 方法是 String 类中的一个方法，它可以去掉字符串两端的空格
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }
}
```

路径配置：

```properties
#请求上传文件总的最大
spring.servlet.multipart.max-request-size=50MB
#请求上传文件单个的最大
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.location=E://upload
```

### 5.7.3 多文件上传

1. 修改页面，多个文件上传

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>上传文件</title>
</head>
<body>
<div>
    <h3>上传文件</h3>
    <form action="uploadMultiFile" enctype="multipart/form-data" method="post">
        选择文件-1:<input type="file" name="upfile"><br><br>
        选择文件-2:<input type="file" name="upfile"><br><br>
        选择文件-3:<input type="file" name="upfile"><br><br>
        <input type="submit" value="上传">
    </form>
</div>
</body>
</html>
```

2. 控制器，使用MultipartFile

```java
package com.zyf.uploadfiles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:
 **/
@Controller
public class UploadMultiFileController {

    //上传文件
    @PostMapping("/uploadMultiFile")
    public String uploadFile(@RequestParam("upfile") MultipartFile[] multipartFiles) {
        System.out.println("开始处理上传文件");
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                if (!multipartFile.isEmpty()) {
                    var ext = "unknow";
                    var filename = multipartFile.getOriginalFilename();
                    if (filename.indexOf(".") > 0) {
                        ext = filename.substring(filename.lastIndexOf(".") + 1);
                    }
                    var newFileName = UUID.randomUUID().toString() + "." + ext;
                    var path = "E://upload//" + newFileName;
                    multipartFile.transferTo(new File(path));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/index.html";
    }
}
```

使用Part接口，实现多文件上传。

```java
package com.zyf.uploadfiles.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @author:zhouyangfan
 * @date:2023/4/17
 * @Description:Part接口实现文件上传
 **/
@Controller
public class PartController {

    @PostMapping("/files")
    public String uploadFilePart(HttpServletRequest request) {
        try {
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                part.write(fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/index.html";
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        System.out.println(contentDisposition);
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            //trim() 方法是 String 类中的一个方法，它可以去掉字符串两端的空格
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }
}
```

## 5.8 全局异常处理

- 在Controller处理请求过程中发生了异常，DispatcherServlet将异常委托给异常处理器（处理异常的类）。

- 实现了HandlerExceptionResolver接口的都是异常处理类。
- 使用到的注解：@ExceptionHandler、@ControllerAdvice和@RestControllerAdvice（@ControllerAdvice与@RepsonseBody结合）

### 5.8.1 全局异常处理

范例：使用全局异常处理处理算数异常

1. 编写页面，两个数相除

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h3>计算器</h3>
    <form action="/divide" method="get">
        被除数:<input type="text" name="dividend" placeholder="被除数"><br>
        除数：<input type="text" name="divisor" placeholder="除数"><br>
        <input type="submit" value="计算结果">
    </form>
</body>
</html>
```

2. 编写控制器

```java
package com.zyf.exceptionhandler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:zhouyangfan
 * @date:2023/4/18
 * @Description:
 **/
@RestController
public class NumberController {

    @GetMapping("/divide")
    public String divide(int dividend, int divisor){
        int res = dividend/divisor;
        System.out.println(res);
        return "" + res;
    }
}
```

3. 编写异常处理类

```java
package com.zyf.exceptionhandler.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author:zhouyangfan
 * @date:2023/4/18
 * @Description: 方法的定义和controller一样
 **/
//控制器增强
@ControllerAdvice
public class GlobalExceptionHandler {
    //定义方法，处理计算异常
    //如果抛出的异常类与ExceptionHandler注解种声明的类相同，则由这个方法来处理异常
    @ExceptionHandler({ArithmeticException.class})
    public String handlerArithmeticException(ArithmeticException e, Model model) {
        String error = e.getMessage();
        model.addAttribute("error", error);
        return "exp";//视图
    }
}
```

4. 异常显示的页面

```java
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>算数异常</title>
</head>
<body>
      <h3>异常信息:<span th:text="${error}"></span></h3>
</body>
</html>
```

5. 启动SpringBoot测试

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418194002284.png" alt="image-20230418194002284" style="zoom:50%;" />

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418194014016.png" alt="image-20230418194014016" style="zoom:50%;" />

异常捕获成功

为了全面性，可以加上根异常处理，处理其余的异常

```java
@ExceptionHandler({Exception.class})
public String handlerDefaultException(Exception e, Model model) {
    String error = e.getMessage();
    model.addAttribute("error", error);
    return "exp";//视图
}
```

