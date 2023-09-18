

# Mybatis

# 1. mybatis程序

## 1.1 环境搭建

- 创建maven父工程，jdk8
- 引入依赖，配置pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>Mybtis-Zyf</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>mybatis-01</module>
    </modules>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>


    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.46</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>

</project>
```

- 编写mybatis的核心配置文件（mybatis-config.xml）

  <img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230216141955512.png" alt="image-20230216141955512" style="zoom: 50%;" />

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<!--核心配置文件-->
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!--transactionManager 事务管理-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <!--jdbc驱动-->
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;userUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="zyf2563085"/>
            </dataSource>
        </environment>
    </environments>
</configuration>
```

- 编写mybatis工具类

```java
package com.zyf.utils;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: 周杨凡
 * @date: 2023-02-16
 * 工具类
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //使用mybatis第一步，构建sqlSessionFactory对象
            String resource = "mybatis-config.xml";
            InputStream resourceAsStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```

## 1.2 编写代码

1. 实体类bean

   ```java
   package com.zyf.bean;
   
   /**
    * @author: 周杨凡
    * @date: 2023-02-16
    */
   public class User {
       private int id;
   
       private String name;
   
       private String pwd;
   
       public User(int id, String name, String pwd){
           this.id = id;
           this.name = name;
           this.pwd = pwd;
       }
   
       public int getId() {
           return id;
       }
   
       public String getName() {
           return name;
       }
   
       public String getPwd() {
           return pwd;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public void setPwd(String pwd) {
           this.pwd = pwd;
       }
   
       public void setId(int id) {
           this.id = id;
       }
   
       @Override
       public String toString() {
           return "User{" +
                   "id=" + id +
                   ", name='" + name + '\'' +
                   ", pwd='" + pwd + '\'' +
                   '}';
       }
   }
   ```

   

2. DAO类

   ```java
   import java.util.List;
   
   /**
    * @author: 周杨凡
    * @date: 2023-02-16
    */
   public interface UserDao {
       List<User> getUserList();
   }
   ```

   

3. 接口实现类由原来的UserDaoImpl转变为一个Mapper配置文件

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!--    namespace绑定一个对应的DAO/Mapper接口-->
   <mapper namespace="com.zyf.dao.UserDao">
   <!-- 查询语句-->
       <select id="getUserList" resultType="com.zyf.bean.User">
           select * from mybatis.user
       </select>
   </mapper>
   ```

   

4. Junit测试

   错误：org.apache.ibatis.binding.BindingException: Type interface com.zyf.dao.UserDao is not known to the MapperRegistry.

   **在核心配置文件mybatis-config.xml中配置mappers**

   ```xml
       <mappers>
           <mapper resource="com/zyf/dao/UserMapper.xml"/>
       </mappers>
   ```
   
   **测试类**
   
   ```java
   
   /**
    * @author: 周杨凡
    * @date: 2023-02-16
    */
   public class UserDaoTest {
       @Test
       public void test(){
           //获得sqlSession对象
           SqlSession sqlSession = MybatisUtils.getSqlSession();
           //方式一：执行sql
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           List<User> userList = mapper.getUserList();
           for (User user : userList) {
               System.out.println(user);
           }
           sqlSession.close();
       }
   }
   ```
   
   maven导出资源错误，在pom.xml中加入
   
   ```xml
   <build>
           <resources>
               <resource>
                   <directory>src/main/resources</directory>
                   <includes>
                       <include>**/*.properties</include>
                       <include>**/*.xml</include>
                   </includes>
                   <filtering>true</filtering>
               </resource>
               <resource>
                   <directory>src/main/java</directory>
                   <includes>
                       <include>**/*.properties</include>
                       <include>**/*.xml</include>
                   </includes>
                   <filtering>true</filtering>
               </resource>
           </resources>
       </build>
   ```
   
   

## 1.3 CRUD

**1. namespace**

namespace中的包名要与Mapper接口中的包名一致

**2. select**

- id：就是对应的namespace中的方法；
- resultType：SQL语句执行的返回值；
- parameterType: 参数类型；

**3. insert**

编写接口

```java
package com.zyf.dao;

import com.zyf.bean.User;

import java.util.List;

/**
 * @author: 周杨凡
 * @date: 2023-02-16
 */
public interface UserMapper {

    /**
     * 获取全部用户
     * @return 查询到所有的用户集合
     */
    List<User> getUserList();

    /**
     * @param id 用户的id
     * @return 查询到的用户的dao类
     */
    User getUserById(int id);

    /**
     * @param user
     * @return 大于0代表插入成功。
     */
    int addUser(User user);
}
```

编写sql语句

```xml
<!--对象中的属性可以直接取出来-->
    <insert id="addUser" parameterType="com.zyf.bean.User">
        insert into mybatis.user (id, name, pwd) values (#{id},#{name},#{pwd});
    </insert>
```

测试

**增删改必要要提交事务**

```JAVA
public void addUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int rs = mapper.addUser(new User(4, "lucy", "123123"));
        String result = rs > 0 ? "插入成功" : "插入失败";
        System.out.println(result);
        //事务提交
        sqlSession.commit();
        sqlSession.close();
    }
```

**4.update**

```xml
<!--对象中的属性可以直接取出来-->
<update id="updateUser" parameterType="com.zyf.bean.User">
    update mybatis.user set name=#{name}, pwd=#{pwd} where id = #{id};
</update>
```

**5.delete**

```xml
<delete id="delUser" parameterType="int">
    delete from mybatis.user where id = #{id};
</delete>
```

**6.万能map**

假设我们的实体类，或者数据库中的表字段或者参数过多，我们应当使用map。

接口

```java
int addUserByMap(Map<String, Object> map);
```

SQL语句

```xml
<insert id="addUserByMap" parameterType="map">
    insert into mybatis.user (id, name, pwd) values (#{userId},#{userName},#{userPwd});
</insert>
```

测试代码

```java
@Test
public void addUserByMap(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    HashMap<String, Object> map = new HashMap<>();
    map.put("userId",5);
    map.put("userName","ikun");
    map.put("userPwd","123457");
    int rs = mapper.addUserByMap(map);
    String result = rs > 0 ? "插入成功" : "插入失败";
    System.out.println(result);
    //事务提交
    sqlSession.commit();
    sqlSession.close();
}
```

Map传递参数，直接在sql中取出key即可

对象传递参数，直接在sql中取对象的属性即可

只有一个基本类型参数的情况下，可以直接在sql中取到

多个参数用Map或者**注解**

## 1.4 模糊查询

1. Java执行代码的时候，传递通配符%%。

   ```java
   List<User> userList = mapper.getUserLike("%周%");
   ```

2. 在SQL中拼接使用通配符。

   ```xml
   select * from user where name like "%"#{value}"%";
   ```

# 2. 配置解析

## 2.1 核心配置文件

- mybatis-config.xml

- Mybatis的配置文件会深深影响MyBatis行为和属性信息。

  ```xml
  configuration（配置）
  properties（属性）
  settings（设置）
  typeAliases（类型别名）
  typeHandlers（类型处理器）
  objectFactory（对象工厂）
  plugins（插件）
  environments（环境配置）
  environment（环境变量）
  transactionManager（事务管理器）
  dataSource（数据源）
  databaseIdProvider（数据库厂商标识）
  mappers（映射器）
  ```

## 2.2 环境配置environments

- MyBatis 可以配置成适应多种环境。
- 尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。
- transactionManager事务管理默认为JDBC
- dataSource默认为连接池POOLED

## 2.3 属性（properties）

我们可以通过properties属性来实现引用配置文件。

编写一个数据库配置文件：db.properties

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;userUnicode=true&amp;characterEncoding=UTF8
username=root
password=zyf2563085
```

核心配置文件

```xml
<configuration>
    <properties resource="db.properties"/>
    <environments default="development">
        <!-- default用于切换环境       -->
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!--transactionManager 事务管理-->
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <!--jdbc驱动-->
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>

        <environment id="test">
            <transactionManager type="JDBC"/>
            <!--transactionManager 事务管理-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <!--jdbc驱动-->
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;userUnicode=true&amp;characterEncoding=UTF8"/>
                <property name="username" value="root"/>
                <property name="password" value="zyf2563085"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/zyf/dao/UserMapper.xml"/>
    </mappers>

</configuration>
```

如果一个属性在不只一个地方进行了配置，那么MyBatis 将按照下面的顺序来加载：

1. 首先读取在 properties 元素体内指定的属性。
2. 然后根据 properties 元素中的 resource 属性读取类路径下属性文件，或根据 url 属性指定的路径读取属性文件，并**覆盖**之前读取过的同名属性。
3. 最后读取作为方法参数传递的属性，并覆盖之前读取过的同名属性。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<!--核心配置文件-->
<configuration>
    <properties resource="db.properties">
        <property name="username" value="root"/>
        <property name="password" value="11111"/>
    </properties>
    <environments default="development">
        <!-- default用于切换环境       -->
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!--transactionManager 事务管理-->
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <!--jdbc驱动-->
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>

        <environment id="test">
            <transactionManager type="JDBC"/>
            <!--transactionManager 事务管理-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <!--jdbc驱动-->
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;userUnicode=true&amp;characterEncoding=UTF8"/>
                <property name="username" value="root"/>
                <property name="password" value="zyf2563085"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/zyf/dao/UserMapper.xml"/>
    </mappers>

</configuration>
```

## 2.4 别名

- **类型别名可为 Java 类型设置一个缩写名字。 **

- **它仅用于 XML 配置，意在降低冗余的全限定类名书写。**

**2.4.1 第一种方法typeAlias**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<!--核心配置文件-->
<configuration>
    <properties resource="db.properties"/>

<!--    给实体类取别名-->
    <typeAliases>
        <typeAlias type="com.zyf.bean.User" alias="user"/>
    </typeAliases>


    <environments default="development">
        <!-- default用于切换环境 -->
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!--transactionManager 事务管理-->
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <!--jdbc驱动-->
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/zyf/dao/UserMapper.xml"/>
    </mappers>
</configuration>
```

**2.4.2 第二种方法**

可以指定一个包名，Mybatis会在这个包下搜索需要的JavaBean，比如：扫描实体类的包，它的默认别名就为这个类的类名首字母小写。

```xml
<typeAliases>
    <package name="com.zyf.bean"/>
</typeAliases>
```

1. 实体类比较少的时候，使用第一种方法，如果实体类非常多，建议使用第二种方法。
2. 第一种可以DIY，第二种不行。
3. 当使用第二种方法，如果非要改成自定因，使用注解**@Alias("user")**

## 2.5 设置

![image-20230220180747759](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230220180747759.png)

## 2.6 其他设置

## 2.7 映射器（mappers）

1. MyBatis 到哪里找到SQL语句。
2. 自动查找资源方面，Java 并没有提供一个很好的解决方案，所以最好的办法是直接告诉 MyBatis 到哪里去找映射文件。

方式一：使用相对于类路径的资源引用

```xml
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
```



方式二：使用class文件绑定注册

```xml
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
```

**注意点：**

- 接口和他的Mapper配置文件必须同名；
- 接口和他的Mapper配置文件必须在同一个包下。

方式三：将包内的映射器接口全部注册为映射器

```xml
<mappers>
    <package name="com.zyf.dao"/>
</mappers>
```

**注意点：**

- 接口和他的Mapper配置文件必须同名；
- 接口和他的Mapper配置文件必须在同一个包下。

# 3. 生命周期

声明周期和作用域是至关重要的，因为错误的使用会导致非常严重的并发问题。

**3.1、SqlSessionFactoryBuilder：**

- 一旦创建了sqlSessionFactoryBuilder，就不再使用它了；
- 局部变量。

**3.2、SqlSessionFactory：**

- 可以认为是：数据库连接池；
- SqlSessionFactory一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃或重新创建另外一个实例；
- SqlSessionFactory的作用域为应用作用域；
- 单例模式或者是静态单例模式

**3.3、SqlSession：**

- 连接到连接池的一个请求；
- SqlSession实例不是线程安全的，不能被共享，因此最佳作用域是在方法或一个请求中；
- 用完之后赶紧关闭，否则资源被占用（放到finally块中）；
- 每一个Mapper代表着一个具体的业务。



<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230220190131875.png" alt="image-20230220190131875" style="zoom:67%;" />

# 4. 属性名与字段名不一致问题

## 4.1 解决方法

- SQL语句中使用AS起别名
- 结果集映射

```xml
<resultMap id="UserMap" type="User">
<!--column数据库中的字段-->
    <result column="id" property="id"></result>
    <result column="name" property="name"></result>
    <result column="pwd" property="password"></result>
</resultMap>
<!--查询语句-->
<select id="getUserById" parameterType="int" resultType="user" resultMap="UserMap">
    select * from mybatis.user where id = #{id};
</select>
```

## 4.2 resultMap

- 元素是 MyBatis 中最重要最强大的元素。
- ResultMap 的设计思想是，对简单的语句做到零配置，对于复杂一点的语句，只需要描述语句之间的关系就行了。

# 5. 日志

## 5.1 日志工厂

如果一个数据库操作，出现了异常，我们需要排错，日志就是最好的助手。

- 曾经：sout，debug
- 现在：日志工厂
  - LOG4J
  - STDOUT_LOGGING
  - SLF4J

在Mybatis中具体使用哪个日志实现，在设置中设定。

## 5.2 STDOUT_LOGGING

标准输出日种子

```xml
<settings>
   <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```

![image-20230221100927099](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230221100927099.png)

## 5.3 LOG4J日志实现

Log4j是Apache的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是控制台、文件、GUI组件，甚至是套接口服务器、NT的事件记录器、UNIXSyslog守护进程等；我们也可以控制每一条日志的输出格式；**通过定义每一条日志信息的级别**，我们能够更加细致地控制日志的生成过程。最令人感兴趣的就是，这些可以**通过一个配置文件来灵活地进行配置，而不需要修改应用的代码**。

```XML
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

log4j.properties

```properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,console,file

#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=【%c】-%m%n

#文件输出的相关设置
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/kuang.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=【%p】【%d{yy-MM-dd}】【%c】%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```

配置setting

```xml
<settings>
    <setting name="logImpl" value="LOG4J"/>
</settings>
```

简单使用

1. 在要使用log4j的类中，导入包import org.apache.log4j.Logger;

2. 创建日志对象，参数为当前类的class

   ```java
   import org.apache.log4j.Logger;
   ```

3. 日志级别

   ```java
   logger.info("info:进入了testLog4J方法");
   logger.debug("debug:进入了testLog4J方法");
   logger.error("error:进入了testLog4J方法");
   ```

# 6. 分页

为什么要分页？

- 减少数据的处理量

## 6.1 使用limit进行分页（map传参）

```xml
<select id="getUserByLimit" parameterType="map" resultType="user" resultMap="UserMap">
    select * from mybatis.user limit #{startIndex}, #{pageSize};
</select>
```

接口

```java
List<User> getUserByLimit(Map<String, Integer> map);
```

测试

```java
@Test
public void getUserByLimitTest() {
    SqlSession sqlSession = null;
    try {
        sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("startIndex", 1);
        map.put("pageSize", 2);
        List<User> list = mapper.getUserByLimit(map);
        for (User user : list) {
            System.out.println(user);
        }
    } catch (Exception e) {
        throw new RuntimeException(e);
    } finally {
        sqlSession.close();
    }
}
```

```
User{id=3, name='jack', password='123123'}
User{id=4, name='lucy', password='123123'}
```

## 6.2 使用RowBounds分页

不再使用SQL实现分页

xml配置

```xml
<select id="getUserByRowBounds"  resultType="user" resultMap="UserMap">
    select * from mybatis.user;
</select>
```

测试类

```java
@Test
public void getUserByRowBoundsTest(){
    SqlSession sqlSession = null;
    try {
        RowBounds rowBounds = new RowBounds(1, 2);
        sqlSession = MybatisUtils.getSqlSession();
        List<User> list = 
                sqlSession.selectList("com.zyf.dao.UserMapper.getUserByRowBounds", null, rowBounds);
        for (User user : list) {
            System.out.println(user);
        }
    } catch (Exception e) {
        throw new RuntimeException(e);
    }finally {
        sqlSession.close();
    }
}
```

## 6.3 分页插件（PageHelper）

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230221154132839.png" alt="image-20230221154132839" style="zoom:33%;" />



- 只有紧跟在`PageHelper.startPage`方法后的**第一个**Mybatis的**查询（Select）**方法会被分页。
- 请不要在系统中配置多个分页插件(使用Spring时,`mybatis-config.xml`和`Spring<bean>`配置方式，请选择其中一种，不要同时配置多个分页插件)！
- 对于带有`for update`的sql，会抛出运行时异常，对于这样的sql建议手动分页，毕竟这样的sql需要重视。
- 分页插件不支持嵌套结果映射，由于嵌套结果方式会导致结果集被折叠，因此分页查询的结果在折叠后总数会减少，所以无法保证分页结果数量正确。

# 7. 使用注解开发

## 7.1 面向接口编程

- 在真实开发中，我们大多数会选择面向接口编程。
- 根本原因：解耦，可拓展，提高复用，分层开发中，上层不用管具体类的实现，大家都遵守共同的标准，使得开发变得容易，规范性更好。

面向接口编程的理解：

1. 接口更深层次的理解，应该是定义（规范、约束）与实现（实名分离的原则）的分离。
2. 接口的本身反映了系统设计人员对系统的抽象理解。
3. 接口由两类：
   - 第一类是对一个个体的抽象，它可对应一个抽象体。
   - 第二类是对一个个体某一方面的抽象，即形成一个抽象面。
4. 一个个体有可能有多个抽象面，抽象体与抽象面还是有区别的。

## 7.2 使用注解

- 注解直接在接口上实现

  ```java
  @Select("select * from mybatis.user")
  List<User> getUsersList();
  ```

- 需要在核心配置文件中绑定接口。

  ~~~xml
  <mappers>
      <mapper class="com.zyf.dao.UserMapper"/>
  </mappers>
  ~~~

  

## 7.3 使用注解CRUD

我们可以在工具类创建的时候自动提交事务。

```java
public static SqlSession getSqlSession(){
  return sqlSessionFactory.openSession(true);
}
```

### 7.3.1 带参数查询

```java
@Select("SELECT * FROM user WHERE id = #{id}")
User getUserById(@Param("id") int id);
```

查询语句参数不止一个时，所有参数前都要加上注解

```java
@Select("SELECT * FROM user WHERE id = #{id} AND name = #{name}")
User getUserById(@Param("id") int id, @Param("name") String name);
```

### 7.3.2 INSERT插入

```java
@Insert("INSERT INTO user(id,name,pwd) VALUES (#{id}, #{name}, #{pwd})")
int addUser(User user);
```

测试类

```java
@Test
public void addUserTest() {
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    int rs = mapper.addUser(new User(8, "李四", "123456"));
    String result = rs > 0 ? "添加成功" : "添加失败";
    System.out.println(result);
    sqlSession.close();
}
```

### 7.3.3 UPDATE修改

```java
@Update("UPDATE user SET name = #{name}, pwd = #{pwd} WHERE id = #{id}")
int updateUser(User user);
```

测试

```java
@Test
public void updateUser() {
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    int rs = mapper.updateUser(new User(8, "老李", "123457"));
    String result = rs > 0 ? "修改成功" : "修改失败";
    System.out.println(result);
    sqlSession.close();
}
```

### 7.3.4 DELETE删除

```java
@Delete("DELETE FROM user WHERE id = #{id}")
int deleteUser(@Param("id") int id);
```

测试

```java
@Test
public void deleteUser() {
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    int rs = mapper.deleteUser(5);
    String result = rs > 0 ? "删除成功" : "删除失败";
    System.out.println(result);
    sqlSession.close();
}
```



关于@Param()注解

- 基本类型的参数或者String类型，需要加上。
- 引用类型不需要添加。
- 如果只有一个基本类型话，可以忽略，但是建议加上。
- 在SQL语句中引用的是@Param("id")中设定的属性。

# 8. Lombok

Project Lombok 是一个 java 库，可自动插入您的编辑器和构建工具，为您的 java 增添趣味。永远不要再写另一个 getter 或 equals 方法，通过一个注解，您的类就有一个功能齐全的构建器，自动化您的日志变量，等等。

- 是一个java库
- 插件
- 构建工具
- 通过注解就有一个功能齐全的构建器

使用步骤：

1. 在IDEA中安装Lombok，Setting-> Plugins -> lombok

2. 导入JAR包

   ```xml
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.26</version>
   </dependency>
   ```

3. 注解

   ```
   @Getter and @Setter
   @FieldNameConstants
   @ToString
   @EqualsAndHashCode
   @AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
   @Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog
   @Data
   @Builder
   @SuperBuilder
   @Singular
   @Delegate
   @Value
   @Accessors
   @Wither
   @With
   @SneakyThrows
   @StandardException
   @val
   @var
   experimental @var
   @UtilityClass
   ```

   

## 8.1 注解

- @Data：get、set、equals
- @AllArgsConstructor：有参构造
- @NoArgsConstructor：无参构造
- @Getter：放在字段上，get方法
- @Setter：放在字段上，set方法



# 9.多对一查询(association)

```mysql
CREATE TABLE `teacher`(
		`id` INT(10) NOT NULL,
		`name`VARCHAR(30) DEFAULT NULL,
		PRIMARY KEY (`id`)student
)ENGINE=INNODB DEFAULT CHARSET=utf8


INSERT INTO teacher(`id`, `name`) VALUES (1, '秦老师');

CREATE TABLE `student`(
		`id` INT(10) NOT NULL,
		`name`VARCHAR(30) DEFAULT NULL,
		`tid` INT(10) DEFAULT NULL,
		PRIMARY KEY (`id`),
		KEY `fktid` (`tid`),
		CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8


INSERT INTO student(`id`, `name`, `tid`) VALUES (1, '小明', 1);
INSERT INTO student(`id`, `name`, `tid`) VALUES (2, '小红', 1);
INSERT INTO student(`id`, `name`, `tid`) VALUES (3, '小刚', 1);
INSERT INTO student(`id`, `name`, `tid`) VALUES (4, '小李', 1);
INSERT INTO student(`id`, `name`, `tid`) VALUES (5, '小军', 1);
```

表结构

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230222154814432.png" alt="image-20230222154814432" style="zoom:50%;" />

实体类

```java
@Data
public class Student {
    private Teacher teacher;
    private int id;
    private String name;
}

@Data
public class Teacher {
    private int id;
    private String name;
}

```

## 9.1 **查询嵌套处理**

StudentMapper接口

```java
public interface StudentMapper {
    //查询所有的学生信息，以及对应的老师的信息
    public List<Student> getStudent();
}
```

TeacherMapper接口

```java
public interface TeacherMapper {
    List<Teacher> getTeachers();
    Teacher getTeacherById(int id);
}
```

StudentMapper.xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--    namespace绑定一个对应的DAO/Mapper接口-->
<mapper namespace="com.zyf.dao.StudentMapper">
    <select id="getStudent" resultMap="studentTeacher">
        SELECT * FROM mybatis.student
    </select>
    
    <resultMap id="studentTeacher" type="Student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacherById"/>
    </resultMap>

    <select id="getTeacherById" parameterType="int" resultType="Teacher">
        SELECT * FROM mybatis.teacher WHERE id = #{id};
    </select>
</mapper>
```

TeacherMapper.xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--    namespace绑定一个对应的DAO/Mapper接口-->
<mapper namespace="com.zyf.dao.TeacherMapper">
    <select id="getTeachers" resultType="Teacher">
        Select * FROM mybatis.teacher
    </select>

    <select id="getTeacherById" parameterType="int" resultType="Teacher">
        SELECT * FROM mybatis.teacher WHERE id = #{id};
    </select>
</mapper>
```

## 9.2 **结果嵌套处理（多表查询，查出来再映射）**

StudentMapper.xml配置文件

```xml
<select id="getStudent02" resultMap="studentTeacher02">
    SELECT student.id AS sId, student.name AS sName,
    teacher.name AS tName, teacher.id as tId
    FROM student, teacher
    WHERE student.tid = teacher.id;
</select>

<resultMap id="studentTeacher02" type="Student">
    <result property="id" column="sId"/>
    <result property="name" column="sName"/>
    <association property="teacher" javaType="Teacher">
        <result property="name" column="tName"/>
        <result property="id" column="tId"/>
    </association>
</resultMap>
```

## 9.3 总结

- 子查询
- 联表查询

# 10. 一对多处理（collection）

一个老师拥有多个学生，对于老师而言就是一对多。

```java
@Data
public class Teacher {
    private int id;
    private String name;
    private List<Student> students;
}
```

```java
@Data
public class Student {
    private int id;
    private String name;
}
```

## 10.1 查询嵌套处理

xml配置

```xml
<select id="getTeacherById1" parameterType="int" resultMap="teacherStudent1">
    SELECT * FROM teacher where id = #{id};
</select>

<resultMap id="teacherStudent1" type="teacher">
    <result property="id" column="id"/>
    <result property="name" column="name"/>
    <collection property="students" column="id" javaType="ArrayList" ofType="student"
                select="getStudents"/>
</resultMap>

<select id="getStudents" resultType="Student">
    SELECT * FROM student WHERE tid = #{tid};
</select>
```

注：

```xml
<collection property="students" column="id" javaType="ArrayList" ofType="student"
                select="getStudents查询语句中的参数"/>
中的column是传到getStudents查询语句中的参数
```

## 10.2 结果嵌套处理

xml配置文件

```xml
<select id="getTeacherById" parameterType="int" resultMap="teacherStudent">
    SELECT student.id AS sId, student.name AS sName,
    teacher.id AS tId, teacher.name AS tName
    FROM teacher, student
    WHERE teacher.id = student.tid AND teacher.id = #{id};
</select>

<resultMap id="teacherStudent" type="teacher">
    <result property="id" column="tId"/>
    <result property="name" column="tName"/>
    <collection property="students" javaType="ArrayList" ofType="Student" >
        <result property="id" column="sId"/>
        <result property="name" column="sName"/>
    </collection>
</resultMap>
```

## 10.3 注意点

- 保证SQL的可读性，尽量保证通俗易懂。
- 注意一对多和多对一中，属性和字段的问题。
- 如果问题不好排斥错误，建议使用日志。

面试：

- Mysql引擎
- innoDB底层原理
- 索引
- 索引优化

# 11. 动态SQL

- 什么是动态SQL：动态SQL就是指根据不同的条件生成不同的SQL语句。

- **所谓的动态SQL还是sql语句，只是我们在SQL层面增加逻辑代码。**

```mysql
CREATE TABLE `blog`(
		`id` VARCHAR(50) NOT NULL COMMENT '博客id',
		`title` VARCHAR(100) NOT NULL COMMENT '博客标题',
		`author` VARCHAR(30) NOT NULL COMMENT '博客作者',
		`create_time` datetime NOT NULL COMMENT '创建时间',
		`views` int(30) NOT NULL COMMENT '浏览量' 
)ENGINE=InnoDB DEFAULT CHARSET=utf8
```

编写实体类

```java
@Data
public class Blog {
    private String id;

    private String title;

    private String author;

    private Date createTime;

    private int views;
}
```

## 11.1 if语句

根据不同的条件进行查询

```java
List<Blog> queryBlogIF(Map map);
```

BlogMapper.xml配置

```xml
<select id="queryBlogIF" parameterType="map" resultType="blog">
    SELECT * FROM mybatis.blog WHERE 1 = 1
    <if test="title != null">
        and title = #{title}
    </if>
    <if test="author != null">
        and author = #{author}
    </if>
</select>
```

使用where标签

```xml
<select id="queryBlogIF" parameterType="map" resultType="blog">
    SELECT * FROM mybatis.blog
    <where>
        <if test="title != null">
            title = #{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </where>
</select>
```

测试

```java
public void queryBlogIFTest(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap<Object, Object> map = new HashMap<>();
    map.put("title","mybatis如此简单");
    List<Blog> blogs = mapper.queryBlogIF(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
    sqlSession.close();
}
```

## 11.2 choose语句（when、otherwise）

```java
 List<Blog> queryBlogChoose(Map map);
```

```xml
<select id="queryBlogChoose" parameterType="map" resultType="blog">
    SELECT * FROM mybatis.blog
    <where>
        <choose>
            <when test="title != null">
                title = #{title}
            </when>
            <when test="author != null">
                AND author = #{author}
            </when>
            <otherwise>
                AND views = #{views}
            </otherwise>
        </choose>
    </where>
</select>
```

## 11.3 set语句

**set语句可以前置SET关键字，并自动删除无关的逗号。**

```java
int updateBlog(Map map);
```

配置文件（set语句）

```xml
<update id="updateBlog" parameterType="map">
    UPDATE mybatis.blog
    <set>
        <if test="title != null">
            title = #{title},
        </if>
        <if test="author != null">
            author = #{author}
        </if>
    </set>
    WHERE id = #{id};
</update>
```

测试

```java
public void updateBlogTest(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap<Object, Object> map = new HashMap<>();
    map.put("title","mybatis如此简单2");
    map.put("id","4572b5346e024e60b1883c67b604c8e2");
    int rs = mapper.updateBlog(map);
    System.out.println(rs>0 ? "修改成功":"修改失败");
    sqlSession.close();
}
```

## 11.4 trim（where、set）



## 11.5 Foreach

- 动态SQL的另一个常用需求是对一个集合进行遍历，通常实在构成IN条件语句的时候。
- 本质上是拼接SQL语句，将集合中的元素遍历，按照需求进行拼接。

**查询指定id的blog**

Mapper

```JAVA
List<Blog> queryBlogForeach(Map map);
```

xml配置

```XML
<select id="queryBlogForeach" resultType="blog" parameterType="map">
    SELECT * FROM mybatis.blog
    <where>
        <foreach collection="idList" item="id" open="" close=""
                 separator="or">
            id = #{id}
        </foreach>
    </where>
</select>
```

测试代码

```java
@Test
public void queryBlogForeachTest(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap<Object, Object> map = new HashMap<>();
    ArrayList<String> list = new ArrayList<>();
    list.add("4572b5346e024e60b1883c67b604c8e2");
    list.add("7426dbc388ff4a1b9cdda47d821d3451");
    map.put("idList",list);
    List<Blog> blogs = mapper.queryBlogForeach(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
    sqlSession.close();
}
```

```
Blog(id=4572b5346e024e60b1883c67b604c8e2, title=mybatis如此简单2, author=周杨凡, createTime=Thu Feb 23 15:30:40 CST 2023, views=9999)
Blog(id=7426dbc388ff4a1b9cdda47d821d3451, title=java如此简单, author=周杨凡, createTime=Thu Feb 23 15:30:40 CST 2023, views=1000)
```

## 11.6 SQL片段

- 简化代码，实现代码的复用。
- sql标签抽取公共部分，在需要使用的地方使用include标签引用。
- sql标签中不要引用where标签。

```xml
<select id="queryBlogIF" parameterType="map" resultType="blog">
    SELECT * FROM mybatis.blog
    <where>
        <include refid="if-title-author"></include>
    </where>
</select>

<sql id="if-title-author">
    <if test="title != null">
        title = #{title}
    </if>
    <if test="author != null">
        and author = #{author}
    </if>
</sql>
```

# 12.缓存

1. 什么是缓存：
   - 存在内存中的临时数据。
   - 将用户经常查询的数据放在缓存中，用户去查询数据就不用从磁盘上查询，从缓存中查询，从而提高查询效率，解决了高并发系统的性能问题。
2. 为什么要使用缓存？
   - 减少和数据库的交互次数，减少系统开销，提高系统效率。
3. 什么样的数据能使用缓存？
   - 经常查询并且不经常改变的数据。

## 12.1 Mybaatis缓存

- Mybatis系统中默认定义了两级缓存：一级缓存和二级缓存
  - 默认情况下，只有一级缓存开启。（SqlSession级别的缓存，也称为本地缓存）
  - 二级缓存需要手动开启和配置，是基于namespace级别的缓存。
  - 为了提高拓展性，Mybatis定义了缓存接口Cache。我们可以通过实现Cache接口来定义二级缓存。

## 12.2 一级缓存（本地缓存）

- 一级缓存也叫本地缓存
  - 与数据库同一次会话期间查询到的数据会放在本地缓存中。
  - 以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库。

```java
User user1 = mapper.queryUserById(1);
User user2 = mapper.queryUserById(1);
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230225095240072.png" alt="image-20230225095240072" style="zoom:50%;" />

```java
User user1 = mapper.queryUserById(1);
User user2 = mapper.queryUserById(2);
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230225100044305.png" alt="image-20230225100044305" style="zoom:50%;" />

```java
User user1 = mapper.queryUserById(1);
sqlSession.clearCache();
User user2 = mapper.queryUserById(1);
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230225100200257.png" alt="image-20230225100200257" style="zoom:50%;" />

一级缓存是默认开启的，只是在一次sqlSession中有效，也就是拿到连接和关闭连接这个时间段。

一级缓存相当于一个map，查到的结果放在map中。

## 12.3 二级缓存（全局缓存）

**工作机制**

- 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中。
- 如果会话关闭了，这个会话对应的一级缓存就清除了。
- 开启全局缓存，一级缓存中保存的数据会在**会话关闭时**被放到二级缓存中。新的查询就可以从二级缓存中获取内容。
- 不同的mapper查出的数据会放在自己对应的缓存（map）中。

**步骤**

1. 开启全局缓存

   ```xml
   <setting name="cacheEnabled" value="true"/>
   ```

2. 在要使用二级缓存的mapper中开启缓存

   ```xml
   <cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"/>
   ```

3. 测试

   ```java
   SqlSession sqlSession1 = MybatisUtils.getSqlSession();
   SqlSession sqlSession2 = MybatisUtils.getSqlSession();
   UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
   UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
   User user1 = mapper1.queryUserById(1);
   sqlSession1.close();
   User user2 = mapper2.queryUserById(1);
   sqlSession2.close();
   ```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230225103306312.png" alt="image-20230225103306312" style="zoom:50%;" />

**所有的数据都会先放在一级缓存中，只有当会话提交或关闭时候，才会提交到二级缓存中。**

# 13. Mybatis整合Spring

- 将SqlSessionFactory，sqlSession交给Spring来管理。使用getBean来获取sqlSession对象。
- mybatis-config配置文件只需要配置别名和setting，其余配置写在spring-dao中。

## 13.1 整合方式一

### 13.1.1 创建mapper

UserMapper接口

```java
package com.zyf.mapper;

import com.zyf.bean.User;

import java.util.List;

public interface UserMapper {
    List<User> queryUsers();
}
```

UserMapper配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zyf.mapper.UserMapper">
    <select id="queryUsers" resultType="User">
        SELECT * FROM mybatis.user
    </select>
</mapper>
```

接口实现类

```java
package com.zyf.mapper;

import com.zyf.bean.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl  implements UserMapper {
    private SqlSessionTemplate sqlSession;

    public UserMapperImpl(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> queryUsers() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.queryUsers();
        return users;
    }
}
```

### 13.1.2 配置spring-dao.xml

前面是将数据源配置到mybatis-config中，创建sqlSessionFactory来获取sqlSession执行sql语句，在这里，我们将sqlSessionFactory交给spring去管理。

- `SqlSessionFactory` 需要一个 `DataSource`（数据源）。 这可以是任意的 `DataSource`，只需要和配置其它 Spring 数据库连接一样配置它就可以了。
- 在基础的 MyBatis 用法中，是通过 `SqlSessionFactoryBuilder` 来创建 `SqlSessionFactory` 的。 而在 MyBatis-Spring 中，则使用 `SqlSessionFactoryBean` 来创建。
- SqlSessionFactory` 有一个唯一的必要属性：用于 JDBC 的 `DataSource。
- 一个常用的属性是 `configLocation`，它用来指定 MyBatis 的 XML 配置文件路径。它在需要修改 MyBatis 的基础配置非常有用。通常，基础配置指的是 `<settings>` 或 `<typeAliases>` 元素。只有`<settings>` 或 `<typeAliases>` 配置在mybatis-config.xml中。
- `SqlSessionTemplate` 是 MyBatis-Spring 的核心。作为 `SqlSession` 的一个实现，这意味着可以使用它无缝代替你代码中已经在使用的 `SqlSession`。

![image-20230303102503238](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230303102503238.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;userUnicode=false&amp;characterEncoding=utf8"/>
        <property name="username" value="root"/>
        <property name="password" value="zyf2563085"/>
    </bean>

    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/zyf/mapper/*.xml"/>
    </bean>

    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <!--利用构造器注入-->
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>

    <bean id="userMapperImpl" class="com.zyf.mapper.UserMapperImpl">
        <!--使用属性注入，dao实现类中需要提供sqlsession的set方法-->
        <constructor-arg index="0" ref="sqlSession"/>
    </bean>
</beans>
```

### 13.1.3 测试

```java
import java.util.List;

public class MyTest {
    @Test
    public void test01(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapperImpl userMapperImpl = classPathXmlApplicationContext.getBean("userMapperImpl", UserMapperImpl.class);
        List<User> users = userMapperImpl.queryUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
```

```
User(id=1, name=周杨凡, pwd=122222)
User(id=2, name=JAMES, pwd=123456)
User(id=3, name=jack, pwd=123123)
User(id=4, name=lucy, pwd=123123)
User(id=6, name=周先生, pwd=123134)
User(id=7, name=王五, pwd=123456)
User(id=8, name=老李, pwd=123457)
```

## 13.2 整合方式二

- 只需要配置SqlSessionFactory，让mapper实现类继承SqlSessionDaoSupport类，在mapper实现类中注入配置的SqlSessionFactory为属性。
- 在mapper实现类中注入SqlSessionFactory属性，会调用父类SqlSessionDaoSupport的setSqlSessionFactory方法，setSqlSessionFactory方法将会调用createSqlSessionTemplate方法生成sqlSessionTemplate对象，作为类的属性。
- mapper实现类继承了SqlSessionDaoSupport的getSqlSession方法以及属性sqlSessionTemplate，因此子类可以直接调用getSqlSession方法得到sqlSessionTemplate
- SqlSessionTemplate类是SqlSession的重要子类

```java
public final SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionTemplate != null ? this.sqlSessionTemplate.getSqlSessionFactory() : null;
    }
```

1. 配置SqlSessionFactory（数据源、mybatis配置和mapper映射）

2. 配置mapperImpl类

   ```java
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
       <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
           <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
           <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;userUnicode=false&amp;characterEncoding=utf8"/>
           <property name="username" value="root"/>
           <property name="password" value="zyf2563085"/>
       </bean>
   
       <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource" />
           <property name="configLocation" value="classpath:mybatis-config.xml"/>
           <property name="mapperLocations" value="classpath:com/zyf/mapper/*.xml"/>
       </bean>
   
       <bean id="userMapperImpl" class="com.zyf.mapper.UserMapperImpl">
           <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
       </bean>
   </beans>
   ```

3. mapperImpl类继承SqlSessionDaoSupport类

   ```java
   package com.zyf.mapper;
   
   import com.zyf.bean.User;
   import org.apache.ibatis.session.SqlSession;
   import org.mybatis.spring.SqlSessionTemplate;
   import org.mybatis.spring.support.SqlSessionDaoSupport;
   
   import java.util.List;
   
   public class UserMapperImpl extends SqlSessionDaoSupport  implements UserMapper {
   
       @Override
       public List<User> queryUsers() {
           UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
           List<User> users = mapper.queryUsers();
           return users;
       }
   }
   ```

4. 测试

   ```java
   package com.zyf.mapper;
   
   import com.zyf.bean.User;
   import org.apache.ibatis.session.SqlSession;
   import org.mybatis.spring.SqlSessionTemplate;
   import org.mybatis.spring.support.SqlSessionDaoSupport;
   
   import java.util.List;
   
   public class UserMapperImpl extends SqlSessionDaoSupport  implements UserMapper {
   
       @Override
       public List<User> queryUsers() {
           UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
           List<User> users = mapper.queryUsers();
           return users;
       }
   }
   ```

