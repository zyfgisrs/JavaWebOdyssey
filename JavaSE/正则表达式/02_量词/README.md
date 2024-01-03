# 标准量词

- `*`：匹配前面的模式**零次或多次**。例如，`ab*`可以匹配`a`, `ab`, `abb`, `abbb`等。
- `+`：匹配前面的模式**一次或多次**。例如，`ab+`可以匹配`ab`, `abb`, `abbb`等，但不匹配单独的`a`。
- `?`：匹配前面的模式**零次或一次**。例如，`ab?`可以匹配`a`或`ab`。

```java
public static void main(String[] args) {
    String text = "aabbbabba";

    String pattern = "ab*";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
         //a
         //abbb
         //abb
         //a
    }
}
```

```java
public static void main(String[] args) {
    String text = "aabbbabba";

    String pattern = "ab+";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //abbb
        //abb
    }
}
```

```java
public static void main(String[] args) {
    String text = "aabbbabba";

    String pattern = "ab?";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //a
        //ab
        //ab
        //a
    }
}
```

# 精确量词

- `{n}`：匹配前面的模式**恰好n次**。例如，`a{3}`仅匹配`aaa`。
- `{n,}`：匹配前面的模式**至少n次**。例如，`a{2,}`可以匹配`aa`, `aaa`, `aaaa`等。
- `{n,m}`：匹配前面的模式**至少n次，但不超过m次**。例如，`a{2,3}`可以匹配`aa`或`aaa`。

```java
public static void main(String[] args) {
    String text = "aabbbabba";

    String pattern = "ab{2}";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //ab
        //ab
    }
}
```

```java
package com.zhouyf.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO1 {
    public static void main(String[] args) {
        String text = "aabbbbabbba";

        String pattern = "ab{2,}";

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);

        while (matcher.find()){
            System.out.println(matcher.group());
            //abbbb
            //abbb
        }
    }
}
```



# 贪婪量词与非贪婪量词

- **贪婪模式**：量词默认是贪婪的，意味着它们会尽可能多地匹配字符。例如，`a+`在字符串`"aaa"`中会匹配所有三个`a`。
- **非贪婪模式**：通过在量词后面加上`?`，可以使其成为非贪婪模式，即尽可能少地匹配字符。例如，`a+?`在字符串`"aaa"`中仅匹配单个`a`。

```java
public static void main(String[] args) {
    String text = "aabbbabba";

    String pattern = "ab*?";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //a
        //a
        //a
        //a
    }
}
```

```java
public static void main(String[] args) {
    String text = "aabbbabba";

    String pattern = "ab+?";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //ab
        //ab
    }
}
```

```java
public static void main(String[] args) {
    String text = "aabbbabba";

    String pattern = "ab??";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //ab
        //ab
    }
}
```

