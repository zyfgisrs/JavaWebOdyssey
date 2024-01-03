# Random类

```java
package com.zhouyf.random;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random(888);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.printf("【%s】线程生成的随机数:[%d]\n", Thread.currentThread().getName(), random.nextInt(10));
            }, i + "").start();
        }

        TimeUnit.MILLISECONDS.sleep(400);
        Random random1 = new Random(888);
        for (int i = 0; i < 10; i++) {
            System.out.print(random1.nextInt(10));
        }
    }
}
```

输出

```
【0】线程生成的随机数:[9]
【8】线程生成的随机数:[5]
【9】线程生成的随机数:[3]
【7】线程生成的随机数:[8]
【6】线程生成的随机数:[4]
【5】线程生成的随机数:[8]
【4】线程生成的随机数:[8]
【3】线程生成的随机数:[1]
【2】线程生成的随机数:[9]
【1】线程生成的随机数:[9]
9991884835
```

- 无论是在多线程环境下还是在单线程环境下，使用相同的种子（这里是 `888`）初始化的 `Random` 实例都会生成相同的随机数序列。这是因为 `Random` 类的随机数生成算法是确定性的：给定相同的初始种子，它总是会按照相同的顺序生成相同的随机数。
- 当多个线程共享同一个 `Random` 实例时，每个线程对 `nextInt` 方法的调用都会影响到种子的状态。因此，虽然每个具有相同seed的 `Random` 实随生成的随机数相同，但生成的顺序依赖于线程的执行顺序，这在并发环境中是不确定的。
- 由于 `Random` 类使用 CAS 操作来更新种子值，因此在高并发环境中，多个线程同时尝试更新同一个种子可能会导致性能问题。CAS 操作可能需要多次尝试才能成功，尤其是在高并发的场景下。CSA的自旋会增加处理器的工作量，从而造成额外的性能开销。

1. `next(int bits)` 方法使用当前的种子值（`seed`）来计算下一个种子值和生成随机数。通过CAS操作更新种子值。如果多个线程尝试同时更新种子，CAS 机制确保只有一个线程能够成功地进行更新。源码如下：

```java
protected int next(int bits) {
    long oldseed, nextseed;
    AtomicLong seed = this.seed;
    do {
        oldseed = seed.get();
        nextseed = (oldseed * multiplier + addend) & mask;
    } while (!seed.compareAndSet(oldseed, nextseed));
    return (int)(nextseed >>> (48 - bits));
}
```

# ThreadLocalRandom

`ThreadLocalRandom` 是 Java 中的一个类，专为多线程环境中的高效随机数生成而设计。它是 `java.util.concurrent` 包的一部分，自 Java 7 起提供。`ThreadLocalRandom` 解决了在并发程序中使用共享 `Random` 实例时可能出现的争用问题，并提供了更高效的随机数生成机制。以下是 `ThreadLocalRandom` 的一些主要作用和特点：

1. **减少线程争用**：在传统的 `Random` 类中，多个线程共享同一个 `Random` 实例可能导致性能问题，因为线程安全的随机数生成涉及到同步问题。`ThreadLocalRandom` 为每个线程提供了一个独立的随机数生成器实例，从而减少了线程之间的争用。

2. **提高性能**：由于每个线程都有自己的 `ThreadLocalRandom` 实例，避免了使用CAS的同步机制，因此在多线程环境中生成随机数的性能得到了显著提升。

3. **易于使用**：`ThreadLocalRandom` 的使用非常简单，不需要显式地创建随机数生成器的实例。可以直接调用 `ThreadLocalRandom.current()` 来获取当前线程的随机数生成器实例。

4. **丰富的API**：`ThreadLocalRandom` 提供了生成各种类型随机数（如整数、长整数、双精度数、布尔值等）的方法，以及生成有界随机数的便利方法。

下面是一个使用 `ThreadLocalRandom` 的简单示例：

```java
for (int i = 0; i < 10; i++) {
    new Thread(() -> {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        System.out.printf("【%s】线程生成的随机数:[%d]\n",
                Thread.currentThread().getName(), current.nextInt(10));
    }, i + "").start();
}
```

在多线程应用程序中，特别是在使用并发框架如Java的`ForkJoinPool`时，`ThreadLocalRandom` 是生成随机数的首选方法。

