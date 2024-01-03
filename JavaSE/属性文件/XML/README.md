# XML格式

XML（Extensible Markup Language）是一种标记语言，用于描述数据和文本。XML 遵循以下基本规则和约定：

1. **基本结构**：XML 文档包含一个或多个元素，由开始标签和结束标签包围。例如：`<element>内容</element>`。

2. **根元素**：每个 XML 文档有一个单独的根元素，它包含所有其他元素。

3. **标签命名**：元素名称区分大小写。命名应遵循特定的规范，例如避免使用空格和特殊字符。

4. **属性**：元素可以包含属性，以 `name="value"` 的形式出现在开始标签中。例如：`<element attribute="value">`。

5. **嵌套和层次结构**：元素可以嵌套，形成树状结构。每个元素都必须正确关闭，并保持适当的嵌套顺序。

6. **闭合标签**：每个开始标签必须有一个对应的结束标签。对于没有内容的元素，可以使用自闭合标签，例如 `<element />`。

7. **特殊字符处理**：使用实体引用来表示特殊字符，如 `&lt;` 代表小于号 `<`，`&amp;` 代表和号 `&`。

8. **字符编码**：XML 支持多种字符编码，但 UTF-8 和 UTF-16 是最常用的。文档声明应指明所用的编码，例如 `<?xml version="1.0" encoding="UTF-8"?>`。

9. **注释**：通过 `<!-- 注释内容 -->` 来添加注释，注释不能嵌套。

10. **XML 命名空间**：用于避免元素名称冲突，通过 `xmlns` 属性指定。例如：`<html xmlns="http://www.w3.org/1999/xhtml">`。

11. **文档类型声明（DTD）或 XML Schema**：用于定义 XML 文档的结构。它们不是必需的，但可以提供有效性验证。

12. **格式良好的要求**：文档必须遵守所有 XML 语法规则，才被认为是格式良好的。

这些规则构成了 XML 的基础，确保数据在不同系统和平台之间能够正确地交换和处理。对于在 Java 或相关技术中使用 XML，还可能涉及到对应的解析和生成技术，例如 DOM, SAX, JAXB 等。

# 使用DOM4J生成XML文件

```java
public static void main(String[] args) throws IOException {
    //创建文档
    Document document = DocumentHelper.createDocument();
    //添加根元素
    Element users = document.addElement("users");
    //添加子元素
    Element user1 = users.addElement("user");
    Element user2 = users.addElement("user");
    Element people = users.addElement("people");
    //添加子元素的属性
    user1.addAttribute("id", "1");
    user2.addAttribute("id", "2");
    //添加子元素
    user1.addElement("name").addText("Tom");
    user2.addElement("name").addText("Jack");
    user1.addElement("age").addText("18");
    user2.addElement("age").addText("21");
    user1.addElement("address").addText("南京");
    user2.addElement("address").addText("长春");
    people.addElement("name").addText("james");

    LocalDate birthday = LocalDate.of(1999, 5, 6);
    user1.addElement("birthday").addText(birthday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    user2.addElement("birthday").addText(birthday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    //创建输出流
    XMLWriter writer = new XMLWriter(new FileWriter("src/main/resources/output.xml"));
    //输出xml文件
    writer.write(document);
    writer.close();
}
```

生成xml文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<users>
    <user id="1">
        <name>Tom</name>
        <age>18</age>
        <address>南京</address>
        <birthday>1999/05/06</birthday>
    </user>
    <user id="2">
        <name>Jack</name>
        <age>21</age>
        <address>长春</address>
        <birthday>1999/05/06</birthday>
    </user>
    <people>
        <name>james</name>
    </people>
</users>
```

# 使用DOM4J解析XML文件

`dom4j` 是一个用于 Java 的灵活的 XML 解析库，它提供了简单易用的 API 来读取、修改和写入 XML 文件。以下是 `dom4j` 的一些核心 API 和常见用法概述：

## 创建 `Document` 对象
   - **解析 XML 文件**：使用 `SAXReader` 类来解析 XML 文件并创建 `Document` 对象。
     ```java
     SAXReader reader = new SAXReader();
     Document document = reader.read(new File("example.xml"));
     ```

## 读取元素
   - **获取根元素**：使用 `Document` 对象的 `getRootElement()` 方法。
     ```java
     Element root = document.getRootElement();
     ```
   - **遍历元素**：使用递归或迭代器遍历子元素。
     ```java
     for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
         Element element = it.next();
         // 处理 element
     }
     ```

## 获取和设置元素的值
   - **读取元素内容**：使用 `Element` 的 `getText()` 方法。
     ```java
     String content = element.getText();
     ```
   - **读取属性值**：使用 `element.attributeValue("attrName")` 获取属性值。
     ```java
     String attrValue = element.attributeValue("attributeName");
     ```

## 修改 XML 文档
   - **添加新元素**：使用 `addElement()` 方法。
     ```java
     Element newElement = root.addElement("newElement");
     newElement.setText("value");
     ```
   - **修改元素值**：直接调用 `setText()` 方法。
     ```java
     element.setText("newValue");
     ```
   - **添加或修改属性**：使用 `addAttribute()` 方法。
     ```java
     element.addAttribute("newAttr", "value");
     ```

## 删除元素或属性
   - **删除元素**：使用 `detach()` 方法或从其父元素中移除。
     ```java
     element.detach();
     // 或
     parentElement.remove(element);
     ```
   - **删除属性**：使用 `remove(Attribute attribute)` 方法。
     ```java
     Attribute attribute = element.attribute("attrName");
     element.remove(attribute);
     ```

## **写入 XML 文件**
   - **使用 `XMLWriter` 类**：将 `Document` 对象写回文件。
     ```java
     XMLWriter writer = new XMLWriter(new FileWriter("output.xml"));
     writer.write(document);
     writer.close();
     ```

## **格式化输出**
   - **美化输出**：使用 `OutputFormat` 类来格式化输出。
     ```java
     OutputFormat format = OutputFormat.createPrettyPrint();
     XMLWriter writer = new XMLWriter(new FileWriter("output.xml"), format);
     writer.write(document);
     ```

`dom4j` 提供了这些灵活的 API，使得在 Java 中处理 XML 文件变得相对简单。不过，请注意，在使用 `dom4j` 之前，需要将其依赖添加到项目中，通常可以通过 Maven 或 Gradle 来管理这些依赖。