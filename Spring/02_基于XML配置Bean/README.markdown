[TOC]

# IoC（Inversion of Control）容器

> Inversion of Control（IoC）是一种设计原则，在 Spring 框架中，IoC 容器是管理对象创建、配置和生命周期的核心组件。它负责实例化、配置和组装应用中的对象。通过控制这些对象的创建和管理，IoC 容器带来了代码的松耦合和更加清晰的模块化。

## IoC设计原则

想象你去餐厅吃饭的经历。在家里做饭时，你要掌控一切：准备食材、烹饪、设置餐桌、清理等。所有的控制权都在你手中，你决定烹饪的方法、时间以及你将吃什么。在餐厅吃饭则完全不同。你坐下，阅读菜单，并告诉服务员你的选择。然后你就等待食物被送到你的桌子前。你不需要关心食物是如何准备的，不需要关心中间的过程，只需要享受结果。在这里，控制权从你手中转移到了餐厅及其员工。他们控制了烹饪的整个过程，并最终提供给你一个结果（一道美味的菜肴）。

1. **做饭（传统的编程模型）：**
   - 你得亲自管理对象的创建、生命周期和依赖关系。
   - 就像你在家里做饭，你需要手动准备食材、做菜和清理。
2. **餐厅（IoC）：**
   - 你只负责告诉“服务员”（IoC 容器）你要什么，然后就等待结果。
   - 在编程中，你只需定义好对象（Bean）的蓝图（如何创建、初始化以及它的依赖关系等），然后交给IoC容器管理。你不再亲自创建和管理对象，而只关心它们的交互。

**总结：**

- **传统模型**就像**在家做饭**，你管理所有事务，但这样可能较为复杂和耗时。
- **IoC**就像**在餐厅吃饭**，你将控制权交给了专家（这里是餐厅或者IoC容器），从而可以更专注于享受餐点（或专注于业务逻辑的实现）。

## Spring中IoC容器的作用

1. **管理对象的生命周期：**
   - IoC 容器负责 Bean 的创建、初始化、配置以及销毁。开发者无需关注对象的生命周期细节，可以集中精力开发业务逻辑。
2. **依赖注入（DI）：**
   - 容器负责依赖关系的注入，它将配置好的依赖自动注入到组件中，保证了系统各个部分的协同工作。
3. **配置集中管理：**
   - 通过 XML、注解或 Java 配置类，IoC 容器允许开发者集中管理应用的配置。所有关于对象创建和依赖关系的信息都储存在一个地方，提高了代码的可维护性。
4. **解耦：**
   - 对象之间的依赖关系减少，降低了代码之间的耦合度。代码变得更加模块化，开发者可以独立地开发、测试、修改和升级代码模块。
5. **面向切面编程（AOP）：**
   - 通过 IoC 容器，Spring 支持面向切面编程，允许开发者定义横切关注点（例如，日志、安全和事务管理）而不干扰到应用的主要业务逻辑。
6. **事件传播：**
   - Spring IoC 容器允许定义和传播事件，开发者可以定义监听器来处理这些事件，从而实现在 Bean 之间的低耦合通信。
7. **国际化支持：**
   - 通过 IoC 容器，Spring 提供了国际化（i18n）支持，可以方便地实现应用的多语言输出。
8. **提供企业服务：**
   - IoC 容器通过解耦的方式让基础服务（例如事务管理、消息服务、认证和授权）可以很容易地整合到应用中。

# DI依赖注入

> 依赖注入（Dependency Injection，DI）是一种实现控制反转（Inversion of Control，IoC）的技术，主要目的是为了解耦。简单来说，就是一个组件需要依赖另一个组件时，不再自己去创建依赖对象，而是由外部（通常是框架或容器）注入所需的依赖。

**通俗类比：**

- **没有使用 DI 的情况**： 想象你购买一辆新汽车，但出于某种原因，你必须自己组装发动机——这对大多数人来说是一项复杂且困难的任务。在这里，“自己组装发动机”可以类比为“自己创建依赖对象”。
- **使用 DI 的情况**： 当你购买一辆新汽车时，它已经配备了一个发动机，你无需关心它是如何安装和配置的——你只需享受驾驶这辆车的乐趣。在这里，“车已经配备发动机”就类比为“依赖对象已被注入”。

# Bean的配置方法

在 Spring 中，可以通过多种方式来配置 Bean 并将它们注册到 IoC 容器中。主要的方法可以归纳为以下几类：

## 基于 XML 的配置

使用 XML 文件定义 Bean 和它们的依赖关系。通过 `<bean>` 标签我们可以定义一个 Bean，通过 `<property>` 或 `<constructor-arg>` 标签我们可以设置它的属性或构造参数。

```xml
<bean id="exampleBean" class="com.example.ExampleBean">
    <property name="someProperty" value="someValue"/>
</bean>
```

##  基于注解的配置

使用 Java 注解来定义和配置 Bean。在类定义前使用 `@Component`（或其派生注解如 `@Service`、`@Repository`、`@Controller`）来标记一个类作为 Spring Bean。

```java
@Component
public class ExampleBean {
    // ...
}
```

此外，`@Autowired` 注解通常用于自动注入依赖，`@Value` 可用于注入基本类型的值等。

## 基于 Java 的配置

使用 Java 类作为配置文件。在这里，使用 `@Configuration` 注解来标识配置类，并使用 `@Bean` 注解在方法上来定义一个 Bean。

```java
@Configuration
public class AppConfig {

    @Bean
    public ExampleBean exampleBean() {
        return new ExampleBean();
    }
}
```

##  自动扫描

通过设置 `@ComponentScan`（可以在 XML 或 Java 配置中设置），Spring 会自动扫描指定包下的类，并检查 `@Component` 及其派生注解，将这些类自动注册为 Bean。

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
    // ...
}
```
或在 XML 中：

```xml
<context:component-scan base-package="com.example"/>
```

## 条件配置

Spring 提供了诸如 `@Conditional` 和 `@Profile` 这样的注解，允许在满足特定条件时才注册 Bean。这在不同环境（开发、测试、生产等）需要不同 Bean 配置的场景下非常有用。

```java
@Bean
@Profile("development")
public ExampleBean devExampleBean() {
    return new ExampleBean("dev");
}

@Bean
@Profile("production")
public ExampleBean prodExampleBean() {
    return new ExampleBean("prod");
}
```

# XML配置

## Setter方法注入Bean

- `com.zhouyf.pojo.Person`类，并配置setter方法

```java
package com.zhouyf.pojo;

public class Person {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
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

- XML配置`resources\META-INFO\spring\spring-pojo.xml`

```java
<bean id="personBean" class="com.zhouyf.pojo.Person">
    <property name="name" value="John"/>
    <property name="age" value="30"/>
</bean>
```

- 测试Bean配置`src\test\java\com\zhouyf\test\PersonTest.java`

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-pojo.xml")
public class PersonTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonTest.class);

    @Autowired
    private Person person;

    @Test
    void test(){
        LOGGER.info("测试Bean配置");
        System.out.println(person);
    }
}
```

Output：

```
17:03:35.666 [main] INFO com.zhouyf.test.PersonTest -- 测试Bean配置
Person{name='John', age=30}
```

- 在set方法中加入输出语句

```java
package com.zhouyf.pojo;

public class Person {
    private String name;
    private int age;

    public void setName(String name) {
        System.out.println("调用了setName方法");
        this.name = name;
    }

    public void setAge(int age) {
        System.out.println("调用了setAge方法");
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

- 重新启动测试

![image-20231011183914225](assets/image-20231011183914225.png)

可以看到，对象属性是通过set方法进行注入

## 构造函数注入Bean

- `main.java.com.zhouyf.pojo.Student`类，并配置一个有参构造

```java
package com.zhouyf.pojo;

public class Student {
    private String name;

    private String id;


    public Student(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
```

- XML配置`resources\META-INFO\spring\spring-pojo.xml`

```xml
<bean id="studentBean" class="com.zhouyf.pojo.Student">
    <constructor-arg value="John"/>
    <constructor-arg value="20"/>
</bean>
```

- 测试Bean配置`src\test\java\com\zhouyf\test\StudentTest`

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class StudentTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentTest.class);

    @Autowired
    private Student student;


    @Test
    void test() {
        LOGGER.info("测试Bean配置");
        System.out.println(student);
    }
}
```

- 输出结果

![image-20231011183832609](assets/image-20231011183832609.png)

- 在构造器中加入输出语句

```java
package com.zhouyf.pojo;

public class Student {
    private String name;

    private String id;


    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        System.out.println("使用构造器");
    }



    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
```

- 进行测试

![image-20231011184242543](assets/image-20231011184242543.png)

可以看到，在注入属性时使用的是构造器方法

## 外部Bean注入属性

- `com.zhouyf.pojo.Article`类和`com.zhouyf.pojo.Author`类

Article类中的一个属性为Author类

```java
package com.zhouyf.pojo;

public class Author {
    private String string;
    
    public Author() {
    }

    public Author(String string) {
        this.string = string;
    }


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "Author{" +
                "string='" + string + '\'' +
                '}';
    }
}
```

```java
package com.zhouyf.pojo;

public class Article {
    private String name;

    private Author author;
    
    public Article() {
    }

    public Article(String name, Author author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", author=" + author +
                '}';
    }
}
```

- XML配置`resources\META-INFO\spring\spring-pojo`

```xml
<bean id="authorBean" class="com.zhouyf.pojo.Author">
    <property name="name" value="zhouyf"/>
</bean>

<bean id="articleBean" class="com.zhouyf.pojo.Article">
    <property name="name" value="水浒传"/>
    <property name="author" ref="authorBean"/>
</bean>
```

- 测试类`src\test\java\com\zhouyf\test\ArticleTest`

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class ArticleTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleTest.class);

    @Autowired
    private Article article;

    @Test
    void test() {
        System.out.println(article);
    }
}
```

- 启动测试

```
Article{name='水浒传', author=Author{name='Tom'}}
```

## 内部Bean对象注入属性

- 两个类com.zhouyf.pojo.Engine和com.zhouyf.pojo.Car

```java
package com.zhouyf.pojo;

public class Engine {
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "model='" + model + '\'' +
                '}';
    }
}

```

```java
package com.zhouyf.pojo;

public class Car {
    private String brand;

    private Engine engine;


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", engine=" + engine +
                '}';
    }
}
```

- XML配置`resources\META-INFO\spring\spring-pojo.xml`

```xml
<bean id="carBean" class="com.zhouyf.pojo.Car">
    <property name="brand" value="Toyota"/>
    <property name="engine">
        <bean class="com.zhouyf.pojo.Engine">
            <property name="model" value="V8"/>
        </bean>
    </property>
</bean>
```

- 测试类`src\test\java\com\zhouyf\test\CarTest`

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-pojo.xml")
public class CarTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarTest.class);

    @Autowired
    private Car car;

    @Test
    void test() {
        LOGGER.info("测试Bean配置");
        System.out.println(car);
    }
}
```

- 启动测试

```
Car{brand='Toyota', engine=Engine{model='V8'}}
```

##   注入List集合类型属性

- com.zhouyf.pojo.Library类和com.zhouyf.pojo.Book类

```java
package com.zhouyf.pojo;

import java.util.List;

public class Library {
    private List<Book> books;


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                '}';
    }
}
```

```java
package com.zhouyf.pojo;

public class Book {
    private String name;

    private double price;

    public Book() {
    }

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
```

- 配置XML：`resources\META-INFO\spring\spring-pojo.xml`

```xml
<bean id="libraryBean" class="com.zhouyf.pojo.Library">
    <property name="books">
        <list>
            <bean class="com.zhouyf.pojo.Book">
                <property name="name" value="水浒传"/>
                <property name="price" value="30.4"/>
             </bean>

            <bean class="com.zhouyf.pojo.Book">
                <property name="name" value="西游记"/>
                <property name="price" value="29.3"/>
            </bean>
        </list>
    </property>
</bean>
```

- 测试Bean配置：`src\test\java\com\zhouyf\test\LibraryTest.java`

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Library;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-pojo.xml")
public class LibraryTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(LibraryTest.class);

    @Autowired
    private Library library;

    @Test
    void test(){
        LOGGER.info("测试Bean配置");
        System.out.println(library);
    }
}
```

- 启动测试类

```
19:48:09.236 [main] INFO com.zhouyf.test.LibraryTest -- 测试Bean配置
Library{books=[Book{name='水浒传', price=30.4}, Book{name='西游记', price=29.3}]}
```

## 注入Map集合类型属性

- com.zhouyf.pojo.CityTemperatureRecorder类

```java
package com.zhouyf.pojo;

import java.util.Map;

public class CityTemperatureRecorder {
    private Map<String, Double> cityTemperatures;

    public Map<String, Double> getCityTemperatures() {
        return cityTemperatures;
    }

    public void setCityTemperatures(Map<String, Double> cityTemperatures) {
        this.cityTemperatures = cityTemperatures;
    }

    @Override
    public String toString() {
        return "CityTemperatureRecorder{" +
                "cityTemperatures=" + cityTemperatures +
                '}';
    }
}
```

- XML配置文件`resources\META-INFO\spring\spring-pojo.xml`

```xml
<bean id="cityTemperatureRecorderBean" class="com.zhouyf.pojo.CityTemperatureRecorder">
    <property name="cityTemperatures">
        <map>
            <entry key="New York" value="16"/>
            <entry key="Los Angeles" value="18"/>
            <entry key="Miami" value="25"/>
        </map>
    </property>
</bean>
```

- 测试类`test\CityTemperatureRecorderTest`

```java
package com.zhouyf.test;

import com.zhouyf.pojo.CityTemperatureRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class CityTemperatureRecorderTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityTemperatureRecorderTest.class);

    @Autowired
    private CityTemperatureRecorder cityTemperatureRecorder;

    @Test
    void test(){
        LOGGER.info("测试Bean配置");
        System.out.println(cityTemperatureRecorder);
    }
}
```

- 启动测试

```
20:06:41.993 [main] INFO com.zhouyf.test.CityTemperatureRecorderTest -- 测试Bean配置
CityTemperatureRecorder{cityTemperatures={New York=16.0, Los Angeles=18.0, Miami=25.0}}
```

## 注入 Set 集合类型属性

- `com.zhouyf.pojo.Animal`

```java
package com.zhouyf.pojo;

import java.util.Set;

public class Animal {
    private Set<String> sets;

    public Set<String> getSets() {
        return sets;
    }

    public void setSets(Set<String> sets) {
        this.sets = sets;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "sets=" + sets +
                '}';
    }
}
```

- 配置XML：`resources\META-INFO\spring\spring-pojo.xml`

```xml
<bean id="animalBean" class="com.zhouyf.pojo.Animal">
    <property name="sets">
        <set>
            <value>猫</value>
            <value>狗</value>
            <value>鸡</value>
        </set>
    </property>
</bean>
```

测试类`test\AnimalTest`

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Animal;
import com.zhouyf.pojo.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class AnimalTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalTest.class);

    @Autowired
    private Animal animal;

    @Test
    void test() {
        System.out.println(animal);
    }
}
```

- 运行测试

```
Animal{sets=[猫, 狗, 鸡]}
```

## 注入Properties类型属性

> `java.util.Properties`是一个从键到值映射的HashTable，其中键和值都是字符串。在Spring的配置文件中注入`Properties`对象的常见用例包括配置外部属性（例如，数据库连接参数）。

- `com.zhouyf.config.DateBaseConfig`类

```java
package com.zhouyf.config;

import java.util.Properties;

public class DataBaseConfig {
    private Properties connectionProps;

    public Properties getConnectionProps() {
        return connectionProps;
    }

    public void setConnectionProps(Properties connectionProps) {
        this.connectionProps = connectionProps;
    }
}
```

- 配置XML：`resources\META-INFO\spring\spring-config.xml`

```xml
<bean id="dbProperties" class="org.springframework.beans.factory.config.PropertiesFactoryBean">
    <property name="properties">
        <props>
            <prop key="url">jdbc:mysql://localhost:3306/mydb</prop>
            <prop key="username">zhouyf</prop>
            <prop key="password">12345</prop>
        </props>
    </property>
</bean>

<bean id="databaseConfigBean" class="com.zhouyf.config.DataBaseConfig">
    <property name="connectionProps" ref="dbProperties"/>
</bean>
```

- 测试类`src\test\java\com\zhouyf\test\DataBaseConfigTest`

```java
package com.zhouyf.test;

import com.zhouyf.config.DataBaseConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Properties;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class DataBaseConfigTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseConfigTest.class);

    @Autowired
    private DataBaseConfig dataBaseConfig;

    @Test
    void test() {
        Properties connectionProps = dataBaseConfig.getConnectionProps();
        LOGGER.info("url:{}", connectionProps.get("url"));
        LOGGER.info("usrname:{}", connectionProps.get("usrname"));
        LOGGER.info("password:{}", connectionProps.get("password"));
    }
}
```

- 执行测试

```
20:45:32.832 [main] INFO com.zhouyf.test.DataBaseConfigTest -- url:jdbc:mysql://localhost:3306/mydb
20:45:32.833 [main] INFO com.zhouyf.test.DataBaseConfigTest -- usrname:null
20:45:32.833 [main] INFO com.zhouyf.test.DataBaseConfigTest -- password:12345
```

# Bean的作用域

在Spring框架中，Bean的作用域决定了Spring容器如何创建Bean的实例。下面是两种常用的Bean作用域：

1. **Singleton**：Spring容器中只创建单一实例。
2. **Prototype**：每次请求时都创建一个新的实例。

- `com.zhouyf.pojo.Cat`

```java
package com.zhouyf.pojo;

public class Cat {
    public Cat() {
        System.out.println("创建Cat对象");
    }
}
```

- XML配置：单例配置和原型配置

```xml
<bean id="catSingle" class="com.zhouyf.pojo.Cat" scope="singleton"/> 
<bean id="catProto" class="com.zhouyf.pojo.Cat" scope="prototype"/>
```

- 测试类：`src\test\java\com\zhouyf\test\CatTest.java`

在你提供的测试单元`CatTest`中，有5个`Cat`实例：`cat1`, `cat2`, `cat3`, `cat4`, 和`cat5`。其中`cat1`, `cat2`, 和`cat3` 是由ID为`catSingle`的bean注入的，而`cat4`和`cat5` 是由ID为`catProto`的bean注入的。在你的XML配置文件中，`catSingle`的作用域被定义为`singleton`，而`catProto`的作用域被定义为`prototype`。

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Cat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class CatTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(CatTest.class);

    @Qualifier("catSingle")
    @Autowired
    private Cat cat1;

    @Qualifier("catSingle")
    @Autowired
    private Cat cat2;

    @Qualifier("catSingle")
    @Autowired
    private Cat cat3;

    @Qualifier("catProto")
    @Autowired
    private Cat cat4;

    @Qualifier("catProto")
    @Autowired
    private Cat cat5;

    @Test
    void test(){
        LOGGER.info("single对象:{}", cat1.hashCode());
        LOGGER.info("single对象:{}", cat2.hashCode());
        LOGGER.info("single对象:{}", cat3.hashCode());
        LOGGER.info("proto对象:{}", cat4.hashCode());
        LOGGER.info("proto对象:{}", cat5.hashCode());
    }
}
```

- 启动测试

![image-20231011211732860](assets/image-20231011211732860.png)

在`test`方法的输出中，你将观察到`singleton`作用域的Bean的地址一致，而`prototype`作用域的Bean的地址则各不相同。同时，在`Cat`的构造函数中插入的日志将证明在应用的生命周期中，单例Bean只被创建一次，而原型Bean在每次注入时都被重新创建。

# Bean的生命周期回调

> 在Spring框架中，你可以使用XML配置文件来定义和管理你的beans。你也可以定义bean的生命周期回调方法。生命周期回调方法在bean的生命周期的特定点被调用。

- `com.zhouyf.pojo.Dog`

```java
package com.zhouyf.pojo;

public class Dog {
    private String name;


    public void init(){
        System.out.println("DogBean初始化");
    }


    public void destroy(){
        System.out.println("DogBean销毁");
    }

    public void sayHello(){
        System.out.println("Hello from " + this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

- 配置XML

指定`init`和`destroy`方法作为生命周期的回调方法。

```
<bean id="dogBean" class="com.zhouyf.pojo.Dog" init-method="init" destroy-method="destroy">
    <property name="name" value="Tom"/>
</bean>
```

- 测试类`src\test\java\com\zhouyf\test\DogTest`

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Dog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-pojo.xml")
public class DogTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DogTest.class);


    @Autowired
    private Dog dog;

    @Test
    void test(){
        LOGGER.info("测试Bean的生命周期回调");
        dog.sayHello();
    }
}
```

- 执行测试

![image-20231012093439235](assets/image-20231012093439235.png)

## Bean的生命周期

当我们在Spring框架中谈论Bean的生命周期时，我们主要讨论Bean从创建到销毁过程中的各个阶段。下面是关于Bean生命周期的基本步骤和概述：

1. **实例化 (Instantiation)**

- Spring容器开始Bean的生命周期通过实例化Bean对象。

2. **设置Bean属性 (Populate Properties)**

- 一旦Bean被实例化，Spring就会基于配置文件填充Bean的属性。

3. **设置Bean名称 (Set Bean Name)**

- 如果Bean实现了`BeanNameAware`接口，Spring将传入Bean的ID。

4. **设置Bean工厂 (Set Bean Factory)**

- 如果Bean实现了`BeanFactoryAware`接口，Spring将传入工厂实例。

5. **预初始化 (Pre-initialization)**

- 如果定义了Bean的后置处理器（实现了`BeanPostProcessor`接口），那么`postProcessBeforeInitialization`方法会被调用。

6. **初始化方法 (Initialization)**

- 如果Bean实现了`InitializingBean`接口，`afterPropertiesSet`方法将被调用。
- 如果在Bean的定义中使用了`init-method`，指定的初始化方法将被调用。

7. **后初始化 (Post-initialization)**

- 如果定义了Bean的后置处理器（实现了`BeanPostProcessor`接口），那么`postProcessAfterInitialization`方法会被调用。

8. **Bean的使用 (Usage)**

- 一旦Bean被初始化，它就可以被应用中的其他Bean用来执行业务逻辑。

9. **销毁前调用 (Pre-destroy)**

- 如果定义了Bean的后置处理器（实现了`DestructionAwareBeanPostProcessor`接口），那么`postProcessBeforeDestruction`方法会被调用。

10. **销毁 (Destroy)**

- 如果Bean实现了`DisposableBean`接口，`destroy`方法将被调用。
- 如果在Bean的定义中使用了`destroy-method`，指定的销毁方法将被调用。

11. **销毁后调用 (Post-destroy)**

- 如果定义了Bean的后置处理器（实现了`DestructionAwareBeanPostProcessor`接口），且该处理器的`requiresDestruction`方法返回`true`，那么`postProcessAfterDestruction`方法会被调用。

在整个Bean的生命周期中，Spring允许开发者通过实现特定接口或使用注解和XML配置来插入自定义的逻辑代码。

# 延迟初始化Bean

Spring框架中，你可以使用`lazy-init`属性来控制Bean是否应该在启动时初始化或在首次请求时进行延迟初始化。下面将通过一个简单的例子来展示如何在XML配置文件中定义一个延迟初始化的Bean，以及它与非延迟初始化Bean之间的差异。

- `com.zhouyf.pojo.Bird`

```java
package com.zhouyf.pojo;

public class Bird {
    private String name;

    public void sayHello() {
        System.out.println("sayHello from " + this.name);
    }
}
```

- XML配置，lazyBirdBean为懒加载，birdBean为默认方式加载

```xml
<bean id="lazyBirdBean" class="com.zhouyf.pojo.Bird" lazy-init="true">
    <property name="name" value="lazyBirdBean"/>
</bean>
<bean id="birdBean" class="com.zhouyf.pojo.Bird">
    <property name="name" value="birdBean"/>
</bean>
```

- 测试单元

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Bird;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class BirdTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BirdTest.class);

    @Qualifier("lazyBirdBean")
    @Autowired
    @Lazy
    private Bird lazyBird;

    @Qualifier("birdBean")
    @Autowired
    private Bird bird;

    @Test
    void test() {
        LOGGER.info("非延迟化Bean请求");
        bird.sayHello();
        LOGGER.info("非延迟化Bean请求结束");
        LOGGER.info("迟化Bean请求");
        lazyBird.sayHello();
        LOGGER.info("迟化Bean请求结束");
    }

}
```

- 输出日志：

![image-20231012102142956](assets/image-20231012102142956.png)

可以看到懒加载配置并没有生效：当你在测试中使用Spring Framework的`@Autowired`注解来注入`Bean`，即使你为`Bean`设置了`lazy-init="true"`，Bean的依赖解析也会在容器启动时发生，从而触发所有`Autowired` Bean的创建。这意味着所有通过`@Autowired`注解的Bean，即使标记为延迟初始化，也会在应用上下文启动的时候被初始化，因为Spring在启动时要确保所有的依赖关系都可以被满足。

- 使用`@Lazy`注解来进行测试

```java
package com.zhouyf.test;

import com.zhouyf.pojo.Bird;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:META-INFO/spring/spring-*.xml")
public class BirdTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BirdTest.class);

    @Qualifier("lazyBirdBean")
    @Autowired
    @Lazy
    private Bird lazyBird;

    @Qualifier("birdBean")
    @Autowired
    private Bird bird;

    @Test
    void test() {
        LOGGER.info("非延迟化Bean请求");
        bird.sayHello();
        LOGGER.info("非延迟化Bean请求结束");
        LOGGER.info("迟化Bean请求");
        lazyBird.sayHello();
        LOGGER.info("迟化Bean请求结束");
    }

}
```

- 启动测试

![image-20231012103120940](assets/image-20231012103120940.png)





