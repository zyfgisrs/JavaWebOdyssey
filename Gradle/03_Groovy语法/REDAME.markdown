---
typora-copy-images-to: ./
---

# 配置Groovy环境

- 下载Groovy SDK安装包https://groovy.apache.org/download.html
- 将bin目录添加到本地环境变量中
- IDEA中创建Groovy项目

<img src="F:\GITHUB\JavaWebOdyssey\Gradle\03_Groovy语法\image-20231006194101795.png" alt="image-20231006194101795" style="zoom:67%;" />

# Hello，world程序

- 在包`com.zhouyf.groovy`新建一个HelloWorld类。

- 编写代码：

  ```groovy
  class HelloWorld {
      static void main(String[] args){
          println("groovy hello, world!")
      }
  }
  ```

  点击运行，控制台成功打印输出`groovy hello, world!`

# 语法

## 定义变量

在Groovy中，`def` 关键字被用于定义变量，尤其是当你不希望或不需要指定明确的类型时。以下是使用 `def` 进行变量定义的一些要点：

1. **动态类型**: 使用 `def` 定义的变量是动态类型的，这意味着它们可以引用任何类型的对象，并且随着时间的推移可以改变其类型。

   ```groovy
   def x = "Hello"
   x = 123 // 合法，因为 x 是动态类型的
   ```

2. **隐式类型**: 尽管变量是动态类型的，但Groovy在运行时知道该变量当前的实际类型，并且可以执行相应的操作。

   ```groovy
   def y = "Hello"
   println(y.toUpperCase()) // 输出 "HELLO"
   ```

3. **局部变量**: 在方法内部使用 `def` 可以定义局部变量。

4. **全局变量**: 在脚本顶级或类级别使用 `def` 可以定义全局变量或类成员。

5. **不可变性**: 使用 `def` 定义的变量本身是可变的，但如果你希望它是不可变的，你可以使用 `final` 关键字。

   ```groovy
   final def z = "Cannot change me"
   z = "Try to change" // 会引发错误
   ```

6. **默认初始化**: 使用 `def` 定义的变量在没有赋初始值时默认为 `null`。

   ```groovy
   def a
   assert a == null
   ```

7. **类型安全**: 虽然 `def` 允许动态类型，但你也可以结合使用静态类型检查或编译时类型检查来提供更多的类型安全性。

8. **泛型支持**: 使用 `def` 定义的变量也可以利用泛型。

   ```groovy
   def list = new ArrayList<String>()
   list.add("Groovy")
   ```

9. **可读性**: 使用 `def` 可以提高代码的可读性，因为它减少了代码中的类型冗余，尤其是在使用明确类型不会增加额外价值的地方。

## 数据类型

Groovy是一个动态语言，但它运行在Java虚拟机(JVM)上，所以它的数据类型基本上和Java的数据类型相同。但是，Groovy提供了更多的简便和强化功能。以下是Groovy中常见的数据类型：

1. **基本数据类型** (和Java的原始数据类型对应):
   - `byte`
   - `short`
   - `int`
   - `long`
   - `float`
   - `double`
   - `char`
   - `boolean`

2. **封装的数据类型**:
   - `Byte`, `Short`, `Integer`, `Long`, `Float`, `Double`, `Character`, `Boolean` (与Java的封装类型相同)
   
   Groovy自动进行原始数据类型和封装类型之间的转换，这被称为自动装箱和拆箱。

3. **字符串**:
   - `String`: 基于Java的String类型，但Groovy增加了许多有用的方法。
   - `GString`: Groovy的插值字符串，允许在字符串中直接嵌入表达式。

4. **集合类型**:
   - `List`: 和Java中的List相同，但在Groovy中，你可以更简洁地创建列表，例如`[1, 2, 3]`.
   - `Map`: 和Java中的Map相同，但Groovy提供了一个更简洁的语法，例如`['name': 'John', 'age': 30]`.
   - `Set`: 和Java中的Set相同。

5. **范围**:
   - `Range`: 一个表示值范围的类型，例如`1..5`表示从1到5的整数范围。

6. **其他Java类**:
   由于Groovy运行在JVM上，你可以使用所有Java类库中的类型，并可以轻松地将Java代码集成到Groovy中，反之亦然。

7. **动态类型**:
   - `def`: 使用`def`关键字可以定义一个动态类型的变量，意味着在编码时不知道其具体类型，但在运行时会确定。

8. **闭包**:
   - `Closure`: 这是Groovy的一个核心特性，允许你定义匿名的代码块并将它们作为一等公民进行传递。

9. **其他Groovy类型**:
   - `BigInteger`和`BigDecimal`被Groovy内置支持，允许进行高精度的数学运算。

## 字符串

在Groovy中，字符串是非常核心的数据类型，它为字符串提供了多种表示方式，以满足不同的需求。以下是Groovy中关于字符串的不同表达方式：

1. **单引号** (`'`):
   - 用于表示一个普通的字符串。
   - 不支持字符串插值。
   - 例子: `'This is a simple string.'`

   ```groovy
   def simpleString = 'Hello, World!'
   println(simpleString)
   ```

2. **双引号** (`"`):
   - 也用于表示字符串。
   - 支持字符串插值（使用`${expression}`）。
   - 例子: `"Hello, ${name}!"`

   ```groovy
   def name = "Groovy"
   println("Hello, ${name}!")  // 输出: Hello, Groovy!
   ```

3. **三引号** (`'''`):
   - 用于表示多行字符串。
   - 支持或不支持插值，取决于你是否使用双引号或单引号。
   - 例子:

   ```groovy
   def name = "Groovy"
   def messageWithoutInterpolation = '''Hello, ${name}!'''  // 插值不会发生
   def messageWithInterpolation = """Hello, ${name}!"""     // 插值会发生
   println(messageWithoutInterpolation)  // 输出: Hello, ${name}!
   println(messageWithInterpolation)     // 输出: Hello, Groovy!
   ```

4. **插值字符串**:
   - 插值字符串允许在字符串中嵌入Groovy表达式，这些表达式在运行时被求值，并将其结果插入到字符串中。
   - 使用双引号来定义一个支持插值的字符串。
   - 插入表达式使用`${expression}`或简化为`$variable`。
   
   使用复杂表达式：
   
   ```groovy
   def a = 5
   def b = 10
   println("The sum of ${a} and ${b} is ${a + b}.") 
   ```
   
   访问对象属性
   
   ```groovy
   class Person {
       String firstName
       String lastName
   }
   
   def person = new Person(firstName: "John", lastName: "Doe")
   println("Full Name: ${person.firstName} ${person.lastName}")
   ```
   
   调用方法：
   
   ```groovy
   def greet(name) {
       return "Hello, ${name}!"
   }
   
   def user = "Bob"
   println("Greeting: ${greet(user)}")  // 输出: Greeting: Hello, Bob!
   ```
   
   简化插值：
   
   ```groovy
   def language = "Groovy"
   def year = 2023
   println("You are using $language in the year ${year}.")
   ```

## 流控制

### 条件语句

1. **if-else**
   
   基本的`if-else`结构与大多数编程语言类似。
   ```groovy
   def number = 10
   if (number > 5) {
       println("The number is greater than 5.")
   } else {
       println("The number is 5 or less.")
   }
   ```

2. **switch-case**

   Groovy的`switch-case`结构提供了比Java更强大的功能，因为它不仅限于常量表达式。
   ```groovy
   def value = "two"
   switch (value) {
       case "one":
           println("It's one")
           break
       case "two":
           println("It's two")
           break
       default:
           println("Unknown value")
   }
   ```

   在Groovy中，你还可以使用更复杂的表达式，如范围或列表。
   ```groovy
   def number = 3
   switch (number) {
       case 1..5:
           println("The number is between 1 and 5.")
           break
       case [6, 7, 8]:
           println("The number is 6, 7, or 8.")
           break
       default:
           println("The number is outside the specified range.")
   }
   ```

### 循环

1. **for**

   传统的`for`循环，主要用于计数或遍历数组/列表：
   ```groovy
   for (int i = 0; i < 5; i++) {
       println(i)
   }

   def fruits = ["apple", "banana", "cherry"]
   for (fruit in fruits) {
       println(fruit)
   }
   ```

2. **while**

   传统的`while`循环：
   ```groovy
   def count = 0
   while (count < 5) {
       println(count)
       count++
   }
   ```

3. **each**

   Groovy提供了`.each`方法来简化集合的迭代：
   ```groovy
   def names = ["Alice", "Bob", "Charlie"]
   names.each { name ->
       println(name)
   }
   ```

4. **times**

   `.times`是一个很酷的Groovy特性，用于执行指定次数的操作：
   ```groovy
   5.times {
       println("Hello, Groovy!")
   }
   ```

## 数据结构

### 列表

在Groovy中，你可以使用方括号(`[]`)来创建一个列表。

- **创建列表**:
  ```groovy
  def fruits = ['apple', 'banana', 'cherry']
  println(fruits)  // 输出: [apple, banana, cherry]
  ```

- **访问元素**:
  ```groovy
  println(fruits[0])  // 输出: apple
  ```

- **添加元素**:
  ```groovy
  fruits << 'date'
  println(fruits)  // 输出: [apple, banana, cherry, date]
  ```

- **列表方法**:
  Groovy为列表提供了许多有用的方法，如`each`, `find`, `collect`等。
  ```groovy
  fruits.each { println(it) }
  ```

### 映射

映射（或称为字典或哈希表）是键值对的集合。在Groovy中，你可以使用冒号(`:`)来定义键值对，并使用方括号(`[]`)来创建映射。

- **创建映射**:
  ```groovy
  def person = ['name': 'Alice', 'age': 30]
  println(person)  // 输出: [name:Alice, age:30]
  ```

- **访问元素**:
  ```groovy
  println(person['name'])  // 输出: Alice
  ```

- **添加或修改元素**:
  ```groovy
  person['city'] = 'New York'
  println(person)  // 输出: [name:Alice, age:30, city:New York]
  ```

- **映射方法**:
  Groovy为映射提供了许多有用的方法，如`each`, `find`, `collect`等。
  ```groovy
  person.each { key, value -> println("$key: $value") }
  ```

### 范围

范围是一个连续的、有序的数值或字符序列。

- **创建范围**:
  ```groovy
  def numbers = 1..5
  for (x in numbers) {
      println(x)
  } //输出1，2，3，4，5
  ```
  
- **使用范围**:
  范围经常与循环结合使用。
  ```groovy
  for (num in 1..5) {
      println(num)
  }
  ```

- **步进**:
  使用`step`方法可以定义范围的步进。
  ```groovy
  (1..10).step(2).each { println(it) }  // 输出: 1, 3, 5, 7, 9
  ```

- **检查元素是否在范围内**:
  ```groovy
  def value = 3
  println(value in 1..5)  // 输出: true
  ```

## 方法和闭包

### 方法

1. **定义方法**: 在Groovy中，你可以使用`def`关键字定义方法。方法的参数和返回值都是动态类型。
   ```groovy
   def greet(name) {
       return "Hello, ${name}!"
   }
   println(greet("Alice"))  // 输出: Hello, Alice!
   ```

2. **指定返回类型**: 尽管Groovy是动态类型的，但你仍然可以指定方法的返回类型。
   ```groovy
   String greetWithName(String name) {
       return "Hello, ${name}!"
   }
   ```

3. **参数默认值**: Groovy支持为方法参数提供默认值。
   ```groovy
   def greet(name = "Guest") {
       return "Hello, ${name}!"
   }
   println(greet())  // 输出: Hello, Guest!
   ```

### 闭包

闭包是Groovy中的一等公民。它是一种可以引用其自身周围上下文的代码块。

1. **定义闭包**: 闭包在Groovy中使用花括号定义，并可以赋值给变量。
   ```groovy
   def sayHello = { name ->
       "Hello, ${name}!"
   }
   println(sayHello("Bob"))  // 输出: Hello, Bob!
   ```

2. **闭包参数**: 闭包可以有参数，如上面的例子所示。如果闭包只有一个参数，你可以使用默认的参数名`it`。
   ```groovy
   def square = { it * it }
   println(square(4))  // 输出: 16
   ```

3. **闭包委托**: 闭包可以有一个委托对象，闭包内的方法调用会委托给这个对象。
   ```groovy
   class Greeter {
       def greet(name) {
           "Hello, ${name}!"
       }
   }
   
   def greeterClosure = { name ->
       greet(name)
   }
   greeterClosure.delegate = new Greeter()
   println(greeterClosure("Charlie"))  // 输出: Hello, Charlie!
   ```

4. **闭包作为参数**: 在Groovy中，很常见的一个用法是将闭包作为方法的参数传递。
   ```groovy
   def names = ["Alice", "Bob", "Charlie"]
   names.each { name ->
       println("Hello, ${name}!")
   }
   ```

6. **闭包的其他特性**:
    - **闭包中的`return`**: 默认情况下，闭包的最后一个表达式的值会被作为结果返回。
    - **`with` 方法**: 使用闭包的`with`方法，你可以在某个对象的上下文中执行闭包的代码。

## 面向对象

### 类定义

在Groovy中，定义类的语法与Java相似，但更简洁。

```groovy
class Person {
    String name
    int age
}
```

上面的代码定义了一个`Person`类，它有两个属性：`name`和`age`。

### 继承:

Groovy支持单继承，你可以使用`extends`关键字来继承另一个类。

```groovy
class Student extends Person {
    String studentId
}
```

在这个例子中，`Student`类继承了`Person`类，并添加了一个新的属性`studentId`。

### 接口

Groovy也支持接口的定义和实现，与Java的语法相似。

```groovy
interface Drivable {
    void drive()
}

class Car implements Drivable {
    void drive() {
        println("Driving the car.")
    }
}
```

在上面的代码中，我们定义了一个`Drivable`接口，然后`Car`类实现了这个接口。

### 属性和方法

1. **属性**: Groovy为类属性自动提供了getter和setter方法，除非你自己提供了它们。

```groovy
class Person {
    String name
}

def person = new Person()
person.name = "Alice"  // 调用setter方法
println(person.name)   // 调用getter方法
```

2. **方法**: 方法的定义与Java类似，但你可以省略返回类型并使用`def`关键字。

```groovy
class Calculator {
    def add(a, b) {
        return a + b
    }
}

def calculator = new Calculator()
println(calculator.add(5, 3))  // 输出: 8
```

3. **构造方法**: Groovy为类提供了默认的无参构造方法，但你也可以自定义构造方法。

```groovy
class Person {
    String name
    int age

    Person(String n, int a) {
        this.name = n
        this.age = a
    }
}

def person = new Person("Bob", 30)
```

4. **访问修饰符**: Groovy支持Java的访问修饰符如`public`, `private`, 和 `protected`，但默认修饰符是`public`。

5. **静态属性和方法**: 使用`static`关键字可以定义静态属性和方法。

```groovy
class Utils {
    static def square(x) {
        return x * x
    }
}

println(Utils.square(4))  // 输出: 16
```
