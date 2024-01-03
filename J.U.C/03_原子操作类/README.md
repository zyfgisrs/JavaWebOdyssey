# AtomicLong底层实现

## 字段

底层使用基本数据类型long来维护，value字段使用volatile来修饰，保证了其在内存中的可见性

```java
private volatile long value;

/**
 * Creates a new AtomicLong with the given initial value.
 *
 * @param initialValue the initial value
 */
public AtomicLong(long initialValue) {
    value = initialValue;
}
```

CAS操作需要知道特定字段在内存中的准确位置，以便直接对其进行原子更新。这个位置就是字段的偏移量valueOffset。

`valueOffset`是通过调用`Unsafe`类的`objectFieldOffset`方法获得的，该方法需要一个反映特定字段的`Field`对象作为参数。

```java
private static final Unsafe unsafe = Unsafe.getUnsafe();
private static final long valueOffset;

static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicLong.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
}
```

## 操作

`getAndIncrement`方法为数据的增加1的操作。

```java
public final long getAndIncrement() {
    //调用了unsafe类中的方法，参数分别为偏移量和增加的数据1
    return unsafe.getAndAddLong(this, valueOffset, 1L);
}
```

`getAndAddLong`方法中，var1为需要修改的目标对象，var2为偏移量，var4为增加的数据1

`this.compareAndSwapLong(var1, var2, var6, var6 + var4)`: 这是CAS操作的核心。它尝试将对象`var1`中偏移量为`var2`的字段的值从`var6`更改为`var6 + var4`。如果在这个操作期间，字段的值没有被其他线程更改（即仍然等于`var6`），那么操作成功，字段的值被更新为`var6 + var4`，并且方法返回`true`。否则，表示字段的值已被其他线程修改，循环将继续，再次尝试。

```java
public final long getAndAddLong(Object var1, long var2, long var4) {
    // 这里声明了一个局部变量var6，用于存储当前值。
    long var6;
    do {
        //从指定对象var1的指定偏移量var2处读取当前的long值，并将其存储在var6中。
        var6 = this.getLongVolatile(var1, var2);
    } while(!this.compareAndSwapLong(var1, var2, var6, var6 + var4));

    return var6;
}
```

这个循环过程被称为CAS（Compare-And-Swap）的自旋。

compareAndSet这个方法中直接调用了compareAndSwapLong方法，这个方法只运行一次，不会进行自旋操作。expect为修改前的预期值，update为修改后的预期值，首先通过this和valueOffset获取内存中的值，然后比较内存中的值对于修改前的预期值，判断有没有其他线程修改了该值，若没有，就将目标值修改为update，并返回true，若内存中的值与修改前的预期值不相同，那么说明这过程中其他线程修改了该值，那么compareAndSwapLong方法将不会对数据进行修改，将会返回false。

```java
public final boolean compareAndSet(long expect, long update) {
    return unsafe.compareAndSwapLong(this, valueOffset, expect, update);
}
```

```java
public static void main(String[] args) {
    AtomicLong num = new AtomicLong(100L);
    System.err.println(num.compareAndSet(200L, 300L));
    System.err.println(num);
    System.out.println(num.compareAndSet(100L, 300L));
    System.out.println(num);
}
```

## 自旋

具体解释一下自旋的机制：

- 在`do-while`循环中，首先通过`getLongVolatile`方法从对象`var1`的偏移量`var2`处安全地读取当前的`long`值，存储在局部变量`var6`中。
- 然后，使用`compareAndSwapLong`方法尝试原子地更新该值。这个方法会比较对象`var1`中偏移量`var2`处的值是否仍然等于`var6`。如果是，它会将该位置的值更新为`var6 + var4`。
- 如果`compareAndSwapLong`在检查时发现值已经被其他线程修改，它不会执行更新操作，而是返回`false`，这将导致循环继续，再次从头开始尝试。