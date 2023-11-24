# `PropertySource`抽象类

> `PropertySource` 是Spring框架中一个非常重要的抽象类，用于表示属性源，也就是配置数据的源头。Spring框架使用`PropertySource`来管理和访问应用程序中用到的各种配置信息，例如来自于属性文件、环境变量、系统属性或者其他数据源的配置数据。

- **属性源抽象**：`PropertySource`是一个抽象基类，代表了一个属性源。在Spring中，它被用来统一抽象和访问不同来源的配置数据。
- **`PropertySource`的实现**：`PropertySource`是抽象的，所以你可以实现自己的属性源。Spring提供了几种实现，比如`MapPropertySource`、`PropertiesPropertySource`等，分别对应不同类型的源。
- `PropertySource`类提供了几个核心的方法，比如`getName()`用于获取属性源的名称，`getSource()`用于访问底层属性源对象，`getProperty(String name)`用于根据属性名获取属性值。

## `MapPropertySource`

范例：使用`MapPropertySource`资源管理

```java
HashMap<String, Object> map = new HashMap<>();
map.put("name", "zhouyf");
map.put("age", 12);
MapPropertySource source = new MapPropertySource("url", map);
Object age = source.getProperty("age");
LOGGER.info("age = {}", age);
```

## PropertiesPropertySource

范例：使用`PropertiesPropertySource`资源管理

```java
FileInputStream inputStream = null;
try {
    inputStream = new FileInputStream("src/test/resources/source.properties");
} catch (FileNotFoundException e) {
    throw new RuntimeException(e);
}
Properties properties = new Properties();
properties.load(inputStream);
PropertiesPropertySource source = new PropertiesPropertySource("source", properties);
Object name = source.getProperty("name");
System.out.println(name);
```

- 通过`PropertiesPropertySource`的使用，Spring提供了对属性源的抽象，方便地与Spring的其他部分（例如环境抽象`Environment`）集成。
- 通过`getProperty`方法，Spring提供了一个统一的方式来访问属性，无论属性的来源是什么。

# PropertyResolver属性解析

> `PropertyResolver` 是一个在 Spring Framework 中定义的接口，它提供了解析属性和替换相关属性值的功能。这个接口定义了一些基本的方法，允许开发者查询属性是否存在，获取属性的值，以及替换包含占位符的文本中的属性值。

`PropertyResolver` 接口的一些关键方法包括：

- `boolean containsProperty(String key)`: 用于检查是否存在某个属性。
- `String getProperty(String key)`: 根据给定的键来获取属性值。
- `String getProperty(String key, String defaultValue)`: 根据给定的键获取属性值，如果属性不存在，则返回默认值。
- `<T> T getProperty(String key, Class<T> targetType)`: 根据给定的键获取属性值，并将其转换为指定的目标类型。
- `<T> T getProperty(String key, Class<T> targetType, T defaultValue)`: 根据给定的键获取属性值，将其转换为指定的目标类型，如果属性不存在，则返回默认值。
- `String getRequiredProperty(String key) throws IllegalStateException`: 根据给定的键获取属性值，如果属性不存在，则抛出异常。
- `String resolvePlaceholders(String text)`: 解析给定文本中的所有占位符，替换为相应的属性值。
- `String resolveRequiredPlaceholders(String text) throws IllegalArgumentException`: 解析给定文本中的所有占位符，并替换为相应的属性值；如果无法解析某个占位符，则抛出异常。

## `PropertySourcesPropertyResolver`

`PropertySourcesPropertyResolver`的抽象父类`AbstractPropertyResolver`中

```java
private String placeholderPrefix = SystemPropertyUtils.PLACEHOLDER_PREFIX;

private String placeholderSuffix = SystemPropertyUtils.PLACEHOLDER_SUFFIX;
```

`SystemPropertyUtils`中：

```java
/** Prefix for system property placeholders: "${". */
public static final String PLACEHOLDER_PREFIX = "${";

/** Suffix for system property placeholders: "}". */
public static final String PLACEHOLDER_SUFFIX = "}";

/** Value separator for system property placeholders: ":". */
public static final String VALUE_SEPARATOR = ":";
```

可以看到属性占位符（Property Placeholders）的为`${}`

`PropertySourcesPropertyResolver`类中判断是否存在某个属性，采用的是遍历的方式

```java
public boolean containsProperty(String key) {
    if (this.propertySources != null) {
        for (PropertySource<?> propertySource : this.propertySources) {
            if (propertySource.containsProperty(key)) {
                return true;
            }
        }
    }
    return false;
}
```

范例：实现属性的解析

```java
public static void main(String[] args) throws IOException {
    FileInputStream inputStream = null;
    try {
        inputStream = new FileInputStream("src/test/resources/source.properties");
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
    Properties properties = new Properties();
    properties.load(inputStream);
    PropertiesPropertySource source1 = new PropertiesPropertySource("source", properties);
    Map<String, Object> map = Map.of("id", "123", "address", "changchun");
    MapPropertySource source2 = new MapPropertySource("map", map);
    MutablePropertySources source = new MutablePropertySources();
    source.addLast(source1);
    source.addLast(source2);
    PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(source);
    String id = resolver.getProperty("id");
    String name = resolver.getProperty("name");
    System.out.println(id);
    System.out.println(name);
}
```

范例：资源表达式的解析处理：

```java
public static void main(String[] args) throws IOException {
    FileInputStream inputStream = null;
    try {
        inputStream = new FileInputStream("src/test/resources/source.properties");
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
    Properties properties = new Properties();
    properties.load(inputStream);
    PropertiesPropertySource source1 = new PropertiesPropertySource("source", properties);
    Map<String, Object> map = Map.of("id", "123", "address", "changchun");
    MapPropertySource source2 = new MapPropertySource("map", map);
    MutablePropertySources source = new MutablePropertySources();
    source.addLast(source1);
    source.addLast(source2);
    PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(source);
    String name = resolver.resolvePlaceholders("${name}"); //存在
    String name1 = resolver.resolvePlaceholders("${name1}");//不存在
    System.out.println(name);
    System.out.println(name1);
    String name2 = resolver.resolveRequiredPlaceholders("${name}"); //存在
//        String name3 = resolver.resolveRequiredPlaceholders("${name1}");
//         不存在的话将会报错
    System.out.println(name2);
}
```