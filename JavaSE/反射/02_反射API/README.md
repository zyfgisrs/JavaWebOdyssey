# 获取Class类对象

```java
Class<?> clazz = Class.forName("com.zhouyf.demo1.Dog");
```

Dog类

```java
package com.zhouyf.demo1;

public class Dog {
    private String name;


    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public Dog(String name) {
        this.name = name;
    }

    private Dog(int age){
        this.age = age;
    }

    public void show(){
        System.out.println(this.name + " : " + this.age);
    }

}
```

使用forName获取Class对象

```java
public static void main(String[] args) throws Exception {
    Class<?> clazz = Class.forName("com.zhouyf.demo1.Dog");
    System.out.println(clazz);//class com.zhouyf.demo1.Dog
}
```

# 获取构造器对象

## `getConstructor`

`getConstructor()`方法只返回`public`构造器。如果类中没有公开的构造器，这个方法将不会找到任何构造器。

```java
public static void main(String[] args) throws Exception {
    Class<?> clazz = Class.forName("com.zhouyf.demo1.Dog");
    Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
    Object dog = constructor.newInstance("tom", 11);
    System.out.println(dog);//Dog{name='tom', age=11}
}
```

## `getDeclaredConstructor()`

`getDeclaredConstructor()`方法返回类中声明的所有构造器，不论其访问权限（即可以是`public`、`protected`、`private`或包私有）。

```java
public static void main(String[] args) throws Exception {
    Class<?> clazz = Class.forName("com.zhouyf.demo1.Dog");
    Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(int.class);
    declaredConstructor.setAccessible(true);
    Object o = declaredConstructor.newInstance(12);
    System.out.println(o);
}
```

1. **获取私有构造器**：首先，使用`getDeclaredConstructor()`方法获取私有构造器。传入的参数应与构造器参数类型相匹配。
2. **设置访问权限**：调用获取到的构造器对象的`setAccessible(true)`方法，这样可以暂时改变构造器的访问权限，使其可访问。
3. **创建实例**：最后，使用`newInstance()`方法来创建类的实例。

# 方法

方法声明：

```java
public String info(String id, int num){
    return this.name + id + num;
}
```

获取方法的Method对象

在Java中，`Method`类是`java.lang.reflect`包的一部分，用于表示类的单个方法。这个类提供了一种方式，允许程序员通过Java反射机制在运行时检查或者修改类和接口的方法行为。

```java
Method info = clazz.getMethod("info", String.class, int.class);
```

方法相关API

```java
public static void main(String[] args) throws Exception {
    Class<?> clazz = Class.forName("com.zhouyf.demo1.Dog");
    Method info = clazz.getMethod("info", String.class, int.class);
    String name = info.getName();//获取方法的名称
    System.out.println("方法名称：" + name);
    Class<?> returnType = info.getReturnType();//获取方法的返回类型
    System.out.println("方法的返回类型：" + returnType);
    Class<?>[] parameterTypes = info.getParameterTypes();
    for (Class<?> parameterType : parameterTypes) {
        System.out.println("方法参数类型：" + parameterType.getName());
    }
}
```

输出

```
方法名称：info
方法的返回类型：class java.lang.String
方法参数类型：java.lang.String
方法参数类型：int
```

