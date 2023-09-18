# SpringMVC

- MVC：模型层（model）、视图（view）和控制器（controller）
- 模型层：JavaBean和行为（bean层和dao层）
  1. 业务逻辑。
  2. 保存数据的状态。
- 视图层：显示界面。
- 控制层：接收请求，委托给模型进行处理，再把处理的结果返回视图。
  1. 取得表单数据。
  2. 调用业务逻辑。
  3. 转向指定的页面。

# 1.HelloSpringMVC！

## 1.1 依赖jar包导入

```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
  
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
  
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
  
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
  
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
</dependencies>
```

## 1.2 注册DispatcherServlet

```xml
<!--1.注册DispatcherServlet-->
<servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--关联一个springmvc的配置文件:【servlet-name】-servlet.xml-->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc-servlet.xml</param-value>
    </init-param>
    <!--启动级别-1-->
    <load-on-startup>1</load-on-startup>
</servlet>
<!--/ 匹配所有的请求；（不包括.jsp）-->
<!--/* 匹配所有的请求；（包括.jsp）-->
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

## 1.3 编写SpringMVC配置文件

- 配置文件名与DispatcherServlet配置一致，格式为 : [servletname]-servlet.xml
- 添加处理器映射器
- 添加处理器适配器
- 添加视图解析器（拼接）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
<!--添加处理器映射器-->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
<!--添加处理器适配器-->
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>

    <!--视图解析器:DispatcherServlet给他的ModelAndView-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>
```

## 1.4 编写Controller类

**要么实现Controller接口，要么增加注解；需要返回一个ModelAndView，装数据，封视图**

```java
package com.zyf.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","HelloSpringMVC!");
        mv.setViewName("hello");
        return mv;
    }
}
```

## 1.5 controller类交给SpringIOC容器管理

```xml
<!--Handler-->
<bean id="/hello" class="com.kuang.controller.HelloController"/>
```

------

# 2. SpringMVC执行原理



# 3. 使用注解进行开发

springmvc-servlet配置

```xml
<context:component-scan base-package="com.zyf.controller"/>
<mvc:default-servlet-handler/>
<mvc:annotation-driven/>

<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <!--前缀-->
    <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
    <property name="suffix" value=".jsp"/>
</bean>
```

- mvc:default-servlet-handler 作用：不让静态资源被SpringMVC框架拦截。
- mvc:annotation-driven ： 使用注解进行开发。
- 使用注解进行开发，不再需要配置映射器和适配器。

```java
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("msg","Hello,SpringMVCAnnotation!");
        return "hello";
        //会被视图解析器处理
    }
}
```

# 4. Controller和ResquestMapping

@Controller：

- @Controller注解代表这个会被Spring接管
- 被这个注解注解的类，如果方法返回值是String，并且有具体的页面跳转，那么就会被视图解析器解析。
- 返回的字符串会被拼接，执行页面的跳转。

@ResquestMapping用于映射url到控制器类或一个特定的处理程序方法，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。



# 5. RestFul风格

RestFul风格就是一个资源定位及资源操作的风格。基于这个风格设计的软件的可以更简洁，更有层次感，更易于实现缓存等机制。

两种写法

```java
@RequestMapping(value = "/add/{a}/{b}",method = RequestMethod.GET)
等价于
@GetMapping("/add/{a}/{b}")
```

```java
@RequestMapping(value = "/add/{a}/{b}",method = RequestMethod.POST)
等价于
@PostMapping("/add/{a}/{b}")
```

```java
package com.zyf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestFulController {
    @RequestMapping(value = "/add/{a}/{b}",method = RequestMethod.GET)
    public String test1(@PathVariable int a, @PathVariable int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", "结果为" + res);
        return "test";
    }
}
```

**@PathVariable注解映射RequestMapping中value对应的值，传入到方法中。**

# 6. 结果跳转方式

- 设置ModelAndView对象，根据view的名称，和视图解析器跳转指定的页面

- 页面：{视图解析器前缀}+view+{视图解析器后缀}

## 6.1 通过SpringMVC来实现转发和重定向（有视图解析器）

虽然有视图解析器，但转发前写上forward:加上全路径，就不会走视图解析器，因此可以正常进行转发

```java
@RequestMapping("/hello1")
public String test01(Model model){
    model.addAttribute("msg","zyf");
    return "forward:/WEB-INF/jsp/hello.jsp";
}
```

有视图解析器的情况下，返回字符串前不写forward:，就会执行视图解析器进行拼接生成路径，因此无法正常进行转发。

```java
@RequestMapping("/hello1")
public String test01(Model model){
    model.addAttribute("msg","zyf");
    return "/WEB-INF/jsp/hello.jsp";
}
```

- **WEB-INF目录：** 是Java的WEB应用的安全目录。所谓安全就是客户端无法访问，只有服务端可以访问的目录。
- 因为重定向实际上是客户端再次向服务器发送请求，所以无法访问WEB-INF目录。

- 重定向不会经过视图解析器，因此返回字符串前要加上redirect:

## 6.2 通过SpringMVC来实现转发和重定向（无视图解析器）

```java
@RequestMapping("/hello1")
public String test01(Model model){
    model.addAttribute("msg","zyf");
    return "/WEB-INF/jsp/hello.jsp";
}
```

- 没有视图解析器，没有`forward:`，路径不会被拼接，因此可以正常访问

```java
@RequestMapping("/hello1")
public String test01(Model model){
    model.addAttribute("msg","zyf");
    return "forward:/WEB-INF/jsp/hello.jsp";
}
```

- 这种写法，在有视图解析器和无视图解析器的情况下都可以正常进行转发。

```java
@RequestMapping("/hello1")
public String test01(Model model){
    model.addAttribute("msg","zyf");
    return "hello";
}
```

- 无视图解析器，返回的字符串不会被拼接，因此无法正常进行转发。

# 7. 接收请求参数

## 7.1 提交域名称和处理方法的参数不一致

使用@RequestParam

提交数据：http://localhost:8080/springmvc_03_annotation_war_exploded/hello?userName=zyf

处理方法

```java
@Controller
public class ResultSpringMVC {
    @RequestMapping(value = "/hello")
    public String test(@RequestParam("userName") String name, Model model) {
        System.out.println(name);
        model.addAttribute("msg", name);
        return "hello";
    }
}
```

后台输出zyf

## 7.2 提交的是一个对象

- 要求提交的表单域和对象的属性名一致，参数使用对象即可。
- 使用对象的话，前端传递的参数名和对象名必须一致，否则就是null。

# 8. 数据回显到前端

## 8.1 ModelAndView

- 可以在存储数据的同时，可以进行设置返回逻辑视图，进行控制展示层的跳转。

## 8.2 ModelMap

- modelmap继承了LinkedHashMap，除了自身方法外，同样继承了LinkedMap的方法和特性。

## 8.3 通过Model

- 只有几个方法，只适合用于存储数据，简化了新手对于Model对象的操作和理解。

# 9. 解决乱码问题

在配置文件中配置Spring的乱码过滤器

```xml
<filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <!--  设置项目中使用的字符编码 -->
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
    <!-- 强制请求对象（HttpServletRequest）使用 encoding编码的值  -->
    <init-param>
        <param-name>forceRequestEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
    <!--强制应答对象（HttpServletResponse）使用encoding编码的值 -->
    <init-param>
        <param-name>forceResponseEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

# 10. JSON

- 轻量级的数据交换格式
- 采用完全独立于编程语言的文本格式来存储和表示数据。
- 简洁清晰的层次结构
- 可以有效提升网络传输的效率

```HTML
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Document</title>

        <script type="text/javascript">
            var person = {
                name: "zyf",
                age: 19,
                sex: "男"
            }
            //将js对象转换为json对象
            var json = JSON.stringify(person)
            console.log(json)

            //将json转换为javascript对象
            var person1 = JSON.parse(json)
            console.log(person1)
        </script>

    </head>

    <body>

    </body>

</html>
```

- 前端的JSON.stringify将对象转为json类型。
- JSON.parse将json字符串解析为一个对象。

## 10.1 Jackson

不使用json，来传递字符串

- ResponseBody就不会走视图解析器，会直接返回一个字符串
- **@RestController = @Controller + @ResponseBody**

```java
@RequestMapping("/j1")
@ResponseBody
//加了ResponseBody就不会走视图解析器，会直接返回一个字符串
public String json1(){
    //创建一个对象
    User user = new User("周大哥",12,"男");
    return user.toString();
}
```

使用json并使用注解来解决json乱码的问题

```java
@RequestMapping(value = "/j1",produces = "application/json;charset=utf-8")
@ResponseBody
//加了ResponseBody就不会走视图解析器，会直接返回一个字符串
public String json1() throws JsonProcessingException {
    //创建一个对象
    User user = new User("周大哥",12,"男");
    ObjectMapper mapper = new ObjectMapper();
    String str = mapper.writeValueAsString(user);
    return str;
}
```

使用配置文件来解决json乱码的问题

```xml
<mvc:annotation-driven>
   <mvc:message-converters register-defaults="true">
       <bean class="org.springframework.http.converter.StringHttpMessageConverter">
           <constructor-arg value="UTF-8"/>
       </bean>
       <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
           <property name="objectMapper">
               <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                   <property name="failOnEmptyBeans" value="false"/>
               </bean>
           </property>
       </bean>
   </mvc:message-converters>
</mvc:annotation-driven>
```

使用json传递集合到前端

```java
@RequestMapping(value = "/j2")
@ResponseBody
//加了ResponseBody就不会走视图解析器，会直接返回一个字符串
public String json2() throws JsonProcessingException {
    ArrayList<User> users = new ArrayList<>();
    User user1 = new User("周大哥1",12,"男");
    User user2 = new User("周大哥2",13,"男");
    User user3 = new User("周大哥3",12,"男");
    User user4 = new User("周大哥4",12,"男");
    users.add(user1);
    users.add(user2);
    users.add(user3);
    users.add(user4);
    ObjectMapper mapper = new ObjectMapper();
    String str = mapper.writeValueAsString(users);
    return str;
}
```

```
[{"name":"周大哥1","age":12,"sex":"男"},{"name":"周大哥2","age":13,"sex":"男"},{"name":"周大哥3","age":12,"sex":"男"},{"name":"周大哥4","age":12,"sex":"男"}]
```

使用json传递日期到前端

```java
@RequestMapping(value = "/j3")
@ResponseBody
//加了ResponseBody就不会走视图解析器，会直接返回一个字符串
public String json3() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(new Date());
}
```

```
1677652089716
输出为时间戳
```

使用SimpleDateFormat来解决

```java
@RequestMapping(value = "/j3")
@ResponseBody
//加了ResponseBody就不会走视图解析器，会直接返回一个字符串
public String json3() throws JsonProcessingException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return new ObjectMapper().writeValueAsString(sdf.format(new Date()));
}
```

```
"2023-03-01 14:30:57"
```

使用mapper配置，mapper默认使用时间戳去表示

```java
public String json3() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    mapper.setDateFormat(sdf);
    return mapper.writeValueAsString(new Date());
}
```

## 10.2 JsonUtils工具类

```java
package com.zyf.utlis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonUtils {
    public static String getJson(Object object, String sdf) throws JsonProcessingException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        mapper.setDateFormat(simpleDateFormat);
        return mapper.writeValueAsString(object);
    }

    public static String getJson(Object object) throws JsonProcessingException {
        String str = getJson(object, "yyyy-MM-dd HH:mm:ss");
        return str;
    }
}
```

controller

```java
@RequestMapping(value = "/j2")
@ResponseBody
//加了ResponseBody就不会走视图解析器，会直接返回一个字符串
public String json2() throws JsonProcessingException {
    ArrayList<User> users = new ArrayList<>();
    User user1 = new User("周大哥1",12,"男");
    User user2 = new User("周大哥2",13,"男");
    User user3 = new User("周大哥3",12,"男");
    User user4 = new User("周大哥4",12,"男");
    users.add(user1);
    users.add(user2);
    users.add(user3);
    users.add(user4);
    return JsonUtils.getJson(users);
}

@RequestMapping(value = "/j3")
@ResponseBody
//加了ResponseBody就不会走视图解析器，会直接返回一个字符串
public String json3() throws JsonProcessingException {
    return JsonUtils.getJson(new Date());
}
```

## 10.3 Fastjson

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.66</version>
</dependency>
```

```java
@RequestMapping(value = "/j2")
@ResponseBody
//加了ResponseBody就不会走视图解析器，会直接返回一个字符串
public String json2() throws JsonProcessingException {
    ArrayList<User> users = new ArrayList<>();
    User user1 = new User("周大哥1",12,"男");
    User user2 = new User("周大哥2",13,"男");
    User user3 = new User("周大哥3",12,"男");
    User user4 = new User("周大哥4",12,"男");
    users.add(user1);
    users.add(user2);
    users.add(user3);
    users.add(user4);
    String s = JSON.toJSONString(users);
    return s;
}
```



# 11. 项目搭建

## 11.1 环境依赖

- junit
- 数据库驱动
- 连接池
- servlet，jsp
- mybatis、mybatis-spring，spring

```xml
<dependencies>
    <!--Junit-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
    <!--数据库驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.47</version>
    </dependency>
    <!-- 数据库连接池 -->
    <dependency>
        <groupId>com.mchange</groupId>
        <artifactId>c3p0</artifactId>
        <version>0.9.5.2</version>
    </dependency>
    <!--Servlet - JSP -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
    <!--Mybatis-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.2</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>2.0.2</version>
    </dependency>
    <!--Spring-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
</dependencies>
```

## 11.2 数据库连接

## 11.3 建立包结构

- com.zyf.dao
- com.zyf.pojo
- com.zyf.controller
- com.zyf.service
- com.zyf.utils

## 11.4 resources配置文件

- mybatis-config.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  </configuration>
  ```

- applicationContext.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
  </beans>
  ```

- database.properties

  ```properties
  jdbc.driver=com.mysql.jdbc.Driver
  jdbc.url=jdbc:mysql://localhost:3306/ssmbuild?useSSL=true&useUnicode=true&characterEncoding=utf8
  jdbc.username=root
  jdbc.password=zyf2563085
  ```

## 11.5 编写实体类（bean）和mapper接口（dao层）

实体类

```java
package com.zyf.pojo;


public class Books {
    private int bookID;

    private String bookName;

    private int bookCounts;

    private String detail;

    public Books(int bookID, String bookName, int bookCounts, String details) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookCounts = bookCounts;
        this.detail = details;
    }

    public Books() {
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookCounts() {
        return bookCounts;
    }

    public void setBookCounts(int bookCounts) {
        this.bookCounts = bookCounts;
    }

    public String getDetails() {
        return detail;
    }

    public void setDetails(String details) {
        this.detail = details;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                ", bookCounts=" + bookCounts +
                ", details='" + detail + '\'' +
                '}';
    }
}
```

mapper接口

```java
package com.zyf.mapper;

import com.zyf.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {

    //增加一本书
    int addBook(Books books);
    //删除一本书
    int deleteBookById(@Param("book_id") int id);
    //更新一本书
    int updateBook(Books books);
    //查询一本书
    Books queryBookById(@Param("BookID") int id);
    //查询全部的书
    List<Books> queryAllBook();
}
```

mapper配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zyf.mapper.BookMapper">
    <insert id="addBooks" parameterType="Books">
        INSERT INTO ssmbuild.books (book_name, book_counts, detail)
        VALUES (#{bookName}, #{bookCounts}, #{detail});
    </insert>


    <delete id="deleBookById" parameterType="int">
        DELETE FROM ssmbuild.books WHERE book_id = #{book_id};
    </delete>

    <update id="updateBook" parameterType="Books">
        UPDATE ssmbuild.books SET book_name = #{bookName}, book_counts = #{bookCounts},
                                  detail = #{detail} WHERE book_id = #{bookID};
    </update>

    <select id="queryBookById" resultType="Books">
        SELECT * FROM ssmbuild.books WHERE book_id = #{BookID};
    </select>

    <select id="queryAllBook" resultType="Books">
        SELECT * FROM ssmbuild.books;
    </select>

</mapper>
```

编写完mapper接口，注册mapper

```xml
<mappers>
    <mapper class="com.zyf.mapper.BookMapper"/>
</mappers>
```

## 11.6 service层

BookService接口

```java
package com.zyf.service;

import com.zyf.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookService {
    //增加一本书
    int addBook(Books books);
    //删除一本书
    int deleteBookById(int id);
    //更新一本书
    int updateBook(Books books);
    //查询一本书
    Books queryBookById(int id);
    //查询全部的书
    List<Books> queryAllBook();
}
```

BookService接口的实现类

```java
package com.zyf.service;

import com.zyf.mapper.BookMapper;
import com.zyf.pojo.Books;

import java.util.List;
//service层调Dao层
public class BookServiceImpl implements BookService{
    private BookMapper bookMapper;

    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public int addBook(Books books) {
        return bookMapper.addBook(books);
    }

    @Override
    public int deleteBookById(int id) {
        return bookMapper.deleteBookById(id);
    }

    @Override
    public int updateBook(Books books) {
        return bookMapper.updateBook(books);
    }

    @Override
    public Books queryBookById(int id) {
        return bookMapper.queryBookById(id);
    }

    @Override
    public List<Books> queryAllBook() {
        return bookMapper.queryAllBook();
    }
}
```

## 11.7 Spring配置

### 11.7.1 配置spring-dao.xml

- 关联数据库配置文件

  ```xml
  <!--整合数据库配置文件-->
      <context:property-placeholder location="classpath:database.properties"/>
  ```

- 连接池

  ```xml
  <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
      <property name="driverClass" value="${jdbc.driver}"/>
      <property name="jdbcUrl" value="${jdbc.url}"/>
      <property name="user" value="${jdbc.username}"/>
      <property name="password" value="${jdbc.password}"/>
      <property name="maxPoolSize" value="30"/>
      <property name="minPoolSize" value="10"/>
      <!--连接池关闭后，不自动提交-->
      <property name="autoCommitOnClose" value="false"/>
      <!--获取连接超时时间-->
      <property name="checkoutTimeout" value="10000"/>
      <!--获取连接失败重新连接的次数-->
      <property name="acquireRetryAttempts" value="2"/>
  </bean>
  ```

- sqlSessionFactory

  ```java
  <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <property name="dataSource" ref="dataSource"/>
      <!--mybatis绑定配置文件-->
      <property name="configLocation" value="classpath:mybatis-config.xml"/>
  </bean>
  ```

- dao接口注入到Spring容器

  ```xml
  <!--配置dao接口扫描包，动态实现了dao接口注入到Spring容器中-->
  <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
      <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
      <!--扫描dao包-->
      <property name="basePackage" value="com.zyf.mapper"/>
  </bean>
  ```

### 11.7.2 配置spring-service.xml

- 将业务类注入到spring中

  ```xml
  <!--扫描包，注解-->
  <context:component-scan base-package="com.zyf.service"/>
  <!--将所有的业务类注入到Spring-->
  <bean id="BookServiceImpl" class="com.zyf.service.BookServiceImpl">
      <property name="bookMapper" ref="bookMapper"/>
  </bean>
  ```

  若ref标红，则需要import关联相关的dao配置文件

- 声明式事务配置

  ```xml
  <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
      <!--注入数据源-->
      <property name="dataSource" ref="dataSource"/>
  </bean>
  ```

  

# 12. AJAX技术

- AJAX是一种无需重新加载整个网页的情况下，能够更新网页的技术。
- 不是一门语言，是一种用于创建更好更快交互性更强的Web应用程序的技术。

## 12.1 引入jquery包

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230307185619375.png" alt="image-20230307185619375" style="zoom:50%;" />

## 12.2 编写后端

```java
@RequestMapping("/a1")
public void a1(String name, HttpServletResponse response) throws IOException {
    System.out.println("a1:param"+name);
    if("zyf".equals(name)){
        response.getWriter().print("true");
    }else {
        response.getWriter().print("false");
    }
}
```

a1接收前端发来的请求，然后对请求的数据进行处理，并对不同的请求返回数据。

## 12.3 编写前端

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.6.3.js">

    </script>

    <script>
        function a() {
            $.post({
                url: "${pageContext.request.contextPath}/a1",
                // 后的从data中取数据
                data: {"name": $("#username").val()},
                success: function (data) {
                    alert(data)
                }
            })
        }
    </script>
</head>
<body>
<%--失去焦点的时候，发起一个请求(信息)到后台--%>
用户名:<input type="text" id="username" onblur="a()">
</body>
</html>
```

文本框定义了一个失去焦点事件，当事件出发，前端会给后端发送请求。

## 12.4 AJAX异步加载数据

前端

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.6.3.js">

    </script>

    <script>
        $(function () {
            $("#btn").click(function () {
                $.post({
                    url: "${pageContext.request.contextPath}/a2",
                    // 后的从data中取数据
                    success: function (data) {
                        console.log(data)
                        var html = "";
                        for (let i = 0; i < data.length; i++) {
                            html += "<tr>"
                                + "<td>" + data[i].userName + "</td>"
                                + "<td>" + data[i].age + "</td>"
                                + "<td>" + data[i].sex + "</td>"
                                + "</tr>"
                        }
                        $("#content").html(html);
                    }
                })
            })
        })
    </script>

</head>
<body>

<input type="button" value="加载数据" id="btn">
<table>
    <tr>
        <td>姓名</td>
        <td>年龄</td>
        <td>性别</td>
    </tr>

    <tbody id="content">

    </tbody>
</table>
</body>
</html>
```

后端

```java
@RequestMapping("/a2")
public List<User> a2() {
    ArrayList<User> userArrayList = new ArrayList<>();
    //添加数据
    userArrayList.add(new User("zyf", 1, "男"));
    userArrayList.add(new User("jack", 2, "男"));
    userArrayList.add(new User("tom", 23, "男"));
    //@RestController自动将list转换为json
    return userArrayList;
}
```

## 12.5 登录提示效果

前端 

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.6.3.js"></script>


    <script>
        function a1() {
            $.post({
                url: "${pageContext.request.contextPath}/a3",
                data: {'name': $("#name").val()},
                success: function (data) {
                    if (data.toString() == 'OK') {
                        $("#userInfo").css("color", "green");
                    } else {
                        $("#userInfo").css("color", "red");
                    }
                    $("#userInfo").html(data);
                }
            });
        }

        function a2() {
            $.post({
                url: "${pageContext.request.contextPath}/a3",
                data: {'pwd': $("#pwd").val()},
                success: function (data) {
                    if (data.toString() == 'OK') {
                        $("#pwdInfo").css("color", "green");
                    } else {
                        $("#pwdInfo").css("color", "red");
                    }
                    $("#pwdInfo").html(data);
                }
            });
        }
    </script>

</head>
<body>

<p>
    用户名：<input type="text" id="name" onblur="a1()">
    <span id="userInfo"></span>
</p>

<p>
    密码：<input type="text" id="pwd" onblur="a2()">
    <span id="pwdInfo"></span>
</p>

</body>
</html>
```

后端

```java
@RequestMapping("/a3")
public String a3(String name, String pwd) {
    String msg = "";
    if (name != null) {
        if ("zyf".equals(name)) {
            msg = "OK";
        } else {
            msg = "用户名不存在";
        }
    }

    if (pwd != null) {
        if ("123456".equals(pwd)) {
            msg = "OK";
        } else {
            msg = "输入密码有误";
        }
    }
    return msg;
}
```

# 13. 拦截器

- 拦截器是SpringMVC自己的，只有使用了SpringMVC框架的工程才能使用
- 拦截器只会拦截访问的控制器方法，如果访问的是jsp/html/css/image/js是不会进行拦截的
- 拦截器是AOP思想的具体应用
- 自定义拦截器，必须实现HandlerInterceptor接口

**创建一个Interceptor类实现HandlerInterceptor接口**

```java
package com.zyf.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    //return true执行下一个拦截器，放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("================处理前=====================");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("================处理后=====================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("================清理=====================");
    }
}
```

preHandle方法，返回true代表放行，返回false不会放行。

拦截器配置

```xml
<mvc:interceptors>
    <mvc:interceptor>
<!--            包括这个请求下面的所有请求-->
        <mvc:mapping path="/**"/>
        <bean class="com.zyf.config.MyInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```

# 14 SpringMVC理解

​     SpringMVC是基于Java的Web应用程序开发框架，它是Spring Framework的一个模块，主要用于开发MVC架构的Web应用程序。SpringMVC框架通过提供一组丰富的组件和API，简化了Web应用程序的开发，提高了开发效率和代码质量。

​     SpringMVC框架的核心是DispatcherServlet，它是整个框架的入口，负责接收HTTP请求、调用相应的处理器方法并返回响应结果。在DispatcherServlet的处理过程中，涉及到多个组件的协作，包括HandlerMapping、HandlerAdapter、ViewResolver、LocaleResolver、ThemeResolver等，它们各司其职，共同完成请求处理和响应结果的生成。

​       在SpringMVC开发中，开发人员主要需要编写Controller类和相应的处理器方法，并通过配置文件或Java代码注册到SpringMVC框架中。Controller类中的处理器方法可以接收HTTP请求参数、调用业务逻辑、返回响应结果等，开发人员可以根据实际需求自由组合和调用这些处理器方法，以实现Web应用程序的各种功能。

1. MultipartResolver文件处理器：MultipartResolver是用于处理HTTP请求中的文件上传的接口。它定义了处理文件上传所需的方法，具体实现由不同的MultipartResolver实现类来完成。
2. LocaleResolver语言环境处理器：用于解析客户端的语言环境信息并返回对应的Locale对象。它允许开发人员在应用程序中根据客户端的语言环境进行不同的处理，例如显示不同的语言文本、使用不同的日期格式等。
3. ThemeResolver主题处理器：析当前请求所使用的主题（Theme）的组件。主题是指应用程序中使用的外观样式或视觉风格，例如颜色、字体、图片等，可以根据不同的主题来展示不同的视觉效果。
4. HandlerMapping处理器映射器：用于根据请求的URL映射到处理请求的Controller中的处理器方法。配置拦截器（Interceptor），以便在请求到达Controller之前或之后执行一些额外的处理逻辑。
5. HandlerAdapter处理器适配器：HandlerAdapter是Spring MVC中用于将请求映射到具体的处理器方法并执行该方法的接口，它是Spring MVC中的核心组件之一，不同的HandlerAdapter实现类可以支持不同类型的处理器方法。
6. HandlerExceptionResolver异常处理器：用于处理在请求处理过程中抛出的异常。它允许开发人员在应用程序中集中处理异常，并将异常信息转换为适当的响应格式返回给客户端。
7. RequestToViewNameTranslater视图名称翻译器：用于将请求的URL转换为视图名称（View Name），以便在视图解析器（ViewResolver）中查找匹配的视图（View）。
8. ViewResolver视图解析其：用于将视图名称（View Name）解析为实际的视图（View）实例。它将逻辑视图名称转换为物理视图，以便返回给客户端。
9. FlashMapManager参数传递处理器：FlashMapManager是用于管理FlashMap对象的接口。FlashMap对象是用于在请求之间传递数据的一种机制，它可以在一个请求中设置一些属性，在下一个请求中获取这些属性并使用。具体的FlashMapManager实现类可以使用不同的机制来管理FlashMap对象，例如使用Cookie、使用Session、使用RedirectAttributes等。

# i18n

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230415151101106.png" alt="image-20230415151101106" style="zoom:50%;" />

