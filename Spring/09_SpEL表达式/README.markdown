# SpEL表达式

> Spring Expression Language (SpEL) 是Spring框架中的一个强大的表达式语言，它支持在运行时查询和操作对象图。SpEL可以在XML和注解中使用，提供了类似于Java的语法以及额外的特性。SpEL主要用于查询和操作对象，以及Spring配置中的值注入。

Spring Expression Language (SpEL) 的主要作用和优势如下：

1. **灵活的表达式评估**:
   - SpEL提供了一种强大且灵活的方法来查询和操作对象图。它允许开发人员在运行时通过表达式语言来访问和操纵对象的属性和方法，提供了极大的灵活性。

2. **配置外部化**:
   - 通过SpEL，开发人员可以在配置文件中使用表达式，实现配置的外部化和动态化。这样可以根据不同的运行时环境动态地注入不同的配置值。

3. **简化代码**:
   - SpEL可以简化Java代码，减少模板代码的编写。例如，它可以用简单的表达式替换复杂的条件逻辑，使代码更为简洁和清晰。

4. **条件化Bean的注册**:
   - SpEL可以用于Spring配置中的条件化Bean注册，使得开发人员可以根据特定的条件动态地注册或注销Bean。

5. **数据验证**:
   - SpEL可以用于数据验证，例如，可以用SpEL表达式来检查用户输入是否符合特定的格式或范围。

6. **集成Spring功能**:
   - SpEL与Spring框架紧密集成，能够方便地访问Spring容器中的Bean，以及其他Spring的功能，如安全性和消息解析。

7. **类型安全**:
   - SpEL提供了类型安全的表达式评估，可以在编译时检查表达式的类型，减少运行时错误的可能性。

8. **支持多种数据结构**:
   - SpEL支持数组、集合、列表和映射等多种数据结构，以及它们的操作和查询，使得处理这些数据结构变得非常方便和简单。

9. **支持方法调用**:
   - SpEL支持方法调用，使得可以在表达式中调用Java方法，增加了表达式的功能和灵活性。

10. **易于扩展和定制**:
    - SpEL是易于扩展和定制的，开发人员可以通过注册自定义的函数、操作符和方法解析器来扩展SpEL的功能。

# SpEL表达式使用的场景

Spring Expression Language (SpEL) 在Spring应用中有很多用途。以下是一些常见的使用场景和例子：

1. **配置注入**：通过SpEL, 你可以在Spring的配置文件中使用表达式来动态计算属性值。这对于基于不同环境条件定制配置非常有用。

```java
@Value("#{properties['some.property']}")
private String someProperty;
```

2. **条件Bean注册**：你可以使用SpEL表达式来控制Bean的注册。例如，你可以根据某个条件来决定是否注册一个Bean。

```java
@Bean
@ConditionalOnExpression("#{T(java.lang.System).getProperty('os.name').contains('Windows')}")
public MyBean myBean() {
    // ...
}
```

3. **方法安全**：使用SpEL可以在方法级别上应用安全限制，例如使用`@PreAuthorize`, `@Secured`等注解来控制方法的访问权限。

```java
@PreAuthorize("hasRole('ROLE_ADMIN') and #user.name == authentication.name")
public void updateUser(User user);
```

4. **数据验证**：SpEL可以用于数据验证，例如，可以用SpEL表达式来检查用户输入是否符合特定的格式或范围。

```java
@AssertTrue(message = "Error: Start date is after end date")
public boolean isDateRangeValid() {
    return startDate.before(endDate);
}
```

5. **动态查询**：在创建动态查询时，SpEL可以帮助构建查询字符串，例如在Spring Data JPA或Spring Data MongoDB中。

```java
@Query("select u from User u where u.status = ?#{T(com.example.Status).ACTIVE}")
List<User> findActiveUsers();
```

6. **模板渲染**：在模板引擎（如Thymeleaf或FreeMarker）中，可以使用SpEL来动态渲染页面内容。

```html
<p th:text="#{message}"></p>
```

7. **事件处理**：在处理应用事件时，可以使用SpEL来动态计算事件处理逻辑。

```java
@EventListener(condition = "#event.success")
public void handleSuccessfulEvent(MyEvent event) {
    // ...
}
```

8. **缓存抽象**：在使用Spring的缓存抽象时，可以使用SpEL来动态计算缓存的key。

```java
@Cacheable(value = "books", key = "#isbn")
public Book findBook(String isbn);
```

# SpEL表达式的使用范例

## 解析字符串表达式

```java
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpELTest {
    @Test
    public void test1(){
        String context1 = "#{\"zyf\" + \"slp\"}";
        String context2 = "#{'zyf' + 'slp'}";
        System.out.println(spel(context1));
        System.out.println(spel(context2));
    }

    public static Object spel(String context){
        SpelExpressionParser parser = new SpelExpressionParser();
        //使用#{...}作为表达式的定界符
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        //评估表达式，并返回结果
        return (String)expression.getValue();
    }
}
```

输出：

```
zyfslp
zyfslp
```

## 解析字面表达式与数学表达式

```java
String context1 = "#{1.1}";
String context2 = "#{1 + 1}";
String context3 = "#{1.1 * 1}";
String context4 = "#{true}";
String context5 = "#{'zyf'}";
System.out.println("结果" + spel(context1) + ", 类型为" +
        spel(context1).getClass().getName());
System.out.println("结果" + spel(context2) + ", 类型为" +
        spel(context2).getClass().getName());
System.out.println("结果" + spel(context3) + ", 类型为" +
        spel(context3).getClass().getName());
System.out.println("结果" + spel(context4) + ", 类型为" +
        spel(context4).getClass().getName());
System.out.println("结果" + spel(context5) + ", 类型为" +
        spel(context5).getClass().getName());
```

输出：

```
结果1.1, 类型为java.lang.Double
结果2, 类型为java.lang.Integer
结果1.1, 类型为java.lang.Double
结果true, 类型为java.lang.Boolean
结果zyf, 类型为java.lang.String
```

## 解析关系表达式

```java
String context1 = "#{10 eq 10}";
String context2 = "#{1 gt 10}";
String context3 = "#{2 le 1}";
String context4 = "#{4 ge 3}";
String context5 = "#{3 lt 4}";
String context6 = "#{3 between {2,5}}";
String context7 = "#{'zyf' == 'jjj'}";
String context8 = "#{'zyf' eq 'zyf'}";
String context9 = "#{10 < 11}";
System.out.println("结果" + spel(context1) + ", 类型为" +
        spel(context1).getClass().getName());
System.out.println("结果" + spel(context2) + ", 类型为" +
        spel(context2).getClass().getName());
System.out.println("结果" + spel(context3) + ", 类型为" +
        spel(context3).getClass().getName());
System.out.println("结果" + spel(context4) + ", 类型为" +
        spel(context4).getClass().getName());
System.out.println("结果" + spel(context5) + ", 类型为" +
        spel(context5).getClass().getName());
System.out.println("结果" + spel(context6) + ", 类型为" +
        spel(context6).getClass().getName());
System.out.println("结果" + spel(context7) + ", 类型为" +
        spel(context7).getClass().getName());
System.out.println("结果" + spel(context8) + ", 类型为" +
        spel(context8).getClass().getName());
System.out.println("结果" + spel(context9) + ", 类型为" +
        spel(context9).getClass().getName());
```

输出：

```
结果true, 类型为java.lang.Boolean
结果false, 类型为java.lang.Boolean
结果false, 类型为java.lang.Boolean
结果true, 类型为java.lang.Boolean
结果true, 类型为java.lang.Boolean
结果true, 类型为java.lang.Boolean
结果false, 类型为java.lang.Boolean
结果true, 类型为java.lang.Boolean
结果true, 类型为java.lang.Boolean
```

## 解析逻辑表达式

`and`与，`or`或，`not`非

```java
String context1 = "#{10 eq 10 and 10 lt 12}";
String context2 = "#{11 gt 10 or 10 lt 12}";
String context3 = "#{not(10 gt 11)}";
System.out.println("结果" + spel(context1) + ", 类型为" +
        spel(context1).getClass().getName());
System.out.println("结果" + spel(context2) + ", 类型为" +
        spel(context2).getClass().getName());
System.out.println("结果" + spel(context3) + ", 类型为" +
        spel(context3).getClass().getName());
```

输出：

```
结果true, 类型为java.lang.Boolean
结果true, 类型为java.lang.Boolean
结果true, 类型为java.lang.Boolean
```

## 解析三目运算符

```java
String context1 = "#{10 == 10 ? 'zyf' : 'slp'}";
String context2 = "#{11 < 9 ? 7.1 : 5}";
String context3 = "#{'zyf' != null ? 'zyf' : 12}";
System.out.println("结果" + spel(context1) + ", 类型为" +
        spel(context1).getClass().getName());
System.out.println("结果" + spel(context2) + ", 类型为" +
        spel(context2).getClass().getName());
System.out.println("结果" + spel(context3) + ", 类型为" +
        spel(context3).getClass().getName());
```

输出：

```
结果zyf, 类型为java.lang.String
结果5, 类型为java.lang.Integer
结果zyf, 类型为java.lang.String
```

## 解析字符串处理操作

```java
String context1 = "#{'spel'.toUpperCase()}";
String context2 = "#{'spel'[0]}";
String context3 = "#{'spel'.substring(0,2)}";
System.out.println("结果" + spel(context1) + ", 类型为" +
        spel(context1).getClass().getName());
System.out.println("结果" + spel(context2) + ", 类型为" +
        spel(context2).getClass().getName());
System.out.println("结果" + spel(context3) + ", 类型为" +
        spel(context3).getClass().getName());
```

输出

```
结果SPEL, 类型为java.lang.String
结果s, 类型为java.lang.String
结果sp, 类型为java.lang.String
```

## 解析Java类型

`T`操作符则是用来表示一个Java类型的，通常用于在SpEL表达式中引用静态方法或静态字段

```java
String context1 = "#{T(java.lang.String)}";
String context2 = "#{T(java.util.Date)}";
String context3 = "#{T(java.util.Arrays)}";
String context4 = "#{T(java.lang.String).valueOf(123)}"
String context5 = "#{T(java.util.Arrays).asList(1,2,3)}
System.out.println("结果" + spel(context1) + ", 类型为" +
        spel(context1).getClass().getName());
System.out.println("结果" + spel(context2) + ", 类型为" +
        spel(context2).getClass().getName());
System.out.println("结果" + spel(context3) + ", 类型为" +
        spel(context3).getClass().getName());
System.out.println("结果" + spel(context4) + ", 类型为" +
        spel(context4).getClass().getName());
System.out.println("结果" + spel(context5) + ", 类型为" +
        spel(context5).getClass().getName());
```

输出：

```
结果class java.lang.String, 类型为java.lang.Class
结果class java.util.Date, 类型为java.lang.Class
结果class java.util.Arrays, 类型为java.lang.Class
结果123, 类型为java.lang.String
结果[1, 2, 3], 类型为java.util.Arrays$ArrayList
```

## 表达式变量操作

```java
String exp = "#{#varA + #varB}";
SpelExpressionParser parser = new SpelExpressionParser();
Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
StandardEvaluationContext context = new StandardEvaluationContext();
context.setVariable("varA", "zyf");
context.setVariable("varB", "slp");
String value1 = expression.getValue(context, String.class);
System.out.println(value1);
context.setVariable("varA", 111.1);
context.setVariable("varB", 222);
Double value2 = expression.getValue(context, Double.class);
System.out.println(value2);
```

输出：

```
zyfslp
333.1
```

## 解析集合类型

- `list`类型

```java
String context = "#{{'zyf','slp','lucy'}}";
System.out.println("结果" + spel(context) + ", 类型为" +
        spel(context).getClass().getName());
```

输出：

```
结果[zyf, slp, lucy], 类型为java.util.Collections$UnmodifiableRandomAccessList
```

- map类型

```java
String context = "#{{'zyf':111,'slp':222,'lucy':333}}";
System.out.println("结果" + spel(context) + ", 类型为" +
        spel(context).getClass().getName());
```

输出

```
结果{zyf=111, slp=222, lucy=333}, 类型为java.util.Collections$UnmodifiableMap
```

# 使用SpEL表达式进行属性注入

- `com.zhouyf.pojp.Example`

```java
package com.zhouyf.pojo;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Data
public class Example {
    @Value("#{T(com.zhouyf.utils.MapUtil).createMap()}")
    private Map<String, String> map1;

    @Value("#{{'name':'zyf', 'age':'12'}}")
    private Map<String, String> map2;

    @Value("#{mapBean}")
    private Map<String, String> map3;

    @Value("#{1 + 12}")
    private Integer age;
}
```

- 使用Bean工厂进行注入

```java
@Bean("mapBean")
public MapFactoryBean mapFactoryBean(){
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("name", "zyf");
    hashMap.put("age", "12");
    MapFactoryBean mapFactoryBean = new MapFactoryBean();
    mapFactoryBean.setSourceMap(hashMap);
    return mapFactoryBean;
}
```

```java
@Value("#{mapBean}")
private Map<String, String> map3;
```

- 静态方法进行注入

```java
public class MapUtil {
    public static Map<String, String> createMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "zyf");
        map.put("age", "12");
        return map;
    }
}
```

```java
@Value("#{T(com.zhouyf.utils.MapUtil).createMap()}")
private Map<String, String> map1;
```

- 字符串解析注入

```java
@Value("#{{'name':'zyf', 'age':'12'}}")
private Map<String, String> map2;
```

- 测试

```java
package com.zhouyf.test;

import com.zhouyf.config.MapBeanConfig;
import com.zhouyf.pojo.Example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapBeanConfig.class)
public class ExampleTest {
    @Autowired
    private Example example;

    @Test
    public void test(){
        System.out.println(example);
    }
}
```

输出：

```
Example(map1={name=zyf, age=12}, map2={name=zyf, age=12}, map3={name=zyf, age=12}, age=13)
```

在 SpEL 表达式或者任何其他地方引用一个 `FactoryBean` 时，Spring 容器会自动调用 `FactoryBean` 的 `getObject()` 方法，以获取由 `FactoryBean` 创建的对象，而不是 `FactoryBean` 本身。

但是，如果你确实需要引用 `FactoryBean` 本身，而不是它创建的对象，你可以在 bean 的名称前面加上一个 `&` 符号。例如，如果 `FactoryBean` 的名字是 `mapBean`，可以使用 `&mapBean` 来引用 `FactoryBean` 本身

```java
@Value("#{&mapBean}")
private FactoryBean<Map<String, String>> factoryBean;
```

测试：

```
Example(map1={name=zyf, age=12}, map2={name=zyf, age=12}, map3={name=zyf, age=12}, factoryBean=org.springframework.beans.factory.config.MapFactoryBean@780ec4a5, age=13)
```

可以看到`MapFactoryBean`本身被注入到了属性中。

