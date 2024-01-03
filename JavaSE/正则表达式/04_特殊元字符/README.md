# 特殊元字符

## \b

`\b` 的作用：

1. **标识单词的开始或结束**：`\b` 用于确保模式位于单词的边界。它不匹配任何实际的字符，而是匹配一个位置。
2. **不消耗任何字符**：它仅仅匹配一个位置，而不是字符序列。
3. **增强匹配精确性**：使用`\b`可以帮助确保模式匹配独立的单词，而不是单词中的一部分。

```java
public static void main(String[] args) {
    String text = "zhou-yf";

    String pattern = "\\bzhou\\b";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //zhou
    }
}
```

```java
public static void main(String[] args) {
    String text = "zhou1-yf";

    String pattern = "\\bzhou\\b";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //没有匹配到
    }
}
```

## 脱字符^

在正则表达式中，`^` 符号有两种主要用法：

1. **行的开头**：当 `^` 出现在正则表达式的开始位置时，它表示匹配必须从目标字符串的开头开始。例如，正则表达式 `^abc` 将匹配以 "abc" 开头的字符串。
2. **否定字符集**：当 `^` 出现在一个字符集（`[]`）的开始位置时，它表示一个否定的字符集。这意味着你想匹配除了括号内指定字符之外的任何字符。例如，正则表达式 `[^abc]` 将匹配任何不是 'a'、'b' 或 'c' 的单个字符。

匹配以Java开头的字符串

```java
public static void main(String[] args) {
    String text1 = "Java Programming";
    String text2 = "I love Java";
    String pattern = "^Java";


    Pattern compile = Pattern.compile(pattern);
    System.out.println(compile.matcher(text1).find());//true
    System.out.println(compile.matcher(text2).find());//false
}
```

匹配存在非数字字符的字符串

```java
public static void main(String[] args) {
    String text1 = "Java Programming";
    String text2 = "111";
    String pattern = "[^0-9]+";

    Pattern compile = Pattern.compile(pattern);
    System.out.println(compile.matcher(text1).find());//true
    System.out.println(compile.matcher(text2).find());//false
}
```

匹配以非数字开头的字符串

```java
public static void main(String[] args) {
    String text1 = "Java";
    String text2 = "1Java";
    String pattern = "^[^0-9]";

    Pattern compile = Pattern.compile(pattern);
    System.out.println(compile.matcher(text1).find());//true
    System.out.println(compile.matcher(text2).find());//false
}
```

## 美元符号$

在正则表达式中，`$` 符号是一个特殊字符，它主要用于表示字符串的结束。当你在正则表达式中使用 `$`，它确保匹配必须发生在输入字符串的末尾。这对于确保整个字符串或其特定部分符合特定格式非常有用。

匹配以end结尾的字符串

```java
public static void main(String[] args) {
    String text1 = "The end";
    String text2 = "endless";
    String pattern = "end$";


    Pattern compile = Pattern.compile(pattern);
    System.out.println(compile.matcher(text1).find());//true
    System.out.println(compile.matcher(text2).find());//false
}
```

匹配只含数字的字符串

```java
public static void main(String[] args) {
    String text1 = "87234987";
    String text2 = "8a7234987";
    String pattern = "^[0-9]+$";


    Pattern compile = Pattern.compile(pattern);
    System.out.println(compile.matcher(text1).find());//true
    System.out.println(compile.matcher(text2).find());//false
}
```

匹配首字母大写但是其余小写的字符串

```java
public static void main(String[] args) {
    String text1 = "Hello";
    String text2 = "HelloY";
    String pattern = "^[A-Z][a-z]+$";


    Pattern compile = Pattern.compile(pattern);
    System.out.println(compile.matcher(text1).find());//true
    System.out.println(compile.matcher(text2).find());//false
}
```

## 管道符|

在正则表达式中，管道符 `|` 是一个非常重要的符号，它用于表示“或”（OR）操作。这意味着你可以用它来指定两个或更多可选的模式中的任何一个。

多个单词匹配：

```java
public static void main(String[] args) {
    String text = "pig is black";

    String pattern = "cat|dog|pig";


    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);
    System.out.println(matcher.find());//true
}
```

结合量词使用，匹配dogs、dog、cat和cats

```java
public static void main(String[] args) {
    String text = "dog";

    String pattern = "(cat|dog)s?";


    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);
    System.out.println(matcher.find());//true
}
```

