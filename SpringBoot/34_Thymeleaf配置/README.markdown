# 配置Thymeleaf环境

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