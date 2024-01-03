# 标准字符类

- `[abc]`: 匹配任何一个字符`a`、`b`或`c`。
- `[^abc]`: 匹配任何不是`a`、`b`或`c`的字符。
- `[a-z]`: 匹配任何一个小写字母。
- `[A-Z]`: 匹配任何一个大写字母。
- `[a-zA-Z]`: 匹配任何一个字母，不论大小写。
- `[0-9]`: 匹配任何一个数字。
- `[a-zA-Z0-9]`: 匹配任何一个字母或数字。

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ _!@#";

    String pattern = "[abc]";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //a
		//b
		//c
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ _!@#";

    String pattern = "[^abc]";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        // 123 XYZ _!@#
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ _!@#";

    String pattern = "[a-z]";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //abc
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ _!@#";

    String pattern = "[A-Z]";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //XYZ
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ _!@#";

    String pattern = "[a-zA-Z]";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //abcXYZ
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ _!@#";

    String pattern = "[0-9]";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //123
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ _!@#";

    String pattern = "[[a-zA-Z0-9]]";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //abc123XYZ
    }
}
```

# 预定义字符类

- `.`: 匹配任何单个字符（通常不包括换行符）。
- `\d`: 匹配任何数字，等同于`[0-9]`。
- `\D`: 匹配任何非数字字符，等同于`[^0-9]`。
- `\s`: 匹配任何空白字符（包括空格、制表符、换行等）。
- `\S`: 匹配任何非空白字符。
- `\w`: 匹配任何单词字符（包括字母、数字和下划线），等同于`[a-zA-Z0-9_]`。
- `\W`: 匹配任何非单词字符，等同于`[^a-zA-Z0-9_]`。

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ apple_!@#";

    String pattern = ".";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //abc 123 XYZ apple_!@#
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ apple_!@#";

    String pattern = "\\d";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //123
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ apple_!@#";

    String pattern = "\\D";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //abc  XYZ apple_!@#
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ apple_!@#";

    String pattern = "\\s";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //abc123XYZapple_!@#
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ apple_!@#";

    String pattern = "\\S";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //abc123XYZapple_
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ apple_!@#";

    String pattern = "\\w";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //abc123XYZapple_
    }
}
```

```java
public static void main(String[] args) {
    String text = "abc 123 XYZ apple_!@#";

    String pattern = "\\W";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.print(matcher.group());
        //   !@#
    }
}
```

# 组合字符类

字符类可以组合使用，以满足更复杂的匹配需求。例如：

- `[A-Za-z0-9_]`: 匹配任何字母、数字或下划线。
- `[0-9a-fA-F]`: 匹配任何十六进制数字。
- `[^0-9]`: 匹配任何非数字字符。