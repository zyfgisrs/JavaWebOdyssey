# ThreadFactory

`ThreadFactory` 是 Java 中的一个接口，属于 `java.util.concurrent` 包。这个接口主要用于在并发程序中自定义线程的创建。它只有一个方法 `newThread(Runnable r)`，允许你提供自定义的线程创建逻辑。

在并发编程中，`ThreadFactory` 的使用场景包括：

1. **自定义线程属性**：例如，为线程设置优先级、名称、守护状态等。
2. **统一线程管理**：通过一个工厂管理所有线程的创建，便于监控和管理。
3. **异常处理**：为线程指定一个统一的异常处理器。
4. **资源分配**：控制线程的创建，避免资源过度使用。

下面是一个简单的 `ThreadFactory` 示例，演示如何创建一个自定义的线程工厂：

```java
package com.zhouyf.threadfactory;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ZhouThreadFactory implements ThreadFactory {
    private static final String TITLE = "zhouyf-";
    private static final AtomicInteger count = new AtomicInteger(0);

    private ZhouThreadFactory() {
    }

    private final static ZhouThreadFactory INSTANCE = new ZhouThreadFactory();

    public static ZhouThreadFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, TITLE + count.getAndIncrement());
    }
}

class test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ZhouThreadFactory.getInstance().newThread(() -> {
                System.out.println(Thread.currentThread().getName());
            }).start();
        }
    }
}
```

 `ZhouThreadFactory` 是一个实现了 `ThreadFactory` 接口的单例模式的线程工厂。这个工厂每次创建新线程时，都会给线程指定一个唯一的名字，这个名字以 "zhouyf-" 开头，后面跟着一个递增的数字。

out

```
zhouyf-0
zhouyf-6
zhouyf-1
zhouyf-3
zhouyf-5
zhouyf-4
zhouyf-8
zhouyf-7
zhouyf-9
zhouyf-2
```

使用反射创建线程：

```java
package com.zhouyf.threadfactory;


import java.lang.reflect.Constructor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ZhouThreadFactory implements ThreadFactory {
    private static final String TITLE = "zhouyf-";
    private static final AtomicInteger count = new AtomicInteger(0);

    private ZhouThreadFactory() {
    }

    private final static ZhouThreadFactory INSTANCE = new ZhouThreadFactory();

    public static ZhouThreadFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Thread newThread(Runnable r) {
        try {
            Class<?> clazz = Class.forName("java.lang.Thread");
            Constructor<?> constructor = clazz.getConstructor(Runnable.class, String.class);
            return (Thread) constructor.newInstance(r, TITLE + count.getAndIncrement());
        } catch (Exception e) {
            return null;
        }
    }
}

class test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ZhouThreadFactory.getInstance().newThread(() -> {
                System.out.println(Thread.currentThread().getName());
            }).start();
        }
    }
}
```

