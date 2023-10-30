# volatile

## 基本概念

在多线程环境中，当多个线程访问某个变量时，由于各线程在执行时会把变量从主内存拷贝到线程的本地内存，因此各线程可能会看到该变量的不同状态。`volatile` 关键字的主要目的是确保变量的更改对所有线程都是可见的。

## 内存可见性

Java 中的每个线程都有自己的堆栈，它存放本地变量（方法的参数、返回值等）。但是，对象存储在共享的堆中。为了性能，线程可能会在其本地内存中缓存变量，导致不同线程之间看到这些变量的不一致状态。

使用 `volatile` 修饰符声明的变量可以确保所有线程都从主内存中读取其最新值。

## 重排序

重排序是指编译器和处理器为了优化程序性能而重新安排指令的执行顺序。但这种优化不会影响到单线程程序的执行，只是在多线程环境中可能会导致问题。

重排序可以在以下几个层次上发生：

- **编译器优化重排序**：编译器在不改变单线程语义的前提下调整指令。
- **指令级并行重排序**：现代处理器会并行地执行多条指令，这可能会导致指令的完成顺序与原始顺序不一致。
- **内存系统重排序**：由于各种内存和缓存的互动，指令的执行顺序可能会与存储器上的顺序不

## 重排序与volatile

Java 提供了 `volatile` 关键字来保证变量的可见性，并禁止对其进行重排序。具体来说：

- 当第一个线程写入一个 `volatile` 变量，然后第二个线程读取该变量，写入操作对于读取操作是可见的，即使两个线程在不同的处理器上运行。
- `volatile` 关键字确保局部线程的写入立即反映到主内存中，并且其他线程读取 `volatile` 变量时都会从主内存中读取。
- 最重要的是，`volatile` 变量的读取和写入不会被重排序。这意味着：
  - 在写入 `volatile` 变量之前的任何变量的写入操作都不会被推迟到之后。
  - 在读取 `volatile` 变量之后的任何变量的读取操作都不会被提前到之前。

## 底层原理

`volatile` 关键字在 Java 语言中确保了变量修改的可见性，但要理解其底层实现原理，需要涉及 Java 内存模型（JMM）和与底层硬件相关的操作。我们可以从以下几个方面来深入探讨其实现：

1. **Java 内存模型 (JMM)**：
   
   - Java 内存模型定义了变量的读写方式以及它们在并发条件下如何与主存和线程的本地存储进行交互。
   - 在 JMM 中，主内存是所有线程共享的，而每个线程都有自己的本地内存（也可以理解为缓存）。
   - `volatile` 的主要语义是确保变量的写入主内存是立即的，并且读操作直接从主内存中进行。
   
2. **内存屏障（Memory Barriers）**:
   - `volatile` 的实现依赖于内存屏障。
   - 内存屏障是一个硬件层面的指令，它确保特定的内存读/写操作按预期的顺序执行。
   - 在 Java 中，使用 `volatile` 变量时，JVM 会在字节码中插入特定的内存屏障指令来确保正确的读/写顺序。

3. **具体的内存屏障操作**:
   - 当写入一个 `volatile` 变量时，JVM 在写操作后面插入一个写内存屏障（Store Memory Barrier），确保 `volatile` 变量立即被写入主内存。
   - 当从一个 `volatile` 变量读取时，JVM 在读操作前插入一个读内存屏障（Load Memory Barrier），确保读操作是直接从主内存获取的最新值。

4. **禁止指令重排序**:
   - 另一个 `volatile` 的关键语义是防止指令重排序。编译器、运行时和处理器都可能为了优化目的而重排指令。对于 `volatile` 变量，这种重排序是不被允许的。
   - 例如，考虑以下代码：
     ```java
     volatile boolean flag = false;
     int x = 0;
     
     // Thread 1
     x = 42;
     flag = true;
     
     // Thread 2
     if (flag) {
         System.out.println(x);
     }
     ```
     由于 `flag` 是 `volatile`，所以 `x = 42` 永远不会在 `flag = true` 之后执行，这确保了 Thread 2 打印 `x` 的值为 42。

总结：`volatile` 的底层实现原理涉及到 Java 内存模型、内存屏障和禁止指令重排序。通过这些机制，JMM 为 `volatile` 变量提供了特定的语义，确保其修改对所有线程都是立即可见的，并且与其他内存操作的顺序是预期的。

## volatile的限制

- `volatile` 只能确保一个操作的原子性，不能替代 `synchronized` 关键字来提供多个操作的原子性。
- 使用 `volatile` 可能会对性能产生负面影响。
- `volatile`关键字提供了一个轻量级的同步机制，它确保了内存可见性，并且防止了指令重排序。但是，它不提供互斥性。如果多个线程都对一个`volatile`变量进行写入，那么这些写入就不是线程安全的。在这种情况下，可能需要使用更重量级的同步机制，例如`synchronized`块或`java.util.concurrent`包中的工具。

## 使用场景

- 当变量的写入与读取都是原子性的，不依赖于当前值（例如：布尔标记）。
- 当只有一个线程更新变量的值，而其他线程只是读取它。

## 例子

```java
package com.zhouyf.volatile_;

public class VolatileExample {

    private boolean isDataAvailable = false;
    private int data = -1;

    public static void main(String[] args) throws InterruptedException {
        VolatileExample example = new VolatileExample();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(100); // 模拟数据产生的延迟
                    System.out.println("produce: " + i);
                    example.produceData(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                while (!example.isDataAvailable()) {
                    // busy wait until data is available
                }
                int consumedData = example.consumeData();
                System.out.println("Consumed: " + consumedData);
            }
        });

        producer.start();
        consumer.start();

    }

    public void produceData(int newData) {
        data = newData;
        isDataAvailable = true;
    }

    public boolean isDataAvailable() {
        return isDataAvailable;
    }

    public int consumeData() {
        isDataAvailable = false;
        return data;
    }
}
```

Output：

```
produce: 0
produce: 1
produce: 2
produce: 3
produce: 4
```

上面代码中，consumer线程会进入死循环，因为在没有`volatile`修饰的情况下，一个线程对变量的修改可能不会立即刷新到主内存中。而另一个线程可能从其本地缓存中读取该变量的旧值，即使它已经被修改了。这意味着，尽管producer线程设置了`isDataAvailable`为`true`，但consumer线程可能一直看到它的值为`false`。

通过将`isDataAvailable`声明为`volatile`，我们可以确保所有对该变量的读/写操作都直接与主内存交互，从而确保其修改对所有线程都是可见的。

当isDataAvailable声明为volatile，输出入下

```
produce: 0
Consumed: 0
produce: 1
Consumed: 1
produce: 2
Consumed: 2
produce: 3
Consumed: 3
produce: 4
Consumed: 4
```

