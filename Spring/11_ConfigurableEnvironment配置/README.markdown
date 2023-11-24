# 读取环境属性源

`StandardEnvironment` 是 `ConfigurableEnvironment` 接口的一个实现，它提供了对属性源的访问和操作。属性源可能包括环境变量、JVM 系统属性、配置文件等。

![image-20231106144659607](assets/image-20231106144659607.png)

```java
public static void main(String[] args) {
    StandardEnvironment standardEnvironment = new StandardEnvironment();
    MutablePropertySources sources = standardEnvironment.getPropertySources();
    for (PropertySource source : sources) {
        System.out.println(source.getName() + " : " + source.getSource());
    }
}
```

![image-20231106150139017](assets/image-20231106150139017.png)

读取到`systemProperties`和`systemEnvironment`两个属性源，属性源中显示了当前环境的各种参数。

- 解析环境属性源

```java
public static void main(String[] args) {
    PropertyResolver resolver = new StandardEnvironment();
    String version = resolver.resolvePlaceholders("${java.specification.version}");
    System.out.println("java.specification.version = "  + version);
}
```

输出：

```
java.specification.version = 17
```

- 读取配置的JVM环境参数

<img src="assets/image-20231106151057961.png" alt="image-20231106151057961" style="zoom:67%;" />

![image-20231106151029129](assets/image-20231106151029129.png)

```java
public static void main(String[] args) {
    PropertyResolver resolver = new StandardEnvironment();
    String version = resolver.resolvePlaceholders("${java.specification.version}");
    String name = resolver.resolvePlaceholders("${name}");
    String age = resolver.resolvePlaceholders("${age}"); //解析环境中的参数
    System.out.println("java.specification.version = "  + version);
    System.out.println("name = "  + name);
    System.out.println("age = "  + age);
}
```

name参数与age参数都是通过自己的需要动态传递的。

输出：

```java
java.specification.version = 17
name = zyf
age = 13
```

