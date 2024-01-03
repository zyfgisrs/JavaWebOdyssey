# AQS

AbstractQueuedSynchronizer这个类是Java并发包的核心，提它内部维护了一个队列，用于管理试图访问被同步代码块的线程。

## CLH锁队列

AQS的内部类 `Node` 的定义。`Node` 类是 AQS 的核心组成部分，用于表示等待锁的线程以及它们在队列中的位置。每个节点（`Node` 实例）代表一个线程及其在同步队列中的状态。源码如下：

```java
abstract static class Node {
    volatile Node prev;       // 指向前一个节点的指针。使用 volatile 关键字保证了内存可见性。
    volatile Node next;       // 指向下一个节点的指针。这个字段在节点可被唤醒时是可见的。
    Thread waiter;            // 表示与该节点关联的线程。当线程被加入到队列时，这个字段是非空的。
    volatile int status;      // 表示节点的状态，用于控制线程是否应该被阻塞或唤醒。

    // methods for atomic operations
    final boolean casPrev(Node c, Node v) { //使用 CAS操作原子性地更新 prev 指针。
        return U.weakCompareAndSetReference(this, PREV, c, v);
    }
    final boolean casNext(Node c, Node v) {  //使用 CAS 操作原子性地更新 next 指针。
        return U.weakCompareAndSetReference(this, NEXT, c, v);
    }
    final int getAndUnsetStatus(int v) {     //原子性地获取并修改节点状态，用于信号处理。
        return U.getAndBitwiseAndInt(this, STATUS, ~v);
    }
    final void setPrevRelaxed(Node p) {     //用于非队列分配时设置 prev 指针。
        U.putReference(this, PREV, p);
    }
    final void setStatusRelaxed(int s) {     // 用于非队列分配时设置节点状态
        U.putInt(this, STATUS, s);
    }
    final void clearStatus() {               //用于减少不必要的信号时清除状态。
        U.putIntOpaque(this, STATUS, 0);
    }

    //以下这些偏移量用于原子操作和低级内存访问。
    //U为unsafe类型对象，执行原子操作和低级内存操作。
    private static final long STATUS //status字段Node 类中的内存偏移量
        = U.objectFieldOffset(Node.class, "status");
    private static final long NEXT//next字段Node 类中的内存偏移量
        = U.objectFieldOffset(Node.class, "next");
    private static final long PREV//prev字段Node 类中的内存偏移量
        = U.objectFieldOffset(Node.class, "prev");
}
```

`ExclusiveNode`这个类用于表示独占模式的节点，`SharedNode` 类用于表示共享模式的节点，`ConditionNode` 类扩展了 `Node` 类并实现了 `ForkJoinPool.ManagedBlocker` 接口，用于支持条件变量（Condition）的等待/通知机制。

在 AQS的上下文中，区分独占和共享模式的功能并不是由 `ExclusiveNode` 和 `SharedNode` 直接实现的，而是由 AQS 的其他部分根据节点类型的不同来处理的。这些类的存在是为了在 AQS 的内部逻辑中提供一种类型标识，以便 AQS 可以识别和区分处理独占模式和共享模式的不同情况。

```java
static final class ExclusiveNode extends Node { }
static final class SharedNode extends Node { }

static final class ConditionNode extends Node
    implements ForkJoinPool.ManagedBlocker {
    ConditionNode nextWaiter;            

   
    public final boolean isReleasable() {
        return status <= 1 || Thread.currentThread().isInterrupted();
    }

    public final boolean block() {
        while (!isReleasable()) LockSupport.park();
        return true;
    }
}
```

### 工作原理

1. **加入队列**: 当一个线程无法获取同步状态时，AQS会创建一个包含该线程引用的Node，并将其加入到队列尾部。
2. **节点的排队**: 每个新的节点都被插入到队列的末尾，并与前一个节点建立连接。
3. **等待锁**: 线程在其节点上等待，直到前驱节点表示同步状态已经可用。这通常通过自旋等待或阻塞实现。
4. **获取锁**: 当前驱节点释放锁或者指示后续节点可以尝试获取同步状态时，等待在该节点的线程将尝试获取锁。
5. **释放锁**: 当线程完成其临界区的操作后，它会释放锁，并将同步状态传递给队列中的下一个节点（如果存在）。

## state属性

在 Java 中的 `AbstractQueuedSynchronizer` (AQS) 类里，`private volatile int state;` 属性是一个核心组件，用于表示同步状态。这个状态是 AQS 同步机制的基础，用于控制同步资源的获取和释放。以下是这个属性的几个关键作用：

1. **同步状态的表示**：`state` 属性用于表示同步资源的状态。例如，在一个互斥锁的实现中，`state` 可能为 0 表示锁未被持有，为 1 表示锁被某个线程持有。在其他同步器中，如 `Semaphore` 或 `CountDownLatch`，`state` 可能表示剩余的许可数或倒计时的数量。
2. **灵活性**：`state` 的具体含义依赖于 AQS 的子类如何使用它。这种设计使得 AQS 能够被用于构建各种不同的同步器，从简单的互斥锁到复杂的资源控制器。
3. AQS使用一个整数（通常是volatile修饰的）来表示同步状态。这个状态是同步组件的核心，不同的同步组件（如ReentrantLock、Semaphore等）会以不同的方式解释这个状态。

```java
private volatile int state;
```

## AQS的关键方法

1. **acquire / release（独占模式）**: 这两个方法用于独占模式下的获取和释放同步状态。
2. **acquireShared / releaseShared（共享模式）**: 这两个方法用于共享模式下的获取和释放同步状态。
3. **tryAcquire / tryRelease, tryAcquireShared / tryReleaseShared**: 这些是必须由自定义同步组件实现的方法，用于定义该组件获取和释放同步状态的逻辑。

## AQS的运行原理

