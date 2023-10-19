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

- 两个POJO：`com.zhouyf.pojo.Book`类和`com.zhouyf.pojo.Author`类

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

## 注入集合对象到Bean中

- `com.zhouyf.pojo.MyBean`

```java
package com.zhouyf.pojo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MyBean {
    private List<String> name;

    private Map<String, Integer> scores;

    @Autowired
    public void setName(List<String> name) {
        this.name = name;
    }

    @Autowired
    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "name=" + name +
                ", scores=" + scores +
                '}';
    }
}
```

- 配置类`com.zhouyf.MyBeanConfig`

```java
package com.zhouyf.config;

import com.zhouyf.pojo.MyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class MyBeanConfig {
    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

   @Bean
    public List<String> nameList() {
        return List.of("张三", "李四", "王五");
    }

    @Bean
    public Map<String, Integer> scoresMap() {
        return Map.of("张三", 85, "李四", 90, "王五", 95);
    }

}
```

- 测试类`\src\test\java\com\zhouyf\test\MyBeanTest.java`

```java
package com.zhouyf.test;

import com.zhouyf.config.MyBeanConfig;
import com.zhouyf.pojo.MyBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MyBeanConfig.class)
public class MyBeanTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBeanConfig.class);

    @Autowired
    private MyBean myBean;

    @Test
    void test(){
        LOGGER.info("测试bean配置");
        System.out.println(myBean);
    }

}
```

![image-20231018200652211](assets/image-20231018200652211.png)

在Spring框架中，`@Autowired` 注解用于自动装配bean的属性、方法或构造函数。Spring容器查看该注解，并自动满足bean的依赖性。在您提供的代码片段中，尽管定义的bean的方法名称（`nameList` 和 `scoreMap`）与 `MyBean` 类中的setter方法中的参数（`name` 和 `scores`）不完全匹配，但注入仍然可以成功，这是因为Spring的自动装配不仅仅是基于bean的ID/名称，还有几个原因：

1. **类型匹配**：Spring首先尝试通过类型进行自动装配。如果应用上下文中只存在一个与目标类型匹配的bean，则Spring将其注入到使用 `@Autowired` 注解的setter方法中。在您的示例中，应用上下文里只有一个 `List<String>` 类型的bean和一个 `Map<String, Integer>` 类型的bean，所以即便它们的ID/名称与参数名不匹配，Spring依然可以解析到这是要注入的正确bean。

2. **容器中的候选bean**：如果有多个类型匹配的bean，则Spring会考虑变量名称作为默认的候选bean名称。但在这种情况下，您的应用程序上下文中每种类型只有一个bean，因此不会存在歧义。

3. **注解配置的灵活性**：与基于XML的配置相比，使用注解进行配置提供了更大的灵活性。您不需要明确指定bean的ID或名称，Spring会根据上下文和类型自动完成匹配。

因此，即使方法名称或变量名称在某种程度上与bean的名称不一致，只要类型匹配，并且没有引起歧义性问题，Spring通常都能成功地解析依赖关系并进行正确的注入。

- 修改`MyBeanConfig`，添加一个相同类型的Bean

```JAVA
package com.zhouyf.config;

import com.zhouyf.pojo.MyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class MyBeanConfig {
    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

   @Bean
    public List<String> nameList() {
        return List.of("张三", "李四", "王五");
    }

    @Bean
    public Map<String, Integer> scoresMap1() {
        return Map.of("张三", 85, "李四", 90, "王五", 95);
    }

    @Bean
    public Map<String, Integer> scoresMap2() {
        return Map.of("张三", 85, "李四", 90, "王五", 95);
    }

}
```

- 重启测试

```java
package com.zhouyf.test;

import com.zhouyf.config.MyBeanConfig;
import com.zhouyf.pojo.MyBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MyBeanConfig.class)
public class MyBeanTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBeanConfig.class);

    @Autowired
    private MyBean myBean;

    @Test
    void test(){
        LOGGER.info("测试bean配置");
        System.out.println(myBean);
    }

}
```

- 报错：匹配到两个bean，发生了冲突

```
No qualifying bean of type 'java.util.Map<java.lang.String, java.lang.Integer>' available: expected single matching bean but found 2: scoresMap1,scoresMap2
```

- 【解决办法1】修改`setScores`方法中参数名称为指定Bean的ID

```java
package com.zhouyf.pojo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MyBean {
    private List<String> name;

    private Map<String, Integer> scores;

    @Autowired
    public void setName(List<String> name) {
        this.name = name;
    }

    @Autowired
    public void setScores(Map<String, Integer> scoresMap2) {
        this.scores = scoresMap2;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "name=" + name +
                ", scores=" + scores +
                '}';
    }
}
```

- 【解决办法2】使用`@Qualifier`注解，指定Bean的ID

```java
package com.zhouyf.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;

public class MyBean {
    private List<String> name;

    private Map<String, Integer> scores;

    @Autowired
    public void setName(List<String> name) {
        this.name = name;
    }

    @Autowired
    @Qualifier("scoresMap2")
    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "name=" + name +
                ", scores=" + scores +
                '}';
    }
}
```

# @Value使用

我将提供一个基于传统Spring Framework的例子，演示如何使用`@Value`注解。在这个例子中，我们将创建一个简单的Java应用程序，使用Spring框架的核心功能来注入值。

- `app.properties`配置文件

```properties
myapp.message=hello from @Value
```

- 创建`com.zhouyf.pojo.AppBean`,使用`@Value`注解来注入属性。

```java
package com.zhouyf.pojo;

import org.springframework.beans.factory.annotation.Value;

public class MyApp {
    @Value("${myapp.message}")
    private String message;

    public void start(){
        System.out.println(message);
    }
}
```

- 创建一个名为`AppConfig.java`的新类。使用`@PropertySource`来指定属性文件的位置，并创建一个方法来初始化`PropertySourcesPlaceholderConfigurer`，它是用来解析`@Value`中使用的`${...}`占位符的。

```java
package com.zhouyf.config;

import com.zhouyf.pojo.MyApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:app.properties")
public class MyAppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public MyApp myApp(){
        return new MyApp();
    }
}
```

- 测试属性注入效果

```java
package com.zhouyf.test;

import com.zhouyf.config.MyAppConfig;
import com.zhouyf.pojo.MyApp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MyAppConfig.class)
public class MyAppTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(MyAppTest.class);
    @Autowired
    private MyApp myApp;


    @Test
    void test(){
        LOGGER.info("测试Bean配置.....");
        myApp.start();
    }
}
```

![image-20231018211114283](assets/image-20231018211114283.png)

## PropertySourcesPlaceholderConfigurer类

`PropertySourcesPlaceholderConfigurer`类在Spring中起着非常重要的作用，尤其是在处理外部化配置和属性值替换时。它是`BeanFactoryPostProcessor`类型的一个实现，这意味着它可以修改bean定义的值，甚至可以在bean实例化之前处理它们。以下是这个类的一些关键职责和用途：

1. **解析占位符**:
   - `PropertySourcesPlaceholderConfigurer`允许你通过`${...}`占位符在bean定义（例如在XML配置或使用`@Value`注解的类中）中引用属性。例如，如果你有一个属性叫`myapp.name`，你可以在你的bean定义中使用`${myapp.name}`来引用这个值。
   - 当Spring上下文启动和创建beans时，`PropertySourcesPlaceholderConfigurer`确保所有这些占位符都被相应的属性值替换。

2. **属性源注册**:
   - 它从已注册的属性源（如属性文件、环境变量、JVM系统属性等）中读取属性。这意味着你可以将多个属性源组合起来，并且可以使用占位符来引用任何属性源中的值。
   - 你可以自定义属性源的优先级，这在解决属性名称冲突时非常有用。

3. **支持类型转换**:
   - `PropertySourcesPlaceholderConfigurer`不仅仅替换字符串值。它还处理类型转换，这意味着你可以将属性注入到非String类型的bean属性中（例如，int、boolean、enum等）。

4. **`@PropertySource`注解的处理**:
   - 当你在配置类上使用`@PropertySource`注解来指定属性文件的位置时，你需要`PropertySourcesPlaceholderConfigurer`来处理加载和解析这些值的操作。

5. **激活Profiles**:
   - 它允许基于不同的环境（开发、测试、生产等）激活不同的profiles，这样你就可以根据当前激活的profile来加载特定的属性。

6. **属性验证**:
   - 你可以设置`PropertySourcesPlaceholderConfigurer`来要求必须存在某些属性，否则应用程序将无法启动。这对确保所有必需的配置都已正确设置非常有用。

需要注意的是，从Spring 4.3开始，`PropertySourcesPlaceholderConfigurer`通常不再需要明确声明，因为在使用`@PropertySource`注解的情况下，Spring会自动注册一个`PropertySourcesPlaceholderConfigurer`实例。但是，在一些复杂的配置场景或旧版Spring应用程序中，你可能仍然需要明确声明它。

