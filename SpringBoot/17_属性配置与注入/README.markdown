# YMAL

## YMAL的优势

Spring Boot 更推荐使用 YAML (`.yml` 或 `.yaml`) 配置而不是 XML 配置有以下几个原因：

1. **简洁性**：YAML 的格式更为简洁，没有像 XML 那样的开始和结束标签，因此相比之下更加简洁和易读。

2. **结构化**：YAML 通过缩进表示结构，这使得人们更容易一眼看出配置的层次结构，而不是像 XML 那样通过嵌套标签表示。

3. **支持复杂结构**：YAML 可以很容易地表示复杂的数据结构，例如列表和映射，而在 XML 中这样做可能需要更多的标记。

4. **更符合现代习惯**：随着时间的推移，YAML 在配置文件和基础设施作为代码 (IaC) 工具中变得越来越流行。许多现代工具和框架，如 Kubernetes、Ansible 和 Docker Compose，都采用了 YAML 格式。

5. **错误提示**：Spring Boot 在处理 YAML 文件时，如果文件格式不正确或存在语法错误，它可以提供更具体的错误提示。

6. **多文档支持**：YAML 支持在一个文件中包含多个文档，每个文档由 `---` 分隔。这在某些情况下可能非常有用。

7. **更符合 Spring Boot 的设计哲学**：Spring Boot 的目标之一是简化配置和引导应用程序的过程。YAML 的简洁性和易读性与这一目标相吻合。

## YMAL语法

YAML（YAML Ain't Markup Language）是一种用于数据序列化的简洁格式，经常被用于配置文件、数据交换等场景。以下是一些基本的 YAML 结构和语法的教学内容： **基础语法**：

1. - YAML 文件通常以 `.yml` 或 `.yaml` 为扩展名。
   - YAML 是大小写敏感的。
   - YAML 文件中的每个条目都是键值对的形式。
   
2. **标量（Scalars）**：
   
   - 字符串、数字和布尔值都是标量。
   - 字符串可以用双引号或单引号包围，但这并不总是必要的。
     ```yaml
     name: "John"
     age: 25
     isStudent: false
     ```
   
3. **列表（Arrays）**：
   - 列表项以短横线 `-` 开始，并且有相同的缩进。
     ```yaml
     fruits:
       - Apple
       - Banana
       - Orange
     ```

4. **字典（Maps）**：
   - 字典是键值对的集合。
   - 字典可以嵌套。
     ```yaml
     person:
       name: John
       age: 25
     ```

5. **多文档**：
   - 一个 YAML 文件可以包含多个文档，每个文档由 `---` 分隔。
     ```yaml
     ---
     name: John
     ---
     name: Jane
     ```

6. **注释**：
   - 使用 `#` 符号来添加注释。
     ```yaml
     # This is a comment
     name: John
     ```

7. **多行字符串**：
   - 使用 `|` 保留换行符，使用 `>` 折叠换行符。
     ```yaml
     multilineString1: |
       Line 1
       Line 2
     multilineString2: >
       Line 1
       Line 2
     ```

8. **锚点和别名**：
   - 使用 `&` 创建锚点，使用 `*` 引用别名。
     ```yaml
     default: &DEFAULT
       name: John
     user1:
       <<: *DEFAULT
       age: 25
     ```

9. **合并键**：
   - 使用 `<<` 合并其他映射的键值对。
     ```yaml
     base: &base
       name: "Everyone has same name here"
     foo:
       <<: *base
       age: 10
     ```

10. **字面块标量**：
    - 使用 `|` 和缩进来表示多行字符串。
      ```yaml
      description: |
        This is a longer description
        that spans multiple lines.
      ```

# 使用YMAL进行属性注入

- 创建配置文件`application.yml`

<img src="assets/image-20231025134618729.png" alt="image-20231025134618729" style="zoom:50%;" />

- `application.yml`中

```yaml
source:
  mysql: mysql://localhost:3306/zhouyf
  redis: redis://localhost:6379/0
  messages: zhouyf,slp
  info: '{"name":"zhouyf", "age":24}' #Map集合
```

- `com.zhouyf.action.SourceAction`

```java
package com.zhouyf.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//Spring的RESTful控制器
@RequestMapping("/source/*")
public class SourceAction {
    @Value("${source.mysql}")
    private String mysql;
    @Value("${source.redis}")
    private String redis;
    @Value("${source.messages}")
    private List<String> messages;
    @Value("#{${source.info}}") //SpEL表达式
    private Map<String, Object> infos;


    @GetMapping(name = "show", produces = MediaType.APPLICATION_JSON_VALUE)
    //此方法将以JSON格式返回所有配置信息。
    public Object showSource(){
        //创建了一个新的HashMap，并将所有从application.yml文件中注入的配置值添加到此map中
        HashMap<String, Object> info = new HashMap<>();
        info.put("mysql", mysql);
        info.put("redis", redis);
        info.put("message", messages);
        info.put("infos", infos);
        return info;
    }
}
```

对于`info`字段，使用了SpEL(Spring Expression Language, Spring表达式语言)表达式`#{${source.info}}`来将其作为一个Map对象进行解析和注入。

- 访问测试

```
localhost:8080/source/show
```

```json
{
  "mysql": "mysql://localhost:3306/zhouyf",
  "message": [
    "zhouyf",
    "slp"
  ],
  "redis": "redis://localhost:6379/0",
  "infos": {
    "name": "zhouyf",
    "age": 24
  }
}
```

![image-20231025141951276](assets/image-20231025141951276.png)

# 总结

使用 `@Value` 注解时，Spring Boot 会从其已加载的配置中查找对应的属性值。因此，即使没有明确地使用任何注解来加载 `application.yml` 或 `application.properties`，Spring Boot 仍然会自动加载它们并使其内容可用。

这种自动配置的行为是 Spring Boot 的一个核心特性，旨在简化应用程序的配置和引导流程，从而使开发者能够更快速、更轻松地创建 Spring 应用程序。