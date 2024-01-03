# AtomicReference

- **用途**：`AtomicReference` 用于包装单个对象的引用，以便可以在多线程环境中安全地进行读取和写入操作。
- **特点**：它提供原子操作来设置和获取对象引用，例如 `get()`, `set()`, 和 `compareAndSet(expectedValue, newValue)`。
- **问题**：`AtomicReference` 可能会遇到 "ABA 问题"。即在并发环境中，一个线程读取了一个值 A，然后这个值被其他线程改为 B，再改回 A。原先的线程使用 `compareAndSet` 检查时发现值仍然是 A，就无法意识到中间的变化。

```java
Book originalBook = new Book("水浒传", 56.5);
AtomicReference<Book> book = new AtomicReference<>(originalBook);
boolean b = book.compareAndSet(originalBook, new Book("格林童话", 45.0));
System.out.println(b); // 现在应该输出 true
```

下面代码将会修改失败：

```java
AtomicReference<Book> book = new AtomicReference<>(new Book("水浒传", 56.5));
boolean b = book.compareAndSet(new Book("水浒传", 56.5), new Book("格林童话", 45.0));
System.out.println(b);//false
```

原因为：即使两个 `Book` 对象的内容（字段 `name` 和 `price`）相同，它们在内存中仍然是两个不同的对象（即它们的引用地址不同）。`new Book("水浒传", 56.5)` 在 `AtomicReference` 初始化时创建的对象与在 `compareAndSet` 方法调用中创建的 `new Book("水浒传", 56.5)` 是不同的对象实例。

`compareAndSet` 方法期望作为其第一个参数的对象与 `AtomicReference` 当前引用的对象是同一个对象（即它们在内存中的地址相同）。因为这两个 `Book` 对象虽然内容相同，但在内存中的位置不同，所以 `compareAndSet` 方法返回 `false`。

# AtomicStampedReference

- **用途**：`AtomicStampedReference` 用于包装单个对象引用，同时维护这个引用的一个 "版本号" 或 "时间戳"。
- **特点**：它通过维护每个对象的版本号来解决 ABA 问题。每次对象引用更新时，版本号也会相应地更新。
- **操作**：`AtomicStampedReference` 提供了 `compareAndSet(expectedReference, newReference, expectedStamp, newStamp)` 方法，这样就可以检查引用及其时间戳是否都符合预期，从而安全地更新对象。

```java
Book book = new Book("水浒传", 32.2);
AtomicStampedReference<Book> stampedReference = new AtomicStampedReference<Book>(book, 1);
boolean b1 = stampedReference.compareAndSet(book, new Book("格林童话", 23.3), 2, 5);
System.out.println("×的情况" + b1 + "、对象为：" + stampedReference.getReference());
boolean b2 = stampedReference.compareAndSet(book, new Book("格林童话", 23.3), 1, 2);
System.out.println("√的情况" + b2 + "、对象为：" + stampedReference.getReference());
```

输出：

```
×的情况false、对象为：Book{name='水浒传', price=32.2}
√的情况true、对象为：Book{name='格林童话', price=23.3}
```

# AtomicMarkableReference

- **用途**：`AtomicMarkableReference` 类似于 `AtomicStampedReference`，但它维护的是一个布尔标记，而不是版本号。
- **特点**：它用于场景，其中你只关心引用是否被修改过，而不关心修改的次数或顺序。
- **操作**：提供 `compareAndSet(expectedReference, newReference, expectedMark, newMark)` 方法，可以在更新引用的同时更新一个布尔标记。

```java
Book book = new Book("水浒传", 37.3);
AtomicMarkableReference<Book> markable = new AtomicMarkableReference<>(book, false);
boolean b1 = markable.compareAndSet(book, new Book("格林童话", 37.4), true, false);
System.out.println("×的情况" + b1 + "、对象为：" + markable.getReference());
boolean b2 = markable.compareAndSet(book, new Book("格林童话", 37.4), false, true);
System.out.println("√的情况" + b2 + "、对象为：" + markable.getReference());
```

输出：

```
×的情况false、对象为：Book{name='水浒传', price=37.3}
√的情况true、对象为：Book{name='格林童话', price=37.4}
```

# 总结

- `AtomicReference` 适用于需要原子性更新引用的场景，但不能解决 ABA 问题。
- `AtomicStampedReference` 和 `AtomicMarkableReference` 都可以解决 ABA 问题，但前者通过一个额外的 "版本号" 来追踪引用的变化次数，而后者仅通过一个布尔标记来指示引用是否改变过。