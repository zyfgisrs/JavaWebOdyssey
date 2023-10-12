# 基于Java配置Bean

> 基于Java的配置是Spring框架的一种配置方法，它允许你在不使用XML的情况下，通过Java代码来配置你的应用。这种方法通常通过使用注解来完成。在Spring 3.0之前，配置主要是通过XML文件完成的。而从Spring 3.0开始，Java配置逐渐变得流行，因为它提供了一种类型安全且更加紧凑的方式来配置Bean。

优点：

- **类型安全**：基于Java的配置通过直接在代码中进行配置，避免了XML配置中的类型不安全。
- **编译时检查**：由于配置是代码，所以它受益于编译时的类型检查和其他验证。
- **IDE支持**：很多IDE提供了对Java代码的强大支持，包括代码补全、文档查看、错误和警告提示、及时的重构等。

## 普通Bean配置

- `com.zhouyf.pojo.Dog`

```JAVA
package com.zhouyf.pojo;

public class Dog {
    private String name;

    private int age;

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

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

- 配置类`com.zhouyf.config.DogConfig`

```java
package com.zhouyf.config;

import com.zhouyf.pojo.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DogConfig {
    @Bean
    public Dog dogBean(){
        return new Dog("Buddy", 3);
    }
}
```

- 测试类`src\test\java\com\zhouyf\test\DogTest`

```java
package com.zhouyf.test;

import com.zhouyf.config.DogConfig;
import com.zhouyf.pojo.Dog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DogConfig.class)
public class DogTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(DogTest.class);

    @Autowired
    private Dog dog;

    @Test
    void test(){
        LOGGER.info("测试Bean配置");
        System.out.println(dog);
    }
}
```

- 启动测试，验证注入结果

![image-20231012105708797](assets/image-20231012105708797.png)

## 外部Bean注入

- `com.zhouyf.pojo.Book`类和`com.zhouyf.pojo.Author`类

```java
package com.zhouyf.pojo;

public class Book {
    private Author author;

    private String name;


    public Book(Author author, String name) {
        this.author = author;
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author +
                ", name='" + name + '\'' +
                '}';
    }
}
```

```java
package com.zhouyf.pojo;

public class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

- `com.zhouyf.config.BookConfig`配置类

```java
package com.zhouyf.config;

import com.zhouyf.pojo.Author;
import com.zhouyf.pojo.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfig {
    @Bean
    public Book bookBean1(){ //方法名为bean的ID
        return new Book(authorBean1(), "格林童话");
    }

    @Bean
    public Book bookBean2(){ //方法名为bean的ID
        return new Book(authorBean2(), "安徒生童话");
    }

    @Bean
    public Author authorBean1(){ //方法名为bean的ID
        return new Author("格林");
    }

    @Bean
    public Author authorBean2(){ //方法名为bean的ID
        return new Author("安徒生");
    }
}
```

- 测试单元

```java
package com.zhouyf.test;

import com.zhouyf.config.BookConfig;
import com.zhouyf.pojo.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookConfig.class)
public class BookTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookTest.class);

    @Autowired
    @Qualifier("bookBean1") //指定bean的ID
    private Book book1;

    @Autowired
    @Qualifier("bookBean2") //指定bean的ID
    private Book book2;

    @Test
    void test(){
        LOGGER.info("测试Bean配置");
        System.out.println(book1);
        System.out.println(book2);
    }
}
```

- 启动测试

<img src="assets/image-20231012125706924.png" alt="image-20231012125706924"  />





