# 零宽断言

正则表达式中的零宽断言（Zero-width assertions）是一种高级功能，用于匹配位于特定位置的模式，但它们不会消耗任何字符。这意味着匹配操作完成后，搜索的当前位置不会向前移动。零宽断言主要包括以下几种类型：

1. **零宽正预测先行断言**（Positive Lookahead）: `(?=pattern)`
   - 它断言所匹配的模式必须紧跟在某个给定的模式之后。但不会作为匹配结果的一部分返回。
   - 例如，`X(?=Y)` 匹配在 'Y' 后面的 'X'。

2. **零宽负预测先行断言**（Negative Lookahead）: `(?!pattern)`
   - 它断言某个给定的模式不会出现在所匹配的模式之后。
   - 例如，`X(?!Y)` 匹配不在 'Y' 后面的 'X'。

3. **零宽正回顾后发断言**（Positive Lookbehind）: `(?<=pattern)`
   - 它断言所匹配的模式必须紧跟在某个给定的模式之前。它与正预测先行断言相似，但方向相反。
   - 例如，`(?<=Y)X` 匹配在 'Y' 前面的 'X'。

4. **零宽负回顾后发断言**（Negative Lookbehind）: `(?<!pattern)`
   - 它断言某个给定的模式不会出现在所匹配的模式之前。
   - 例如，`(?<!Y)X` 匹配不在 'Y' 前面的 'X'。

# 范例

- 正预测先行断言: `(?=pattern)`
  - 例子: `/\d(?=\D)/` 匹配后面不是数字的数字。

- 负预测先行断言: `(?!pattern)`
  - 例子: `/\d(?!9)/` 匹配后面不是 '9' 的数字。

- 正回顾后发断言: `(?<=pattern)`
  - 例子: `/(?<=\D)\d/` 匹配前面不是数字的数字。

- 负回顾后发断言: `(?<!pattern)`
  - 例子: `/(?<!\d)\d/` 匹配前面不是数字的数字。

```java
public static void main(String[] args) {
    String text = "1a 1a 11";


    String pattern = "\\d(?=\\D)";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);
    while(matcher.find()){
        System.out.println(matcher.group());
        //1
        //1
    }
}
```

```java
public static void main(String[] args) {
    String text = "1b 2a";


    String pattern = "\\d(?!a)";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);
    while(matcher.find()){
        System.out.println(matcher.group());
        //1
    }
}
```

```java
public static void main(String[] args) {
    String text = "a1 b2";


    String pattern = "(?<=a)\\d";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);
    while(matcher.find()){
        System.out.println(matcher.group());
        //1
    }
}
```

```java
public static void main(String[] args) {
    String text = "a1 b2";


    String pattern = "(?<!a)\\d";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);
    while(matcher.find()){
        System.out.println(matcher.group());
        //2
    }
}
```