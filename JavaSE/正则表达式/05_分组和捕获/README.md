# 分组

1. **基本概念**：分组是通过将部分正则表达式括在小括号 `()` 中来创建的。这些小括号内的表达式作为一个整体来处理，可以被整体应用数量词，也可以被引用或后续操作。
2. **用途**：分组的主要作用是控制正则表达式的优先级，定义子表达式，以及通过捕获子表达式的匹配来供后续使用。

# 捕获

1. **基本概念**：捕获是正则表达式的一种机制，它会记住每一个匹配的分组。每个分组会自动拥有一个编号，通常从左到右，以括号的顺序来分配。
2. **捕获组**：在Java中，捕获组可以通过从 `1` 开始的索引来访问。第0个捕获组代表**整个表达式**的匹配。
3. **应用**：这种机制可以在后续的正则操作中被引用（例如，通过反向引用），或者在正则表达式外部使用，如在Java的 `Matcher` 类中通过 `group(int group)` 方法获取特定分组的匹配部分。

```java
public static void main(String[] args) {
    String text = "John Doe, 30  James Harden, 32";

    String pattern = "(\\w+ \\w+), (\\d+)";


    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);


    while(matcher.find()){
        String name = matcher.group(1);
        String age = matcher.group(2);

        String info = age + ": " + name;
        System.out.println(info);
    }
}
```

```java
public static void main(String[] args) {
    String text = "John Doe, 30  James Harden, 32";

    String pattern = "(\\w+ \\w+), (\\d+)";


    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);

    String s = matcher.replaceAll("$2: $1");
    System.out.println(s);//30: John Doe  32: James Harden
}
```

# 反向引用

- 反向引用用于引用正则表达式中之前的一个分组（Group）匹配到的文本。这允许你在正则表达式的后续部分重复使用之前已经匹配的文本。
- 在正则表达式中，分组通常用圆括号 `()` 定义。例如，在表达式 `(abc)\1` 中，`\1` 表示第一个分组 `(abc)` 匹配到的文本。如果这个分组匹配到了文本 "abc"，那么 `\1` 就代表 "abc"。

```java
public static void main(String[] args) {
    String text = "123-ABC-123-ABC";

    String pattern = "([0-9]{3})-([A-Z]{3})-\\1-\\2";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);
    System.out.println(matcher.matches());//true
}
```

```java
public static void main(String[] args) {
    String text = "123-ABC-124-ABC";

    String pattern = "([0-9]{3})-([A-Z]{3})-\\1-\\2";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);
    System.out.println(matcher.matches());//false
}
```

# 非捕获分组

在正则表达式中，非捕获分组（Non-capturing groups）是一种特殊的分组方法，它允许你对一部分正则表达式进行分组，而不保存该分组用于后续的引用或操作。非捕获分组只是用来定义优先级和应用量词，但不会像普通捕获分组那样将其匹配的子串保存起来。

- 在正则表达式中，普通的捕获分组使用圆括号 `()` 来定义。例如，在表达式 `(abc)` 中，`abc` 被捕获并存储，可以通过反向引用或其他方式在后续操作中使用。

- 相比之下，非捕获分组使用 `(?:...)` 的形式。例如，`(?:abc)` 会匹配 `abc`，但匹配结果不会被存储。这对于优化正则表达式的性能很有帮助，特别是在不需要反向引用分组内容时。

```java
public static void main(String[] args) {
    String text = "123-ABC-abc";


    String pattern = "(?:[0-9]{3})-([A-Z]{3})-([a-z]{3})";

    Pattern compile = Pattern.compile(pattern);
    Matcher matcher = compile.matcher(text);
    while(matcher.find()){
        String group = matcher.group(0);
        String group1 = matcher.group(1);
        String group2 = matcher.group(2);
        System.out.println(group);//123-ABC-abc
        System.out.println(group1);//ABC
        System.out.println(group2);//abc
    }
}
```

- `(?:[0-9]{3})`：这是一个非捕获分组，用于匹配恰好三个数字。`[0-9]{3}` 匹配任何三个连续的数字，而 `?:` 前缀表示这是一个非捕获分组，即匹配的数字不会被存储以供后续引用。
- `([A-Z]{3})`：这是一个捕获分组，用于匹配三个连续的大写字母。这个分组中匹配的内容会被存储，可以通过 `group(1)` 访问。
- `([a-z]{3})`：这又是一个捕获分组，用于匹配三个连续的小写字母。这个分组中匹配的内容同样会被存储，可以通过 `group(2)` 访问。