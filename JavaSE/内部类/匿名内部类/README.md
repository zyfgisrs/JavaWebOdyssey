# 匿名内部类

## 作用

1. **简化代码**：在需要实现接口或扩展类的地方，可以使用匿名内部类来避免创建单独的实现类。
2. **即用即弃**：适用于那些仅在一处使用的实现，避免了额外的命名和文件维护。
3. **访问外部作用域变量**：可以访问和操作其外部类的成员变量（final）。

```java
package anonymous;

//使用匿名内部类演示不同动物游泳
public class Test {
    public static void main(String[] args) {
        new Animal().performSwim(new Swimming() {
            @Override
            public void swim() {
                System.out.println("狗在游泳");
            }
        });

        new Animal().performSwim(new Swimming() {
            @Override
            public void swim() {
                System.out.println("猫在游泳");
            }
        });
    }
}

interface Swimming {
    void swim();
}

class Animal{

    public void performSwim(Swimming s){
        System.out.println("开始游泳.....");
        s.swim();
    }
}
```

## 注意事项

1. **访问限制**：匿名内部类只能访问其外部类的final或事实上的final变量（从Java 8开始）。
2. **作用域限制**：不能有静态初始化块或成员，不能存在任何静态方法。
3. **单一用途**：一旦定义，无法重用，只能实例化一次。
4. **可读性问题**：如果匿名类过大或过于复杂，会影响代码的可读性。
5. **命名冲突**：在匿名内部类中，要注意避免与外部类的成员变量或方法产生命名冲突。
6. **构造器问题**：匿名内部类不能定义构造器，初始化需要在声明中完成。
7. **继承限制**：每个匿名内部类只能继承一个类或实现一个接口。