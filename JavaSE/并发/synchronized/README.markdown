# synchronized

## 基础知识

### 临界区

临界区是代码中的一段区域，他需要对共享资源进行独占式的访问。这意味着在同一时间只允许一个线程或进程访问该部分代码。临界区的存在是因为共享资源（如变量、数据结构或设备）在并发环境中被多个任务同时访问时可能会出现不可预期的结果。

不正确的管理临界区会导致以下问题：

1. **数据不一致性**：当多个线程同时访问和修改共享数据时，数据可能变得不一致。
2. **竞态条件**：当多个线程的执行次序影响程序的输出时，我们称之为竞态条件。这意味着输出是不确定的，取决于线程的具体执行时序。

如何保护临界区：

- Java中，我们可以使用几种方法来保护临界区，其中最常用的是`synchronized`关键字。当一个线程进入一个`synchronized`方法或块时，其他试图访问该方法或块的线程会被阻塞，直到第一个线程离开。

  ```java
  public synchronized void updateSharedResource() {
      // 临界区
  }
  ```

  ```java
  public void anotherMethod() {
      synchronized(sharedResource) {
          // 临界区
      }
  }
  ```

### 竞争条件

**竞态条件**是指当多个线程的操作执行顺序影响程序的结果时的现象。简单地说，当两个或多个线程同时访问某个数据，并且至少有一个线程对它进行了修改，那么最终的结果取决于线程运行的精确时序。*想象一下，两个线程都在尝试更新同一个银行账户的余额。如果它们的操作没有正确同步，可能会导致不正确的余额。这就是一个竞态条件的例子。*

当竞态条件发生时，程序的行为变得不可预测。这意味着相同的代码在不同的时刻或在不同的环境下可能会产生不同的结果。这种不确定性和不可预测性使得并发问题（尤其是竞态条件）很难调试。

竞争条件需要如下的**条件**：

- 并发，即至少存在两个并发执行的线程。
- 共享对象，即多个并发流会访问同一对象。**常见的共享对象有共享内存，文件系统，信号。一般来说，这些共享对象是用来使得多个程序执行流相互交流。**此外，我们称访问共享对象的代码为**临界区**。在正常写代码时，这部分应该加锁。
- 改变对象，即至少有一个控制流会改变竞争对象的状态。因为如果程序只是对对象进行读操作，那么并不会产生条件竞争。

在Java中，我们可以使用以下几种方法：

- **synchronized**：Java提供的关键字，它可以确保一次只有一个线程可以访问某个方法或块。
- **Locks**：Java的`java.util.concurrent.locks`包提供了更加灵活的锁机制。
- **原子变量**：`java.util.concurrent.atomic`包中的类，如`AtomicInteger`，提供了原子性的操作。

### 数据不一致性

**数据不一致性**是指在多线程环境中，由于线程间操作的时间差异和交错，导致共享数据的状态与预期不符。例如，如果两个线程都尝试更新同一个数据，但它们的操作没有被正确地同步，最终的数据可能与预期的任何线程都不匹配。

考虑以下场景：两个线程同时读取一个值（例如10），然后各自增加5。我们期望最终的结果是20，但如果这两个操作没有同步，我们可能得到15。这是因为两个线程可能都读取了原始值10，然后都增加了5，结果只增加了5。

## synchronized定义与用法

### 定义

**synchronized** 是Java语言中的一个关键字，用于标记一个方法或一个代码块是同步的。当一个线程进入一个synchronized方法或块时，其他线程将被阻止进入这个方法或块，直到第一个线程退出。

synchronized的主要目的是提供一种机制，使得在并发环境中，多个线程可以安全地访问共享资源或执行有关的操作，从而避免数据不一致性和其他并发相关的问题。

### 使用方法

- 方法级：直接修饰真整个方法

```java
public synchronized void syncMethod() {
    // ... method body ...
}
```

- 代码块级：修饰某个具体的代码块

```java
public void someMethod() {
    synchronized(this) {
        // ... synchronized code ...
    }
}
```

- 静态方法级：修饰静态方法，锁定的对象是Class对象

```java
public static synchronized void staticSyncMethod() {
    // ... method body ...
}
```

## 锁对象

当我们使用synchronized关键字时，实际上是对某个对象进行了加锁。这个对象被称为**监视器对象**或**锁对象**。

- 当synchronized修饰实例方法时，锁对象是该方法所属的对象（也就是通常我们所说的`this`）。
- 当synchronized修饰静态方法时，锁对象是这个方法所在的Class对象。
- 当synchronized修饰代码块时，我们可以自定义锁对象。例如上面的`synchronized(this)`中，锁对象就是`this`。

### 锁对象的选择

synchronized关键字在Java中用于同步，它的工作原理是基于对象的内部锁或监视器锁。选择正确的锁对象非常关键，因为它决定了同步的范围和效果。让我们深入了解这方面的内容：

首先，要确定你希望同步的是什么。你是想同步对某个对象的所有操作，还是仅仅是对某个类的所有实例的操作，还是对类级别的操作。

锁对象的选择

- **this（当前实例对象）**  
  如果你想同步一个对象实例的操作，最常见的方法是使用`this`作为锁对象。
  
  ```java
  public synchronized void myMethod() {
      // ... 
  }
  ```
  或
  ```java
  public void myMethod() {
      synchronized(this) {
          // ...
      }
  }
  ```
  
- **类的Class对象**  
  
  如果你想同步某个类的所有实例的操作，或者类级别（静态）的操作，你应该使用该类的Class对象作为锁。
  
  ```java
  public static synchronized void myStaticMethod() {
      // ...
  }
  ```
  或
  ```java
  public static void myStaticMethod() {
      synchronized(MyClass.class) {
          // ...
      }
  }
  ```
  
- **专门的锁对象**  
  
  有时，为了更细粒度的控制或为了明确表示锁的目的，你可能会选择一个专门的锁对象。
  
  ```java
  private final Object lock = new Object();
  
  public void myMethod() {
      synchronized(lock) {
          // ...
      }
  }
  ```

 注意事项

- **选择合适的粒度**  
  
  太大的锁粒度可能会降低性能（因为线程经常需要等待），而太小的锁粒度可能不会提供足够的数据保护。
  
- **避免使用可变或不可控的对象作为锁** 
  
  使用`String`、`Integer`等常见的不可变对象作为锁对象可能会引起混淆，因为它们可能被其他部分的代码所共享。
  
- **始终保持锁的一致性** 
  如果你在一个方法中使用一个对象作为锁，那么在所有需要访问共享资源的方法中都应该使用同一个锁对象。

### this作为锁对象

BankAccount类中模拟了银行账户存钱和取钱的业务

```java
package com.zhouyf.synchronized_;

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        synchronized (this) {
            if (amount > 0) {
                balance += amount;
                System.out.println(Thread.currentThread().getName() + "deposited" + amount
                        + ". New balance: " + balance);
            }
        }
    }

    public void withdraw(double amount){
        synchronized (this){
            if(amount>0 && balance >= amount){
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + "withdraw" + amount
                        + ". New balance: " + balance);
            }
        }
    }

    public double getBalance() {
       synchronized (this){
           return balance;
       }
    }
}
```

创建两个线程分别对同一个账户进行存钱和取钱的操作：

```java
package com.zhouyf.synchronized_;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount(1000);


        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                bankAccount.deposit(50);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "deposit::");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                bankAccount.withdraw(30);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "withdraw::");

        thread2.start();
        thread1.start();

    }
}
```

### 类对象作为锁对象

假设有一个`Counter`类，它有一个静态整数字段`count`，表示全局计数器。我们想要提供一个方法来安全地递增这个计数器，确保在并发环境中其值总是正确的。

```java
package com.zhouyf.synchronized_;

public class Counter {
    private static int count;


    public static void increment(){
        synchronized (Counter.class){
            count++;
        }
    }

    public static int getCount() {
        synchronized(Counter.class) {
            return count;
        }
    }
}
```

```java
package com.zhouyf.synchronized_;

public class CounterDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++){
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++){
                    Counter.increment();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        System.out.println(Counter.getCount());
    }
}
```

我们使用`Counter.class`作为锁对象，确保在多线程环境中递增和获取计数器的操作是线程安全的。这是因为，对于Java中的每个类，都有一个与之关联的唯一的`Class`对象，因此使用类对象作为锁可以确保在整个JVM中都是同步的。

## synchronized实现原理

### 基本概念

- `synchronized`在JVM中是基于进入和退出Monitor对象来实现的，这个Monitor对象在Java中是不可见的，但在JVM中，每个对象都有一个Monitor与之关联。
- 对象头（Header）中的Mark Word就是用来存放Monitor对象的指针。

### 字节码层面

在Java字节码中，与`synchronized`相关的主要有两个指令：`monitorenter`和`monitorexit`。

- 当我们在一个方法前使用`synchronized`关键字，或在一个代码块上使用`synchronized`时，都会在相应的字节码上看到这两个指令。
  
- `monitorenter`在进入同步块的开始部分，`monitorexit`在结束部分（无论是正常结束还是因为异常退出）。

### 锁的升级与演化

为了提高锁的效率，JVM内部采用了锁升级的概念。具体来说，synchronized锁可以分为以下几种：

- **偏向锁**: 当一个线程首次访问某个对象的同步代码块时，JVM会将该对象的头部信息中的线程ID设置为当前线程的ID，这意味着该对象目前偏向于当前线程。如果此后该线程再次请求锁，JVM会检查该对象的头部的线程ID是否为当前线程，如果是，则直接允许进入同步块。

- **轻量级锁**: 当有另一个线程尝试访问同一对象的同步块时，偏向锁就会升级为轻量级锁。在此模式下，线程不会立即阻塞，而是进行自旋，查看是否可以在短时间内获得锁。轻量级锁的一个特点是“自旋”。当另一个线程已经拥有了轻量级锁，当前线程会在不放弃CPU的情况下不断地尝试获取锁，这称为自旋等待。这是为了避免线程阻塞导致的用户态和内核态之间的切换开销。轻量级锁最适合那些执行时间非常短的代码块，因为线程如果只是短暂地持有锁，其他等待的线程可以通过自旋操作快速获取到锁。

- **重量级锁**: 如果自旋失败，轻量级锁会升级为重量级锁，此时线程会被阻塞并进入等待状态。

### 总结

在JVM层面，synchronized是通过关联每个对象的Monitor对象，并使用`monitorenter`和`monitorexit`指令来实现的。为了提高效率，JVM使用了锁的升级策略，从偏向锁到轻量级锁，再到重量级锁。

这种复杂性是为了确保在不同的并发场景下，锁操作都能够尽可能高效。例如，对于单线程访问的场景，偏向锁是非常高效的；而在有少量线程竞争的情况下，轻量级锁可能更有优势；但在高度并发的情况下，传统的重量级锁可能是最合适的。

## Monitor对象

确实，Java对象的头部信息（通常称为Mark Word）在不同的锁状态下，其存储的内容和意义会有所不同。首先，我们明确一下基本的概念，然后详细讨论与Monitor对象的关系。

###  Java对象头与Mark Word

Java对象在内存中的布局，主要分为三个部分：

- **对象头 (Object Header)**
- **实例数据 (Instance Data)**
- **对齐填充 (Padding)**

对象头中最主要的部分是Mark Word，它用来存储对象的元数据信息，如哈希码、GC状态、锁状态等。

###  Mark Word中的锁状态

Mark Word中关于锁的标记有以下几种：

- **无锁态 (Unlocked)**
- **偏向锁态 (Biased)**
- **轻量级锁态 (Lightweight Locked)**
- **重量级锁态 (Heavyweight Locked)**

###  Monitor对象与重量级锁

当我们谈到Java中的`synchronized`时，其实是在讨论与对象关联的内置锁。每个Java对象都可以作为一个锁，但并不是每个对象都有一个物理存在的Monitor对象。

只有在重量级锁的状态下，Mark Word才会关联一个Monitor对象。具体来说：

- **偏向锁**: Mark Word记录了线程ID。
- **轻量级锁**: Mark Word中存放的是指向栈中锁记录的指针。
- **重量级锁**: Mark Word中存放的是指向堆中Monitor对象的指针。

这个Monitor对象在JVM的内部实现中通常被称为`ObjectMonitor`。当多个线程竞争同一把锁，且不能短时间内获得锁时，它们会被阻塞并放入到该Monitor对象的等待集中。

###  为什么要这样设计？

这样的设计是为了节省空间和提高效率。如果每个对象都与一个Monitor对象关联，那么每个对象都需要额外的空间来存储这个Monitor对象，这将是一个巨大的浪费。反之，只有在必要的时候（即重量级锁状态下），JVM才会创建Monitor对象。
