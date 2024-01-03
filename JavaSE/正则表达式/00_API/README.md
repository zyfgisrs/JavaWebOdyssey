# 核心类

## Pattern 类

`Pattern` 类用于表示编译后的正则表达式。你不能直接使用其构造函数创建 `Pattern` 对象，因为其构造函数是私有的。相反，你使用 `Pattern.compile(String regex)` 静态方法来创建一个 `Pattern` 对象。这个方法将指定的正则表达式编译成一个模式。

## Matcher类

`Matcher` 类用于对输入字符串进行正则表达式匹配操作。你可以使用 `Pattern` 对象的 `matcher(CharSequence input)` 方法创建一个 `Matcher` 对象。

# API介绍

`java.util.regex.Matcher` 类是 Java 正则表达式 API 的一部分，提供了一系列用于模式匹配和字符串操作的功能。以下是 `Matcher` 对象的一些常用方法：

### 1. **matches()**

- **用途**：检查整个区域是否匹配给定的模式。
- **返回值**：如果整个区域匹配模式，则返回 `true`，否则返回 `false`。
- **示例**：
  
  ```java
  matcher.matches();
  ```
  
  ```java
  package com.zhouyf.demo1;
  
  import java.util.regex.Matcher;
  import java.util.regex.Pattern;
  
  public class DEMO3 {
      public static void main(String[] args) {
          String text1 = "zhouyf468@qq.com";
          String text2 = "-zhouyf468@qq.com";
  
          boolean email = isEmail(text1);
          System.out.println(email);
          System.out.println(isEmail(text2));
      }
  
      public static boolean isEmail(String email){
          String pattern = "^[a-zA-Z0-9]{2,}@[a-z]+\\.[a-z]{2,}$";
  
          Pattern compile = Pattern.compile(pattern);
          Matcher matcher = compile.matcher(email);
          return matcher.matches();
      }
  }
  ```

### 2. **find()**

- **用途**：在目标字符串中查找下一个匹配项。
- **返回值**：如果找到匹配项，则返回 `true`，否则返回 `false`。
- **示例**：
  ```java
  while (matcher.find()) {
      System.out.println(matcher.group());
  }
  ```

### 3. **group()**

- **用途**：返回由之前的匹配操作所匹配的输入子序列。
- **返回值**：返回匹配到的字符串。
- **示例**：
  ```java
  String matchedText = matcher.group();
  ```

### 4. **group(int group)**

- **用途**：返回在之前匹配操作中由给定组捕获的输入子序列。
- **参数**：组编号（从1开始）。
- **返回值**：返回指定组匹配到的字符串。
- **示例**：
  ```java
  String matchedGroup = matcher.group(1);
  ```

### 5. **start()** 和 **end()**

- **用途**：返回最近匹配的子序列的起始索引（`start()`）和结束索引（`end()`）。
- **返回值**：返回索引值。
- **示例**：
  ```java
  int startIndex = matcher.start();
  int endIndex = matcher.end();
  ```

### 6. **replaceFirst(String replacement)**

- **用途**：替换输入中与模式匹配的第一个子序列。
- **返回值**：返回替换后的字符串。
- **示例**：
  ```java
  String result = matcher.replaceFirst("replacement");
  ```

### 7. **replaceAll(String replacement)**

- **用途**：替换输入中所有与模式匹配的子序列。
- **返回值**：返回替换后的字符串。
- **示例**：
  ```java
  String result = matcher.replaceAll("replacement");
  ```
  
  ```java
  public static void main(String[] args) {
      String text = "zyf123";
  
      String pattern = "[a-z]*";
  
      Pattern compile = Pattern.compile(pattern);
      Matcher matcher = compile.matcher(text);
  
      String slp = matcher.replaceAll("slp");
      System.out.println(slp);//slpslp1slp2slp3slp
  }
  ```
  
  - `"zyf"` 被替换为 `"slp"` → `"slp"`
  - 在 `"f"` 和 `"1"` 之间的空位置被替换 → `"slpslp"`
  - `"1"` 后的空位置被替换 → `"slpslp1slp"`
  - `"2"` 后的空位置被替换 → `"slpslp1slp2slp"`
  - `"3"` 后的空位置被替换 → `"slpslp1slp2slp3slp"`

### 8. **reset()**

- **用途**：重置 `Matcher` 对象。
- **返回值**：返回 `Matcher` 对象本身。
- **示例**：
  ```java
  matcher.reset();
  ```

### 9. **reset(CharSequence input)**

- **用途**：重置 `Matcher` 并将新的字符序列作为新的输入。
- **参数**：新的输入字符串。
- **返回值**：返回 `Matcher` 对象本身。
- **示例**：
  ```java
  matcher.reset("new input");
  ```

这些方法为在字符串上执行复杂的搜索和替换操作提供了强大的功能，使得 `Matcher` 类成为 Java 正则表达式库中的核心组件。