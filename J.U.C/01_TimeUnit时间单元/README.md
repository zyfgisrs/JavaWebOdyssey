# TimeUnit时间单元

`TimeUnit` 是 Java 中的一个枚举类型，属于 `java.util.concurrent` 包。它为时间单位提供了多种转换和操作的方法。`TimeUnit` 枚举的主要目的是为不同的时间单位提供统一的接口，以方便在并发编程中处理时间相关的操作。它支持以下时间单位：

1. **NANOSECONDS（纳秒）**：表示最小的时间单位。
2. **MICROSECONDS（微秒）**：比纳秒大的单位。
3. **MILLISECONDS（毫秒）**：常用于网络通信和定时任务。
4. **SECONDS（秒）**：秒级时间单位。
5. **MINUTES（分钟）**：分钟级时间单位。
6. **HOURS（小时）**：小时级时间单位。
7. **DAYS（天）**：天级时间单位。

`TimeUnit` 提供了一些重要的方法，如：

- **convert(long duration, TimeUnit unit)**：将时间从一个单位转换为另一个单位。例如，可以将分钟转换为秒。
- **toNanos(long duration)**、**toMicros(long duration)**、**toMillis(long duration)**、**toSeconds(long duration)**、**toMinutes(long duration)**、**toHours(long duration)**、**toDays(long duration)**：这些方法将给定的时间转换为枚举中指定的单位。
- **sleep(long timeout)**：使当前线程休眠指定的时间。
- **timedWait(Object obj, long timeout)**：让对象在同步代码块内等待一定时间。
- **timedJoin(Thread thread, long timeout)**：使线程等待另一个线程指定时间的完成。

使用 `TimeUnit` 的好处包括代码的清晰性和易读性。它允许开发者以更直观的方式处理不同的时间单位，使得并发和多线程编程中的时间管理更加简单和直接。

## 时间转换

将小时转换为秒：

```java
public class TimeUnit_ {
    public static void main(String[] args) {
        long hour = 1;
        long seconds = TimeUnit.SECONDS.convert(hour, TimeUnit.HOURS);
        System.out.println("转换的秒数: "+seconds); //3600
    }
}
```

## 计算日期

```java
long current = System.currentTimeMillis();
long after = current + TimeUnit.MILLISECONDS.convert(180, TimeUnit.DAYS);
String afterDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(after));
System.out.println(afterDate);
```

## 休眠

```java
for(int i = 0; i < 5; i++){
    try {
        TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    } finally {
        System.out.println("hello,world");
    }
}
```

