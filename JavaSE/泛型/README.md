# 泛型的定义和使用

## 泛型定义

泛型可以用于定义类、接口和方法，通过在类名或方法名后面加上尖括号和类型参数来声明泛型。泛型的好处是可以避免类型转换和类型错误，提高代码的可读性和复用性。

## 泛型类和泛型接口

在类名后面加上尖括号和一个或多个类型参数，例如`public class Box<T>`表示一个泛型类，`T`是一个类型参数，可以在创建对象时指定具体的类型。类型参数可以是任何大写字母，但通常使用`T`表示类型，`E`表示元素，`K`表示键，`V`表示值等。

## 限制泛型类

有时候，你可能想要限制泛型类可以使用的类型参数的范围，例如，你只想让泛型类接受数字类型的参数。这时候，你可以使用`extends`关键字来指定类型参数的上界，例如`public class Box<T extends Number>`表示一个泛型类，它的类型参数`T`必须是`Number`或其子类，如`Integer`，`Double`等。这样，你就可以在泛型类中使用`Number`类的方法，如`doubleValue()`等。

```java
package com.zhouyf;


class Box<T extends Number>{
    private T num;

    public Box(T num) {
        this.num = num;
    }

    public void func(){
        long l = num.longValue();
        System.out.println(l);
    }
}

public class Dem02 {
    public static void main(String[] args) {
        Box<Double> integerBox = new Box<>(1.1);
        integerBox.func();
    }
}
```

## 泛型方法

### 泛型方法的定义

泛型方法的定义是在方法返回类型之前，用尖括号和一个或多个类型参数来声明。泛型方法类型参数部分（`<T>`）位于方法的返回类型之前。定义如下：

```java
public <T> T someMethod(T data) {
    // ...
}
```

### 限制泛型类

使用泛型的限定，即在泛型的类型参数上加上`extends`，来限制类型参数的范围。例如，`public static <T extends Comparable<T>> T maximum(T x, T y, T z)`表示一个泛型方法，它的类型参数`T`必须是`Comparable<T>`的子类，这样就可以在方法中调用`T`的`compareTo`方法。

在泛型方法中不能使用下界。第一，因为使用下界会产生类型安全的问题，如果在这个泛型方法中，你尝试调用`Integer`特有的方法（假设是方法A），而`T`实际是`Object`类型，这将导致编译错误，因为`Object`类型不包含方法A。第二，在面向对象编程中，子类可以替换父类（里氏替换原则）。但在这个情况下，父类（如`Object`）无法替换子类（如`Integer`），因为父类没有子类特有的方法和属性。

```java
public static <T super Integer> void show(T value){
    // 假设调用了Integer特有的方法
    value.someIntegerMethod(); // 这将导致编译错误，如果T是Object
}
```

## 通配符

虽然不能在泛型方法的类型参数中使用下界，但可以在泛型的使用中通过通配符（`? super Type`）指定下界。例如，`List<? super Integer>`可以接受`Integer`的任何超类的列表。这种做法主要用于API设计中，以便在某些特定情况下提供更大的灵活性，如在集合的写入操作中。
范例：

```java
package com.zhouyf;

import java.util.List;

public class Demo3 {
    public static void main(String[] args) {

    }


    public void func1(List<? super Children> list){
        for (Object o : list) { //只能使用Object接收
            System.out.println(o);
        }

        list.add(new Children());
        list.add(new Grandson());//可以添加Children或者Childre的子类
    }


    public void func2(List<? extends Children> list){
        for (Parent o : list) {
            System.out.println(o);
        }

        for (Children o : list) {
            System.out.println(o);
        }

//        list.add(new Parent());//错误
//        list.add(new Children());//错误
//        list.add(new Grandson());//错误
//        list.add(new Object());//错误
    }
}

class Parent{

}

class Children extends Parent{

}

class Grandson extends Children{}
```

从上面的例子中可以得出结论：通配符上限主要用于从集合中读取数据，通配符下限主要用于向集合中添加数据。（上为父类下位子类），读取数据需要知道超类的类型，添加数据需要知道确切的子类类型。

这个问题涉及到Java泛型中的"PECS"原则，即"Producer Extends, Consumer Super"。这个原则解释了为什么通配符上限（Upper Bounded Wildcards）主要用于从集合中读取数据，而通配符下限（Lower Bounded Wildcards）主要用于向集合中添加数据。

1. **通配符上限（? extends T） - "Producer Extends"**:
   - 当你需要从一个数据结构中读取数据时，你会希望这个数据结构可以是T或T的任何子类，因为这些数据都可以被视为是T类型。这样的数据结构可以"生产" T类型的数据。
   - 例如，如果你有一个`List<? extends Number>`，这意味着这个列表可以是`List<Number>`、`List<Integer>`、`List<Double>`等。你可以安全地从这个列表中读取数据，因为无论实际的列表类型是什么，你得到的都是`Number`类型的对象。

2. **通配符下限（? super T） - "Consumer Super"**:
   - 当你需要向一个数据结构中写入数据时，你会希望这个数据结构可以接受T或T的任何父类，因为T类型的对象可以安全地赋值给T或其任何父类的引用。这样的数据结构可以"消费" T类型的数据。
   - 例如，如果你有一个`List<? super Integer>`，这意味着这个列表可以是`List<Integer>`、`List<Number>`、`List<Object>`等。你可以向这个列表中添加`Integer`或其子类的对象，因为这些对象可以安全地赋值给`Integer`或其任何父类类型的引用。

这种设计主要是为了确保类型安全。使用通配符上限时，Java无法确定列表具体的子类型，所以不能安全地向其中添加对象（除了null）。使用通配符下限时，Java无法确定列表具体的超类型，所以不可以安全地假设从中读取的对象是特定的子类型。

简而言之，PECS原则是为了在使用泛型时保证最大的灵活性同时避免类型安全问题。这在处理集合操作时尤为重要，如Java集合框架中的算法设计。

## 泛型的不变性

尝试将`List<String>`类型的对象赋给List<Object>类型的参数，将会导致一个编译错误。

```java
package com.zhouyf;

import java.util.ArrayList;
import java.util.List;


public class Demo4 {
    public void show(List<Object> list){

    }

    public static void main(String[] args) {
        Demo4 demo4 = new Demo4();
        List<String> list = new ArrayList<>();
        demo4.show(list);//编译错误
    }
}

```

泛型的不变性（Invariance）是指在Java泛型系统中，给定的泛型类型与其任何子类型之间不存在子类型关系。具体来说，对于两个具体的泛型类型，比如`List<A>`和`List<B>`，即使类`A`是类`B`的子类型或父类型，在泛型的上下文中，`List<A>`和`List<B>`被认为是完全不相关的类型。

这个概念是理解Java泛型系统的关键，因为它与我们通常理解的类型继承规则有所不同。在Java中，泛型是不变的。这意味着，即使`A`是`B`的子类型，`GenericType<A>`并不是`GenericType<B>`的子类型。这适用于所有泛型类型，包括集合类、自定义泛型类等。

泛型的不变性是为了保证类型安全。考虑以下情况：

```java
List<String> strings = new ArrayList<>();
List<Object> objects = strings; // 假设这是合法的
objects.add(1); // 这里我们向字符串列表中添加了一个整数
String s = strings.get(0); // 这里我们期望得到一个字符串，但实际上是一个整数
```

如果泛型不是不变的，上面的代码将会导致运行时错误，因为我们最终会试图将一个整数转换为字符串。通过使泛型不变，Java阻止了这种类型错误的发生。

## 泛型擦除

