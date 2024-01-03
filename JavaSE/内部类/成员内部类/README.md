# 成员内部类

成员内部类为定义在类内部的非静态类

## 成员内部类与外部类的关系

### 访问权限

- 成员内部类可以无障碍地访问外部类的所有成员（包括私有成员）。这意味着内部类能够直接读取和修改外部类的字段，调用其方法等。
- 相反，外部类要访问内部类的成员，需要通过内部类的实例。

### 实例化依赖

- 成员内部类的实例化通常依赖于外部类的实例。你不能在没有外部类实例的情况下创建成员内部类的对象（除非内部类被声明为static）。
- 这种依赖关系意味着内部类对象总是与一个外部类对象相关联。

### **内部类持有对外部类实例的引用**

- 每个成员内部类的实例都自动持有对其外部类实例的引用。在内部类中，可以使用`OuterClass.this`来引用外部类的实例。
- 这种隐式引用是成员内部类与外部类关联的关键所在。

## 范例

```java
public class OuterClass {
    private String name = "外部类属性";


    private class InnerClass{
        private String name = "内部类属性";

        private void show(){
            System.out.println(OuterClass.this.name);
            System.out.println(this.name);
        }
    }

    public void invokeInnerMethod(){
        InnerClass innerClass = new InnerClass();//成员内部类的实例化
        innerClass.show();
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        outerClass.invokeInnerMethod();
    }
}
```

```
外部类属性
内部类属性
```

成员内部类不能脱离外部类的实例单独存在：

```java
public class AnotherClass {
    public void invokeMethod(){
        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass = new OuterClass.InnerClass();//编译错误
    }
}
```

以下行为是正确，基于外部类的实例去实例化一个对象：

```java
public class AnotherClass {
    public void invokeMethod(){
        OuterClass outerClass = new OuterClass();

        OuterClass.InnerClass innerClass = outerClass.new InnerClass();
    }
}
```

而当内部类的访问权限为私有的private时，其他类不能通过外部类访问内部类的实例：

```java
private class InnerClass{
    private String name = "内部类属性";

    private void show(){
        System.out.println(OuterClass.this.name);
        System.out.println(this.name);
    }
}
```

```java
public class AnotherClass {
    public void invokeMethod(){
        OuterClass outerClass = new OuterClass();

        OuterClass.InnerClass innerClass = outerClass.new InnerClass();//编译错误
    }
}
```

