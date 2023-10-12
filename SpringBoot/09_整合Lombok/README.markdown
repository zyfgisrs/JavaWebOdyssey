# Lombok整合

## Lombok介绍

- Lombok 是一个插件，它通过提供一些注解来帮助开发者减少或消除样板代码，从而节省了开发过程中的宝贵时间。Lombok 提供了多种注解，旨在替换众所周知的样板、重复或繁琐的 Java 代码。一些核心功能包括自动生成 getter 和 setter 方法，以及其他如 `toString()`, `equals()` 和 `hashCode()` 方法，这样开发者就不必手动编写这些通用方法。通过使用 Lombok，你可以显著提高代码的可读性并节省代码空间。

| 注解                       | 描述                                                         |
| -------------------------- | ------------------------------------------------------------ |
| `@Getter` 和 `@Setter`     | 为类中的字段自动生成 getter 和 setter 方法，减少了手动编写这些方法的需要。 |
| `@NoArgsConstructor`       | 为类生成一个无参数的构造函数。`@NonNull`与之冲突             |
| `@AllArgsConstructor`      | 为类中的所有字段生成一个构造函数。                           |
| `@RequiredArgsConstructor` | 为所有标记为 final 或用 `@NonNull` 标记且在声明时未初始化的字段生成一个构造函数。 |
| `@Data`                    | 是 `@ToString`, `@EqualsAndHashCode`, 所有字段的 `@Getter`, 所有非 final 字段的 `@Setter`, 和 `@RequiredArgsConstructor` 的简写。它基本上将常见的注解封装成一个。 |
| `@Slf4j`                   | 在类中创建并注入一个日志记录器，无需手动定义日志记录器。     |
| `@Builder`                 | 为类实现 Builder 模式，无需编写样板代码。                    |
| `@Cleanup`                 | 用于确保在代码执行路径上的指定点关闭资源，从而避免资源泄漏，例如关闭文件流或数据库连接。 |

## Lomok配置

- 添加Gradle依赖

```
compileOnly group: 'org.projectlombok', name: 'lombok', version: '1.18.16'
```

```groovy
//定义版本号
ext.versions = [
        springboot           : '2.4.3', // SpringBoot版本
        junit                : '5.7.1', //配置junit测试工具的版本编号
        junitPlatformLauncher: '1.7.1',//JUnit测试工具运行平台
        lombok               : '1.18.16'
]

//定义所有的依赖库
ext.libraries = [
        //SpringBoot项目所需要的核心依赖:
        'spring-boot-gradle-plugin': "org.springframework.boot:spring-boot-gradle-plugin:${versions.springboot}",
        //以下的配置为与项目用例测试有关的依赖:
        'junit-jupiter-api'        : "org.junit.jupiter:junit-jupiter-api:${versions.junit}",
        'junit-jupiter-engine'     : "org.junit.jupiter:junit-jupiter-engine:${versions.junit}",
        'junit-vintage-engine'     : "org.junit.vintage:junit-vintage-engine:${versions.junit}",
        'junit-bom'                : "org.junit:junit-bom:${versions.junit}",
        'junit-platform-launcher'  : "org.junit.platform:junit-platform-launcher:${versions.junitPlatformLauncher}",
        'lombok'                   : "org.projectlombok:lombok:${versions.lombok}"
]
```

![image-20231010182622313](assets/image-20231010182622313.png)

- 安装lombok插件

![image-20231010182717830](assets/image-20231010182717830.png)

- 构建管理设置`File | Settings | Build, Execution, Deployment | Compiler | Annotation Processors`

![image-20231010182916627](assets/image-20231010182916627.png)

## 使用Lombok

- 编写简单类

![image-20231010183427428](assets/image-20231010183427428.png)

- 使用`@Data`注解

![image-20231010183646033](assets/image-20231010183646033.png)

反编译源码

```java
package com.zhouyf.vo;

import java.util.Date;

public class Message {
  private String title;
  
  private Date pubdate;
  
  private String content;
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public void setPubdate(Date pubdate) {
    this.pubdate = pubdate;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public boolean equals(Object o) {
    if (o == this)
      return true; 
    if (!(o instanceof Message))
      return false; 
    Message other = (Message)o;
    if (!other.canEqual(this))
      return false; 
    Object this$title = getTitle(), other$title = other.getTitle();
    if ((this$title == null) ? (other$title != null) : !this$title.equals(other$title))
      return false; 
    Object this$pubdate = getPubdate(), other$pubdate = other.getPubdate();
    if ((this$pubdate == null) ? (other$pubdate != null) : !this$pubdate.equals(other$pubdate))
      return false; 
    Object this$content = getContent(), other$content = other.getContent();
    return !((this$content == null) ? (other$content != null) : !this$content.equals(other$content));
  }
  
  protected boolean canEqual(Object other) {
    return other instanceof Message;
  }
  
  public int hashCode() {
    int PRIME = 59;
    result = 1;
    Object $title = getTitle();
    result = result * 59 + (($title == null) ? 43 : $title.hashCode());
    Object $pubdate = getPubdate();
    result = result * 59 + (($pubdate == null) ? 43 : $pubdate.hashCode());
    Object $content = getContent();
    return result * 59 + (($content == null) ? 43 : $content.hashCode());
  }
  
  public String toString() {
    return "Message(title=" + getTitle() + ", pubdate=" + getPubdate() + ", content=" + getContent() + ")";
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public Date getPubdate() {
    return this.pubdate;
  }
  
  public String getContent() {
    return this.content;
  }
}
```



- `@NoArgsConstructor`生成无参构造

![image-20231010183856101](assets/image-20231010183856101.png)

反编译源码

```java
package com.zhouyf.vo;

import java.util.Date;

public class Message {
  private String title;
  
  private Date pubdate;
  
  private String content;
}
```

- 使用`@AllArgsConstructor`生成全参构造

![image-20231010184051112](assets/image-20231010184051112.png)

反编译源码

```java
package com.zhouyf.vo;

import java.util.Date;

public class Message {
  private String title;
  
  private Date pubdate;
  
  private String content;
  
  public Message(String title, Date pubdate, String content) {
    this.title = title;
    this.pubdate = pubdate;
    this.content = content;
  }
}
```

