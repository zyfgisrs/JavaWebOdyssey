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
        lombok               : '1.18.30'
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

```groovy
dependencies {
    //允许项目进行热部署
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation('org.springframework.boot:spring-boot-starter-test'){
        exclude group:'junit', module:'junit' //移除旧版本的测试工具
    }
    testImplementation(enforcedPlatform(libraries.'junit-bom')) //将当前项目强制性绑定为JUnit5运行
    testImplementation(libraries.'junit-jupiter-api')
    testImplementation(libraries.'junit-jupiter-engine')
    testImplementation(libraries.'junit-vintage-engine')
    testImplementation(libraries.'junit-platform-launcher')
    //Lombok依赖
    compileOnly(libraries.'lombok')
    annotationProcessor(libraries.'lombok')
    testCompileOnly(libraries.'lombok')
    testAnnotationProcessor(libraries.'lombok')
}
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

## 构建者模式

`@Builder`是Lombok库中的一个注解，用于自动产生一个所注解类的构建者模式（builder pattern）。在Java开发中，构建者模式是一种创建对象的设计模式，它允许你创建一个复杂对象的不同部分，然后将它们组合成一个完整的对象。这种模式特别适合于那些具有多个属性，特别是具有多个可选属性的类。

当你在一个类上添加`@Builder`注解时，Lombok会在编译时自动为这个类生成以下组件：

1. 一个内部的`Builder`静态类。
2. `Builder`类中对应每个属性的设置方法。
3. 一个`build()`方法，用于从`Builder`对象生成一个不可变的类实例。

这样做的好处包括：

- **可读性和易用性**：通过使用`@Builder`，你可以使用方法链来设置你需要的属性，而不是用一个长长的构造函数或者繁琐的setter方法，这使得代码更易读，使用起来也更方便。
- **不可变性**：`@Builder`通常用于构建不可变对象，这种对象一旦创建，其状态就不能改变。这对于多线程环境特别有用，因为不可变对象天然是线程安全的。
- **参数校验**：在构建对象的过程中，可以在一步步构建的每个环节加入参数校验，确保最终构建的对象是符合要求和预期的。

下面是一个简单的使用`@Builder`的例子：

```java
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private final String name;
    private final String email;
    // 其他字段...
}

// 在其他地方使用Builder构建User对象
public class TestBuilder {
    public static void main(String[] args) {
        User user = User.builder()
                .name("张三")
                .email("zhangsan@example.com")
                // 设置其他字段...
                .build();
        
        // 现在'user'是一个初始化完毕的User对象
    }
}
```

在上述代码中，`@Builder`注解使得我们无需手动编写大量模板代码，就能享受构建者模式的所有好处。然而，也请注意，Lombok是通过在编译时进行字节码操作来实现这些功能的，这意味着你的项目必须正确配置Lombok，才能使用`@Builder`以及Lombok的其他特性。

### 通过构建者模式创建Message对象实例

- `com.zhouyf.vo.Message`类

```java
package com.zhouyf.vo;

import lombok.Builder;

import java.util.Date;

@Builder
public class Message {
    private String title;
    private Date pubdate;

    private String content;
}
```

- `src\test\java\com\zhouyf\test\MessageBuildTest`

```java
package com.zhouyf.test;

import com.zhouyf.vo.Message;

public class MessageBuildTest {
    public static void main(String[] args) {
        Message message = Message.builder().title("zhouyf").
                content("我是zhouyf").build();

        System.out.println(message);
    }
}
```

![image-20231019165832937](assets/image-20231019165832937.png)

## Accessor

`@Accessors`是Lombok提供的一个注解，用于控制生成的getter和setter方法的形式，以及可以影响`@Builder`注解的行为。通过使用`@Accessors`，你可以定制如何访问和修改实体类中的属性，它允许你改变Lombok生成的代码的默认约定。

`@Accessors`主要有以下几个属性：

1. **fluent**：默认为`false`。如果设置为`true`，则getter和setter方法的命名不会遵循Java Bean标准约定，即getter方法不会以`get`开头，setter方法不会以`set`开头，并且setter方法会返回`this`（实现方法链）。例如，对于属性`name`，其getter将成为`name()`，setter将成为`name(String name)`。

2. **chain**：默认为`false`。如果设置为`true`，则setter方法会返回`this`（实现方法链），允许你链式调用setter方法。这不会改变方法的名称，只是改变返回类型为当前对象。例如，你可以这样调用：`obj.setName("name").setAge(30);`

3. **prefix**：如果设置了这个属性（例如，`@Accessors(prefix = "m")`），则在生成的getter和setter方法中，会去掉字段名中的指定前缀。例如，字段`mName`的getter和setter将变为`getName()`和`setName(String name)`，而不是`getMName()`和`setMName(String mName)`。

这里有个简单的例子来说明这个注解的使用：

```java
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class User {
    private String name;
    private int age;
    // other fields...

    public static void main(String[] args) {
        User user = new User().name("test").age(25);
        System.out.println(user.name()); // 输出 "test"
        System.out.println(user.age()); // 输出 25
    }
}
```

在上述代码中，`@Accessors(fluent = true, chain = true)`导致生成的`name`和`age`的getter方法是`name()`和`age()`，而setter方法是`name(String name)`和`age(int age)`，并且这些setter返回`this`对象，允许链式调用。

注意：`@Accessors`是Lombok的实验性特性，这意味着在未来的版本中其API可能会改变。使用这个注解时需要考虑这种不确定性。

- 创建一个`com.zhouyf.vo.Person`类，并添加注解`@Accessors(fluent = true, chain = true)`

```java
package com.zhouyf.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Person {
    private String name;

    private int age;
}
```

- 测试`com.zhouyf.test`

```java
package com.zhouyf.test;

import com.zhouyf.vo.Person;

public class PersonTest {
    public static void main(String[] args) {
        Person p = new Person().name("zhouyf").age(20);
        System.out.println(p);
    }
}
```

![image-20231019182228902](assets/image-20231019182228902.png)

## IO流自动关闭

在Lombok中，可以使用`@Cleanup`注解来自动管理资源，包括IO流的自动关闭。这个注解在底层使用了`try-finally`块来确保在当前代码块执行完毕后资源被关闭，即使是在异常情况下也可以保证资源的关闭，从而避免了资源泄漏。

下面是使用`@Cleanup`注解的一个简单示例，展示了如何用它来自动关闭IO流：

```java
import lombok.Cleanup;
import java.io.*;

public class Example {
    public static void main(String[] args) {
        try {
            // 使用@Cleanup自动关闭文件输入流
            @Cleanup InputStream inputStream = new FileInputStream("path/to/your/file.txt");
            
            // 使用@Cleanup自动关闭文件输出流
            @Cleanup OutputStream outputStream = new FileOutputStream("path/to/your/output.txt");
            
            byte[] buffer = new byte[1024];
            int readSize;
            while ((readSize = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 不需要显式关闭流 - @Cleanup已经自动做了
    }
}
```

在这个例子中，`@Cleanup`注解确保了无论是正常情况下还是在发生异常的情况下，`InputStream`和`OutputStream`都会被正确关闭。这是一种更简洁的方式来替代传统的`try-catch-finally`方法，使代码更易读且不易出错。

然而，请注意，从Java 7开始，你也可以使用`try-with-resources`语句，这是一种原生支持的语言特性，用于自动关闭实现了`AutoCloseable`接口的资源。在处理资源关闭方面，`try-with-resources`和Lombok的`@Cleanup`非常相似，但`try-with-resources`是Java语言的一个内置特性，而不是依赖代码生成和字节码操作。根据具体情况，你可以选择最适合你的方案。

## 异常处理

`@SneakyThrows` 是 Lombok 提供的一个非常特别的注解，它可以在不更改方法签名的情况下，使得你的方法可以抛出已检查的异常（checked exceptions）。在 Java 中，已检查的异常通常需要你在方法签名中使用 `throws` 关键字来声明，或者在方法体内捕获并处理它们。然而，`@SneakyThrows` 允许你抛出这些异常而不需要在方法签名中声明它们。

这里有一个 `@SneakyThrows` 的示例：

```java
import lombok.SneakyThrows;
import java.io.*;

public class SneakyThrowsExample {
    @SneakyThrows(IOException.class)
    public void readFile(String path) {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        System.out.println(reader.readLine());
        // 不需要捕获或声明 IOException，@SneakyThrows 会处理
    }

    public static void main(String[] args) {
        new SneakyThrowsExample().readFile("somefile.txt");
    }
}
```

在这个例子中，`readFile` 方法使用 `@SneakyThrows(IOException.class)` 抛出了一个 `IOException`。这意味着调用这个方法的代码不需要处理 `IOException`，并且方法签名也不需要声明 `throws IOException`。

虽然 `@SneakyThrows` 在某些情况下可能会很有用（特别是对于那些不太可能恢复的异常），但它也被认为是一种反模式，因为它破坏了 Java 异常处理的显式性。它隐藏了方法可能抛出的异常，可能会使错误更难追踪和调试，特别是对于其他阅读你代码的人。因此，在使用 `@SneakyThrows` 时应该谨慎考虑，确保它不会引入难以诊断的问题或者与团队的代码标准相冲突。
