# 时间单位

1. **纳秒 (Nanoseconds)**:
   - 最小的时间单位。
   - 1秒 = 1,000,000,000纳秒 (10^9)。
   - 主要用于高精度时间测量，如系统计时、性能分析。
2. **微秒 (Microseconds)**:
   - 1微秒 = 1,000纳秒 (10^3)。
   - 在某些高精度应用中使用，但不如纳秒普遍。
3. **毫秒 (Milliseconds)**:
   - 在Java中非常常用的时间单位。
   - 1秒 = 1,000毫秒 (10^3)。
   - 常用于日期时间计算、延时、定时任务。
4. **秒 (Seconds)**:
   - 基础的时间单位。
   - 在多种编程和现实世界场景中广泛使用。
5. **分钟 (Minutes)**:
   - 1分钟 = 60秒。
   - 常用于表示较长的时间跨度。
6. **小时 (Hours)**:
   - 1小时 = 60分钟 = 3,600秒。
   - 用于较长时间跨度的计算和表示。
7. **天 (Days)**:
   - 1天 = 24小时 = 1,440分钟 = 86,400秒。
   - 用于日期和长时间跨度的计算。

```java
NANOSECONDS(TimeUnit.NANO_SCALE),
/**
 * Time unit representing one thousandth of a millisecond.
 */
MICROSECONDS(TimeUnit.MICRO_SCALE),
/**
 * Time unit representing one thousandth of a second.
 */
MILLISECONDS(TimeUnit.MILLI_SCALE),
/**
 * Time unit representing one second.
 */
SECONDS(TimeUnit.SECOND_SCALE),
/**
 * Time unit representing sixty seconds.
 * @since 1.6
 */
MINUTES(TimeUnit.MINUTE_SCALE),
/**
 * Time unit representing sixty minutes.
 * @since 1.6
 */
HOURS(TimeUnit.HOUR_SCALE),
/**
 * Time unit representing twenty four hours.
 * @since 1.6
 */
DAYS(TimeUnit.DAY_SCALE);
```

# LocalDate

> 表示没有时间或时区的日期（年-月-日）

`LocalDate` 是 Java 8中引入的`java.time`包的一部分，专门用于表示不含时间和时区信息的日期。以下是`LocalDate`类的一些主要API及其用法：

## 创建和获取 `LocalDate`

1. **当前日期**:
   ```java
   LocalDate today = LocalDate.now();
   ```

2. **指定日期**:
   ```java
   LocalDate specificDate = LocalDate.of(2023, Month.JANUARY, 1); // 使用Month枚举
   LocalDate specificDate2 = LocalDate.of(2023, 1, 1); // 直接使用整数值
   ```

3. **解析字符串为日期**:
   ```java
   LocalDate dateFromString = LocalDate.parse("2023-01-01"); // 默认格式为 yyyy-MM-dd
   ```

## 检查信息

1. **获取年、月、日**:
   ```java
   int year = today.getYear();
   Month month = today.getMonth();
   int day = today.getDayOfMonth();
   ```

2. **检查是否是闰年**:
   ```java
   boolean isLeapYear = today.isLeapYear();
   ```

## 操作日期

1. **增加或减少日期的部分**:
   ```java
   LocalDate tomorrow = today.plusDays(1);
   LocalDate lastMonth = today.minusMonths(1);
   ```

2. **使用 TemporalAdjusters**:
   
   ```java
   LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
   LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfYear());
   ```
   
   
   
3. **设置特定的日期部分**:
   
   ```java
   LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
   LocalDate in2025 = today.withYear(2025);
   ```

## 比较日期

1. **比较两个日期**:
   ```java
   boolean isBefore = today.isBefore(specificDate);
   boolean isAfter = today.isAfter(specificDate);
   ```

2. **计算两个日期之间的差异**:
   ```java
   Period period = today.until(specificDate);
   long daysBetween = ChronoUnit.DAYS.between(today, specificDate);
   ```

## 格式化和解析

1. **格式化为字符串**:
   ```java
   String formattedDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
   ```

2. **从字符串解析**:
   ```java
   LocalDate dateFromString = LocalDate.parse("01/01/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
   ```

# TemporalAdjusters

`TemporalAdjusters` 是 Java 8及以后版本中 `java.time` 包的一部分，用于提供一组实用的静态方法，这些方法返回 `TemporalAdjuster` 对象，它们能够对日期-时间对象进行复杂的调整操作。`TemporalAdjuster` 是一个功能接口（functional interface），表示一个策略模式的实现，用于调整日期时间对象。

## 主要功能

`TemporalAdjusters` 类的主要功能是提供一系列常用的日期调整器，可以用于执行诸如查找每月的第一天、最后一天、下一个星期一等常见的日期操作。这些方法简化了对日期的复杂计算，使代码更加清晰和易于维护。

## 常用方法

以下是一些`TemporalAdjusters`中的常见方法：

1. **firstDayOfMonth()**:
   返回当月的第一天。
   
   ```java
   LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
   ```
   
2. **lastDayOfMonth()**:
   返回当月的最后一天。
   ```java
   LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
   ```

3. **firstDayOfNextMonth()**:
   返回下个月的第一天。
   ```java
   LocalDate firstDayNextMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
   ```

4. **firstDayOfYear()**:
   返回当年的第一天。
   ```java
   LocalDate firstDayOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
   ```

5. **lastDayOfYear()**:
   返回当年的最后一天。
   ```java
   LocalDate lastDayOfYear = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
   ```

6. **next(DayOfWeek)**:
   返回下一个指定星期几。
   ```java
   LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
   ```

7. **nextOrSame(DayOfWeek)**:
   返回下一个指定星期几，如果今天就是那天，则返回今天。
   ```java
   LocalDate nextOrSameMonday = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
   ```

8. **previous(DayOfWeek)**:
   返回上一个指定星期几。
   ```java
   LocalDate previousMonday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
   ```

## 自定义调整器

除了使用预定义的调整器外，您还可以创建自定义的`TemporalAdjuster`，通过实现`TemporalAdjuster`接口来处理更特定的日期调整需求。这为日期时间的操作提供了极大的灵活性和扩展性。

使用 `TemporalAdjusters` 可以大大简化日期计算的复杂性，使日期相关的操作更加直观和易于管理。

# LocalTime

`LocalTime` 是 Java 8中引入的 `java.time` 包的一部分，专门用于表示不含日期和时区信息的时间。以下是 `LocalTime` 类的一些主要API及其使用方法：

## 创建和获取 `LocalTime`

1. **当前时间**:
   ```java
   LocalTime now = LocalTime.now();
   ```

2. **指定时间**:
   ```java
   LocalTime specificTime = LocalTime.of(13, 45); // 13:45
   LocalTime specificTimeWithSeconds = LocalTime.of(13, 45, 30); // 13:45:30
   ```

3. **解析字符串为时间**:
   ```java
   LocalTime timeFromString = LocalTime.parse("13:45:30"); // 默认格式为 HH:mm:ss
   ```

## 检查信息

1. **获取小时、分钟、秒和纳秒**:
   ```java
   int hour = now.getHour();
   int minute = now.getMinute();
   int second = now.getSecond();
   int nano = now.getNano();
   ```

## 操作时间

1. **增加或减少时间的部分**:
   ```java
   LocalTime plusHours = now.plusHours(1);
   LocalTime minusMinutes = now.minusMinutes(30);
   ```

2. **设置特定的时间部分**:
   ```java
   LocalTime withHour = now.withHour(10);
   LocalTime withMinute = now.withMinute(30);
   ```

## 比较时间

1. **比较两个时间**:
   ```java
   boolean isBefore = now.isBefore(specificTime);
   boolean isAfter = now.isAfter(specificTime);
   ```

## 其他操作

1. **转换为 `SecondOfDay` 和 `NanoOfDay`**:
   ```java
   long secondOfDay = now.toSecondOfDay();
   long nanoOfDay = now.toNanoOfDay();
   ```

2. **格式化和解析**:
   ```java
   LocalTime time1 = 
       LocalTime.parse("07-07-12", DateTimeFormatter.ofPattern("HH-mm-ss"));
   String strTime = time1.format(DateTimeFormatter.ofPattern("HH mm ss"));
   ```

# Duration

`Duration` 是 Java 8中引入的 `java.time` 包的一部分，用于表示时间量，主要以秒和纳秒为单位。这个类非常适合用来处理持续时间、间隔或者时间差等场景。以下是 `Duration` 类的一些主要API及其使用方法：

## 创建 `Duration`

1. **直接创建**:
   ```java
   Duration threeMinutes = Duration.ofMinutes(3); // 3分钟
   Duration tenSeconds = Duration.ofSeconds(10); // 10秒
   Duration milliseconds = Duration.ofMillis(1000); // 1000毫秒
   Duration nanoseconds = Duration.ofNanos(1000000); // 1000000纳秒
   ```

2. **解析字符串创建**:
   ```java
   Duration fromString = Duration.parse("PT1H2M3.004S"); // 1小时2分钟3.004秒
   ```

3. **两个时间点之间的持续时间**:
   ```java
   Instant start = Instant.now();
   Instant end = Instant.now(); // 假设在这之后有耗时操作
   Duration durationBetween = Duration.between(start, end);
   ```

## 检查信息

1. **获取持续时间的部分**:
   ```java
   long seconds = durationBetween.getSeconds();
   int nano = durationBetween.getNano();
   ```

2. **检查是否为零或负**:
   ```java
   boolean isZero = durationBetween.isZero();
   boolean isNegative = durationBetween.isNegative();
   ```

## 操作 `Duration`

1. **增加或减少持续时间**:
   
   ```java
   Duration addedDuration = durationBetween.plus(Duration.ofMinutes(2));
   Duration subtractedDuration = durationBetween.minus(Duration.ofMinutes(1));
   ```
   
2. **乘以或除以标量**:
   ```java
   Duration doubled = durationBetween.multipliedBy(2);
   Duration halved = durationBetween.dividedBy(2);
   ```

## 其他操作

1. **转换为特定单位**:
   ```java
   long totalMillis = durationBetween.toMillis();
   long totalSeconds = durationBetween.getSeconds();
   ```

2. **格式化输出**:
   由于 `Duration` 是基于时间的量度，它没有直接的格式化方法。但可以将其转换为秒、毫秒等，然后按需格式化。
   ```java
   String formatted = String.format("%d seconds", durationBetween.getSeconds());
   ```

3. **与 `Temporal` 对象的交互**:
   `Duration` 可以与时间对象（如 `LocalDateTime`、`Instant` 等）相加或相减。
   ```java
   LocalDateTime dateTime = LocalDateTime.now();
   LocalDateTime dateTimeAfterDuration = dateTime.plus(durationBetween);
   ```

## 注意事项

- `Duration` 用于表示时间的量度，它不同于 `Period`，后者用于表示以年、月、日为单位的日期量度。
- `Duration` 是不可变且线程安全的。
- 在处理较大的时间量度时，需要注意长整型的溢出问题。

`Period` 是 Java 8 中引入的 `java.time` 包的一部分，用于表示基于日期的时间量，如年、月和日。它专门用于处理日期之间的差异。以下是 `Period` 类的一些主要API及其使用方法：

# Period

## 创建 `Period`

1. **直接创建**:
   ```java
   Period tenDays = Period.ofDays(10); // 10天
   Period threeWeeks = Period.ofWeeks(3); // 3周
   Period twoYearsSixMonths = Period.of(2, 6, 0); // 2年6个月
   ```

2. **解析字符串创建**:
   ```java
   Period fromString = Period.parse("P2Y6M1D"); // 2年6个月1天
   ```

3. **两个日期之间的周期**:
   ```java
   LocalDate startDate = LocalDate.of(2023, 1, 1);
   LocalDate endDate = LocalDate.of(2023, 12, 31);
   Period periodBetween = Period.between(startDate, endDate);
   ```

## 检查信息

1. **获取周期的部分**:
   ```java
   int days = periodBetween.getDays();
   int months = periodBetween.getMonths();
   int years = periodBetween.getYears();
   ```

2. **检查是否为零或负**:
   ```java
   boolean isZero = periodBetween.isZero();
   boolean isNegative = periodBetween.isNegative();
   ```

## 操作 `Period`

1. **增加或减少周期的部分**:
   ```java
   Period addedPeriod = periodBetween.plusDays(10);
   Period subtractedPeriod = periodBetween.minusMonths(2);
   ```

2. **乘以标量**:
   ```java
   Period doubled = periodBetween.multipliedBy(2);
   ```

## 其他操作

1. **转换为特定单位**:
   由于 `Period` 是日期的量度，它没有直接转换为小时或秒等时间单位的方法。但可以计算总的天数、月数或年数。
   ```java
   long totalMonths = periodBetween.toTotalMonths();
   ```

2. **与 `LocalDate` 对象的交互**:
   `Period` 可以与日期对象（如 `LocalDate`）相加或相减。
   ```java
   LocalDate laterDate = startDate.plus(periodBetween);
   LocalDate earlierDate = endDate.minus(periodBetween);
   ```

## 注意事项

- `Period` 用于表示基于日期的时间量度，与基于时间的 `Duration` 不同。
- `Period` 是不可变的，任何操作都会返回一个新的 `Period` 实例。
- `Period` 不包含时区信息，仅处理日期。