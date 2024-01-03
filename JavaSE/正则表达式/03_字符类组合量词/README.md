# 字符类与量词组合

1. `[a-z]+`
   - 匹配一个或多个小写字母。
   - 例如，`"apple"` 中的 `"apple"`，`"hello"` 中的 `"hello"`。
2. `[0-9]{3}`
   - 匹配恰好三个数字。
   - 例如，`"123"` 中的 `"123"`，`"abc123xyz"` 中的 `"123"`。
3. `[A-Za-z]*`
   - 匹配零个或多个字母（不区分大小写）。
   - 例如，`"HelloWorld"` 中的 `"HelloWorld"`，`"123"` 中的无匹配。
4. `[0-9a-fA-F]+`
   - 匹配一个或多个十六进制数字。
   - 例如，`"1a3b"` 中的 `"1a3b"`，`"GHI"` 中的无匹配。
5. `[a-zA-Z]{2,4}`
   - 匹配最少2个，最多4个字母。
   - 例如，`"Hello"` 中的 `"Hell"`，`"Hi"` 中的 `"Hi"`。

```java
public static void main(String[] args) {
    String text = "zhouyf123";

    String pattern = "[a-z]{2,4}";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //zhou
        //yf
    }
}
```

```java
public static void main(String[] args) {
    String text = "zhouyf123";

    String pattern = "[0-9]*";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //
        //
        //
        //
        //
        //
        //123
    }
}
```

```java
public static void main(String[] args) {
    String text = "zhouyf123";

    String pattern = "[0-9]+?";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    while (matcher.find()){
        System.out.println(matcher.group());
        //1
        //2
        //3
    }
}
```

