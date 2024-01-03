# AtomicIntegerFieldUpdater



`AtomicIntegerFieldUpdater`为一个抽象类

```java
public abstract class AtomicIntegerFieldUpdater<T>
```

`AtomicIntegerFieldUpdaterImpl`为`AtomicIntegerFieldUpdater`的静态内部子类，其中提供了AtomicIntegerFieldUpdater中方法的具体实现。

```java
private static final class AtomicIntegerFieldUpdaterImpl<T>
        extends AtomicIntegerFieldUpdater<T>
```

AtomicIntegerFieldUpdater提供了newUpdater静态方法，获取实例化AtomicIntegerFieldUpdaterImpl对象

```java
public static <U> AtomicIntegerFieldUpdater<U> newUpdater(Class<U> tclass,
                                                          String fieldName) {
    return new AtomicIntegerFieldUpdaterImpl<U>
        (tclass, fieldName, Reflection.getCallerClass());
}
```

范例

```java
package com.zhouyf.field;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class Obj{
	//获取实例
    public static final AtomicIntegerFieldUpdater<Obj> updater =
            AtomicIntegerFieldUpdater.newUpdater(Obj.class, "count");
    
    //属性必须使用volatile，保证可见性
    private volatile int count = 0;

    public void increment(){
        updater.incrementAndGet(this);
    }

    public int getCount() {
        return count;
    }
}
public class Test {

    public static void main(String[] args) throws InterruptedException {
        final Obj obj = new Obj();

        for(int i = 0; i < 2000; i++){
            new Thread(()->{
                obj.increment();
            }).start();
        }

        TimeUnit.SECONDS.sleep(1);

        int count = obj.getCount();
        System.out.println(count);
    }
}

```

输出：

```
2000
```

## 特点

1. **性能**：相比于将整个对象声明为原子类，字段修改器使用更少的资源，并且更高效。
2. **安全性**：确保对特定字段的原子操作，防止并发环境中的竞争条件。
3. **灵活性**：能够对非原子对象中的字段执行原子操作。

## 使用要求

- 字段必须是 `volatile` 类型的，确保了字段的可见性。
- 字段必须是实例变量，不能是静态变量。
- 字段不能是 `final` 类型的，因为 `final` 字段不能被修改。