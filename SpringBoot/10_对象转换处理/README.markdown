# 对象转换处理

## AbstractBaseAction

在 `microboot-common`子项目中创建`com.zhouyf.common.action.abs.absAbstractBaseAction`抽象类，可能意在为不同的控制器（controller）或动作（action）提供共通的功能。这里特别定义了一个`@InitBinder`注解的方法，用于自定义Spring的数据绑定。

```java
package com.zhouyf.common.action.abs;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class AbstractBaseAction { //定义一个公共的Action类
    
    //将字符串转换为日期，考虑到多线程环境下的并发问题，一定要使用LocalDate。
    //是一个私有的静态常量成员变量，用于存储日期格式的模式字符串，它被初始化为 "yyyy-MM-dd"。这个日期格式将用于将字符串解析为日期。
    private static final DateTimeFormatter LOCAL_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @InitBinder
    //这是一个带有 @InitBinder 注解的方法定义，它被用于自定义数据绑定规则。@InitBinder 注解告诉Spring在控制器方法执行之前调用这个方法。
    public void initBinder(WebDataBinder binder){
        //这是在 WebDataBinder 对象上调用 registerCustomEditor 方法，用于注册自定义的属性编辑器。它接受两个参数，第一个参数是要处理的属性类型（java.util.Date.class），第二个参数是一个匿名内部类，继承自 PropertyEditorSupport，用于处理属性的转换和绑定。
        binder.registerCustomEditor(java.util.Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                //这是在匿名内部类中覆盖了 PropertyEditorSupport 类的 setAsText 方法，这个方法用于将文本表示的属性值转换为目标类型。
                LocalDate localDate = LocalDate.parse(text, LOCAL_DATE_FORMAT);
                //这一行代码解析了传入的字符串 text，将其转换为 LocalDate 对象，使用了之前定义的日期格式模式 "yyyy-MM-dd"。
                Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                //这一行代码将 LocalDate 转换为 Instant 对象。Instant 表示时间的瞬时点，它是用于处理日期时间的一种标准方式。
                super.setValue(java.util.Date.from(instant));
                //这一行代码调用了父类 PropertyEditorSupport 的 setValue 方法，将 Instant 对象转换为 java.util.Date 对象，并将其设置为属性的值。这完成了日期字符串到 java.util.Date 的转换过程。
            }
        });
    }
}
```

代码中的 `@InitBinder` 方法和自定义属性编辑器的配置旨在处理日期字符串到 `java.util.Date` 对象的转换，这可以帮助控制器方法正确地处理日期数据。

## Message类

`com.zhouyf.vo.Message`

```java
package com.zhouyf.vo;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String title;
    private Date pubdate;
    private String content;
}
```

## MessageAction控制器

`com.zhouyf.action.MessageAction`

```java
package com.zhouyf.action;

import com.zhouyf.common.action.abs.AbstractBaseAction;
import com.zhouyf.vo.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//直接进行Rest架构进行处理，省略了@ResponseBody注解
@RequestMapping("/message/*")
public class MessageAction extends AbstractBaseAction {

    @RequestMapping("echo")
    public Message echo(Message message){
        message.setTitle("【ECHO】"+message.getTitle());
        message.setContent("【ECHO】"+message.getContent());
        return message;
    }
}
```

## 修改子模块任务

> 在一个使用Gradle构建的项目中，特别是在一个多模块的项目中，`spring-boot-gradle-plugin`插件被引入以便将Spring Boot的特定构建功能集成到Gradle构建过程中。这通常包括将应用程序打包为可执行的jar或war文件，运行Spring Boot应用程序，以及其他与Spring Boot相关的任务。
>

在这个特定情况下，`microboot-common`子模块本身并不是一个完整的Spring Boot应用程序模块；它是一个库或共享的组件模块，供其他模块使用。尽管如此，如果`spring-boot-gradle-plugin`在该`microboot-common`模块中被应用，Gradle会尝试按照Spring Boot应用程序的方式来构建它，这会导致构建失败，因为该模块不包含Spring Boot应用程序所需的特定结构或配置。为了解决这个问题，需要调整`microboot-common`模块的`build.gradle`文件，确保`spring-boot-gradle-plugin`仅应用于那些真正包含Spring Boot应用程序的模块，而不是共享库或通用模块。换句话说，项目配置需要细化，以确保构建逻辑与模块的实际用途相符。这可能意味着在`build.gradle`中移除对`spring-boot-gradle-plugin`的引用，或者调整插件的配置，让它知道不应该对这个特定的子模块执行Spring Boot特有的构建步骤。这样的修改将确保各个模块能够根据它们的实际职责和结构正确构建。

`microboot/microboot-common/build.gradle`中：

```groovy
jar {enabled = true} //允许当前模块打包为jar文件
javadocTask {enabled = false} //不启用javadoc任务
javadocJar {enabled = false} //不生成javadoc的"*.jar文件"
bootJar {enabled = false} //不执行SpringBoot的打包命令
```

## 子模块`microboot-common`构建

<img src="assets/image-20231019194339023.png" alt="image-20231019194339023" style="zoom:50%;" />

## 启动服务器，发送请求

![image-20231019194939470](assets/image-20231019194939470.png)

```json
{
  "title": "【ECHO】zhouyf",
  "pubdate": "2021-11-10T16:00:00.000+00:00",
  "content": "【ECHO】zhouyangfan"
}
```

# 总结

## `@InitBinder` 注解和 `WebDataBinder`

在代码中，`@InitBinder` 注解和 `WebDataBinder` 对象的作用是用于自定义数据绑定规则，特别是在控制器方法中接收请求参数时。

具体来说，以下是它们的作用：

1. **@InitBinder 注解**：
   - `@InitBinder` 注解标记在控制器类的方法上，用于告诉 Spring 在执行该控制器方法之前执行指定的数据绑定配置。
   - 在你的示例中，`@InitBinder` 注解被标记在 `initBinder` 方法上，这意味着在执行 `MessageAction` 控制器中的任何方法之前，`initBinder` 方法会被调用以配置数据绑定规则。

2. **WebDataBinder 对象**：
   - `WebDataBinder` 对象是Spring MVC用于数据绑定的关键组件，它用于控制数据绑定的行为。
   - 在 `initBinder` 方法中，你可以配置 `WebDataBinder` 对象，例如注册自定义的属性编辑器，指定数据格式，以及其他与数据绑定相关的设置。
   - 在你的示例中，`initBinder` 方法注册了一个自定义的属性编辑器，用于将日期字符串（"yyyy-MM-dd" 格式）转换为 `java.util.Date` 对象。这使得当你的控制器方法接收到 `Message` 对象时，日期属性会自动从请求参数中解析和转换，而不需要在每个控制器方法中重复此操作。

综合来说，通过在 `AbstractBaseAction` 中定义 `@InitBinder` 方法并配置 `WebDataBinder`，你可以实现全局的数据绑定规则，以确保在多个控制器方法中都能够正确地处理日期字符串到 `java.util.Date` 的转换。这提高了代码的可维护性和重用性，减少了代码重复。在 `MessageAction` 控制器中的 `echo` 方法中，你可以直接使用 `Message` 对象，而不需要担心日期字符串的处理，因为全局的绑定规则已经在 `AbstractBaseAction` 中配置好了。

## `binder.registerCustomEditor()`

`binder.registerCustomEditor()` 方法用于在 `WebDataBinder` 中注册自定义属性编辑器（`PropertyEditor`）。它的两个参数分别是：

1. **第一个参数**：指定要注册自定义编辑器的属性类型（`Class<?>`）。这是你希望在绑定过程中自定义处理的属性类型。通常，你会指定Java类，例如`String.class`、`LocalDate.class`、或自定义的枚举类型等。

2. **第二个参数**：指定自定义的属性编辑器实例（`PropertyEditor`）。这是一个实现了 `PropertyEditor` 接口的对象，它包含了将属性从字符串表示形式转换为目标类型（和反向）的逻辑。通常，你会创建一个自定义的属性编辑器对象，或使用Spring提供的内置编辑器。

