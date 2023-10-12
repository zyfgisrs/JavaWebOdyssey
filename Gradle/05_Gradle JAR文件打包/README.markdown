# 插件

## 制作JAR包

案例：将自己编写的项目打包成JAR（Java Archive）文件并使其可供其他项目使用

- **首先创建一个Gradle项目（GradleJAR）**

<img src="assets/image-20231007151328694.png" alt="image-20231007151328694" style="zoom:67%;" />

- **配置插件**

  1. `java-library` 插件是Gradle构建工具中用于构建Java库的一个插件。与经典的 `java` 插件相比，`java-library` 插件提供了更多专用于构建Java库的功能和选项。这个插件不仅包括构建Java类库所需的所有功能，还提供了处理库的依赖关系的强大工具。
  2. `maven-publish` 插件是 Gradle 的一个插件，它提供了能力来将你的 Gradle 构建的产物（比如 JAR 文件）发布到 Maven 仓库。Maven 仓库是一种存储构建产物的仓库，这样其他项目就可以使用这些产物作为依赖。这不仅仅限于在你自己的项目中，如果你发布到公共仓库中，其他开发者也可以使用你的库。

  ![image-20231008155035631](assets/image-20231008155035631.png)

- **刷新项目，任务中多了publishing分组**

  <img src="assets/image-20231008155328373.png" alt="image-20231008155328373" style="zoom: 67%;" />

- `build.gradle`中配置发布

  `publications` 块定义了要发布的产物及其元数据。`repositories` 块定义了发布的目标 Maven 仓库的位置：

  ```groovy
  publishing {
      //配置发布动作
      publications {
          maven(MavenPublication){
              from components.java
  
              groupId = 'com.zhouyf.demo'
              artifactId = 'zhouyf'
              version = '1.0.0'
          }
      }
      repositories {
          mavenLocal()
      }
  }
  ```

  刷新项目，产生任务：

  ![image-20231008160240445](assets/image-20231008160240445.png)

- **项目中编写代码**

  编写一个Student类和eat方法

  ```java
  package com.zhouyf.demo;
  
  public class Student {
      public void eat(){
          System.out.println("students are eating!!!");
      }
  }
  ```

- **进行发布**

![image-20231008160638942](assets/image-20231008160638942.png)

<img src="assets/image-20231008160859595.png" alt="image-20231008160859595" style="zoom: 50%;" />

- 查看JAR的配置文件

  位置：`C:\Users\zhouy\.m2\repository\com\zhouyf\demo\zhouyf`

  ![image-20231008161057784](assets/image-20231008161057784.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<metadata>
  <groupId>com.zhouyf.demo</groupId>
  <artifactId>zhouyf</artifactId>
  <versioning>
    <latest>1.0.0</latest>
    <release>1.0.0</release>
    <versions>
      <version>1.0.0</version>
    </versions>
    <lastUpdated>20231008080331</lastUpdated>
  </versioning>
</metadata>
```

- 新建项目

<img src="assets/image-20231008161512397.png" alt="image-20231008161512397" style="zoom: 80%;" />

- 引入刚才生成的JAR包

  ```groovy
  implementation group: 'com.zhouyf.demo', name: 'zhouyf', version: '1.0.0' //与配置文件中的信息对应
  ```

<img src="assets/image-20231008161922653.png" alt="image-20231008161922653" style="zoom: 80%;" />

- 点击刷新，可以看到外部库中成功显示了引入的JAR

  <img src="assets/image-20231008162141219.png" alt="image-20231008162141219" style="zoom: 80%;" />

- 创建测试类，并调用JAR中的api

  ```java
  import com.zhouyf.demo.Student;
  
  public class Test {
      public static void main(String[] args) {
          Student student = new Student();
          student.eat();
      }
  }
  ```

- 测试运行

  <img src="assets/image-20231008162446498.png" alt="image-20231008162446498" style="zoom: 80%;" />

至此，将自己编写的项目打包成JAR（Java Archive）文件并使其可供其他项目使用的全部流程已经完毕。
