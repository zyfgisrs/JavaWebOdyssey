# Thymeleaf编程起步

- 创建子项目`microboot-thymaleaf`
- `microboot/build.gradle`在`microboot-thymaleaf`子项目中添加`Thymeleaf`依赖

```
project('microboot-thymeleaf') { // 子模块
    dependencies { // 配置子模块依赖
        compile("org.springframework.boot:spring-boot-starter-web")
        compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    }
}
```

- `microboot-thymaleaf`子项目中配置新的源代码目录`src/main/view/templates`

- `microboot/build.gradle`源代码目录配置

```groovy
sourceSets {    // 源代码目录配置
    main { // main及相关子目录配置
        java { srcDirs = ['src/main/java'] }
        resources { srcDirs = ['src/main/resources', "src/main/view"] }
    }
```

- 创建模板引擎`message/message_show.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>thymeleaf模板渲染</title>
</head>
<body>
<p th:text="'title = ' + ${title}"/>
<p th:text="'content = ' + ${content}"/>
<p th:text="${message}"/>
</body>
</html>
```

- `com.zhouyf.action.ThymeleafAction`

```java
package com.zhouyf.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/thymeleaf/*")
public class ThymeleafAction {
    @RequestMapping("view")
    public ModelAndView view(String message){
        ModelAndView modelAndView = new ModelAndView("message/message_show");
        modelAndView.addObject("message", message);
        modelAndView.addObject("title", "zhouyf");
        modelAndView.addObject("content", "zyf0503");
        return modelAndView;
    }
}
```

Thymeleaf 和 Spring Boot 在集成时遵循一些约定，使得你无需为视图指定完整路径。具体地，有以下几点需要注意：

1. **默认路径**：Spring Boot 将 `src/main/resources/templates` 设置为模板引擎（如Thymeleaf）的默认路径。因此，你不需要指定完整的路径，只需指定模板文件的相对路径即可。

2. **省略文件扩展名**：当你在 `ModelAndView` 或控制器中返回模板名称时，不需要加 `.html` 后缀。Spring Boot 已经知道它应该是 `.html` 文件，因为 Thymeleaf 默认处理这种类型的文件。

3. **斜杠 vs. 文件路径**：在模板名称中，你使用的斜杠（`/`）实际上被解释为目录路径。所以当你写 `"message/message_show"` 时，Spring Boot 和 Thymeleaf 解释为 `src/main/resources/templates/message/message_show.html`。

综上所述，当你写：
```java
ModelAndView modelAndView = new ModelAndView("message/message_show");
```
Spring Boot 和 Thymeleaf 将其解释为：
```
src/main/resources/templates/message/message_show.html
```
- 访问测试

![image-20231030200635984](assets/image-20231030200635984.png)

# 配置Thymeleaf环境

当使用 Spring Boot 与 Thymeleaf 集成时，大部分的默认配置都已经很合理，但在某些情况下，可能希望自定义这些配置。Spring Boot 使用 `application.properties` 或 `application.yml` 文件来管理这些配置参数。

下面，我将会介绍如何在 `application.yml` 文件中配置 Thymeleaf 与 Spring Boot 的集成，并按照层次性对这些配置参数进行解释：

1. **基础配置**:
    - **spring.thymeleaf.prefix**: 设置模板的前缀，默认是 `classpath:/templates/`。
    - **spring.thymeleaf.suffix**: 设置模板的后缀，默认是 `.html`。

    示例：
    ```yaml
    spring:
      thymeleaf:
        prefix: classpath:/templates/
        suffix: .html
    ```

2. **模板解析**:
    - **spring.thymeleaf.mode**: 设置模板模式，例如 `HTML`, `XML`。默认是 `HTML`。
    - **spring.thymeleaf.encoding**: 设置模板文件的字符编码，默认是 `UTF-8`。
    - **spring.thymeleaf.cache**: 设置是否缓存模板，默认是 `true`（在生产环境下）。在开发过程中，你可能希望将其设置为 `false`。

    示例：
    ```yaml
    spring:
      thymeleaf:
        mode: HTML
        encoding: UTF-8
        cache: false
    ```

3. **额外设置**:
    - **spring.thymeleaf.servlet.content-type**: 设置 Content-Type，默认是 `text/html`。
    - **spring.thymeleaf.template-resolver-order**: 设置模板解析器的顺序，有时在使用多个模板引擎时可能需要。
    - **spring.thymeleaf.check-template-location**: 检查模板位置是否存在，默认是 `true`。

4. **MIME 设置**:
    - **spring.thymeleaf.servlet.producible-media-types**: 定义可以生成的媒体类型，如 `text/html`。

5. **其他集成**:
    - **spring.thymeleaf.excluded-view-names**: 定义排除的视图名，这些视图名不使用 Thymeleaf 来渲染。
    - **spring.thymeleaf.enabled**: 启用或禁用 Thymeleaf，默认是 `true`。

# 整合静态资源

在Web开发中除了要进行动态页面的显示处理外，也需要引入一些静态资源，如图片、样式文件、脚本等。在Thymeleaf中这些静态资源要定义在`static`路径中，同时可以直接进行资源引入。

<img src="assets/image-20231031101135429.png" alt="image-20231031101135429" style="zoom:67%;" />

- `style.css`

```css
.message {
    background: rgb(128, 128, 128);
    color: white;
    font-size: 22px;
    text-align: center;
}
```

- `message_index.js`

```javascript
window.onload = function (){
    console.log("zhouyf:我是周杨凡")
}
```

- `message_index.html`

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>thymeleaf模板渲染</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="icon" type="image/x-icon" href="/images/favicon.ico">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script type="text/javascript" src="/js/message_index.js"></script>
</head>
<body>
    <div><img src="/images/staffelei.jpg"></div>
    <div class="message">zhouyangfan</div>
</body>
</html>
```

- 启动SpringBoot服务，访问静态资源

```
http://localhost:8080/message_index.html
```

![image-20231031103304513](assets/image-20231031103304513.png)

可以看到客户端成功访问了服务器的静态资源。

但是，SpringBoot的将访问映射到一下目录

```
classpath:/static
classpath:/public
classpath:/resources
classpath:/META-INF/resources
```

而我们静态资源的目录位于`src/main/view/static`，正常来说如果不做任何配置，是不能进行访问的。而这里可以进行访问的原因是因为我们在`build.gradle`配置文件中将`src/main/view`也配置为了资源文件目录

```groovy
sourceSets {    // 定义源代码的目录块
    main { // 定义源代码和资源文件的目录
        java { srcDirs = ['src/main/java'] } //定义主源代码目录
        //定义主资源目录
        resources { srcDirs = ['src/main/resources', "src/main/view"] }
    }
    test { // 定义测试源代码和资源的目录
        java { srcDirs = ['src/test/java'] }
        resources { srcDirs = ['src/test/resources'] }
    }
}
```

# 路径访问支持

本例通过一个控制层跳转到指定的Thymeleaf模板页，而后在模板页中进行资源加载时采用`@{static子目录/资源名称}`的形式处理。

- `src/main/view/templates/message/message_index.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>thymeleaf模板渲染</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="icon" type="image/x-icon" th:href="@{/images/favicon.ico}">
    <link rel="stylesheet" type="text/css" th:href="@{/css/style.css}">
    <script type="text/javascript" th:src="@{/js/message_index.js}"></script>
</head>
<body>
<div><img src="/images/staffelei.jpg"></div>
<div class="message">zhouyangfan</div>
</body>
</html>
```

- `src\main\java\com\zhouyf\action\ThymeleafAction`

```java
package com.zhouyf.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf/*")
public class ThymeleafAction {
    @RequestMapping("path")
    public String path() {
        return "message/message_path.html";
    }
}
```

- 客户端访问

```
http://localhost:8080/thymeleaf/path
```

![image-20231031110512333](assets/image-20231031110512333.png)

在动态页面上可以直接利用@{}的形式从根路径下加载指定的目录中的资源信息。

# 读取资源文件

国际化（简称 "i18n"，因为 "internationalization" 之间有 18 个字符）是为了使软件能够适应多种语言和地域的需求。在 `SpringBoot` 开发中，我们经常使用资源文件进行国际化。

- 资源文件`resources\i18n`

```properties
zhouyf.message=zyf
zhouyf.email=36075103@qq.com
zhouyf.welcome=欢迎"{0}"光临zhouyf站点
```

- `resources\i18n\Message_en_US.properties`

```properties
zhouyf.message=zhouyangfan
zhouyf.email=36075103@qq.com
zhouyf.welcome=Welcome "{0}" access site
```

- `resources\i18n\Message_zh_CN.properties`

```properties
zhouyf.message=周杨凡
zhouyf.email=36075103@qq.com
zhouyf.welcome=欢迎"{0}"光临zhouyf站点
```

- `resources\i18n\Message_zh_TW.properties`

```properties
zhouyf.message=周杨凡(TW)
zhouyf.email=36075103@qq.com
zhouyf.welcome=欢迎"{0}"光临zhouyf站点
```

- `application.yaml`

```yaml
spring:
  messages:
    basename: i18n/Message
```

在Spring Boot中，`spring.messages.basename` 配置属性用于指定国际化（i18n）资源文件的基础名称（basename）。这是Spring Boot应用加载国际化属性文件的起点。

这里的配置含义是：

- **`i18n/Message`**：这是资源文件的基础路径和名称。假设这些资源文件位于 `src/main/resources` 目录下，那么实际的文件路径会是 `src/main/resources/i18n/Message.properties`。此外，对于其他语言或地区的特定版本，Spring Boot会在此基础上查找相应的后缀。例如，对于美国英语，它会查找 `i18n/Message_en_US.properties`；对于简体中文，它会查找 `i18n/Message_zh_CN.properties`。

作用总结：

1. **指定资源文件位置**：告诉Spring Boot在哪里可以找到国际化的属性文件。
2. **自动查找特定语言/地区版本**：基于上述的基础名称，Spring Boot会自动查找与当前设置的语言/地区匹配的资源文件。

所以，当你在应用中进行国际化时，只需为每种语言/地区提供一个相应的资源文件，并按照约定的命名方式命名。然后，Spring Boot和Spring MVC会根据用户的语言设置自动选择并使用相应的资源文件。

- `ThymeleafConfig`类

是一个配置类，定义了如何解析语言设置和如何根据请求更改语言。

`localeResolver()` 方法定义了如何获取当前的语言设置，这里默认为简体中文。

`localeChangeInterceptor()` 定义了一个拦截器，该拦截器会检查每个请求的 `lang` 参数来更改语言设置。

`addInterceptors()` 方法将上面定义的拦截器注册到Spring MVC中，使其生效。

```java
package com.zhouyf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
@Configuration
//标记该类是一个配置类，用于定义Bean或与Spring框架的集成。
public class ThymeleafConfig implements WebMvcConfigurer {
    //实现了 WebMvcConfigurer 接口。这意味着这个类可以用来配置Spring MVC的特定功能。
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.TRADITIONAL_CHINESE);
        //这个方法创建了一个 SessionLocaleResolver 对象，
        return slr;
    }

    public LocaleChangeInterceptor localeChangeInterceptor() {
        //它创建并返回一个 LocaleChangeInterceptor 对象。
        // 这个拦截器用于检测请求参数中的 lang 参数，以动态更改语言设置。
        LocaleChangeInterceptor lcl = new LocaleChangeInterceptor();
        lcl.setParamName("lang");
        return lcl;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());//添加拦截器
    }
    //这个方法是 WebMvcConfigurer 接口的一个方法。
    // 当Spring MVC启动时，它会调用这个方法
    // 这个方法里面的代码会把 LocaleChangeInterceptor 注册到Spring MVC的拦截器中。
    // 这意味着，对于每一个请求，LocaleChangeInterceptor 都会被触发，从而实现了根据请求参数更改语言的功能。
}
```

- 控制器方法

请求会被映射到与 `@RequestMapping("i18n")` 注解匹配的控制器方法，该方法简单地返回了 `message/message_i18n.html` 的路径。

```java
@RequestMapping("i18n")
public String i18n() {
    return "message/message_i18n.html";
}
```

- 视图解析

由于返回的是一个HTML文件路径，Spring Boot会使用Thymeleaf模板引擎来解析该HTML文件。

Thymeleaf会查找文件 `message/message_i18n.html`。

它会解析文件中的所有Thymeleaf语法，如 `#{zhouyf.message}`。

基于当前的语言设置（传统中文），Thymeleaf会从 `Message_zh_TW.properties` 文件中获取对应的值。

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h2 th:text="'【资源】message = ' + #{zhouyf.message}"/>
    <h2 th:text="'【资源】email = ' + #{zhouyf.email}"/>
    <h2 th:text="'【资源】welcome = ' + #{zhouyf.welcome('周杨凡')}"/>
</body>
</html>
```

- 请求访问

```
http://localhost:8080/thymeleaf/i18n
```

![image-20231031134847456](assets/image-20231031134847456.png)

```
http://localhost:8080/thymeleaf/i18n?lang=zh_CN
```

![image-20231031134902961](assets/image-20231031134902961.png)

```
http://localhost:8080/thymeleaf/i18n?lang=en_US
```

![image-20231031134933891](assets/image-20231031134933891.png)

可以看到当不使用`lang`参数来指定语言，返回就是传统中文，这是在配置类中定义的，当使用lang参数来指定语言时，服务器能够动态地提供相应语言的内容。

# 环境对象支持

当我们在使用Thymeleaf模板引擎时，Thymeleaf为我们提供了一系列的内置对象，这些对象在模板中可以直接使用，大大增强了我们的开发效率。这些对象被称为“环境对象”或“上下文对象”。

为了方便理解，我们可以把这些环境对象想象成工具箱，当你在设计一个页面时，Thymeleaf提供了这个工具箱来帮助你完成各种功能。

以下是Thymeleaf提供的一些主要的环境对象：

1. **#ctx**: 上下文对象，它包含了关于模板本身的各种信息，如模板的名称、URL等。

2. **#vars**: 共享变量的集合。这实际上是上下文对象中的一部分，但由于它很常用，因此Thymeleaf提供了一个专门的变量来访问它。

3. **#locale**: 当前的Locale对象，通常用于国际化。

4. **#httpServletRequest** 和 **#httpSession**: 它们分别代表了HttpServletRequest和HttpSession对象，允许我们从模板直接访问这些对象的属性。

5. **#execInfo**: 提供了有关当前模板执行的信息，例如模板名称、处理时间等。

6. **#dates**, **#calendars**, **#numbers**, **#strings**: 这是一组实用工具对象，用于日期、日历、数字和字符串的常见操作。例如，日期格式化、字符串连接等。

7. **#url**: 用于URL处理的实用工具对象。

8. **#messages**: 用于处理国际化消息的实用工具对象。

9. **#objects**: 一个通用对象工具。

10. **#bools**: 用于布尔操作的实用工具对象。

这些环境对象为我们提供了在模板中执行各种操作的能力，从基本的字符串操作到复杂的日期和数字格式化，以及直接从`HttpServletRequest`或`HttpSession`中获取数据。

- `message_attribute.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div th:text="'【request属性】 message = ' +  ${#httpServletRequest.getAttribute('message')}"/>
    <div th:text="'【session属性】 message = ' +  ${#httpSession.getAttribute('message')}"/>
    <div th:text="'【application属性】 message = ' +  ${#httpServletRequest.getServletContext().getAttribute('message')}"/>
</body>
</html>
```

- `com.zhouyf.Action.ThymaleafAction`

```java
@RequestMapping("attribute")
public String showAttribute(HttpServletRequest request, HttpSession session) {
    request.setAttribute("message", "【request】message");
    session.setAttribute("message", "【session】message");
    request.getServletContext().setAttribute("message", "【application】message");
    return "message/message_attribute";
}
```

- 客户端访问

![image-20231031143048679](assets/image-20231031143048679.png)

# 渲染对象

在Thymeleaf中，`${...}`和`*{...}`都是变量表达式，它们用于从上下文中获取数据。但它们的使用场景和方式略有不同。

`${...}`是标准的变量表达式，它用于直接从模板上下文中获取数据。

- 当你在模板的任何地方引用上下文中的变量时，通常使用此表达式。
  
  ```html
  <p th:text="${username}">用户名</p>
  ```
  

`*{...}`是选择表达式，它是在特定对象上下文中使用的。当我们对某个对象设置一个“局部”上下文后，我们可以使用`*{...}`来直接访问该对象的属性，而不需要再次指定对象名。

- 当你使用`th:object`为某个HTML元素（如`<form>`标签）定义了一个特定的对象上下文时，使用此表达式来引用这个对象的属性是很有用的。

  示例：
  ```html
  <form th:object="${user}">
      <input type="text" th:field="*{username}" />
  </form>
  ```
  
  假设`user`对象有一个属性`username`的值为"John"，`th:field`会绑定该输入框到`user`对象的`username`属性。使用`*{...}`可以直接引用属性，而不需要写为`${user.username}`。

 **总结**:

- `${...}`是用于直接从模板上下文中获取变量的标准方法，无论你在模板的哪里都可以使用。

- `*{...}`通常与`th:object`一起使用，当你为某个元素设置了一个对象上下文，并想直接引用该对象的属性时使用。

将服务器创建的对象通过thymeleaf渲染到网页上

- 首先创建一个`com.zhouyf.vo.member`类

```java
package com.zhouyf.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class Member {
    private String name;

    private String mid;

    private Integer age;

    private Double salary;

    private Date birthday;
}
```

- 控制器`com.zhouyf.action.memberAction`

负责创建对象以及将对象渲染到视图中

```java
package com.zhouyf.action;

import com.zhouyf.vo.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/member/*")
public class MemberAction {
    @RequestMapping("show")
    public String showMember(Model model) throws ParseException {
        Member member = new Member("周杨凡", "zhouyf", 18, 8999.8,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").
                        parse("2021-02-24 21:23:32"));

        model.addAttribute("member", member);
        return "member/member_show";
    }
}
```

- `src/main/view/templates/member/member_show.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div th:text="'【Member属性】name = ' + ${member.name}"/>
    <div th:text="'【Member属性】mid = ' + ${member.mid}"/>
    <div th:text="'【Member属性】age = ' + ${member.age}"/>
    <div th:text="'【Member属性】salary = ' + ${#numbers.formatCurrency(member.salary)}"/>
    <div th:text="'【Member属性】birthday = ' + ${member.birthday}"/>
</body>
</html> 
```

另一种写法：

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div th:object="${member}">
    <div th:text="'【Member属性】name = ' + *{name}"/>
    <div th:text="'【Member属性】mid = ' + *{mid}"/>
    <div th:text="'【Member属性】age = ' + *{age}"/>
    <div th:text="'【Member属性】salary = ' + *{#numbers.formatCurrency(salary)}"/>
    <div th:text="'【Member属性】birthday = ' + *{birthday}"/>
</div>
</body>
</html>
```

- 客户端访问

![image-20231031150913715](assets/image-20231031150913715.png)

# 页面逻辑处理

`Thymeleaf` 提供了一系列的条件语句属性来帮助你根据条件渲染HTML内容。这些条件语句在模板中是非常有用的，因为它们允许你根据上下文数据动态地显示或隐藏HTML元素。

## 条件渲染

`Thymeleaf` 提供了`th:if`和`th:unless`这两个属性来进行条件渲染。

- `src/main/view/member/member_if.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div th:object="${member}">
    <div th:if="*{age lt 18}">
        年轻人，踏踏实实学好一门实用技术，才能获得你想要的未来
    </div>

    <div th:unless="*{age le 18}">
        你已经是一个成年人了，要担负起生活和家庭的重任，加油
    </div>
</div>
</body>
</html>
```

- 控制器方法

```java
@Controller
@RequestMapping("/member/*")
public class MemberAction {
    @RequestMapping("show")
    public String showMember(Model model) throws ParseException {
        Member member = new Member("周杨凡1", "zhouyf", 19, 8999.8,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").
                        parse("2021-02-24 21:23:32"));

        model.addAttribute("member", member);
        return "member/member_if";
    }
}
```

- 访问测试

```
http://localhost:8080/member/show
```

![image-20231031162144408](assets/image-20231031162144408.png)

三元运算符

- `src/main/view/member/member_ternary.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div th:object="${member}">
        <div th:text="*{mid == 'zhouyf' ? '是周杨凡' : '不是周杨凡'}"></div>
    </div>
</body>
</html>
```

- 控制器方法

```java
package com.zhouyf.action;

import com.zhouyf.vo.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/member/*")
public class MemberAction {
    @RequestMapping("show")
    public String showMember(Model model) throws ParseException {
        Member member = new Member("周杨凡", "zhouyf", 19, 8999.8,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").
                        parse("2021-02-24 21:23:32"));

        model.addAttribute("member", member);
        return "member/member_ternary";
    }
}
```

- 访问测试

```
http://localhost:8080/member/show
```

![image-20231031163512399](assets/image-20231031163512399.png)

switch多条件判断

- `src/main/view/member/member_switch.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>switch多条件判断</title>
</head>
<body>
    <div th:object="${member}">
        <div th:switch="*{mid}">
            <span th:case="111">111</span>
            <span th:case="zhouyf">周杨凡</span>
            <span th:case="222">222</span>
        </div>
    </div>
</body>
</html>
```

- 控制器方法

```java
package com.zhouyf.action;

import com.zhouyf.vo.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/member/*")
public class MemberAction {
    @RequestMapping("show")
    public String showMember(Model model) throws ParseException {
        Member member = new Member("周杨凡", "zhouyf", 19, 8999.8,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").
                        parse("2021-02-24 21:23:32"));

        model.addAttribute("member", member);
        return "member/member_switch";
    }
}
```

- 访问测试

```
http://localhost:8080/member/show
```

![image-20231031164702822](assets/image-20231031164702822.png)

## 数据迭代处理

### List数据

- `src/main/view/templates/member/member_list.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <style>
        table {
            border-collapse: collapse; /* 这会确保边框不会重叠 */
            width: 70%; /* 可选：使表格宽度为100% */
            border: 2px solid black; /* 设置表格的外边框 */
        }

        th, td {
            border: 1px solid black; /* 为每个表头和单元格设置边框 */
        }
    </style>
</head>
<body>
<div class="row">
    <div class="col-md-6">
        <table style="text-align: center">
            <tr>
                <th style="width: 10%" class="text-center">N0.</th>
                <th style="width: 20%" class="text-center">编号</th>
                <th style="width: 10%" class="text-center">姓名</th>
                <th style="width: 10%" class="text-center">年龄</th>
                <th style="width: 15%" class="text-center">月薪</th>
                <th style="width: 20%" class="text-center">生日</th>
            </tr>

            <tr th:each="member,memberStat:${memberList}">
                <td th:text="${memberStat.index+1}"/>
                <td th:text="${member.mid}"/>
                <td th:text="${member.name}"/>
                <td th:text="${member.age}"/>
                <td th:text="${#numbers.formatCurrency(member.salary)}"/>
                <td th:text="${#dates.format(member.birthday, 'yyyy-MM-dd')}"/>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
```

- 控制器方法

```java
@RequestMapping("list")
public String MemberList(Model model) throws ParseException {
    HashMap<String, Member> map = new HashMap<>();
    ArrayList<Member> list = new ArrayList<>();
    for (int l = 0; l < 5; l++) {
        Member member = new Member("周杨凡", "zhouyf" + l, 12, 888.8,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2001-02-23 12:12:12"));
        map.put("member" + l, member);
        list.add(member);
    }
    model.addAttribute("memberList", list);
    model.addAttribute("memberMap", map);
    return "member/member_list";
}
```

- 访问测试

```
http://localhost:8080/member/list
```

![image-20231031192821381](assets/image-20231031192821381.png)

### Map数据

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <style>
        table {
            border-collapse: collapse; /* 这会确保边框不会重叠 */
            width: 70%; /* 可选：使表格宽度为100% */
            border: 2px solid black; /* 设置表格的外边框 */
        }

        th, td {
            border: 1px solid black; /* 为每个表头和单元格设置边框 */
        }
    </style>
</head>
<body>
<div>
    <table style="text-align: center">
        <tr>
            <th style="width: 5%" class="text-center">N0.</th>
            <th style="width: 15%" class="text-center">KEY</th>
            <th style="width: 10%" class="text-center">编号</th>
            <th style="width: 20%" class="text-center">姓名</th>
            <th style="width: 10%" class="text-center">年龄</th>
            <th style="width: 10%" class="text-center">薪水</th>
            <th style="width: 20%" class="text-center">生日</th>
        </tr>

        <tr th:each="memberEntry,memberStat:${memberMap}">
            <td th:text="${memberStat.index + 1}"/>
            <td th:text="${memberEntry.key}"/>
            <td th:text="${memberEntry.value.mid}"/>
            <td th:text="${memberEntry.value.name}"/>
            <td th:text="${memberEntry.value.age}"/>
            <td th:text="${#numbers.formatCurrency(memberEntry.value.salary)}"/>
            <td th:text="${#dates.format(memberEntry.value.birthday, 'yyyy-MM-dd')}"/>
        </tr>
    </table>
</div>
</body>
</html>
```

- 控制器方法

```java
@RequestMapping("map")
public String MemberMap(Model model) throws ParseException {
HashMap<String, Member> map = new HashMap<>();
for (int l = 0; l < 5; l++) {
    Member member = new Member("周杨凡", "zhouyf" + l, 12, 888.8,
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2001-02-23 12:12:12"));
    map.put("member" + l, member);
}
model.addAttribute("memberMap", map);
return "member/member_map";
}
```

- 访问测试

```
http://localhost:8080/member/map
```

![image-20231031193153647](assets/image-20231031193153647.png)