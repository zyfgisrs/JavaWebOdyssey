# 1. Spring简介

## 1.1 Spring框架的定义

Spring 框架是一个开源的 JavaEE（现在的 Jakarta EE）全栈框架，它提供了一系列的软件组件，可以用于开发企业级应用程序。

Spring 的设计目标是促进应用程序的**松耦合**、**灵活性**和**可重用性**，同时提高开发效率和测试性。

## 1.2 Spring中的核心概念

1. IoC容器：Spring 的 IoC（Inversion of Control）容器是框架的核心，它可以管理对象的生命周期和依赖关系，从而实现对象的**解耦**。通过 IoC 容器，我们可以实现依赖注入（DI）和控制反转（IoC）等功能。
2. AOP面向切面编程：AOP（Aspect-Oriented Programming）是 Spring 框架中的另一个核心概念。它通过对横切关注点进行切面化编程，从而实现了代码的模块化和重用。Spring 的 AOP 实现基于动态代理和字节码生成技术。

## 1.3 Spring与SpringBoot关系

- Spring 是一个开源的、轻量级的企业级应用程序开发框架，它提供了众多的特性和组件，包括 IoC 容器、AOP、JDBC、ORM、MVC 等，可以帮助开发人员构建高效、灵活和可扩展的应用程序。
- Spring Boot 是在 Spring 基础上进行进一步**封装和简化**，使得开发人员可以更加方便快捷地开发和部署 Spring 应用程序。

## 1.4 Spring和SpringMVC的关系

- Spring 和 Spring MVC 是两个不同的概念，但它们之间有密切的关系。
- Spring MVC 可以看作是 Spring 的**一个模块**，是在 Spring 基础上进行了进一步的扩展和优化，使得开发人员可以更加**方便地构建 Web 应用程序**。同时，Spring MVC 也保持了 Spring 的优良传统和灵活性，开发人员可以自由地选择使用 Spring MVC 提供的控制器、视图和处理流程，也可以进行个性化的配置和扩展。

# 2. IoC容器

## 2.1  IoC的概念

- 定义：IoC即控制反转，是一种设计思想，它是面向对象编程中**依赖注入**的一种实现方式。
- 核心思想：将对象的创建、依赖关系的维护和对象的调用解耦，由**容器来负责**对象的创建和管理，控制对象之间的依赖关系。
- 作用： IoC 把对象的创建和依赖关系的维护交给了容器来完成，容器管理对象的生命周期和依赖关系，实现了对象之间的松耦合，使得系统更加灵活、可扩展和易于维护。

## 2.2 Spring中的IoC容器

- 定义：负责创建、管理和协调应用程序程序中的对象，也成为Bean容器。

- 作用：Spring的IoC容器实现了IoC控制反转，IoC 容器负责管理 Bean（即被 IoC 容器管理的对象），包括 Bean 的创建、初始化、依赖注入、销毁等，开发者只需要定义 Bean 的配置信息（如 Bean 的类、属性、依赖关系等），由容器来完成实例化和依赖注入的过程。

### 2.2.1 IOC容器底层实现原理

1. IoC容器底层实现技术：**反射技术**和**配置元数据**（ XML配置文件、JavaConfig 和注解）
2. IoC容器中使用到的设计模式：工厂模式（BeanFactory 接口为工厂接口，IoC容器的核心）和单例模式（减少内存开销，提高系统性能，可以通过配置修改）。

### 2.2.2 BeanFactory和ApplicationContext 接口

BeanFactory接口是ApplicationContext接口的父接口

![image-20230327170654585](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230327170654585.png)

BeanFactory： Spring IoC 容器的**最基本形式**，它提供了最基本的 Bean 创建和管理功能，但是它的实现方式比较简单，只提供了一些基本的容器功能。它的主要作用是提供 IoC 容器的基本功能，包括 Bean 的创建、初始化、装配、生命周期管理等。BeanFactory 接口只是一个接口，它并没有提供具体的实现，是spring内部自己使用的接口，不提供给开发者使用。

ApplicationContext ：是在 BeanFactory 的基础上扩展出来的。它不仅提供了 BeanFactory 接口的所有功能，还提供了很多其他的高级特性，比如事件监听、国际化支持、资源访问、Bean 自动装配等。ApplicationContext 接口的实现方式比较复杂，它包括了多个模块和组件，比如 BeanFactory、AOP、事务管理等。ApplicationContext 接口提供了一个完整的 IoC 容器实现，可以方便地进行应用程序开发。

在做服务器端开发的时候，使用ApplicationContext比较多，因为所有bean初始化操作在项目启动完成之前都已经初始化了。

### 2.2.3 ApplicationContext实现类

在 Spring 框架中，常用的 ApplicationContext 实现类包括：

1. ClassPathXmlApplicationContext：从类路径下加载 XML 配置文件，可以加载多个配置文件。
2. FileSystemXmlApplicationContext：从文件系统中加载 XML 配置文件，可以加载多个配置文件。
3. AnnotationConfigApplicationContext：通过注解方式加载配置类，用于基于 Java 配置的 Spring 应用程序上下文。
4. XmlWebApplicationContext：用于基于 Web 的应用程序，从 Web 应用程序上下文中加载 XML 配置文件。
5. AnnotationConfigWebApplicationContext：用于基于 Web 的应用程序，从 Web 应用程序上下文中加载基于注解的配置类。
6. GenericXmlApplicationContext：通用的 XML 应用程序上下文，可以从任何资源（如文件系统、类路径、URL 等）加载 XML 配置文件。
7. StaticApplicationContext：静态应用程序上下文，适用于在测试中创建简单的 Spring 应用程序上下文。

# 3. Bean配置

## 3.1 SpringTest

Spring测试之中需要有一个上下文应用环境，如果没有，很难进行程序的正确运行，所以在Spring的内部提供有一个SpringTest的依赖库的支持。

1. **配置Junit5**

导入maven依赖

```xml
<!-- https://mvnrepository.com/artifact/org.junit/junit-bom -->
<dependency>
    <groupId>org.junit</groupId>
    <artifactId>junit-bom</artifactId>
    <version>5.9.2</version>
    <type>pom</type>
</dependency>

<!-- https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/org.junit.vintage/junit-vintage-engine -->
<dependency>
    <groupId>org.junit.vintage</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher -->
<dependency>
    <groupId>org.junit.platform</groupId>
    <artifactId>junit-platform-launcher</artifactId>
    <version>1.9.2</version>
    <scope>test</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>6.0.7</version>
    <scope>test</scope>
</dependency>

```

2. 配置文件applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="com.zyf"/>
    <context:annotation-config/>
</beans>
```

3. 定义service接口和类

```java
package com.zyf.service;

import com.zyf.pojo.Book;

public interface BookService {
    void insertBook(Book book);
}
```

```java
package com.zyf.service;

import com.zyf.pojo.Book;
import org.springframework.stereotype.Service;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:service实现类
 **/
@Service
public class BookServiceImpl implements BookService {

    @Override
    public void insertBook(Book book) {
        System.out.println("添加了一本书 : " + book);
    }
}
```

4. 编写测试类，进行测试

```java
package com.zyf.service;

import com.zyf.pojo.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//定义XML配置文件
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@ExtendWith(SpringExtension.class)//表示此时使用外部的测试工具(junit5)
class BookServiceImplTest {
    @Autowired
    private BookServiceImpl service;

    @Test
    void testJunit5() {
        Book book = new Book("三国演绎", 50);
        this.service.insertBook(book);
    }
}
```

输出结果，可见测试成功。测试环境自定启动了一个Spring容器。

```
添加了一本书 : Book{name='三国演绎', price=50}
```

## 3.2 Logback日志组件

- 日志主要用来记录项目运行的内容（异常信息、用户行为）
- sprigboot项目默认使用的日志logback
- 注意：日志包括：记录程序员编写的日志信息和框架的日志

maven依赖

```xml
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.7</version>
</dependency>

<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.6</version>
    <scope>test</scope>
</dependency>

```

日志使用：

```java
package com.zyf.service;

import com.zyf.pojo.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:service实现类
 **/
@Service
public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public void insertBook(Book book) {
        LOGGER.info("书：{},添加成功", book);
    }
}
```

## 3.3 基于XML的配置

### 3.1.1 set方法注入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="bookEntity" class="com.zyf.entity.BookEntity">
        <property name="bookName" value="三国演义"></property>
        <property name="bookPrice" value="99.8"></property>
    </bean>
</beans>
```

name为属性名称，value为属性值。

**底层基于反射机制，调用set方法来做赋值。**

```java
package com.zyf.entity;

/**
 * @author: 周杨凡
 * @date: 2023-02-10
 */
public class BookEntity {
    private String bookName;

    private Double bookPrice;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        System.out.println("bookname属性注入");
        this.bookName = bookName;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "bookName='" + bookName + '\'' +
                ", bookPrice=" + bookPrice +
                '}';
    }
    
}
```

```java
import com.zyf.entity.BookEntity;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.print.Book;

/**
 * @author: 周杨凡
 * @date: 2023-02-09
 */
public class Test01 {
    public static void main(String[] args) {
        //spring配置bean容器，容器注入，方法：注解或者xml
        //bean实际上是一个对象
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("book.xml");
        //根据bean的id获取对象
        BookEntity bookEntity = classPathXmlApplicationContext.getBean("bookEntity",BookEntity.class);
        System.out.println(bookEntity);
    }
}
```

输出：

```
bookname属性注入
BookEntity{bookName='三国演义', bookPrice=99.8}
```

### 3.1.2 基于有参构造注入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="orderEntity" class="com.zyf.entity.OrderEntity">
        <constructor-arg index="0" value="1"/>
        <constructor-arg index="1" value="zyf"/>
    </bean>
</beans>
```

```java
package com.zyf.entity;

/**
 * @author: 周杨凡
 * @date: 2023-02-10
 */
public class OrderEntity {
    private int id;

    private String name;

    public OrderEntity(int id, String name) {
        this.id = id;
        this.name = name;
        System.out.println("反射机制，调用有参构造器");
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
```

### 3.1.3 注入空值与特殊字符

**注入空值**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="bookEntity" class="com.zyf.entity.BookEntity">
        <property name="bookName">
            <null></null>
        </property>
        <property name="bookPrice" value="88.9"></property>
    </bean>
</beans>
```

**特殊符号注入**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress ALL -->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--
    注入特殊符号
    -->
    <bean id="bookEntity01" class="com.zyf.entity.BookEntity">
        <property name="bookName" value="&lt;&lt;武汉&gt;&gt;"></property>
        <property name="bookPrice" value="88.9"></property>
    </bean>


    <bean id="bookEntity02" class="com.zyf.entity.BookEntity">
        <property name="bookName" >
            <value><![CDATA[<<武汉>>]]></value>
        </property>
        <property name="bookPrice" value="88.9"></property>
    </bean>
</beans>
```

### 3.1.4 外部bean对象注入属性

MemberDao类

```java
public interface MemberDao {
    public void addMember();
}
```

```java
public class MemberDaoImp implements MemberDao{
    @Override
    public void addMember() {
        System.out.println(">>MemberDao层添加成员<<");
    }
}
```

service类，现在要把dao类注入到service中

```java
package com.zyf.service;

import com.zyf.dao.MemberDao;

/**
 * @author: 周杨凡
 * @date: 2023-02-11
 */
public class MemberService {
    private MemberDao memberDao;

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }


    public void addMember(){
        memberDao.addMember();
        System.out.println(">>memberservice层添加member<<");
    }
}
```

xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="memberDao" class="com.zyf.dao.MemberDao"></bean>
    <bean id="memberService" class="com.zyf.service.MemberService">
        <property name="memberDao" ref="memberDao"></property>
    </bean>
</beans>
```

### 3.1.5 内部bean对象注入属性

创建一个员工类EmpEntity，有属性name、age和DeptEntity类型的部门属性deptEntity，现在要将deptEntity实例注入到EmpEntity的bean中。

```java
package com.zyf.entity;

/**
 * @author: 周杨凡
 * @date: 2023-02-11
 */
public class EmpEntity {
    private String name;

    private Integer age;

    private DeptEntity deptEntity;

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDeptEntity(DeptEntity deptEntity) {
        this.deptEntity = deptEntity;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmpEntity{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", deptEntity=" + deptEntity +
                '}';
    }
}
```

DeptEntity类，其中只有一个部门名称name字段

```java
package com.zyf.entity;

/**
 * @author: 周杨凡
 * @date: 2023-02-11
 */
public class DeptEntity {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DeptEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress ALL -->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="empEntity" class="com.zyf.entity.EmpEntity">
        <property name="age" value="12"></property>
        <property name="name" value="zyf"></property>
        <property name="deptEntity">
            <bean id="deptEntity" class="com.zyf.entity.DeptEntity">
                <property name="name" value="教育部门"></property>
            </bean>
        </property>
    </bean>
</beans>
```

### 3.1.6 注入集合类型属性

创建学生类StuEntity，其中有四个集合属性，分别为数组arrays，有序列表list、无序列表set和键值对map。

```java
package com.zyf.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: 周杨凡
 * @date: 2023-02-11
 */
public class StuEntity {
    private String[] arrays;

    private List<String> list;

    private Set<String> set;

    private Map<String,String> map;

    public void setArrays(String[] arrays) {
        this.arrays = arrays;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "StuEntity{" +
                "arrays=" + Arrays.toString(arrays) +
                ", list=" + list +
                ", set=" + set +
                ", map=" + map +
                '}';
    }
}
```

-

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="stuEntity" class="com.zyf.entity.StuEntity">
        <property name="arrays">
            <array>
                <value>zyf01</value>
                <value>zyf02</value>
            </array>
        </property>

        <property name="list">
            <list>
                <value>list01</value>
                <value>list02</value>
            </list>
        </property>

        <property name="map">
            <map>
                <entry key="zyf" value="123"></entry>
                <entry key="hhh" value="234"></entry>
            </map>
        </property>


        <property name="set">
            <set>
                <value>set01</value>
                <value>set02</value>
            </set>
        </property>
    </bean>
</beans>
```

### 3.1.7 注入对象为集合元素

创建Student类和Course类，Student类中有list类型的属性，list中为Course实例

```java
package com.zyf.pojo;

import java.util.List;
import java.util.Map;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:student bean
 **/
public class Student {
    private String name;

    private int age;

    private Map<String,Integer> scores;

    private List<Course> courses;

    public Student(String name, int age, Map<String, Integer> scores, List<Course> courses) {
        this.name = name;
        this.age = age;
        this.scores = scores;
        this.courses = courses;
    }

    public Student() {
    }

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

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
               "name='" + name + '\'' +
               ", age=" + age +
               ", scores=" + scores +
               ", courses=" + courses +
               '}';
    }
}
```

```java
package com.zyf.pojo;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:course bean
 **/
public class Course {
    private String name;

    public Course(String name) {
        this.name = name;
    }

    public Course() {
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course{" +
               "name='" + name + '\'' +
               '}';
    }
}
```

配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="student" class="com.zyf.pojo.Student">
        <property name="name" value="周杨凡"/>
        <property name="age" value="24"/>
        <property name="courses">
            <list>
                <ref bean="course01"></ref>
                <ref bean="course02"></ref>
            </list>
        </property>
        <property name="scores">
            <map>
                <entry key="英语" value="100"></entry>
                <entry key="数学" value="98"></entry>
            </map>
        </property>
    </bean>

    <bean id="course01" class="com.zyf.pojo.Course">
        <property name="name" value="数学"/>
    </bean>
    <bean id="course02" class="com.zyf.pojo.Course">
        <property name="name" value="英语"/>
    </bean>

</beans>
```

测试类

```java
public class TestBean {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student = classPathXmlApplicationContext.getBean("student", Student.class);
        System.out.println(student);
    }
}
```

输出，可以看到student已被成功注入IoC容器中。

```
Student{name='周杨凡', age=24, scores={英语=100, 数学=98}, courses=[Course{name='数学'}, Course{name='英语'}]}
```

## 3.5 基于编程与注解的配置

### 3.5.1 @Configuration

javaConfig是Spring的一个子项目，在Spring4之后，它成为了核心功能。

- @Configuration代表这是一个配置类，就等价于我们之前看到的beans.xml
- @Configuration修饰的类也会被托管到容器中，因为它本来就是一个@Component
- @Bean注释的方法，方法名相当于bean标签的id属性，返回类型相当于bean标签的class属性
- 完全使用配置类的方式去做，我们只能通过annotationConfigApplicationContext上下文来获取容器，通过配置类的class对象加载。

**一个Message类和MessageStore类，MessageStore类中有List容器来存放Message实例，有一个业务了额MessageService来中定义了send方法将MessageStore中的message进行输出，我们使用注解和JavaConfig来实现Bean的配置和相关属性的注入**

```java
package com.zyf.pojo;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:Message Bean
 **/
public class Message {
    private String title;

    private String content;

    public Message(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Message() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
               "title='" + title + '\'' +
               ", content='" + content + '\'' +
               '}';
    }
}
```

```java
package com.zyf.pojo;

import java.util.List;
import java.util.Map;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:MessageStore Bean
 **/
public class MessageStore {
    private List<Message> messageList;

    public MessageStore() {
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
```

Service接口

```java
package com.zyf.service;

import com.zyf.pojo.MessageStore;

public interface MessageService {

    void sentMessage(MessageStore messageStore);

}
```

Service接口的实现类，实现了具体的sendMessage的方法

```java
package com.zyf.service;

import com.zyf.pojo.Message;
import com.zyf.pojo.MessageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:
 **/
@Service
public class MessageServiceImpl implements MessageService{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);


    @Override
    public void sentMessage(MessageStore messageStore) {
        List<Message> messageList = messageStore.getMessageList();
        for (Message message : messageList) {
            String title = message.getTitle();
            String content = message.getContent();
            LOGGER.info("发送信息:标题:{},内容:{}", title, content);
        }
    }
}
```

配置类，配置了3个Message实例，将三个实例注入到MessageStore的容器中

```java
package com.zyf.config;

import com.zyf.pojo.Message;
import com.zyf.pojo.MessageStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:配置类
 **/
@Configuration
public class MessageStoreConfig {

    @Bean("hello")
    public Message getMessage01() {
        return new Message("问候", "你好");
    }

    @Bean("news")
    public Message getMessage02() {
        return new Message("新闻", "男篮夺冠");
    }

    @Bean("goodNight")
    public Message getMessage03() {
        return new Message("关心", "晚安");
    }

    @Bean
    public MessageStore getMessageStore(
      //按照名称进行匹配
            Message hello,
            Message news,
            Message goodNight) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(hello);
        messages.add(news);
        messages.add(goodNight);
        MessageStore messageStore = new MessageStore();
        messageStore.setMessageList(messages);
        return messageStore;
    }
}
```

测试类

```java
package com.zyf.service;

import com.zyf.pojo.MessageStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@ExtendWith(SpringExtension.class)
class MessageServiceImplTest {
    @Autowired
    private MessageServiceImpl service;

    @Autowired
    private MessageStore store;

    @Test
    void test01() {
        service.sentMessage(store);
    }
}
```

输出：

```
INFO com.zyf.service.MessageServiceImpl -- 发送信息:标题:问候,内容:你好
INFO com.zyf.service.MessageServiceImpl -- 发送信息:标题:新闻,内容:男篮夺冠
INFO com.zyf.service.MessageServiceImpl -- 发送信息:标题:关心,内容:晚安
```

### 3.5.2 Qualifier和@Primary

Spring可以实现注入的操作，而在注入处理的时候分为类型（byType）和名称（byName）两种，现在假设有如下情况：

```java
package com.zyf.config;

import com.zyf.pojo.Message;
import com.zyf.pojo.MessageStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:配置类
 **/
@Configuration
public class MessageStoreConfig {

    @Bean("hello")
    public Message getMessage01() {
        return new Message("问候", "你好");
    }

    @Bean("news")
    public Message getMessage02() {
        return new Message("新闻", "男篮夺冠");
    }

    @Bean("goodNight")
    public Message getMessage03() {
        return new Message("关心", "晚安");
    }

    @Bean
    public MessageStore getMessageStoreA(
            Message hello,
            Message news,
            Message goodNight) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(hello);
        messages.add(news);
        messages.add(goodNight);
        MessageStore messageStore = new MessageStore();
        messageStore.setMessageList(messages);
        return messageStore;
    }

    @Bean
    public MessageStore getMessageStoreB(
            Message hello,
            Message news,
            Message goodNight) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(hello);
        messages.add(news);
        messages.add(goodNight);
        MessageStore messageStore = new MessageStore();
        messageStore.setMessageList(messages);
        return messageStore;
    }
}
```

```java
package com.zyf.service;

import com.zyf.pojo.MessageStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@ExtendWith(SpringExtension.class)
class MessageServiceImplTest {
    @Autowired
    private MessageServiceImpl service;

    @Autowired
    private MessageStore store;

    @Test
    void test01() {
        service.sentMessage(store);
    }
}
```

输出

```
Caused by: org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.zyf.pojo.MessageStore' available: expected single matching bean but found 2: getMessageStoreA,getMessageStoreB
```

可以看到配置类中有两个MessageStore类型的Bean，而测试类中要注入的MessageStore属性名称为store，因此无法进行名称的匹配，而使用类型的匹配，会发现容器中存在两个匹配的Bean，因此这里就要使用Qualifier注解，来指定注入的Bean的名称。

```java
package com.zyf.service;

import com.zyf.pojo.MessageStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@ExtendWith(SpringExtension.class)
class MessageServiceImplTest {
    @Autowired
    private MessageServiceImpl service;

    @Autowired
    @Qualifier(value = "getMessageStoreA")
    private MessageStore store;

    @Test
    void test01() {
        service.sentMessage(store);
    }
}
```

发现可以正常进行输出日志信息：

```
INFO com.zyf.service.MessageServiceImpl -- 发送信息:标题:问候,内容:你好
INFO com.zyf.service.MessageServiceImpl -- 发送信息:标题:新闻,内容:男篮夺冠
INFO com.zyf.service.MessageServiceImpl -- 发送信息:标题:关心,内容:晚安
```

**如果不使用@Qualifier名称的配置，希望可以自动实现，那么就使用@Primary设置主要注入的Bean。**

```java
package com.zyf.config;

import com.zyf.pojo.Message;
import com.zyf.pojo.MessageStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:配置类
 **/
@Configuration
public class MessageStoreConfig {

    @Bean("hello")
    public Message getMessage01() {
        return new Message("问候", "你好");
    }

    @Bean("news")
    public Message getMessage02() {
        return new Message("新闻", "男篮夺冠");
    }

    @Bean("goodNight")
    public Message getMessage03() {
        return new Message("关心", "晚安");
    }

    @Bean
    @Primary
    public MessageStore getMessageStoreA(
            Message hello,
            Message news,
            Message goodNight) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(hello);
        messages.add(news);
        messages.add(goodNight);
        MessageStore messageStore = new MessageStore();
        messageStore.setMessageList(messages);
        return messageStore;
    }

    @Bean
    public MessageStore getMessageStoreB(
            Message hello,
            Message news,
            Message goodNight) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(hello);
        messages.add(news);
        messages.add(goodNight);
        MessageStore messageStore = new MessageStore();
        messageStore.setMessageList(messages);
        return messageStore;
    }
}
```

### 3.5.3 @DependsOn

在Spring之中是可以自动的实现依赖配置管理的，但是默认情况下很难明确的知道某些依赖的顺序（整个的过程都是由spring解析处理完成的），但是假设说现在必须要某个Bean在某个Bean之后进行配置，这样就需要通过一个依赖关联。

该注解的核心目的就是确定不同Beaa之间的关联问题，当前的Bean在指定的Bean之后进行初始化的处理操作。

### 3.5.4 @Conditional

- 某些Bean在满足一定的条件后才可以注册，这个时候就要通过Condition接口来定义条件处理逻辑。它允许开发者基于特定条件来有条件地注册 Bean 或组件。它是一种动态控制创建 Bean 的方式。
- 所有的Bean的注册都需要设置有一个条件，当条件满足之后才运行Bean的注册，实际上做一个很简单的分析，当前存在有某些类之后才允许你注册Bean，如果不存在就不要注册了。
- 使用 `@Conditional` 注解时，需要编写一个条件类实现 `Condition` 接口，并实现 `matches` 方法，这个方法返回一个布尔值，用于判断是否满足条件。如果返回 `true`，则会注册 Bean 或组件，否则不会注册。

```java
package com.zyf.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:StuCondition类
 **/
public class StuCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return true;
    }
}
```

```java
package com.zyf.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:
 **/
public class EmpCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return false;
    }
}
```

Config配置类

```java
package com.zyf.config;

import com.zyf.condition.EmpCondition;
import com.zyf.condition.StuCondition;
import com.zyf.pojo.Emp;
import com.zyf.pojo.Stu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.swing.plaf.PanelUI;

/**
 * @author:zhouyangfan
 * @date:2023/4/4
 * @Description:
 **/
@Configuration
public class Config {
    @Bean
    @Conditional(StuCondition.class)
    public Stu getStu(){
        return new Stu("jack");
    }


    @Bean
    @Conditional(EmpCondition.class)
    public Emp getEmp(){
        return new Emp("tom");
    }
}
```

结果表明，Emp类不能注入到IoC容器中

### 3.5.5 @Profile注解

在Spring之中如果谈到@profile，本质上指的是多个不同的运行环境设置问题。例如，在学习构建工具的时候，一般要考虑开发环境、测试环境、线上生产环境，当前的Gradle项目里面实际上也是存在有profile定义。

`@Profile` 是 Spring 框架中的一个注解，用于标记特定的 Bean 应该在哪些环境中激活。它可以与 `@Configuration`、`@Component` 或者其他带有 `@Bean` 方法的注解一起使用。

### 3.5.6 @ComponentScan

如果使用的是Annotation注解的方式进行的配置的话，一般需要定义扫描包，所以在配置文件中存在以下扫描包的配置：

```xml
<context:component-scan base-package="com.zyf"/>
```

那么如果不希望通过配置文件的方式进行扫描包的定义，也可以基于注解的模式的完成。

在config包下创建GlobalConfig全局配置类，使用@ComponentScan注解来进行包的扫描

```java
package com.zyf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author:zhouyangfan
 * @date:2023/4/5
 * @Description:全局配置类
 **/
@Configuration
@ComponentScan(basePackages = {"com.zyf.service","com.zyf.config"})
public class GlobalConfig {

}
```

### 3.5.7 Resource作用

在Spring之中表面上d额技术有两个，IoC&DI、AOP，而对于Spring内部来讲，他会有众多的核心的接口以及实现类，这些类可以为开发者提供非常方便的开发支持，如果想要理解Spring源代码，就必须对这些操作结构有所熟悉。

在世界开发中，会有各种各样的资源要进行读取，例如：文件资源、CLASSPATH资源、网络资源等等，那么为了规范化读取这些资源，在Spring的内部就提供了一个Resource接口，而且该接口还有一个继承的父接口：InputStreamSource。

```java
public interface InputStreamSource {

	InputStream getInputStream() throws IOException;

}
```

Resource接口

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230405141209559.png" alt="image-20230405141209559" style="zoom:50%;" />

整个的Resource实现的过程中，会有大量的操作的抽象，不管何种方式读取进来的文件，都可以通过getFile方法获取文件信息（文件的处理器路径以及里面的一系列操作文件），同时还得到一些基础的信息。

Spring容器会帮助开发者完成一个ResourceLoader得功能，而使用者只需要注入字符串就可以自动转为Resource接口实例。

![image-20230405142110383](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230405142110383.png)

创建一个消息读取的工具类MessageResource

```java
package com.zyf.resource;

import org.springframework.core.io.Resource;

/**
 * @author:zhouyangfan
 * @date:2023/4/5
 * @Description:消息资源读取
 **/
public class MessageResource {//消息资源的读取
    //Resource接口的实例将通过外部进行注入
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

}
```

创建一个资源文件message.txt，用来被读取

```
hello,world!
```

在配置文件中注入该类，属性resource使用一个字符串来进行配置

```xml
<!--    此时的bean不使用注解的方式配置-->
<bean id="messageResource" class="com.zyf.resource.MessageResource">
<!--此时需要配置一个字符串, 那么这个字符串会自动转为Resource接口实例-->
    <property name="resource" value="classpath:message.txt"></property>
</bean>
```

测试类

```java
package com.zyf.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@ExtendWith(SpringExtension.class)
class MessageResourceTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(MessageResourceTest.class);

    @Autowired
    public MessageResource messageResource;

    @Test
    void testPrint() throws IOException {
        LOGGER.info("Resource接口的实现类型:{}", this.messageResource.getResource().getClass());
        Scanner scanner = new Scanner(this.messageResource.getResource().getInputStream());
        scanner.useDelimiter("\n");
        while(scanner.hasNext()){
            String result = scanner.next();
            System.out.println(result);
        }
        scanner.close();
    }
}
```

输出

```
Resource接口的实现类型:class org.springframework.core.io.ClassPathResource
hello,world!
```

修改配置文件中文件路径名称

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.zyf.config"/>
    <context:annotation-config/>
<!--    此时的bean不使用注解的方式配置-->
    <bean id="messageResource" class="com.zyf.resource.MessageResource">
<!--此时需要配置一个字符串, 那么这个字符串会自动转为Resource接口实例-->
        <property name="resource" value="file:E:\javaProject\SpringTest\src\main\resources\message.txt"></property>
    </bean>
</beans>
```

```
Resource接口的实现类型:class org.springframework.core.io.FileUrlResource
hello,world!
```

修改配置文件中路径，读取网络资源

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.zyf.config"/>
    <context:annotation-config/>
<!--    此时的bean不使用注解的方式配置-->
    <bean id="messageResource" class="com.zyf.resource.MessageResource">
<!--此时需要配置一个字符串, 那么这个字符串会自动转为Resource接口实例-->
        <property name="resource" value="http://www.baidu.com"></property>
    </bean>
</beans>
```

输出

```xml
Resource接口的实现类型:class org.springframework.core.io.UrlResource
<!DOCTYPE html>
```

可以清楚的发现，只是更换了配置文件之中的字符串，就实现了不同的Resource接口实例注入，而这些操作就是Spring之中最大的亮点。



# 4. Factorybean接口

​          FactoryBean 接口是 Spring 框架中的一个重要接口，它的作用是将复杂的对象的创建和配置与 Spring IoC 容器的管理分离开来。通过实现 FactoryBean 接口，可以将对象的创建过程封装起来，Spring 容器就可以通过调用 FactoryBean 实现的 getObject() 方法来获取对象，而不用关心具体的创建过程。

通过实现 `FactoryBean` 接口，我们可以实现很多有用的功能，比如：

1. 对象的懒加载：只有在真正需要时才创建对象，而不是一开始就创建。这样可以提高系统的性能和资源利用率。
2. 对象的动态创建：根据不同的条件，动态地创建不同的对象实例。
3. 对象的复杂初始化：将对象的创建和初始化过程封装在一起，使代码更加简洁和易于维护。
4. 对象的生命周期管理：可以通过实现 `DisposableBean` 和 `InitializingBean` 接口来管理对象的初始化和销毁过程。

### 4.4.1 普通bean和工厂bean

**普通Bean**

​        普通 Bean 是在 Spring 容器启动时由容器自动创建和初始化的。普通 Bean 的创建和初始化过程完全由 Spring 容器控制，我们只需要在配置文件或者代码中定义好 Bean 的属性和依赖关系，Spring 容器就会自动创建和管理 Bean 的生命周期。

**工厂Bean**

​        工厂 Bean 则是由实现了 FactoryBean 接口的类来创建和管理的。工厂 Bean 本身并不是我们想要的最终 Bean，而是用来创建其他 Bean 的。工厂 Bean 的主要作用是封装复杂的 Bean 创建过程，将 Bean 的创建和配置与IoC 容器的管理分离开来。我们可以通过实现 FactoryBean 接口，并重写 `getObject()`、`getObjectType()` 和 `isSingleton()` 方法来创建和管理工厂 Bean。

### 4.4.2 FactoryBean 接口使用



# 5 SpringBean的作用域

1. 单例的作用域：每次在调用getbean方法获取对象都是为同一个对象。
2. 多例的作用域：每次在调用getbean方法获取对象都是一个新的对象。

在Spring的默认情况下，Bean的作用域就是为单例，节约服务器资源。


单例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
<!--bean的id是不允许重复的
bean的id命名格式一般为类名称首字母小写-->
    <bean id="userEntity" class="com.zyf.entity.UserEntity" scope="singleton"></bean>

</beans>
```

多例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
<!--bean的id是不允许重复的
bean的id命名格式一般为类名称首字母小写-->
    <bean id="userEntity" class="com.zyf.entity.UserEntity" scope="prototype"></bean>

</beans>
```

# 6 SpringBean的生命周期

1. 使用反射技术初始化该对象（无参构造函数）。
2. 给该对象中的属性赋值--反射技术的实现。
3. 调用bean设定init方法。
4. 正常使用到该bean对象
5. 容器关闭--该对象就会被销毁，执行bean对象中的销毁方法。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="memberEntity" class="com.zyf.entity.MemberEntity" init-method="init"
    destroy-method="destroy">
        <property name="name" value="zyf"></property>
    </bean>
</beans>
```

```java
package com.zyf.entity;

/**
 * @author: 周杨凡
 * @date: 2023-02-11
 */
public class MemberEntity {
    private String name;

    public MemberEntity() {
        System.out.println("[第一步流程:]执行MemberEntity的无参构造");
    }

    public void setName(String name) {
        System.out.println("[第二步流程:]执行setName方法");
        this.name = name;
    }

    public void init(){
        System.out.println("[第三步流程:]执行init方法");
    }

    public void destroy(){
        System.out.println("[第五步流程:]执行销毁方法");
    }
}
```

Test

```java
import com.zyf.entity.MemberEntity;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 周杨凡
 * @date: 2023-02-11
 */
public class Test08 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring01.xml");
        MemberEntity memberEntity = (MemberEntity) app.getBean("memberEntity");
        System.out.println("[第四步流程:]使用bean对象" + memberEntity);
        app.close();
    }
}
```

输出

```
[第一步流程:]执行MemberEntity的无参构造
[第二步流程:]执行setName方法
[第三步流程:]执行init方法
[第四步流程:]使用bean对象com.zyf.entity.MemberEntity@12d4bf7e
[第五步流程:]执行销毁方法
```

# 7 后置处理器

**bean的后置处理器在init方法之前执行，在init方法之后执行。**

首先创建一个类，实现BeanPostProcessor接口

```java
package com.zyf.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author: 周杨凡
 * @date: 2023-02-11
 */
public class MyZyfPostProcessor implements BeanPostProcessor{
    /**
     * 调用init方法之前处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[后置处理调用init方法之前执行操作]");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    /**
     * init方法之后执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[后置处理调用init方法之前执行操作]");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="memberEntity" class="com.zyf.entity.MemberEntity" init-method="init"
    destroy-method="destroy">
        <property name="name" value="zyf"></property>
    </bean>

    <bean id="myZyfPostProcessor" class="com.zyf.bean.MyZyfPostProcessor"></bean>
</beans>
```

配置文件中的bean都会执行后置处理器。

**配置多个后置处理器，使用Ordered接口**

```java
package com.zyf.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * @author: 周杨凡
 * @date: 2023-02-11
 */
public class MyZyfPostProcessor01 implements BeanPostProcessor, Ordered {
    /**
     * 调用init方法之前处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[后置处理调用init方法之前执行操作]");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    /**
     * init方法之后执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[后置处理调用init方法之前执行操作]");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
```

```java
package com.zyf.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author: 周杨凡
 * @date: 2023-02-11
 */
public class MyZyfPostProcessor02 implements BeanPostProcessor, Ordered {
    /**
     * 调用init方法之前处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[后置处理器2][后置处理调用init方法之前执行操作]");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    /**
     * init方法之后执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[后置处理器2][后置处理调用init方法之前执行操作]");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
```

getOrder方法返回值越小，后置处理器越先执行。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="memberEntity" class="com.zyf.entity.MemberEntity" init-method="init"
    destroy-method="destroy">
        <property name="name" value="zyf"></property>
    </bean>

    <bean id="myZyfPostProcessor01" class="com.zyf.bean.MyZyfPostProcessor01"></bean>
    <bean id="myZyfPostProcessor02" class="com.zyf.bean.MyZyfPostProcessor02"></bean>
</beans>
```

# 8. AOP面向切面编程

1. AOP是面向切面编程，在方法之前和之后实现处理和增强。
2. 应用场景：日志打印，事务实现，安全，权限控制、自定义注解等。
3. AOP可以解决我们程序上的代码冗余问题。
4. AOP底层基于代理设计模式。

代理设计模式：

- 静态代理
- 动态代理
  1. jdk动态代理
  2. cglib动态代理

### 代理设计模式

应用场景：

1. 日志采集
2. 权限控制
3. 实现aop
4. Mybatis mapper
5. Spring的事务
6. 全局异常捕获
7. Rpc的远程调用接口
8. 代理数据源

### 代理模式实现原理

1. 三个角色：抽象主题角色、委托类角色、代理类角色。
2. 抽象主题角色：可以是接口，也可以是抽象类
3. 委托类角色：真实主题角色，业务逻辑的具体执行者
4. 代理类角色：内部含有对真实对象RealSubject的引用，负责对真实主题角色的调用，并在真实主题角色处理前后做预处理和后处理。

#### 静态代理

代理类角色：

```java
package com.zyf.proxy;

import com.zyf.service.OrderService;
import lombok.extern.slf4j.Slf4j;


/**
 * @author: 周杨凡
 * @date: 2023-02-12
 */
@Slf4j
public class OrderServiceProxy implements OrderService{
    private OrderService orderService;

    public OrderServiceProxy(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String addOrder(String orderName) {
        log.info("<在addorder方法之前处理 orderNname:{}>", orderName);
        String s = orderService.addOrder(orderName);
        System.out.println("执行order方法" + s);
        log.info("<在addorder方法之后处理 orderNname:{}>", orderName);
        return s;
    }
}
```

抽象主题角色

```
package com.zyf.service;

/**
 * @author: 周杨凡
 * @date: 2023-02-12
 */
public interface OrderService {
    String addOrder(String orderName);
}
```

委托类角色

```java
package com.zyf.service;

/**
 * @author: 周杨凡
 * @date: 2023-02-12
 */
public class OrderServiceImpl implements OrderService{
    @Override
    public String addOrder(String orderName) {
        return orderName;
    }
}
```

静态代理需要开发者编写代理类，代码非常冗余，此时需要动态代理。

#### 动态代理

##### jdk动态代理

**jdk动态代理生成代理类，实现被代理实现的接口**

**JDK动态代理的一般步骤：**

1. 创建被代理的接口和类。
2. 实现InvocationHandler接口，对目标接口声明的所有方法进行统一处理。
3. 调用Proxy的静态方法，创建代理类并生成相应的代理对象。

**实现原理**：利用拦截器机制必须实现InvacationHandler接口中的invoke方法实现对我们的目标方法的增强。

```java
package com.zyf.proxy02;

import com.zyf.service.OrderService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: 周杨凡
 * @date: 2023-02-13
 */
@Slf4j
public class Zyfproxy02  {
    public static OrderService createProxy(OrderService orderService){
        OrderService orderServiceProxy = (OrderService)Proxy.newProxyInstance(Zyfproxy02.class.getClassLoader(),
                new Class[]{OrderService.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        log.info("<目标方法之前执行...args:{}>", args);
                        Object rs = method.invoke(orderService, args);
                        System.out.println(rs);
                        log.info("<目标方法之后执行...args:{}>", args);
                        return rs;
                    }
                });
        return orderServiceProxy;
    }
}
```

接口

```
package com.zyf.service;

/**
 * @author: 周杨凡
 * @date: 2023-02-12
 */
public interface OrderService {
    String addOrder(String orderName);
}
```

接口的实现类

```
package com.zyf.service;

/**
 * @author: 周杨凡
 * @date: 2023-02-12
 */
public class OrderServiceImpl implements OrderService{
    @Override
    public String addOrder(String orderName) {
        return orderName;
    }
}
```



##### cglib动态代理

- 直接采用建立fastclass索引的方式调用目标方法。
- jdk7之前Cglib动态代理效率比jdk动态代理效率高很多。
- jdk7之后jdk动态代理比Cglib动态代理效率高。
- cglib底层生成代理类是通过asm生成字节码class。

### AOP

1. 连接点：在该类中哪些方法需要被增强，该方法就可以称为连接点。
2. 通知：在方法前后执行的代码。
   - 前置通知：调用方法之前做处理
   - 后置通知：调用完该方法之后的处理
   - 环绕通知：被代理的方法前后做处理，前置与后置通知做结合
   - 异常通知：方法抛出异常就会执行异常通知
   - 最终通知
3. 切点：实际被增强的方法。
4. 切面：通知应用的过程被称为切面。

#### AOP环境准备

1. Spring框架一般基于AspectJ实现AOP操作，AspectJ是一个面向切面的框架。
2. 一般把AspectJ和Spring框架一起使用，进行AOP操作。
3. 配置：基于xml配置文件实现和注解方法实现。
4. AOP项目工程依赖。

```xml
        <!-- aspectj支持 -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.8.9</version>
        </dependency>
        <dependency>
            <groupId>org.apache.geronimo.bundles</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.6.8_2</version>
        </dependency>
         <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.2.1.RELEASE</version>
        </dependency>
```

#### 切入点表达式

- 需要**描述**具体类中的哪个方法需要来实现增强

- execution(【权限修饰符】【返回类型】【类全路径】【方法名称】(【参数列表】))

  ```
  1.public.String.com.mayikt.service.MayiktService.addUser(..)  
  拦截的是MayiktService类中addUser方法名称 所有参数  返回值String
  
  2.* com.mayikt.service.MayiktService.*（..）拦截我们的
  MayiktService类中的所有方法
  
  3.* com.mayikt.service.*.*（..）拦截就是我们 com.mayikt.service.包下
  的所有的类所有的方法。
  ```

  

  **前置和后置通知**

```JAVA
package com.zyf.service;

import org.springframework.stereotype.Component;

/**
 * @author: 周杨凡
 * @date: 2023-02-15
 */
@Component
public class StuService {
    public String addStu(String name){
        System.out.println("执行addStu方法");
        return name;
    }

    public void delStu(String name){
        System.out.println("执行delStu方法");
    }

    public void updateStu(String name){
        System.out.println("执行updateStu方法");
    }
}
```

```JAVA
package com.zyf.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author: 周杨凡
 * @date: 2023-02-15
 */
@Component
@Aspect
public class StuServiceProxy {
    @Before(value = "execution(* com.zyf.service.StuService.*(..));")
    public void before() {
        System.out.println("前置通知..");
    }

    @After(value = "execution(* com.zyf.service.StuService.*(..));")
    public void after() {
        System.out.println("后置通知..");
    }

    @Around(value = "execution(* com.zyf.service.StuService.*(..));")
    public Object around(ProceedingJoinPoint p) throws Throwable {
        System.out.println("环绕通知调用目标方法之前");
        Object rs = p.proceed();
        System.out.println("环绕通知调用目标方法之后");
        return rs;
    }

    @AfterReturning(value = "execution(* com.zyf.service.StuService.*(..));",
            returning = "res")
    public void afterReturning(Object res) {
        System.out.println("AfterReturning:" + res);
    }

    @AfterThrowing(value = "execution(* com.zyf.service.StuService.*(..));")
    public void afterThrowing(){
        System.out.println("AfterThrowing");
    }
}
```



```JAVA
import com.zyf.service.StuService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 周杨凡
 * @date: 2023-02-15
 */
public class Test12 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("spring02.xml");
        StuService stuService = classPathXmlApplicationContext.getBean("stuService", StuService.class);
        String zyf = stuService.addStu("zyf");
    }
}
```

- 环绕通知在前置通知和后置通知之前执行。
- AfterReturning通知接收主方法返回的值作为参数。
- AfterThrowing通知当方法抛出异常时，发出通知。

# 9. SpringJDBC

## 9.1 JDBC

JDBC的连接：

- JDBC-ODBC的连接（不再使用）
- JDBC网络连接模式

JDBC的开发之中，一定要配置相应的数据库的驱动程序后才可以使用。无论什么样的Java数据库开发框架，核心本质就是JDBC。可是JDBC标准里面的所定义的操作结构是属于较为底层的操作形式，所以使用起来非常繁琐，所有项目都要加载驱动、创建数据库、创建连接、创建数据库的操作对象、关闭数据路连接等，只有CRUD操作是有区别的，那么就要考虑对JDBC的封装，那么这是时候就有了ORMapping组件（采用对象的形式实现JDBC的开发操作）。

ORMapping组件：

- JDO
- Entity Bean
- Hibernate
- IBatis
- SpringJDBC（JDBC的轻度包装组件）
- MyBatis（国内常用，重度包装）
- JPA（国外常用，重度包装）

JDBC vs JdbcTemplate

按照性能来讲，使用JDBC开发性能是最高的。

## 9.2 配置数据源

jdk提供了DataSource规定了连接数据库的规范，Spring提供了实现了DataSource接口的子类来创建数据库连接池（数据源），在这里我们使用DriverMangerDataSource这一个实现子类，位于`package org.springframework.jdbc.datasource`，我们将DriverMangerDataSource注入到Spring容器，让Spring来进行管理。DriverMangerDataSource需要jdbc连接到mysql的驱动（mysql-connector包中），url、username和password。

将DataSource注入到Spring容器中

```java
package com.zyf.jdbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author:zhouyangfan
 * @date:2023/4/1
 * @Description:配置数据源
 **/
@Configuration //配置类
public class DataSourceConfig {
    @Bean("dataSource")
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/blog");
        dataSource.setUsername("root");
        dataSource.setPassword("zyf2563085");
        return dataSource;
    }
}
```

测试类

```java
package com.zyf.jdbc.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

@ContextConfiguration(classes = {DataSourceConfig.class})
@ExtendWith(SpringExtension.class) //Junit5测试
class TestDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataSource.class);

    @Autowired
    private DataSource dataSource;

    @Test
    void test01() throws SQLException {
        LOGGER.info("数据库连接对象:{}", this.dataSource.getConnection());
    }
}
```

输出：

表明数据库连接成功成功

```
INFO com.zyf.jdbc.config.TestDataSource -- 数据库连接对象:com.mysql.cj.jdbc.ConnectionImpl@7a34b7b8
```

使用`@ContextConfiguration`注解可以帮助测试类使用正确的Spring上下文配置，确保测试类能够使用正确的bean以及正确的环境配置来运行测试用例。在测试中，`@ContextConfiguration`注解通常与`@RunWith`注解一起使用，以指定测试类使用哪种测试运行器并指定测试类所需的Spring上下文配置信息。

## 9.3 HikariCP数据库连接池

我们之前在5.2中使用DriverMangerDataSource获取了数据库的连接，但这种方式连接数据库的性能较差。这种连接的方式是在每一次获取连接的时候才进行数据库的连接操作。在数据库连接的处理之中，一定会建立若干个Socket连接，那么会有耗时，而在数据库关闭的时候也会存在有同样的耗时处理，所以在实际的项目之中最佳的数据库连接方式一定是通过连接池实现的。可以考虑在Spring内部去实现一个连接池的维护，Spring默认推荐的数据库连接池组件就是HikariCP，不建议再使用其他的数据库连接池组件。

**配置HikariCP**

datasource.properties

```properties
zyf.database.driverClassName=com.mysql.cj.jdbc.Driver
zyf.database.jdbcUrl=jdbc:mysql://localhost:3306
zyf.database.username=root
zyf.database.password=zyf2563085
#【HikariCP】配置当前数据是否只读
zyf.database.readOnly=false
#【HikariCP】配置数据库连接超时时间(毫秒)
zyf.database.connectionTimeOut=3000
#【HikariCP】配置一个连接最小的维持时间长度
zyf.database.pool.idleTimeOut=3000
#【HikariCP】配置一个连接最长的存活时间
zyf.database.pool.maxLifetime=6000
#【HikariCP】配置一个连接池最大连接对象数量
zyf.database.pool.maximumPoolSize=60
#【HikariCP】数据库没有任何用户访问时，最少维持20个数据库连接
zyf.database.pool.minimumIdle=20
```

配置类

```java
package com.zyf.jdbc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author:zhouyangfan
 * @date:2023/4/1
 * @Description:配置数据源
 **/
@PropertySource("classpath:/datasource.properties")
@Configuration //配置类
public class DataSourceConfig {
    @Value("${zyf.database.driverClassName}")
    private String driverClassName;
    @Value("${zyf.database.jdbcUrl}")
    private String jdbcUrl;
    @Value("${zyf.database.username}")
    private String username;
    @Value("${zyf.database.password}")
    private String password;
    @Value("${zyf.database.readOnly}")
    private Boolean readOnly;
    @Value("${zyf.database.connectionTimeOut}")
    private long connectionTimeOut;
    @Value("${zyf.database.pool.idleTimeOut}")
    private long idleTimeOut;
    @Value("${zyf.database.pool.maxLifetime}")
    private long maxLifetime;
    @Value("${zyf.database.pool.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${zyf.database.pool.minimumIdle}")
    private int minimumIdle;

    @Bean("dataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setJdbcUrl(this.jdbcUrl);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setReadOnly(this.readOnly);
        dataSource.setConnectionTimeout(this.connectionTimeOut);
        dataSource.setIdleTimeout(this.idleTimeOut);
        dataSource.setMaxLifetime(this.maxLifetime);
        dataSource.setMaximumPoolSize(this.maximumPoolSize);
        dataSource.setMinimumIdle(this.minimumIdle);
        return dataSource;
    }
}
```

测试类

```java
package com.zyf.jdbc.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

@ContextConfiguration(classes = {DataSourceConfig.class})
@ExtendWith(SpringExtension.class) //Junit5测试
class TestDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataSource.class);

    @Autowired
    private DataSource dataSource;

    @Test
    void test01() throws SQLException {
        LOGGER.info("数据库连接对象:{}", this.dataSource.getConnection());
    }
}
```

## 9.4 JdbcTemplate

JdbcTemplate是Spring框架中的一个核心组件，它是一个简单的JDBC封装类，用于简化JDBC编程。它提供了一组简单的方法，允许您通过使用占位符参数和自动转换来执行SQL查询、更新和批处理。

JdbcTemplate之中可以通过其继承的jdbcAccessor父类实现DataSource接口实例的配置，同时考虑到JDBC开发之中数据源是一个核心话题，所以会在InitializingBean初始化操作后进行检查，如果发现当没有DataSource实例就抛出异常。

JdbcTemplate配置

```java
package com.zyf.jdbc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * @author:zhouyangfan
 * @date:2023/4/1
 * @Description:springTemplate配置类
 **/
@Configuration
public class SpringJDBCConfig {

    @Bean("jdbcTemplate")
    public JdbcTemplate createJdbcTemplate(@Autowired DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);//设置数据源
        return jdbcTemplate;
    }
}
```

单元测试

```java
package com.zyf.jdbc.config;

import com.zyf.jdbc.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

@ContextConfiguration(classes = {DataSourceConfig.class, SpringJDBCConfig.class})
@ExtendWith(SpringExtension.class) //Junit5测试
class TestDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataSource.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    

    @Test
    void test() throws Exception {
        String sql = "select * from blog.book where id = ?";
        Book book = jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(Book.class), 2);
        LOGGER.info("查询到的对象:{}", book);
    }
}
```

输出

```
INFO com.zyf.jdbc.config.TestDataSource -- 查询到的对象:Book{id=2, name='红楼梦'}
```

其他的JdbcTemplate细节到springboot中学习。

# 10. 事务

事务是指一系列数据库操作（如增、删、改），这些操作要么全部成功提交，要么全部失败回滚，以保证数据的一致性和完整性。

**事务ACID原则：**

1. **原子性：**指一个事务中的所有操作要么全部执行成功，要么全部失败回滚，不会只执行其中的一部分操作。事务中的操作是不可分割的，要么全部执行，要么全部不执行，这样可以保证数据的完整性和一致性。
2. **一致性：**指事务执行前后，数据库中的数据必须满足某种约束条件，例如唯一性约束、外键约束等。在事务开始和结束时，数据的完整性必须保持一致，这可以通过数据库的约束条件和触发器来实现。
3. **隔离性：**指多个事务并发执行时，每个事务都应该与其他事务相互隔离，不会互相干扰。每个事务在执行时必须独立运行，不受其他事务的干扰。事务的隔离级别包括未提交读、已提交读、可重复读和串行化等级别。
4. **持久性：**指一个事务提交之后，它所做的修改会永久保存在数据库中，即使系统出现故障也不会丢失。持久性可以通过将数据写入磁盘或其他可靠的存储介质来实现。

## 6.1 JDBC中的事务

不管现在使用的是哪一种ORM开发框架，只要核心是JDBC，那么所有的事务处理都是围绕着JDBC开展的。在JDBC中，事务是在Connection对象上进行管理的，一个Connection对象代表一个与数据库的连接。JDBC中的事务由以下三个步骤组成：

- **关闭自动事务提交**：connection.setAutoCommit(false)
- **事务手动提交**：connection.commit()
- **事务回滚**：connection.rollback()

JDBC还支持事务的保存点（Savepoint），可以在事务执行过程中设置保存点，以便在回滚时能够回滚到指定的保存点。

## 6.2 Spring事务的处理框架

​        Spring事务是对已有JDBC事务的进一步的包装型处理，所以底层依然是JDBC事务控制，而后在这之上进行了更加合理的而开发设计。

只要说到了事务的开发，那么就必须要考虑ORM组件的整合问题，SpringJDBC是属于一种ORM开发组件，但是世界上各类ORM开发组件太多，同时Spring在设计的时候无法预知未来，那么在Spring框架里面就针对事务的接入提供了一个**开发标准**。

Spring事务的核心实现关键是在于：`PlatformTransactionManager`接口

```java
package org.springframework.transaction;

import org.springframework.lang.Nullable;

public interface PlatformTransactionManager extends TransactionManager {
    TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;

    void commit(TransactionStatus status) throws TransactionException;

    void rollback(TransactionStatus status) throws TransactionException;
}
```

`void commit(TransactionStatus status)`：**事务的提交**

`void rollback(TransactionStatus status)`：**事务的回滚**

`TransactionStatus getTransaction`：**获取事务的状态**

通过代码可以发现，PlatformTransactionManager接口存在一个TransactionManager的父接口，是Spring5.2之后提供新接口。

```JAVA
package org.springframework.transaction;

public interface TransactionManager {
}
```

在现代的开发过程中，最为核心的事务接口主要使用的是`PlatformTransactionManager`，在Spring最早出现声明式事务的时候，就有这个处理接口了。

在进行获取事务的时候可以发现getTransaction()方法内部需要有接收一个TransactionDefinition接口实例，这个接口主要定义了Spring事务的超时时间，以及Spring事务的传播属性（面试的关键所在）

**TransactionDefinition接口**

```java
public interface TransactionDefinition {
    int PROPAGATION_REQUIRED = 0;
    int PROPAGATION_SUPPORTS = 1;
    int PROPAGATION_MANDATORY = 2;
    int PROPAGATION_REQUIRES_NEW = 3;
    int PROPAGATION_NOT_SUPPORTED = 4;
    int PROPAGATION_NEVER = 5;
    int PROPAGATION_NESTED = 6;
    int ISOLATION_DEFAULT = -1;
    int ISOLATION_READ_UNCOMMITTED = 1;
    int ISOLATION_READ_COMMITTED = 2;
    int ISOLATION_REPEATABLE_READ = 4;
    int ISOLATION_SERIALIZABLE = 8;
    int TIMEOUT_DEFAULT = -1;
}
```

而在getTransaction()方法内部会返回一个TransactionStatus接口实例，其中有一个方法为判断是否有事务保存点，而后该接口内部定义的时候又需要继承TransactionExecution, SavepointManager, Flushable三个父接口。

**TransactionStatus接口**

```java
public interface TransactionStatus extends TransactionExecution, SavepointManager, Flushable 
{
    boolean hasSavepoint(); //事务保存点

    void flush(); //事务刷新
}
```

**TransactionExecution接口**

```java
public interface TransactionExecution {
    boolean isNewTransaction();//是否为新的事务

    void setRollbackOnly(); //设置是否允许回滚

    boolean isRollbackOnly(); //判断是否允许回滚

    boolean isCompleted(); //是否处理完成
}
```

上图可以看出，Spring中提供了以上接口规定了事务实现的标准，各个框架需要提供相应的子类去实现Spring事务的接口，引入指定的事务操作。

## 6.3 编程式的事务控制

PlatformTransactionManager接口是Spring框架中的一个核心接口，它定义了事务管理器的行为和功能。PlatformTransactionManager的作用是提供一个**统一的接口**来管理事务，使得应用程序可以在不同的事务管理器之间切换而不需要修改应用程序的代码。

PlatformTransactionManager接口定义了以下几个方法：

1. beginTransaction()：开启一个新的事务，并返回一个TransactionStatus对象，该对象包含了事务的状态信息。
2. commit()：提交事务，并将事务的结果持久化到数据库中。
3. rollback()：回滚事务，并撤销所有未提交的操作。
4. getTransaction()：获取当前正在运行的事务。
5. isExistingTransaction()：判断当前是否有事务正在进行。
6. suspend()：暂停当前的事务，并返回一个Object对象，表示当前事务的状态。
7. resume()：恢复之前暂停的事务。

Spring框架中提供了多个实现PlatformTransactionManager接口的类，包括JdbcTransactionManager、HibernateTransactionManager等。应用程序可以根据自己的需求选择不同的事务管理器实现类。

**Spring事务的配置**

```java
package com.zyf.jdbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author:zhouyangfan
 * @date:2023/4/1
 * @Description:将事务管理器注入IoC容器
 **/
@Configuration
public class TransactionConfig {
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        //PlatformTransactionManager是一个事务控制的标准，而后不同的数据库组件需要实现该标准。
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
```

实现事务

```java
package com.zyf.jdbc.config;

import com.zyf.jdbc.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@ContextConfiguration(classes = {DataSourceConfig.class, SpringJDBCConfig.class, TransactionConfig.class})
@ExtendWith(SpringExtension.class) //Junit5测试
class TestDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataSource.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private PlatformTransactionManager transactionManager;



    @Test
    void test02() throws Exception {
        String sql = "INSERT INTO book (id, name) VALUES (?,?)";
        TransactionStatus status = this.transactionManager.
                getTransaction(new DefaultTransactionDefinition());
      //开启事务
        try {
            this.jdbcTemplate.update(sql, 3, "Spring开发实战");
            this.jdbcTemplate.update(sql, 2, null);
            this.jdbcTemplate.update(sql, 4, "java编程思想");
            this.transactionManager.commit(status);
            LOGGER.debug("数据库更新提交");
        } catch (DataAccessException e) {
            this.transactionManager.rollback(status);
            LOGGER.error("数据库更新错误:{}", e.getMessage());
        }
    }
```

总结：

- 开启JDBC事务

```java
TransactionStatus status = this.transactionManager.
                getTransaction(new DefaultTransactionDefinition());
```

- 提交JDBC事务

```java
transactionManager.commit(status);
```

- 回滚JDBC事务

```java
transactionManager.rollback(status);
```

## 6.4 TransactionStatus

transactionManager提交和回滚的处理方法，仅仅是Spring提供的事务处理的皮毛，而如果要深入理解事务处理的特点，那么就需要分析其中每一个核心的组成类，首先分析的就是TransactionStatus。

在开启事务的时候会返回一个TransactionStatus接口实例，而后在提交或回滚事务的时候需要针对于指定的status实例进行处理。

- DefaultTransactionStatus是TransactionStatus默认的实现子类，而后该类并不是直接实例化的，而是通过事务管理器负责实例化处理的，status所得到的是一个事务的处理标记，而后依照此标记管理事务。
- TransactionStatus实例的创建代表了事务的开启。
- 事务的完成决定于commit()或rollback()的方法是否正常执行，正常执行了就表示事务处理完成，相反，未执行的时候事务一定是未完成的。

下面这段代码中，status设置了只能回滚，在提交事务后，依然会进行回滚。

```java
  @Test
  void test02() throws Exception {
      String sql = "INSERT INTO book (id, name) VALUES (?,?)";
      TransactionStatus status = this.transactionManager.
              getTransaction(new DefaultTransactionDefinition());
      status.setRollbackOnly();//只能回滚
      LOGGER.info("【事务执行前】当前事务是否开启:{}、当前事务是否完成{}",
              status.isNewTransaction(),status.isCompleted());
      try {
          this.jdbcTemplate.update(sql, 3, "Spring开发实战");
          LOGGER.info("【commit执行前】当前事务是否开启:{}、当前事务是否完成{}",
                  status.isNewTransaction(),status.isCompleted());
          this.transactionManager.commit(status);
          LOGGER.info("【commit执行后】当前事务是否开启:{}、当前事务是否完成{}",
                  status.isNewTransaction(),status.isCompleted());
      } catch (DataAccessException e) {
          this.transactionManager.rollback(status);
          LOGGER.error("数据库更新错误:{}", e.getMessage());
      }
      LOGGER.info("【事务执行后】当前事务是否开启:{}、当前事务是否完成{}",
              status.isNewTransaction(),status.isCompleted());
  }
```

输出结果如下

```java
【事务执行前】当前事务是否开启:true、当前事务是否完成false
【commit执行前】当前事务是否开启:true、当前事务是否完成false
【commit执行后】当前事务是否开启:true、当前事务是否完成true
【事务执行后】当前事务是否开启:true、当前事务是否完成true
```

在Spring框架中，当事务被标记为rollback-only后，事务提交时虽然会执行提交操作，但最终会将提交的操作回滚掉，也就是说，事务提交后仍然能够回滚。Spring框架内部通过TransactionSynchronizationManager来管理事务状态，当事务被标记为rollback-only时，Spring会在提交之前进行判断，如果发现事务已经被标记为rollback-only，那么在提交事务之后会自动执行回滚操作。这种设计是为了防止提交后不能回滚的情况出现，即使在提交后事务仍然可以回滚，从而保证了事务的一致性和可靠性。

**在jdbc模块中，如果产生了异常默认是回滚到最初状态的，当然在一个庞大的更新操作业务之中，有可能只希望回滚到指定的更新语句上，所以就需要引入savepoint。**

```java
  @Test
  void test02() throws Exception {
      String sql = "INSERT INTO book (id, name) VALUES (?,?)";
      TransactionStatus status = this.transactionManager.
              getTransaction(new DefaultTransactionDefinition());
      Object savePointA = null;
      LOGGER.info("【事务执行前】当前事务是否开启:{}、当前事务是否完成{}",
              status.isNewTransaction(),status.isCompleted());
      try {
          this.jdbcTemplate.update(sql, 3, "Spring开发实战");
          savePointA = status.createSavepoint();
          this.jdbcTemplate.update(sql, null, null);
          LOGGER.info("【commit执行前】当前事务是否开启:{}、当前事务是否完成{}",
                  status.isNewTransaction(),status.isCompleted());
          this.transactionManager.commit(status);
          LOGGER.info("【commit执行后】当前事务是否开启:{}、当前事务是否完成{}",
                  status.isNewTransaction(),status.isCompleted());
      } catch (DataAccessException e) {
          status.rollbackToSavepoint(savePointA);
          this.transactionManager.commit(status);
          LOGGER.error("数据库更新错误:{}", e.getMessage());
      }
      LOGGER.info("【事务执行后】当前事务是否开启:{}、当前事务是否完成{}",
              status.isNewTransaction(),status.isCompleted());
  }
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230402094732554.png" alt="image-20230402094732554" style="zoom: 50%;" />

## 6.5 事务隔离级别

- Spring面试之中的事务隔离级别的面试问题最为常见，也是一个核心的基础所在，但是所谓的隔离级别一定要记住，是并发环境访问数据库才会存在的问题。
- 数据库是一个项目中应用中的公共存储资源，所以在实际的项目开发过程中，很有可能会有两个不同的线程（每个线程拥有各自的数据库事务），要进行同一条数据的读取以及更新的操作。
- 为了保证数据在更新的时候的正确性，那么就要对数据的同步进行有效的管理，而这就属于数据库隔离级别的概念了。

### 6.5.1 MySQL中的事务隔离级别

多个连接开启各自事务操作数据库中数据时，数据库系统要负责隔离操作，以保证各个连接在获取数据时的准确性。

1. **READ UNCOMMITTED（读未提交）**：在该级别下，一个事务可以读取另一个事务未提交的数据。该级别会导致脏读、不可重复读和幻读的问题。
2. **READ COMMITTED（读已提交**）：在该级别下，一个事务只能读取另一个事务已经提交的数据。这种隔离级别可以避免脏读问题，但是不可重复读和幻读问题仍然存在。
3. **REPEATABLE READ（可重复读）**：在该级别下，一个事务在执行期间看到的所有数据都是一致的，即使其他事务进行了更新操作。这种隔离级别可以避免脏读和不可重复读问题，但是幻读问题仍然存在。
4. **SERIALIZABLE（串行化）**：在该级别下，所有事务串行执行，每个事务都必须等待前一个事务完成才能执行。这种隔离级别可以避免所有的并发问题，但是对性能有很大的影响。

**什么是脏读、不可重复读和幻读？**

- **脏读**：MySQL中脏读是指一个事务在未提交时，另一个事务读取到了该事务未提交的数据，从而产生了不一致的结果。也就是说，脏读是指读取了未经确认的数据，**这些数据可能会被回滚**，因此读取到的结果可能是错误的。
- **幻读**：MySQL中幻读是指一个事务在读取某个范围的数据时，另一个事务插入了一条新的记录，从而导致前一个事务再次读取相同的范围时，出现了新插入的记录，从而产生了不一致的结果。
- **不可重复读**：是指在同一事务中，读取同一个数据，但在事务执行期间，另一个事务修改了该数据，导致前一个事务再次读取该数据时，读取到了不同的结果，从而出现了数据不一致的情况。

在控制台中演示隔离级别和脏读、幻读和不可重复读。首先创建一个account表（id、name和money），表中已经有三条数据。

控制台开启两个客户端程序分别为A客户端和B客户端

**演示脏读：**

查看当前mysql的隔离级别，`SELECT @@tx_isolation`，结果为可重复读

```
mysql> SELECT @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
1 row in set, 1 warning (0.00 sec)
```

把B控制台的隔离级别设置为读未提交

```
SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
```

A和B两个客户端都启动事务

```
 start transaction;
```

A客户端添加一条数据，并且事务没有进行提交。

```
insert into account values(4,'王五',400);
```

B客户端执行查询

```
mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   400 |
+----+-----------+-------+
4 rows in set (0.00 sec)
```

可以看到，B客户端查询到了A客户端添加的数据（未提交事务），这就是脏读。

我们打开C客户端，来验证结果，可以看到当隔离状态为可重复读时，并没有id为4的数据，因此不会发生脏读。

```
mysql> use sw
Database changed
mysql> SELECT @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
1 row in set, 1 warning (0.00 sec)

mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
+----+-----------+-------+
3 rows in set (0.00 sec)
```

**演示幻读：**

查看B客户端的隔离级别以及修改B客户端的隔离级别

```
mysql> use sw
Database changed
mysql> SELECT @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
1 row in set, 1 warning (0.00 sec)

mysql>  SET SESSION  TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
Query OK, 0 rows affected (0.00 sec)
```

A客户端的隔离级别

```
mysql> use sw
Database changed
mysql> SELECT @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
1 row in set, 1 warning (0.00 sec)
```

AB客户端都开启事务

```
mysql> start transaction;
Query OK, 0 rows affected (0.00 sec)
```

B客户端中查询account表中数据，发现有三条数据

```
mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
+----+-----------+-------+
3 rows in set (0.02 sec)
```

A客户端中，插入一条数据，并提交

```
mysql> insert into account values(4, '王五', 500);
Query OK, 1 row affected (0.00 sec)

mysql> commit;
Query OK, 0 rows affected (0.03 sec)
```

B客户端再一次查询，可以看到B客户端中在一个事务中，查询相同的数据，查询结果不同，这就是发生了幻读。

```
mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   500 |
+----+-----------+-------+
4 rows in set (0.00 sec)
```

修改B客户端的隔离级别为可重复读

```
mysql> SET SESSION  TRANSACTION ISOLATION LEVEL REPEATABLE READ;
Query OK, 0 rows affected (0.00 sec)

mysql> SELECT @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
1 row in set, 1 warning (0.00 sec)
```

AB客户端都开启事务

```
mysql> start transaction;
Query OK, 0 rows affected (0.00 sec)
```

B客户端执行查询

```
mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   500 |
+----+-----------+-------+
4 rows in set (0.00 sec)
```

A客户端插入一条记录，并进行提交事务

```
mysql>  insert into account values(5, '赵六', 600);
Query OK, 1 row affected (0.00 sec)

mysql> commit;
Query OK, 0 rows affected (0.03 sec)
```

B客户端执行相同的查询，可以看到，虽然A已经提交事务，数据库已经被修改，但B客户端查询到的结果与第一次相同，这样就是避免了幻读。

```
mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   500 |
+----+-----------+-------+
4 rows in set (0.00 sec)
```

我们提交B事务，再次进行相同的查询，可以证明我们思路的正确。

```
mysql> commit;
Query OK, 0 rows affected (0.00 sec)

mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   500 |
|  5 | 赵六      |   600 |
+----+-----------+-------+
5 rows in set (0.00 sec)
```

**演示不可重复读：**

查看AB客户端的隔离级别，可以看到都是可重复读。

```
mysql> SELECT @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
1 row in set, 1 warning (0.00 sec)
```

将B客户端的隔离级别改为读未提交

```
mysql> SET SESSION  TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
Query OK, 0 rows affected (0.00 sec)

mysql> SELECT @@tx_isolation;
+------------------+
| @@tx_isolation   |
+------------------+
| READ-UNCOMMITTED |
+------------------+
1 row in set, 1 warning (0.00 sec)
```

AB客户端都开启事务

```
mysql> start transaction;
Query OK, 0 rows affected (0.00 sec)
```

B客户端执行查询

```
mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   500 |
|  5 | 赵六      |   600 |
+----+-----------+-------+
5 rows in set (0.00 sec)
```

A客户端修改第五条记录，将money修改为800，并将事务进行提交

```
mysql> update account set money=800 where id=5;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> commit;
Query OK, 0 rows affected (0.04 sec)
```

B客户端再次查询，可以看到在事务中，B客户端两次相同的查询查询到的结果不相同，因此发生不可重复读。

```
mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   500 |
|  5 | 赵六      |   800 |
+----+-----------+-------+
5 rows in set (0.00 sec)
```

我们将B客户端修改隔离级别为可重复读

```
mysql> SET SESSION  TRANSACTION ISOLATION LEVEL REPEATABLE READ;
Query OK, 0 rows affected (0.00 sec)

mysql> SELECT @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
1 row in set, 1 warning (0.00 sec)
```

AB客户端开启事务，B客户端查询数据

```
mysql> start transaction;
Query OK, 0 rows affected (0.00 sec)

mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   500 |
|  5 | 赵六      |   800 |
+----+-----------+-------+
5 rows in set (0.00 sec)
```

A客户端修改第五条记录，将money修改为800，并将事务进行提交。

```
mysql> update account set money=80 where id=5;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> commit;
Query OK, 0 rows affected (0.04 sec)
```

B客户端再次查询相同的数据，可以看到第五条记录的money值仍然为800，即在一个事务中，虽然数据库已经被修改，但也能保证了前后两次查询结果的一致。这样可以说明可重复读这以隔离级别是可以避免不可重复读。

```
mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   500 |
|  5 | 赵六      |   800 |
+----+-----------+-------+
5 rows in set (0.00 sec)
```

B客户端提交事务后再次查询，可以看到第五条记录发生了变化。

```
mysql> commit;
Query OK, 0 rows affected (0.00 sec)

mysql> select * from account;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | 周杨凡    |   100 |
|  2 | 张三      |   200 |
|  3 | 李四      |   300 |
|  4 | 王五      |   500 |
|  5 | 赵六      |    80 |
+----+-----------+-------+
5 rows in set (0.00 sec)
```

### 6.5.2 Spring中事务隔离级别

| 隔离级别常量                                     | 数值 | 描述                                                         |
| ------------------------------------------------ | ---- | ------------------------------------------------------------ |
| TransactionDefinition.ISOLATION_DEFAULT          | -1   | 默认隔离级别，由数据库控制                                   |
| TransactionDefinition.ISOLATION_READ_UNCOMMITTED | 1    | 其他事务可以读取到未提交事务的数据                           |
| TransactionDefinition.ISOLATION_READ_COMMITTED   | 2    | 只允许读取已提交的事务的数据，可以不免脏读。                 |
| TransactionDefinition.ISOLATION_REPEATABLE_READ  | 4    | 数据可重复度，防止脏读、不可重复读，但可能出现幻读           |
| TransactionDefinition.ISOLATION_SERIALIZABLE     | 8    | 最高隔离级别，完整地解决了脏读、幻读、不可重复读问题，花费较大代价。 |

```java
private class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt(1));
        book.setName(rs.getString(2));
        return book;
    }
}

@Test
public void testTransactionIsolation() throws Exception{
    //同时要访问的id
    Integer id = 3;
    //查询语句
    String query = "SELECT id, name FROM blog.book WHERE id=?";
    //更新语句
    String updateSQL = "UPDATE book SET name=? WHERE id=?";
    BookRowMapper bookRowMapper = new BookRowMapper();//对象转换处理
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
    new Thread(() -> {
        TransactionStatus statusA = this.transactionManager.
                getTransaction(definition);
        Book bookA = this.jdbcTemplate.queryForObject(query, bookRowMapper, id);
        LOGGER.info("【{}】第一次数据查询:{}", Thread.currentThread().getName(), bookA);
        try {
            TimeUnit.SECONDS.sleep(5);
            bookA = this.jdbcTemplate.queryForObject(query, bookRowMapper, id);
            LOGGER.info("【{}】第二次数据查询:{}", Thread.currentThread().getName(), bookA);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }, "JDBC操作线程 - A").start();
    new Thread(() -> {
        TransactionStatus statusB = this.transactionManager.
                getTransaction(definition);
        Book bookB = this.jdbcTemplate.queryForObject(query, bookRowMapper, id);
        LOGGER.info("【{}】第一次数据查询:{}", Thread.currentThread().getName(), bookB);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
            int counts = jdbcTemplate.update(updateSQL, "netty实战", id);
            LOGGER.info("【{}】数据更新完成，影响跟新的行数:{}",
                    Thread.currentThread().getName(), counts);
            this.transactionManager.commit(statusB);//事务
        } catch (InterruptedException e) {
            LOGGER.error("【{}】数据的更新出错", Thread.currentThread().getName(), e.getMessage());
            this.transactionManager.rollback(statusB);
        }
    }, "JDBC操作线程 - B").start();
    TimeUnit.SECONDS.sleep(50);
}
```

输出

```
【JDBC操作线程 - B】第一次数据查询:Book{id=3, name='格林童话'}
【JDBC操作线程 - A】第一次数据查询:Book{id=3, name='格林童话'}
【JDBC操作线程 - B】数据更新完成，影响跟新的行数:1
【JDBC操作线程 - A】第二次数据查询:Book{id=3, name='netty实战'}
```

可以看到`ISOLATION_READ_UNCOMMITTED`隔离级别发生了幻读

```java
private class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt(1));
        book.setName(rs.getString(2));
        return book;
    }
}

@Test
public void testTransactionIsolation() throws Exception{
    //同时要访问的id
    Integer id = 3;
    //查询语句
    String query = "SELECT id, name FROM blog.book WHERE id=?";
    //更新语句
    String updateSQL = "UPDATE book SET name=? WHERE id=?";
    BookRowMapper bookRowMapper = new BookRowMapper();//对象转换处理
    new Thread(() -> {
        TransactionStatus statusA = this.transactionManager.
                getTransaction(new DefaultTransactionDefinition());
        Book bookA = this.jdbcTemplate.queryForObject(query, bookRowMapper, id);
        LOGGER.info("【{}】第一次数据查询:{}", Thread.currentThread().getName(), bookA);
        try {
            TimeUnit.SECONDS.sleep(5);
            bookA = this.jdbcTemplate.queryForObject(query, bookRowMapper, id);
            LOGGER.info("【{}】第二次数据查询:{}", Thread.currentThread().getName(), bookA);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }, "JDBC操作线程 - A").start();
    new Thread(() -> {
        TransactionStatus statusB = this.transactionManager.
                getTransaction(new DefaultTransactionDefinition());
        Book bookB = this.jdbcTemplate.queryForObject(query, bookRowMapper, id);
        LOGGER.info("【{}】第一次数据查询:{}", Thread.currentThread().getName(), bookB);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
            int counts = jdbcTemplate.update(updateSQL, "格林童话", id);
            LOGGER.info("【{}】数据更新完成，影响跟新的行数:{}",
                    Thread.currentThread().getName(), counts);
            this.transactionManager.commit(statusB);//事务
        } catch (InterruptedException e) {
            LOGGER.error("【{}】数据的更新出错", Thread.currentThread().getName(), e.getMessage());
            this.transactionManager.rollback(statusB);
        }
    }, "JDBC操作线程 - B").start();
    TimeUnit.SECONDS.sleep(50);
}
```

输出：

```
【JDBC操作线程 - A】第一次数据查询:Book{id=3, name='Spring开发实战'}
【JDBC操作线程 - B】第一次数据查询:Book{id=3, name='Spring开发实战'}
【JDBC操作线程 - B】数据更新完成，影响跟新的行数:1
【JDBC操作线程 - A】第二次数据查询:Book{id=3, name='Spring开发实战'}
```

默认数据库隔离级别，没有发生幻读。

​         从正常的设计角度来讲，在进行Spring事务控制的时候，不要轻易的去修改隔离级别（需要记住概念），因为一般都使用默认的隔离级别，数据库自己来实现的控制。隔离级别是可以进行修改配置的，但是从正常的设计来讲不要去动，因为对于这种数据访问操作，除了依靠数据库之外，还可以基于一些缓存组件实现同步锁来进行控制。

## 6.6 事务的传播机制

propagation

- 事务开发是和业务层有直接联系的，在进行开发的过程中，很难出现业务层之间不相互调用的场景，例如：存在有一个A业务处理，但是A业务在处理的时候有可能会调用B业务，那么如果此时A和B之间各自都存在有事务的机制，那么这个时候就需要进行事务有效的传播管理。
- Spring的事务管理之中提供有7种事务传播的机制，而这传播机制主要通过TransactionDefinition对象的实例来进行配置的，而后在对应的接口之中观察出这七个配置的常量。
- Spring框架中的事务传播机制用于控制在一个事务管理器范围内多个事务之间的交互。它定义了当一个方法调用另一个方法时，如何处理它们之间的事务。

在Spring中，事务传播机制有以下几种：

1. **REQUIRED**：**默认事务的传播机制**，子业务直接支持当前父级事务，如果当前父业务中不存在事务，则创建一个新的事务；如果当前父业务中存在有事务，则合并为一个完整的事务。**简化理解：不管任何时候，只要进行了业务的调用，都需要创建出一个新的事务。这种机制是最为常用的事务的传播机制。**
2. **SUPPORTS**：如果父业务存在事务，则加入该父级事务；如果当前不存在父级事务，则以非事务方式继续执行。
3. **MANDATORY**：如果当前存在事务，则加入该事务；如果当前不存在事务，则抛出异常。**简化理解：必要要有事务。**
4. **REQUIRES_NEW**：建立一个新的子业务的事务，如果存在有父级事务则会自动将其挂起，该操作可以实现子事务的独立提交，不受调用者的事务影响，即便父级事务异常，也可以正常提交。**简单理解：子业务拥有自己独立的事务，即便父事务出现了问题，也不影响子业务的处理。**
5. **NOT_SUPPORTED**：以非事务方式执行操作，如果当前存在事务，则挂起该事务。**在进行其他业务调用的时候，不管是否存在，事务统一关闭。**
6. **NEVER**：以非事务方式执行操作，如果当前存在事务，则抛出异常。
7. **NESTED**：如果当前存在父级事务，则当前子业务中的事务会自动成为父级事务中的一个子事务，只有父级事务提交后才会提交子事务；如果子事务产生异常则可以交由父级调用进行异常处理，如果父级事务产生异常，则其也会回滚。**简单理解：所有事务统一交给调用业务处处理。**

在常规的开发之中，90%的事务处理机制都是使用的是PROPAGATION_REQUIRED传播方式。

**示例：测试NESTED：**

数据据表

```
+----+-------------+
| id | name        |
+----+-------------+
|  1 | 西游记      |
|  2 | 红楼梦      |
|  3 | netty实战   |
+----+-------------+
```

代码运行

```java
package com.zyf.jdbc.test;

import com.zyf.jdbc.config.DataSourceConfig;
import com.zyf.jdbc.config.SpringJDBCConfig;
import com.zyf.jdbc.config.TransactionConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author:zhouyangfan
 * @date:2023/4/2
 * @Description:测试事务传播机制
 **/
@ContextConfiguration(classes = {DataSourceConfig.class, SpringJDBCConfig.class, TransactionConfig.class})
@ExtendWith(SpringExtension.class) //Junit5测试
public class TestTransactionPropagation {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTransactionPropagation.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    public void updateService() throws Exception {//模拟更新
        String updateSQL = "UPDATE book SET name=? WHERE id=?"; //更新的SQL语句
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();//定义事务的控制
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = this.transactionManager.getTransaction(definition);
        try {
            this.insertService();//子业务
            this.jdbcTemplate.update(updateSQL, null, 1);//父业务
            this.transactionManager.commit(status);
        } catch (Exception e) {
            LOGGER.error("更新操作出现异常,{}", e.getMessage());
            this.transactionManager.rollback(status);
        }
    }


    @Test
    public void insertService() throws Exception {//模拟增加处理业务
        String insertSQL = "INSERT INTO book VALUES(?, ?)"; //插入的SQL语句
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();//定义事务的控制
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus status = this.transactionManager.getTransaction(definition);
        int counts = this.jdbcTemplate.update(insertSQL, "4", "手写MyBatis");
        this.transactionManager.commit(status);
    }
}
```

输出

```
更新操作出现异常,PreparedStatementCallback; SQL [UPDATE book SET name=? WHERE id=?]; Column 'name' cannot be null
```

数据库表中，没有实现子业务的insert添加记录。

```
+----+-------------+
| id | name        |
+----+-------------+
|  1 | 西游记      |
|  2 | 红楼梦      |
|  3 | netty实战   |
+----+-------------+
```

通过当前代码执行，可以发现，由于嵌套事务的管理，如果父类调用处出现了错误，那么此时是子业务的事务也会直接进行回滚。

**示例：测试REQUIRES_NEW：**

数据库表：

```
+----+-------------+
| id | name        |
+----+-------------+
|  1 | 西游记      |
|  2 | 红楼梦      |
|  3 | netty实战   |
+----+-------------+
```

java代码

```java
package com.zyf.jdbc.test;

import com.zyf.jdbc.config.DataSourceConfig;
import com.zyf.jdbc.config.SpringJDBCConfig;
import com.zyf.jdbc.config.TransactionConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author:zhouyangfan
 * @date:2023/4/2
 * @Description:测试事务传播机制
 **/
@ContextConfiguration(classes = {DataSourceConfig.class, SpringJDBCConfig.class, TransactionConfig.class})
@ExtendWith(SpringExtension.class) //Junit5测试
public class TestTransactionPropagation {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTransactionPropagation.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    public void updateService() throws Exception {//模拟更新
        String updateSQL = "UPDATE book SET name=? WHERE id=?"; //更新的SQL语句
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();//定义事务的控制
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = this.transactionManager.getTransaction(definition);
        try {
            this.insertService();
            this.jdbcTemplate.update(updateSQL, null, 1);
            this.transactionManager.commit(status);
        } catch (Exception e) {
            LOGGER.error("更新操作出现异常,{}", e.getMessage());
            this.transactionManager.rollback(status);
        }
    }


    @Test
    public void insertService() throws Exception {//模拟增加处理业务
        String insertSQL = "INSERT INTO book VALUES(?, ?)"; //插入的SQL语句
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();//定义事务的控制
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = this.transactionManager.getTransaction(definition);
        int counts = this.jdbcTemplate.update(insertSQL, "4", "手写MyBatis");
        this.transactionManager.commit(status);
    }
}
```

日志输出异常，表明父业务执行失败，抛出了异常：

```
更新操作出现异常,PreparedStatementCallback; SQL [UPDATE book SET name=? WHERE id=?]; Column 'name' cannot be null
```

如下数据库表中显示子业务中的记录插入成功，说明子业务的事务父业务的管理：

```
+----+---------------+
| id | name          |
+----+---------------+
|  1 | 西游记        |
|  2 | 红楼梦        |
|  3 | netty实战     |
|  4 | 手写MyBatis   |
+----+---------------+
```

## 6.7 只读事务控制

- 使用只读事务进行处理的时候，所有的数据不能进行更新，只能够进行读取的操作控制，在进行只读配置的时候，可以通过TransactionDefinition接口提供的serReadOnly()的方法进行配置。
- 在一些查询的操作里面，为了防止可能产生的任何更新操作，所以就可以进行指定业务之中的只读配置。

## 6.8 @Transaction注解

在实际开发中如果采用之间的编程式的模式进行定义，则代码的维护会非常繁琐，这种情况下可以考虑通过注解的方式进行配置，而注解的配置是离不开之前事务分析的结构的。

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.springframework.transaction.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.core.annotation.AliasFor;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Reflective
public @interface Transactional {
  //配置TransactionManager在Spring种注册的Bean名称
    @AliasFor("transactionManager")
    String value() default "";

    @AliasFor("value")
    String transactionManager() default "";

    String[] label() default {};

    Propagation propagation() default Propagation.REQUIRED;//事务传播机制

    Isolation isolation() default Isolation.DEFAULT;//事务隔离级别

    int timeout() default -1;

    String timeoutString() default "";//字符串的超时配置

    boolean readOnly() default false;//是否为只读的配置

    Class<? extends Throwable>[] rollbackFor() default {};//回滚异常类型的集合

    String[] rollbackForClassName() default {};//回滚异常类型的集合

    Class<? extends Throwable>[] noRollbackFor() default {};//配置不回滚的异常类型的集合

    String[] noRollbackForClassName() default {};//配置不回滚的异常类型的集合
}
```

### 6.8.1 使用xml文件配置事务

首先声明一个service

```java
package com.zyf.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:zhouyangfan
 * @date:2023/4/2
 * @Description:service类，其中有remove方法
 * remove方法之后执行insert
 * 这里让insert执行失败来测试事务
 **/
public class BookService {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(){
        String deleteSql = "DELETE FROM book WHERE id=?";
        int counts1 = this.jdbcTemplate.update(deleteSql, 4);
        String insertSql = "INSERT INTO book VALUES(null, null)";
        int counts2 = this.jdbcTemplate.update(insertSql);
    }
}
```

spring-base.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation=
               "http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-4.3.xsd">
    <context:component-scan base-package="com.zyf.jdbc"/>
    <context:annotation-config/>
</beans>
```

spring-transaction.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd">
    
<!--    配置数据源-->
    <bean id="dataSource"
          class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
        <property name="url" value="jdbc:mysql://localhost:3306/blog"></property>
        <property name="username" value="root"></property>
        <property name="password" value="zyf2563085"></property>
    </bean>
<!--配置springjdbc-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>
    </bean>

<!--    配置service类-->
    <bean id="bookService" class="com.zyf.jdbc.service.BookService">
        <property name="jdbcTemplate" ref="jdbcTemplate"></property>
    </bean>

<!--    配置transactionManager-->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
<!--配置事务注解-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```

单元测试

```java
package com.zyf.jdbc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(locations = {"classpath*:/*.xml"})
@ExtendWith(SpringExtension.class)
class BookServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceTest.class);
    @Autowired(required = true)
    private BookService bookService;

    @Test
    void test01() throws Exception {
        this.bookService.remove();
    }
}
```

启动后报错，数据库中id为4的记录没有被删除，说明事务发生了回滚。

以上就是使用注解进行事务的控制。

## 6.9 AOP的切面事务管理

- 使用@Transactional注解的确可以实现事务的配置，但是在一个项目的开发之中，会存有大量的业务方法。
- 所以按照之前的设计方案理解来讲，不应该将事务有关的代码直接硬编码到项目之中，采用注解的形式一定不是当前所推荐的方法，最佳的处理方式应该使用的是AOP切面管理，即通过AOP的表达式实现事务的控制。

创建业务接口：

```java
package com.zyf.jdbc.service;

import com.zyf.jdbc.model.Book;

public interface IBookService {
    boolean add(Book book);

    boolean edit(Book book);
}
```

创建业务的实现类

```java
package com.zyf.jdbc.service;

import com.zyf.jdbc.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author:zhouyangfan
 * @date:2023/4/3
 * @Description:业务的具体实现
 **/
public class IBookServiceImpl implements IBookService {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(Book book) {
        String sql = "INSERT INTO book VALUES(?,?)";
        return this.jdbcTemplate.update(sql, book.getId(), book.getName()) > 0;
    }

    @Override
    public boolean edit(Book book) {
        String sql = "UPDATE book set name=? WHERE id=?";
        return this.jdbcTemplate.update(sql, book.getName(), book.getId()) > 0;
    }
}
```

```java
package com.zyf.jdbc.service;

import com.zyf.jdbc.model.Book;

/**
 * @author:zhouyangfan
 * @date:2023/4/3
 * @Description:
 **/
public class PubService {

    private IBookServiceImpl iBookServiceImpl;

    public void setiBookServiceImpl(IBookServiceImpl iBookServiceImpl) {
        this.iBookServiceImpl = iBookServiceImpl;
    }

    public boolean editAll() {
        Book bookA = new Book();
        bookA.setName("Java进阶开发实战");
        bookA.setId(6);
        Book bookB = new Book();
        bookB.setName(null);
        bookB.setId(5);
        return iBookServiceImpl.add(bookA) && iBookServiceImpl.edit(bookB);
    }
}
```

修改spring-transaction.xml配置文件，通过tx命名空间进行AOP切面事务控制。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

<!--    配置数据源-->
    <bean id="dataSource"
          class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
        <property name="url" value="jdbc:mysql://localhost:3306/blog"></property>
        <property name="username" value="root"></property>
        <property name="password" value="zyf2563085"></property>
    </bean>
<!--配置springJdbc-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>
    </bean>

<!--    配置service类-->
    <bean id="iBookServiceImpl" class="com.zyf.jdbc.service.IBookServiceImpl" >
        <property name="jdbcTemplate" ref="jdbcTemplate"></property>
    </bean>

    <bean id="pubService" class="com.zyf.jdbc.service.PubService">
        <property name="iBookServiceImpl" ref="iBookServiceImpl"></property>
    </bean>

<!--    配置transactionManager-->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>

<!--    &lt;!&ndash;配置事务注解&ndash;&gt;-->
<!--    <tx:annotation-driven transaction-manager="transactionManager"/>-->

    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
<!--            定义方法的名称匹配，以及业务方法对应的事务处理方案-->
            <tx:method name="add*" propagation="REQUIRED"/>
            <tx:method name="edit*" propagation="REQUIRED"/>
            <tx:method name="get*" propagation="REQUIRED" read-only="true"/>
        </tx:attributes>
    </tx:advice>

    <aop:config proxy-target-class="true">
        <aop:pointcut id="transactionPointcut" expression="execution(* com.zyf.jdbc.service..*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="transactionPointcut"/>
    </aop:config>

</beans>
```

测试类

```java
package com.zyf.jdbc.testService;

import com.zyf.jdbc.service.PubService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author:zhouyangfan
 * @date:2023/4/3
 * @Description:测试类
 **/
public class TestIBook {
    @Test
    public void test01(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PubService pubService = classPathXmlApplicationContext.getBean("pubService", PubService.class);
        boolean b = pubService.editAll();
        System.out.println(b);
    }
}
```

输出

```
PreparedStatementCallback; SQL [UPDATE book set name=? WHERE id=?]; Column 'name' cannot be null
```

可以看到edit方法报错，数据库中并没有添加数据，因此事务已经生效。以上就是spring的声明式事务操作。

# 11. 路径通配符

​        所谓通配符指的是就是每一次可以加载多种资源，在之前使用Resource接口处理的时候，都是通过字符串的形式来及进行定义的，但是之前的定义只能够加载一种资源，如果现在需要进行一些资源匹配的处理操作，那么就要通过资源通配符来完成。

Spring中有如下通配符：

1. “?”：表示匹配任意零位或者一位字符，例如spring?.xml则表示匹配`spring1.xml`、`spring.xml`
2. “*”：表示零位、一位或多位字符：例如`spring-*.xml`则表示`spring-service.xml`
3. “**”：表示匹配任意级别的目录。



# 12. 表达式分析

字符串在Spring之中都是非常重要的一个结构，因为字符串的变化可以实现不同的功能，但是字符串的功能不仅仅只能作为工厂设计模式的标记，更多的是它可以完成一些逻辑上的处理，而这样的处理就需要通过SpEL（Spring Expression Language）进行实现，下面从基础结构开始分析其使用的逻辑。

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:字符串解析
 **/
public class SPEL {
    public static void main(String[] args) {
        String str = "(\"hello, world!\").substring(#start, #end).toUpperCase()";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(str);
        //执行表达式还需要考虑两个占位符的配置问题
        //表达式上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("start",6);
        context.setVariable("end",12);
        System.out.println(expression.getValue(context));
    }
}
```

输出：

```
 WORLD
```

整个的实现过程之中，一个字符串定义的所有的处理方法，都可以在SpEL处理结构之中自动完成，极大的丰富了字符串的可操作性。以上处理流程会使用到大量的Spring接口和实现类。

## 12.1 SpEL解析原理

SpEL是以字符串的操作为核心展开的，在进行处理的时候肯定要对表达式的内容进行解析，而这种解析以及最终结果的计算是有其实现的处理流程的。

范例：表达式实现加法计算

```java
public class SPEL {
    public static void main(String[] args) {
        String str = "10 + 20";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(str);
        //表达式上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        System.out.println(expression.getValue(context));
    }
}
```

程序执行结果：

```
30
```

以上功能成功实现了，解析解析的流程：

- 第一阶段：词法分析，需要通过断词器来进行字符串的结构拆分，在Spring之中结果这样的拆分是十分繁琐的，毕竟不同的计算模式有不同的拆分形式。
- 第二阶段：语法分析：生成一个语法树，把对应的内容添加到指定的位置，而后就可以开始解析处理了。
- 第三阶段：生成结果：由于整个表达式的内部可能会有占位符的问题，所以要通过表达式的上下文配置所需要的信息（跟对象的数据，变量或者函数等等）。

表达式的解析操作：

首先调用抽象类TemplateAwareExpressionParser类（实现了ExpressionParser接口）的方法

```java
 Expression expression = parser.parseExpression(str);
```

TemplateAwareExpressionParser类的parseExpression方法

```java
public Expression parseExpression(String expressionString, @Nullable ParserContext context) throws ParseException {
    return context != null && context.isTemplate() ? this.parseTemplate(expressionString, context) : this.doParseExpression(expressionString, context);
}
```

然后调用doParseExpression方法进行解析，doParseExpression是TemplateAwareExpressionParser类中的抽象方法，具体实现在InternalSpelExpressionParser类中。

```java
protected SpelExpression doParseExpression(String expressionString, @Nullable ParserContext context) throws ParseException {
        try {
            this.expressionString = expressionString;
            Tokenizer tokenizer = new Tokenizer(expressionString);
            this.tokenStream = tokenizer.process();
            this.tokenStreamLength = this.tokenStream.size();
            this.tokenStreamPointer = 0;
            this.constructedNodes.clear();
            SpelNodeImpl ast = this.eatExpression();
            Assert.state(ast != null, "No node");
            Token t = this.peekToken();
            if (t != null) {
                throw new SpelParseException(t.startPos, SpelMessage.MORE_INPUT, new Object[]{this.toString(this.nextToken())});
            } else {
                Assert.isTrue(this.constructedNodes.isEmpty(), "At least one node expected");
                return new SpelExpression(expressionString, ast, this.configuration);
            }
        } catch (InternalParseException var6) {
            throw var6.getCause();
        }
    }
```

字符串解析主要在Tokenizer类中的process方法中实现，首先使用字符串实例化一个Tokenizer类，再调用process方法进行字符串的解析，以及生成了一棵SpelNodeImpl类型语法树ast，最终语法树作为参数返回一个SpelExpression对象。

```java
Tokenizer tokenizer = new Tokenizer(expressionString);
this.tokenStream = tokenizer.process();
.....
SpelNodeImpl ast = this.eatExpression();
.....
return new SpelExpression(expressionString, ast, this.configuration);
```

接着使用SpelExpression对象的getValue()方法对表达式进行计算, 

```java
public Object getValue(EvaluationContext context) throws EvaluationException {
  Assert.notNull(context, "EvaluationContext must not be null");

  CompiledExpression compiledAst = this.compiledAst;
  if (compiledAst != null) {
    try {
      return compiledAst.getValue(context.getRootObject().getValue(), context);
    }
    catch (Throwable ex) {
      // If running in mixed mode, revert to interpreted
      if (this.configuration.getCompilerMode() == SpelCompilerMode.MIXED) {
        this.compiledAst = null;
        this.interpretedCount.set(0);
      }
      else {
        // Running in SpelCompilerMode.immediate mode - propagate exception to caller
        throw new SpelEvaluationException(ex, SpelMessage.EXCEPTION_RUNNING_COMPILED_EXPRESSION);
      }
    }
  }

  ExpressionState expressionState = new ExpressionState(context, this.configuration);
  Object result = this.ast.getValue(expressionState);
  checkCompile(expressionState);
  return result;
}
```

最终实现类

```java
public abstract class CompiledExpression {

	public abstract Object getValue(@Nullable Object target, @Nullable EvaluationContext context)
			throws EvaluationException;

}
```

## 12.2 ParserContext与表达式分隔符

学习过的EL表达式，这个表达式在进行处理的时候需要设置一些明显的分隔符，例如“#{属性的内容}”，在SpEL里面也允许开发者自身的需要自定义分隔符，此时就要使用到ParserContext接口

ParserContext接口源码：

```java
public interface ParserContext {
	boolean isTemplate();  //是否启用模板
	String getExpressionPrefix(); //模板的前缀
	String getExpressionSuffix();  //模板的后缀
	ParserContext TEMPLATE_EXPRESSION = new ParserContext() {
   //自定义了一个默认的分隔符
		@Override
		public boolean isTemplate() {
			return true;
		}

		@Override
		public String getExpressionPrefix() {
			return "#{";
		}

		@Override
		public String getExpressionSuffix() {
			return "}";
		}
	};

}
```

可以看到ParserContext接口中默认定义了一个模板。

使用默认的模板进行处理的实例：

```java
public class SPEL {
    public static void main(String[] args) {
        String str = "#{10 + 20 + 30}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(str,ParserContext.TEMPLATE_EXPRESSION);
        //执行表达式还需要考虑两个占位符的配置问题
        //表达式上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        System.out.println(expression.getValue(context));
    }
}
```

输出

```
60
```

自定义分隔符

```java
/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:字符串解析
 **/
public class SPEL {
    public static void main(String[] args) {
        String str = "#[10 + 20 + 30]";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(str, new ParserContext() {
            @Override
            public boolean isTemplate() {
                return true;
            }

            @Override
            public String getExpressionPrefix() {
                return "#[";
            }

            @Override
            public String getExpressionSuffix() {
                return "]";
            }
        });
        //执行表达式还需要考虑两个占位符的配置问题
        //表达式上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        System.out.println(expression.getValue(context));
    }
}

```

SpEL的处理在实际的开发之中，不建议修改模板的配置。记住Spring默认的模板为`#{}`

## 12.3 SpEL字面表达式

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;



/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:字符串解析
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "\"zyf\" + \"slp\"";
        String context2 = "#{\"zyf\" + \"slp\"}";
        String context3 = "#{'zyf' + 'slp'}";
        System.out.println(spel(context1));
        System.out.println(spel(context2));
        System.out.println(spel(context3));
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        String value = (String)expression.getValue();
        return value;
    }
}
```

输出

```
"zyf" + "slp"
zyfslp
zyfslp
```

自动类型的转换

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:字符串解析
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "1";
        String context2 = "1.1";
        String context3 = "true";
        System.out.println("" + spel(context1).toString() + " 类型：" + spel(context1).getClass().getName());
        System.out.println("" + spel(context2).toString() + " 类型：" + spel(context2).getClass().getName());
        System.out.println("" + spel(context3).toString() + " 类型：" + spel(context3).getClass().getName());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出

```properties
1 类型：java.lang.String
1.1 类型：java.lang.String
true 类型：java.lang.String
```

添加表达式分隔符：

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:字符串解析
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{1}";
        String context2 = "#{1.1}";
        String context3 = "#{true}";
        System.out.println("" + spel(context1).toString() + " 类型：" + spel(context1).getClass().getName());
        System.out.println("" + spel(context2).toString() + " 类型：" + spel(context2).getClass().getName());
        System.out.println("" + spel(context3).toString() + " 类型：" + spel(context3).getClass().getName());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出

```properties
1 类型：java.lang.Integer
1.1 类型：java.lang.Double
true 类型：java.lang.Boolean
```

此时的执行过程里面可以发现：

- 如果字符串使用单引号或者双引号效果时相同的。
- 如果传递的是一些基础的数据内容，也会自动进行数据转换，这个转换是要有专属的处理类实现。

## 12.4 SpEL的数学表达式

SpEL里面对于数学计算直接编写字符串就可以实现了。

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL数学表达式
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{1+2+3}";
        String context2 = "#{1.1 + 6}";
        String context3 = "#{1==2}";
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass());
        System.out.println("" + spel(context2) + " 类型：" + spel(context2).getClass());
        System.out.println("" + spel(context3) + " 类型：" + spel(context3).getClass());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出：

```properties
6 类型：class java.lang.Integer
7.1 类型：class java.lang.Double
false 类型：class java.lang.Boolean
```

其他一些：

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:字符串解析
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{10 mod 3}";
        String context2 = "#{10 DIV 3}";
        String context3 = "#{10 ^ 3}";
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass());
        System.out.println("" + spel(context2) + " 类型：" + spel(context2).getClass());
        System.out.println("" + spel(context3) + " 类型：" + spel(context3).getClass());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出

```properties
1 类型：class java.lang.Integer
3 类型：class java.lang.Integer
1000 类型：class java.lang.Integer
```

## 12.5 SpEL关系表达式

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:字符串解析
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{10 EQ 10}";//EQ为等于
        String context2 = "#{10 GT 3}";//GT为大于
        String context3 = "#{10 LT 3}";//LT为小于
        String context4 = "#{10 LE 10}";//LE小于等于
        String context5 = "#{10 GE 10}";//GE大于等于
        String context6 = "#{10 BETWEEN {4,11}}";//区间判断
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass());
        System.out.println("" + spel(context2) + " 类型：" + spel(context2).getClass());
        System.out.println("" + spel(context3) + " 类型：" + spel(context3).getClass());
        System.out.println("" + spel(context4) + " 类型：" + spel(context3).getClass());
        System.out.println("" + spel(context5) + " 类型：" + spel(context3).getClass());
        System.out.println("" + spel(context6) + " 类型：" + spel(context3).getClass());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出

```properties
true 类型：class java.lang.Boolean
true 类型：class java.lang.Boolean
false 类型：class java.lang.Boolean
true 类型：class java.lang.Boolean
true 类型：class java.lang.Boolean
true 类型：class java.lang.Boolean
```

## 12.6 SpEL的逻辑表达式

逻辑运算就是“与或非”的三类结构，SpEL表达式里面也提供有单词方式的逻辑处理。

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:字符串解析
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{10 EQ 10 AND 10 GT 10}";//与
        String context2 = "#{10 EQ 10 OR 10 GT 10}";//或
        String context3 = "#{NOT(10 LT 3)}";//非
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass());
        System.out.println("" + spel(context2) + " 类型：" + spel(context2).getClass());
        System.out.println("" + spel(context3) + " 类型：" + spel(context3).getClass());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出

```properties
false 类型：class java.lang.Boolean
true 类型：class java.lang.Boolean
true 类型：class java.lang.Boolean
```

## 12.7 SpEL的三目运算符

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL三目运算
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{1 == 2 ? 'zyf' : 'slp'}";//三目运算
        String context2 = "#{null == null ? 1 : 2}";//三目运算
        String context3 = "#{true ? 1.1 : 2.2}";//三目运算
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass());
        System.out.println("" + spel(context2) + " 类型：" + spel(context2).getClass());
        System.out.println("" + spel(context3) + " 类型：" + spel(context3).getClass());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

如果判断的内容为null，则返回后面的内容，不为null，则返回本身:

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL三目运算
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{null ?: 'zyf'}";//三目运算
        String context2 = "#{'zyf' ?: 'slp'}";
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass());
        System.out.println("" + spel(context2) + " 类型：" + spel(context2).getClass());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出

```properties
zyf 类型：class java.lang.String
zyf 类型：class java.lang.String
```

## 12.8 SpEL字符串处理

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL 字符串处理
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{'zyf'.toUpperCase()}";//toUpperCase()方法
        String context2 = "#{'hello'[2]}";//
        String context3 = "#{'hello'.substring(0,2)}";
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass());
        System.out.println("" + spel(context2) + " 类型：" + spel(context2).getClass());
        System.out.println("" + spel(context3) + " 类型：" + spel(context2).getClass());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出

```properties
ZYF 类型：class java.lang.String
l 类型：class java.lang.String
he 类型：class java.lang.String
```

## 12.9 Class表达式

Java之中最大的处理机制就在于反射机制，包括JDK之中内存结构的不断优化实际上也都是与反射机制有直接的关联所在（永久代被替换为了元空间），Spring之中最重要的一个特点就是可以通过字符串描述出更多的内容，那么对于反射机制也可以通过字符串的形式去进行描述。

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;



/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL class表达式
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{T(java.lang.String)}";
        String context2 = "#{T(java.util.Date)}";
        String context3 = "#{T(java.util.Arrays)}";
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass());
        System.out.println("" + spel(context2) + " 类型：" + spel(context2).getClass());
        System.out.println("" + spel(context3) + " 类型：" + spel(context2).getClass());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出：

```properties
class java.lang.String 类型：java.lang.Class
class java.util.Date 类型：java.lang.Class
class java.util.Arrays 类型：java.lang.Class
```

class直接调用静态方法

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL class表达式
 **/
public class SPEL {

    public static void main(String[] args) {
        String context1 = "#{T(java.lang.Integer).MAX_VALUE}";//toUpperCase()方法
        String context2 = "#{T(java.time.LocalDateTime).now()}";//
        String context3 = "#{T(java.lang.Integer).parseInt('919')}";
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass().getName());
        System.out.println("" + spel(context2) + " 类型：" + spel(context2).getClass().getName());
        System.out.println("" + spel(context3) + " 类型：" + spel(context3).getClass().getName());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();

        return value;
    }
}
```

输出

```properties
2147483647 类型：java.lang.Integer
2023-04-08T15:13:33.678950500 类型：java.time.LocalDateTime
919 类型：java.lang.Integer
```

这种代码操作的核心意义在于，可以直接在配置文件里面编写的字符串可以定义任意的数据类型，不一定局限在String、Integer、Boolean这种基本类型的处理上。以上使用的全都是系统类，如果有需要，这种反射机制的表达式也可以使用自己定义类进行操作。

范例：操作自定义类

```java
package com.zyf;

import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL class表达式
 **/
public class SPEL {
    public static class Student {
        private String name;
        private Integer age;

        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public Student() {
        }

        @Override
        public String toString() {
            return "Student{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   '}';
        }
    }

    public static void main(String[] args) {
        String context1 = "#{ new com.zyf.SPEL.Student('zyf', 23) }";
        System.out.println("" + spel(context1) + " 类型：" + spel(context1).getClass().getName());
    }

    public static Object spel(String context) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        Object value = expression.getValue();
        return value;
    }
}
```

输出

```properties
Student{name='zyf', age=23} 类型：com.zyf.SPEL$Student
```

直接使用字符串的“new”，根据类之中已经提供的双参构造方法实现了对象实例化的处理操作，整个流程和之前所谓的对象直接实例化语法很相似，本质上是通过SpEL解析得来的。

## 12.10 表达式变量操作

所谓的变量指的就是表达式代替的内容所在，但是这个内容的类型是可以随意地，可能是一个字符串，也有可能是一个整形，更有可能是其他任意类型，如果要进行变量定义，则直接在表达式中进行声明。

范例：

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL class表达式
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) {
        String exp = "#{#varA + #varB}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("varA", "hello");
        context.setVariable("varB", ",world!");
        String result = expression.getValue(context, String.class);
        LOGGER.info("字符串拼接结果: {}", result);
        context.setVariable("varA", 12.1);
        context.setVariable("varB", 12.3);
        Double doubleRes = expression.getValue(context, Double.class);
        LOGGER.info("浮点数相加结果: {}", doubleRes);
    }
}
```

输出

```properties
INFO com.zyf.SPEL -- 字符串拼接结果: hello,world!
INFO com.zyf.SPEL -- 浮点数相加结果: 24.4
```

以上的表达式处理之中，是明确的采用了`#变量名称`的形式定义了两个变量，这个时候变量的类型是可以随意的，因为会由SpEL自动进行区分，但是获取结果的时候就需要根据当前的变量数据的类型来进行配置了，但是除了人为定义的变量之外，实际上还会存在一个根变量的概念。

**范例**：定义根变量

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL class表达式
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) {
        String exp = "#{ #root.contains('zyf') }";
        //根变量的变量名必须为#root
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext("zyfzyf");
        Boolean bool = expression.getValue(context, Boolean.class);
        LOGGER.info("判断结果: {}", bool);
    }
}
```

```properties
INFO com.zyf.SPEL -- 判断结果: true
```

根变量与普通变量最大的区别就在于其可以在实例化表达式解析上下文的时候进行根变量的配置（构造方法上的特权使用，仅此而已），但是现在整个的处理操作都属于常规的配置形式，如果有需要也可以传递一个其他类型的对象实例，例如：本次将传递一个Java反射机制的里的Mothed类型。

范例：

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL class表达式
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        String exp = "#{ #convert('919') }";
        Method method = Integer.class.getMethod("parseInt", String.class);
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("convert", method);
        Integer value = expression.getValue(context, Integer.class);
        LOGGER.info("转换结果: {}", value);
        LOGGER.info("结果的数据类型: {}", value.getClass().getName());
    }
}
```

输出：

```properties
INFO com.zyf.SPEL -- 转换结果: 919
INFO com.zyf.SPEL -- 结果的数据类型: java.lang.Integer
```

此时表达式之中的全部内容都可以由用户根据自身的需要进行设置，那么下面也可以通过其实现类中的属性的调用，例如：在java.util.Date类之中提供有一个getTime()方法，那么本次将通过表达式的操作实现调用。

范例：

```JAVA
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL class表达式
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        String exp = "#{ #var.time }";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("var", new java.util.Date());
        Long time = expression.getValue(context, Long.class);
        LOGGER.info("时间数值: {}", time);
    }
}
```

输出

```
INFO com.zyf.SPEL -- 时间数值: 1681029903688
```

但是需要注意的是，对于此时的程序，必须保证的是对象存在，如果对象不存在，则也会出现异常。

```java
public static void main(String[] args) throws NoSuchMethodException {
    String exp = "#{ #var.time }";
    SpelExpressionParser parser = new SpelExpressionParser();
    Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
    StandardEvaluationContext context = new StandardEvaluationContext();
    context.setVariable("var", null);
    Long time = expression.getValue(context, Long.class);
    LOGGER.info("时间数值: {}", time);
}
```

输出

```
EL1007E: Property or field 'time' cannot be found on null
```

如果此时要回避掉这种null的问题，那么最佳的做法就是可以使用Groovy表达式进行处理。

```java
public static void main(String[] args) throws NoSuchMethodException {
    String exp = "#{ #var?.time }";//定义表达式
    SpelExpressionParser parser = new SpelExpressionParser();
    Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
    StandardEvaluationContext context = new StandardEvaluationContext();
    context.setVariable("var", null);
    Long time = expression.getValue(context, Long.class);
    LOGGER.info("时间数值: {}", time);
}
```

此时会判断当前的变量的内容是否为null，如果是null，就直接返回null，如果不是null，则正常计算处理。

## 12.11 List集合表达式

开发之中最为常用的一项集合就是List，所以SpEL里面对于List有所支持也是可以正常理解的，如果有需要的化可以直接在字符串里进行集合定义。

**范例：创建List集合(加大括号，括号中写元素)**

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL list集合表达式
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        String exp = "#{{'zyf','tom','jack'}}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        List list = expression.getValue(ArrayList.class);
        LOGGER.info("生成类型: {}", list.getClass().getName());
        LOGGER.info("List集合: {}", list);
    }
}
```

输出：

```properties
INFO com.zyf.SPEL -- 生成类型: java.util.ArrayList
INFO com.zyf.SPEL -- List集合: [zyf, tom, jack]
```

**范例：访问外部集合**

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL 访问外部集合
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        List<String> names = List.of("tom", "jack", "zyf"); //外部集合
        String exp = "#{ #all[0]}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("all", names);
        String name = expression.getValue(context, String.class);
        LOGGER.info("生成类型：{}", name.getClass().getName());
        LOGGER.info("访问的集合元素: {}", name);
    }
}
```

输出

```properties
INFO com.zyf.SPEL -- 生成类型: java.lang.String
INFO com.zyf.SPEL -- 访问的集合元素: tom
```

以上功能仅仅完成了一个List集合之中所谓的get()方法调用，但是在List集合接口里面是存在有一个set()方法的，这个方法可以修改指定索引位置上的数据。

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL 访问外部集合
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list,"tom", "jack", "zyf"); //外部集合
        String exp = "#{ #all[1] = 'james'}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("all", list);
        String str = expression.getValue(context, String.class);
        LOGGER.info("生成类型：{}", str.getClass().getName());
        LOGGER.info("生成元素：{}", str);
        LOGGER.info("访问的集合元素: {}", list);
    }
}
```

输出

```properties
INFO com.zyf.SPEL -- 生成类型：java.lang.String
INFO com.zyf.SPEL -- 生成元素：james
INFO com.zyf.SPEL -- 访问的集合元素: [tom, james, zyf]
```

迭代修改整个list数据：

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL 访问外部集合
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list,"tom", "jack", "zyf"); //外部集合
        String exp = "#{ #all.!['【studentName:】' + #this]}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("all", list);
        List newList = expression.getValue(context, List.class);
        LOGGER.info("原来的集合元素: {}", list);
        LOGGER.info("生成类型: {}", newList.getClass().getName());
        LOGGER.info("生成的集合元素: {}", newList);
    }
}
```

输出

```properties
原来的集合元素: [tom, jack, zyf]
生成类型: java.util.ArrayList
生成的集合元素: [【studentName:】tom, 【studentName:】jack, 【studentName:】zyf]
```

此时集合的修改之后，并不会修改原始集合的内容，而是会将修改后的数据保存在一个新的List集合中，同时原始List集合的内容不会发生任何的改变。

## 12.12 Map集合表达式

一般会使用Map集合进行一些结构性数据缓存的存储，这些在正真涉及到Spring核心代码的时候会观察到。

范例：访问Map集合数据

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL 访问外部集合
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        HashMap<String, String> map = new HashMap<>();
        map.put("id1", "jack");
        map.put("id2", "tom");
        map.put("id3", "lucy");
        String exp = "#{ #all[id1]}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("all", map);
        String name = expression.getValue(context, String.class);
        LOGGER.info("指定的value: {}", name);
        LOGGER.info("map集合: {}", map);
    }
}
```

输出

```properties
INFO com.zyf.SPEL -- 指定的value: jack
INFO com.zyf.SPEL -- map集合: {id2=tom, id1=jack, id3=lucy}
```

范例：修改Map集合：

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL 访问外部集合
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        HashMap<String, String> map = new HashMap<>();
        map.put("id1", "jack");
        map.put("id2", "tom");
        map.put("id3", "lucy");
        String exp = "#{ #all[id1] = 'zyf'}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("all", map);
        String update = expression.getValue(context, String.class);
        LOGGER.info("修改的数据: {}", update);
        LOGGER.info("修改后的map集合: {}", map);
    }
}
```

输出：

```properties
INFO com.zyf.SPEL -- 修改的数据: zyf
INFO com.zyf.SPEL -- 修改后的map集合: {id2=tom, id1=zyf, id3=lucy}
```

范例：将Map集合转为List集合

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.List;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL 访问外部集合
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        HashMap<String, String> map = new HashMap<>();
        map.put("id1", "jack");
        map.put("id2", "tom");
        map.put("id3", "lucy");
        String exp = "#{ #all.![#this.key + ' - ' + #this.value]}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("all", map);
        List list = expression.getValue(context, List.class);
        LOGGER.info("原map集合: {}", map);
        LOGGER.info("生成的list集合: {}", list);
    }
}
```

输出：

```properties
INFO com.zyf.SPEL -- 原map集合: {id2=tom, id1=jack, id3=lucy}
INFO com.zyf.SPEL -- 生成的list集合: [id2 - tom, id1 - jack, id3 - lucy]
```

对Map集合的筛选：

只要符合于条件筛选的数据都可以直接转为新的Map集合进行返回。

```java
package com.zyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.List;


/**
 * @author:zhouyangfan
 * @date:2023/4/7
 * @Description:SpEL 访问外部集合
 **/
public class SPEL {
    public static final Logger LOGGER = LoggerFactory.getLogger(SPEL.class);

    public static void main(String[] args) throws NoSuchMethodException {
        HashMap<String, String> map = new HashMap<>();
        map.put("id1", "jack");
        map.put("id2", "tom");
        map.put("id3", "lucy");
        String exp = "#{ #all.?[#this.value.contains('c')]}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("all", map);
        List list = expression.getValue(context, List.class);
        LOGGER.info("原map集合: {}", map);
        LOGGER.info("生成的list集合: {}", list);
    }
}
```

输出

```
INFO com.zyf.SPEL -- 原map集合: {id2=tom, id1=jack, id3=lucy}
INFO com.zyf.SPEL -- 生成的list集合: [{id1=jack, id3=lucy}]
```

## 12.13 配置文件中整合SpEL

想要充分发挥SpEL的使用特点，那么就必须结合于已有的Spring配置环境，在Spring之中所有的Bean都需要进行配置的注册，那么可以在注册的时候直接使用SpEL进行数据处理。

![image-20230409190821793](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409190821793.png)

1. 创建一个表示消息的类

```java
package com.zyf.pojo;

import java.util.Set;

/**
 * @author:zhouyangfan
 * @date:2023/4/9
 * @Description:消息类
 **/
public class Message {
    private long mid;
    private String title;
    private String content;
    private Set<String> tags;

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Message(long mid, String title, String content, Set<String> tags) {
        this.mid = mid;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
               "mid=" + mid +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", tags=" + tags +
               '}';
    }
}
```

2. 在applicationContext.xml中定义Bean实例

```xml
<bean id="message" class="com.zyf.pojo.Message">
    <property name="mid" value="#{'zyf-5000-03'.split('-')[1]}"/>
    <property name="title" value="#{'sjhd22oj'.replace('\d+', '')}"/>
    <property name="content" value="#{'www.BAIDU.com'.toLowerCase()}"/>
    <property name="tags" value="#{'zyf;tom;jack'.split(';')}"/>
</bean>
```

3. 测试

```java
package com.zyf;

import com.zyf.pojo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author:zhouyangfan
 * @date:2023/4/9
 * @Description:测试类
 **/
public class Test01 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test01.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Message message = classPathXmlApplicationContext.getBean("message", Message.class);
        LOGGER.info("message对象: {}", message);
    }
}
```

4. 输出

```
INFO com.zyf.Test01 -- message对象: Message{mid=5000, title='sjhd22oj', content='www.baidu.com', tags=[zyf, tom, jack]}
```

SpEL这种支持可以直接体现在最终的Spring配置项，这样的操作使得Bean的注入或者是定义更加方便，而且更加的灵活。

## 12.14 基于注解使用SpEL

除了在配置文件中使用SpEL定义，当然也可以基于注解的形式直接在类上进行定义。

1. 在属性上直接使用@Value注解并直接使用SpEL表达式

```java
package com.zyf.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Set;

/**
 * @author:zhouyangfan
 * @date:2023/4/9
 * @Description:消息类
 **/

public class Message {
    @Value("#{'zyf-5000-03'.split('-')[1]}")
    private long mid;
    @Value("#{'sjhd22oj'.replace('/d+', '')}")
    private String title;
    @Value("#{'www.BAIDU.com'.toLowerCase()}")
    private String content;
    @Value("#{'zyf;tom;jack'.split(';')}")
    private Set<String> tags;

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Message(long mid, String title, String content, Set<String> tags) {
        this.mid = mid;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
               "mid=" + mid +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", tags=" + tags +
               '}';
    }
}
```

2. 配置类

```java
package com.zyf.config;

import com.zyf.pojo.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:zhouyangfan
 * @date:2023/4/9
 * @Description:message配置类
 **/
@Configuration
public class MessageConfig {
    @Bean
    public Message getMessage(){
        return new Message();
    }
}
```

3. 测试类

```java
package com.zyf;

import com.zyf.pojo.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author:zhouyangfan
 * @date:2023/4/9
 * @Description:测试类
 **/
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@ExtendWith(SpringExtension.class)
public class Test01 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test01.class);

    @Autowired
    private Message message;

    @Test
    void messageTest() {
        LOGGER.info("message: {}", message);
    }
}
```

4. 输出

```
message: Message{mid=5000, title='sjhd22oj', content='www.baidu.com', tags=[zyf, tom, jack]}
```

# 13 PropertySource资源管理

## 13.1 PropertySource抽象类

属性就属于key=value集合，在实际开发中之中，可能有一些配置是通过`*.properties`资源文件进行注入的，还有是通过程序解析得来的map集合或者是其他的属性集合，但是这样外部程序编写起来就繁琐了，那么就需要找到一个统一的操作，所以提供了PropertySource：

```java
public abstract class PropertySource<T> {

	protected final Log logger = LogFactory.getLog(getClass());

	protected final String name;

	protected final T source;

	public PropertySource(String name, T source) {
		this.name = name;
		this.source = source;
	}
}
```

PropertySource是一个抽象类，肯定要有与之相关的实现子类，那么直接通过IDEA查看这些子类可以发现如下信息，这些子类之中实际上也是包含有各类的继承结构：

![image-20230411191721932](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411191721932.png)

发现不同的子类可以保存有不同的数据类型，例如：PropertiesPropertySource可以保存的是properties对象实例，而对应的MapPropertySource那么就可以保存Map接口的实例。

![image-20230411192342522](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411192342522.png)

范例：使用MapPropertySource资源管理

```java
package com.zyf.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;

/**
 * @author:zhouyangfan
 * @date:2023/4/11
 * @Description:map资源获取
 **/
public class PropertySourceDemo {
    public final static Logger LOGGER = LoggerFactory.getLogger(PropertySourceDemo.class);

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zyf");
        map.put("age", 18);
        map.put("sex", '男');
        MapPropertySource mapPropertySource =
                new MapPropertySource("url", map);
        LOGGER.info("name = {}", mapPropertySource.getProperty("name"));
    }
}
```

输出：

```properties
INFO com.zyf.pojo.PropertySourceDemo -- name = zyf
```

范例：使用PropertiesPropertySource资源管理

```java
package com.zyf.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author:zhouyangfan
 * @date:2023/4/11
 * @Description:properties资源获取
 **/
public class PropertySourceDemo {
    public final static Logger LOGGER = LoggerFactory.getLogger(PropertySourceDemo.class);

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/source.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        PropertiesPropertySource propertySource
                = new PropertiesPropertySource("url", properties);
        String name = properties.getProperty("name");
        LOGGER.info("name={}", name);
    }
}
```

输出：

```properties
INFO com.zyf.pojo.PropertySourceDemo -- name=zyf
```

此时是通过了中间过渡环节实现了资源的获取，而这个过渡环节就是由PropertySource抽象类实现的，如果有需要可以更换不同的子类以实现不同类型的资源管理。

## 13.2 PropertySources接口

上面分析了使用PropertySource可以实现单种资源的管理，这是一种开始规范化Spring代码结构的一种手段。但是程序开发之中，资源肯定不止一种，肯定会有多种不同的资源存在，所以就需要对资源的管理做进一步的抽象处理。

**在一个完整的项目应用中，有可能会存在由若干个不同的属性源（propertySource对象实例），为了便于应用程序资源的方便获取，在Spring中提供了PropertySources接口，所有的propertySource对象实例向此接口注册后，就可以根据指定的名称获取一个PropertySource对象实例，从而实现属性源的统一管理。**

```java
package org.springframework.core.env;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.lang.Nullable;

public interface PropertySources extends Iterable<PropertySource<?>> {

	default Stream<PropertySource<?>> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
	
  //是否有指定名称的PropertySource存在
	boolean contains(String name);

	@Nullable
	PropertySource<?> get(String name);
  //根据名称获取propertySource对象实例

}
```

PropertySources是一个接口，其实现子类为MutablePropertySources

```java
public class MutablePropertySources implements PropertySources {

	private final List<PropertySource<?>> propertySourceList = new CopyOnWriteArrayList<>();
}
```

范例：实现多资源管理

```java
package com.zyf.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author:zhouyangfan
 * @date:2023/4/11
 * @Description:properties资源获取
 **/
public class PropertySourceDemo {
    public final static Logger LOGGER = LoggerFactory.getLogger(PropertySourceDemo.class);

    public static void main(String[] args) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mapName", "zyf");
        map.put("mapAge", 18);
        map.put("mapSex", '男');
        MapPropertySource mapPropertySource =
                new MapPropertySource("map", map);
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/source.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        PropertiesPropertySource propertiespropertySource
                = new PropertiesPropertySource("properties", properties);
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addLast(propertiespropertySource);
        propertySources.addLast(mapPropertySource);
        LOGGER.info("MapName={}", propertySources.get("map").getProperty("mapName"));
        LOGGER.info("name={}", propertySources.get("properties").getProperty("name"));
    }
}
```

输出

```properties
INFO com.zyf.pojo.PropertySourceDemo -- MapName=zyf
INFO com.zyf.pojo.PropertySourceDemo -- name=zyf
```

## 13.3 PropertyResolver

13.2中PropertySources接口的规范资源统一管理，如果想要获取指定的KEY对应的内容，需要先获取对应的属性源然后再进行资源的查找。这样做十分繁琐。

```java
public PropertySource<?> get(String name) {
  for (PropertySource<?> propertySource : this.propertySourceList) {
    //通过name查找属性源
    if (propertySource.getName().equals(name)) {
      return propertySource;
    }
  }
  return null;
}
```

Spring是一个容器，容器内部必然封装着大量的属性源，很多使用者可能并不清楚这些属性源的分类，所以就要有一个统一的属性的处理操作，那么就提供了一个属性解析的接口PropertyResolver。

![image-20230411213752822](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411213752822.png)

主要用到了的是PropertySourcesPropertyResolver类，其继承了AbstractPropertyResolver抽象类（接口和子类不要直接实现，通过抽象类做适配器）。

AbstractPropertyResolver类

```java
public abstract class AbstractPropertyResolver implements ConfigurablePropertyResolver {

	protected final Log logger = LogFactory.getLog(getClass());

	private String placeholderPrefix = SystemPropertyUtils.PLACEHOLDER_PREFIX;

	private String placeholderSuffix = SystemPropertyUtils.PLACEHOLDER_SUFFIX;
}
```



```java
protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
  if (this.propertySources != null) {
    for (PropertySource<?> propertySource : this.propertySources) {
      if (logger.isTraceEnabled()) {
        logger.trace("Searching for key '" + key + "' in PropertySource '" +
            propertySource.getName() + "'");
      }
      Object value = propertySource.getProperty(key);
      if (value != null) {
        if (resolveNestedPlaceholders && value instanceof String string) {
          value = resolveNestedPlaceholders(string);
        }
        logKeyFound(key, propertySource, value);
        return convertValueIfNecessary(value, targetValueType);
      }
    }
  }
  if (logger.isTraceEnabled()) {
    logger.trace("Could not find key '" + key + "' in any property source");
  }
  return null;
}
```

