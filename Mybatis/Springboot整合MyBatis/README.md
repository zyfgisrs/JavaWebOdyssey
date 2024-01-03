# MyBatis配置

## 数据源配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: user
    password: pass
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## **MyBatis 配置**

```yaml
mybatis:
  mapper-locations: classpath:mapper/*.xml  #映射文件路径
  type-aliases-package: com.example.model   #别名配置
  configuration:      #全局配置
    map-underscore-to-camel-case: true   #驼峰命名
    cache-enabled: true             #缓存
```

### 驼峰命名

`map-underscore-to-camel-case` 是 MyBatis 配置中的一个重要选项，它用于自动将数据库中的下划线命名转换为 Java 风格的驼峰命名法。这项配置的作用如下：

- **数据库字段到 Java 属性的映射**：在数据库中，字段名经常使用下划线来分隔单词（例如 `user_name`）。而在 Java 中，习惯使用驼峰命名法来命名属性（例如 `userName`）。启用 `map-underscore-to-camel-case` 配置后，MyBatis 会自动将数据库返回的列名从下划线命名转换为驼峰命名，这样就不需要在映射文件中显式地指定列名和属性名的映射关系。
- **简化映射配置**：由于自动进行命名转换，这减少了在 MyBatis 映射文件中定义字段与实体属性映射的工作量。如果数据库列名和 Java 实体属性名遵循上述的命名规则（下划线和驼峰），则可以直接映射而无需额外配置。

例如，当 `map-underscore-to-camel-case` 设置为 `true` 时，如果数据库表中有一个名为 `user_name` 的列，MyBatis 会自动将其映射到 Java 实体类中名为 `userName` 的属性上。

### 别名

在 MyBatis 中，创建别名（alias）是一种简化映射配置的方式。别名用于为 Java 类型指定一个短名称，使得在 MyBatis 的映射文件中引用这些类型变得更加方便和简洁。这种机制特别有用于减少在 XML 映射文件中的重复和冗长的类全名引用。如果不使用XML 映射文件，那么别名需要进行配置。

# MyBatis注解开发

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/school
    username: root
    password: 123456

mybatis:
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

## 单表增删改查

创建实体类

```java
package com.zhouyf.zhouyfbatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

创建Article表

```sql
CREATE TABLE Article (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(255),
    summary TEXT,
    read_count INT DEFAULT 0,
    create_time DATETIME,
    update_time DATETIME
);
```

编写mapper接口，实现增删改查

```java
package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.ArticlePO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ArticleMapper {

    @Select("""
            select id, user_id, title, summary, read_count, create_time, update_time\s
                    from article where id = #{articleId};
            """)

    @Results(id = "BaseArticleMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "title", property = "title"),
            @Result(column = "summary", property = "summary"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    ArticlePO selectById(@Param("id") int id);

    //insert
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

    //delete
    @Delete("""
            delete from article where id = #{id}
            """)
    int deleteArticle(Integer id);
}
```

测试mapper接口

```java
package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.ArticlePO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void testInsert(){
        ArticlePO article = new ArticlePO();
        article.setUserId(19000);
        article.setTitle("MySQL必知必会");
        article.setReadCount(100);
        article.setCreateTime(LocalDateTime.of(2022,1,2,11,11,23));
        article.setUpdateTime(LocalDateTime.of(2022, 5, 2, 11, 11, 23));
        article.setSummary("学习MySQL的最佳书籍");
        int i = articleMapper.insertArticle(article);
        if(i > 0) {
            log.info("成功添加了{}本书", i);
        }
    }

    @Test
    void testUpdate(){
        int i = articleMapper.updateReadCount(1, 900);
        if(i > 0) {
            log.info("成功修改了{}本书的信息", i);
        }
    }

    @Test
    void testDelete(){
        int i = articleMapper.deleteArticle(1);
        if(i > 0) {
            log.info("成功删除了{}本书", i);
        }
    }

    @Test
    void testSelect(){
        ArticlePO articlePO = articleMapper.selectById(2);
        System.out.println(articlePO);
    }
}
```

## 一对多查询

创建部门Department表和员工Employee表

```sql
CREATE DATABASE CompanyDB;



CREATE TABLE Department (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE Employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    department_id INT,
    position VARCHAR(255),
    hire_date DATE,
    FOREIGN KEY (department_id) REFERENCES Department(id)
);


-- 插入部门数据
INSERT INTO Department (name, description) VALUES ('IT', 'Information Technology Department');
INSERT INTO Department (name, description) VALUES ('HR', 'Human Resources Department');
INSERT INTO Department (name, description) VALUES ('Sales', 'Sales Department');

-- 插入员工数据
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('John Doe', 'john.doe@example.com', 1, 'Software Developer', '2021-01-10');
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('Jane Smith', 'jane.smith@example.com', 2, 'HR Manager', '2020-05-15');
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('Emily Johnson', 'emily.johnson@example.com', 1, 'System Administrator', '2019-03-01');
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('Michael Brown', 'michael.brown@example.com', 3, 'Sales Representative', '2021-07-21');
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('Alice Green', 'alice.green@example.com', 1, 'Network Engineer', '2022-02-15');
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('Greg White', 'greg.white@example.com', 2, 'Recruiter', '2021-11-20');
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('Sarah Miller', 'sarah.miller@example.com', 3, 'Account Manager', '2020-08-05');
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('Lucas Brown', 'lucas.brown@example.com', 1, 'Database Administrator', '2019-06-30');
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('Emma Wilson', 'emma.wilson@example.com', 2, 'Training Specialist', '2022-01-07');
INSERT INTO Employee (name, email, department_id, position, hire_date) VALUES ('Noah Harris', 'noah.harris@example.com', 3, 'Sales Associate', '2021-09-14');

```

创建Department和员工Employee类

```java
package com.zhouyf.zhouyfbatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private int id;

    private String name;

    private String description;

    private List<Employee> employees;
}
```

```java
package com.zhouyf.zhouyfbatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;

    private String name;

    private String email;

    private int departmentId;

    private String position;

    private LocalDate hireDate;
}
```

mapper接口

```java
package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.Department;
import com.zhouyf.zhouyfbatis.model.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    @Select("""
            SELECT id, name, description from department where id = #{id};
                    """)

    @Results(id = "departmentMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "employees", column = "id",
                    many = @Many(select = "selectEmployeeByDepartmentId"))

    })
    Department selectDepartmentById(int id);


    @Select("""
            SELECT id, name, email, department_id, position, hire_date from employee
            where department_id = #{departmentId}
                """)
    List<Employee> selectEmployeeByDepartmentId(@Param("departmentId") int departmentId);
}
```

测试

```java
package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeTest {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    void select(){
        Department department = departmentMapper.selectDepartmentById(1);
        System.out.println(department);
    }
}
```



## 多对多查询

创建student表和course表以及students_courses表

```sql
CREATE TABLE students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);


CREATE TABLE courses (
    course_id INT PRIMARY KEY,
    title VARCHAR(100)
);

CREATE TABLE students_courses (
    student_id INT,
    course_id INT,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

INSERT INTO students (student_id, name) VALUES (1, 'Alice');
INSERT INTO students (student_id, name) VALUES (2, 'Bob');
INSERT INTO students (student_id, name) VALUES (3, 'Charlie');


INSERT INTO courses (course_id, title) VALUES (101, 'Mathematics');
INSERT INTO courses (course_id, title) VALUES (102, 'Physics');
INSERT INTO courses (course_id, title) VALUES (103, 'Chemistry');

-- Alice 选修了数学和物理
INSERT INTO students_courses (student_id, course_id) VALUES (1, 101);
INSERT INTO students_courses (student_id, course_id) VALUES (1, 102);

-- Bob 选修了物理和化学
INSERT INTO students_courses (student_id, course_id) VALUES (2, 102);
INSERT INTO students_courses (student_id, course_id) VALUES (2, 103);

-- Charlie 选修了数学和化学
INSERT INTO students_courses (student_id, course_id) VALUES (3, 101);
INSERT INTO students_courses (student_id, course_id) VALUES (3, 103);
```

创建Student和Course类

```java
package com.zhouyf.zhouyfbatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;

    private String name;

    private List<Course> courses;
}
```

```java
package com.zhouyf.zhouyfbatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;

    private String title;

    private List<Student> students;
}
```

mapper接口

```java
package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.Course;
import com.zhouyf.zhouyfbatis.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("""
            SELECT student_id, name FROM students WHERE student_id = #{id}
                """)
    @Results(id = "studentMap", value = {
            @Result(id = true, property = "id", column = "student_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "courses", column = "student_id",
            many = @Many(select = "selectCoursesForStudent"))
    })
    Student selectStudentWithCourses(int id);


    @Select("""
            SELECT c.course_id, c.title FROM courses c
            JOIN students_courses sc ON c.course_id = sc.course_id
            WHERE sc.student_id = #{studentId}
                    """)
    List<Course> selectCoursesForStudent(int studentId);
}
```

