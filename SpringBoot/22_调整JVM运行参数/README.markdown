# 调整JVM的运行参数

## JVM设置与系统参数

- `com.zhouyf.action.JVMAction`

```java
package com.zhouyf.action;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

@RestController
@RequestMapping("/jvm/*")
public class JVMAction {
    @GetMapping("show")
    public List<String> getJVMArgument(){
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getInputArguments();
    }
}
```

- 获取参数

```
http://localhost:8080/jvm/show
```

```json
[
  "-XX:TieredStopAtLevel=1",
  "-Xverify:none",
  "-Dspring.output.ansi.enabled=always",
  "-Dcom.sun.management.jmxremote",
  "-Dspring.jmx.enabled=true",
  "-Dspring.liveBeansView.mbeanDomain",
  "-Dspring.application.admin.enabled=true",
  "-Dmanagement.endpoints.jmx.exposure.include=*",
  "-javaagent:D:\\IntelliJ IDEA 2023.2.2\\lib\\idea_rt.jar=63974:D:\\IntelliJ IDEA 2023.2.2\\bin",
  "-Dfile.encoding=UTF-8"
]
```

1. **`-XX:TieredStopAtLevel=1`**: 这是一个高级JIT编译器选项。它告诉JVM在某一级别停止分层编译。这通常用于加速应用的启动时间。

2. **`-Xverify:none`**: 此选项禁用类字节码的验证过程。这可以提高启动速度，但可能会增加运行时错误的风险。

3. **`-Dspring.output.ansi.enabled=always`**: 这是Spring Boot的系统属性，它允许控制台输出始终使用ANSI编码，这可以产生彩色的日志输出。

4. **`-Dcom.sun.management.jmxremote`**: 允许使用JMX远程管理和监控Java应用。

5. **`-Dspring.jmx.enabled=true`**: 这是Spring Boot的系统属性，启用JMX支持。

6. **`-Dspring.liveBeansView.mbeanDomain`**: 这与Spring的JMX功能有关，用于指定MBean的域。

7. **`-Dspring.application.admin.enabled=true`**: 启用Spring Boot Admin功能。

8. **`-Dmanagement.endpoints.jmx.exposure.include=*`**: 这是Spring Boot的属性，它控制哪些JMX端点是可见的。星号表示所有端点都是可见的。

9. **`-javaagent:D:\IntelliJ IDEA 2023.2.2\lib\idea_rt.jar=63866:D:\IntelliJ IDEA 2023.2.2\bin`**: 这指定了Java代理的路径，通常用于IDE（在这种情况下是IntelliJ IDEA）的某些功能，如调试和性能分析。

10. **`-Dfile.encoding=UTF-8`**: 设置JVM的默认文件编码为UTF-8。

## 内存使用参数

`JVMAction`添加显示内存分配和使用情况的控制器方法

```java
package com.zhouyf.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.util.List;

@RestController
@RequestMapping("/jvm/*")
public class JVMAction {
    @GetMapping(value = "show", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getJVMArgument() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getInputArguments();
    }

    @GetMapping(value = "memory-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemoryInfo getMemory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        return new MemoryInfo(
                heapMemoryUsage.getInit(),
                heapMemoryUsage.getUsed(),
                heapMemoryUsage.getCommitted(),
                heapMemoryUsage.getMax(),
                nonHeapMemoryUsage.getInit(),
                nonHeapMemoryUsage.getUsed(),
                nonHeapMemoryUsage.getCommitted(),
                nonHeapMemoryUsage.getMax()
        );
    }

    @Data
    @AllArgsConstructor
    public static class MemoryInfo {
        public long heapInit;
        public long heapUsed;
        public long heapCommitted;
        public long heapMax;
        public long nonHeapInit;
        public long nonHeapUsed;
        public long nonHeapCommitted;
        public long nonHeapMax;


    }
}
```

- 返回的JSON信息

```
{
  "heapCommitted": 92274688,
  "heapInit": 534773760,
  "heapMax": 8547991552,
  "heapUsed": 18575776,
  "nonHeapCommitted": 56688640,
  "nonHeapInit": 5111808,
  "nonHeapMax": -1,
  "nonHeapUsed": 53049040
}
```

分析Spring Boot程序的内存使用情况：

1. **堆内存 (Heap Memory)**：
   - **heapInit (初始大小)**: 534,773,760 字节，约为 534.8 MB。这是JVM启动时为堆分配的内存。
   - **heapUsed (当前使用)**: 18,575,776 字节，约为 18.6 MB。这是当前被应用程序使用的堆内存。
   - **heapCommitted (已提交)**: 92,274,688 字节，约为 92.3 MB。这是保证给JVM的堆内存，已被分配出来但未必都在使用。
   - **heapMax (最大值)**: 8,547,991,552 字节，约为 8.55 GB。这是JVM配置的最大堆内存，堆使用量不会超过这个值。

2. **非堆内存 (Non-Heap Memory)**：
   - **nonHeapInit (初始大小)**: 5,111,808 字节，约为 5.1 MB。这是JVM启动时为非堆分配的内存。
   - **nonHeapUsed (当前使用)**: 53,049,040 字节，约为 53 MB。这是当前被应用程序使用的非堆内存。
   - **nonHeapCommitted (已提交)**: 56,688,640 字节，约为 56.7 MB。这是保证给JVM的非堆内存，已被分配出来但未必都在使用。
   - **nonHeapMax (最大值)**: -1。这意味着非堆内存的最大值没有明确的限制。

**分析**：

- **堆内存**：Spring Boot应用目前只使用了很小部分的可用堆内存（18.6 MB / 8.55 GB）。这表明应用在当前的负载下运行得很好，有大量的空闲堆内存可以应对未来的需求。

- **非堆内存**：非堆内存主要用于JVM的内部操作，如类的元数据、线程栈和JVM内部结构。当前使用了53 MB，而已提交的大小为56.7 MB。这表示应用在非堆部分也有足够的内存空间。

## 调整JVM的运行参数

| 参数                          | 描述                                                         |
| ----------------------------- | ------------------------------------------------------------ |
| `-Xms<size>`                  | 设置初始堆大小。例如，`-Xms256m` 将初始堆大小设置为256兆字节。 |
| `-Xmx<size>`                  | 设置最大堆大小。例如，`-Xmx1g` 将最大堆大小设置为1吉字节。   |
| `-Xss<size>`                  | 设置线程栈大小。例如，`-Xss1m` 将线程栈大小设置为1兆字节。   |
| `-XX:MetaspaceSize=<size>`    | 设置元空间的初始大小（非堆内存区域，用于存储类的元数据）。   |
| `-XX:MaxMetaspaceSize=<size>` | 设置元空间的最大大小。                                       |
| `-XX:PermSize=<size>`         | 设置永久代的初始大小（在Java 8之前的版本中使用，替换为Metaspace）。 |
| `-XX:MaxPermSize=<size>`      | 设置永久代的最大大小（在Java 8之前的版本中使用）。           |
| `-XX:+UseCompressedOops`      | 启用指针压缩，这通常可以提高应用性能和降低内存使用。         |
| `-XX:+UseG1GC`                | 使用G1垃圾收集器。                                           |
| `-XX:+UseParallelGC`          | 使用并行垃圾收集器。                                         |
| `-XX:+UseConcMarkSweepGC`     | 使用CMS垃圾收集器。                                          |
| `-XX:SurvivorRatio=<ratio>`   | 设置新生代中Eden区与Survivor区的大小比例。例如，`-XX:SurvivorRatio=8` 表示Eden区的大小是一个Survivor区的8倍。 |
| `-XX:NewRatio=<ratio>`        | 设置新生代（包括Eden和两个Survivor区）与老年代的比例。例如，`-XX:NewRatio=2` 表示新生代的总大小是老年代大小的1/3。 |
| `-Xlog:gc`                    | 根据需求选择是否开启GC日志跟踪                               |

### 在IDEA中进行配置JVM参数

- 修改运行选项

<img src="assets/image-20231026125045475.png" alt="image-20231026125045475" style="zoom:67%;" />

- 添加虚拟机选项

<img src="assets/image-20231026125108755.png" alt="image-20231026125108755" style="zoom:50%;" />

![image-20231026125504513](assets/image-20231026125504513.png)

- 启动SpringBoot

```
http://localhost:8080/jvm/memory-info
```

![image-20231026125536042](assets/image-20231026125536042.png)

```json
{
  "heapCommitted": 645922816,
  "heapInit": 536870912,
  "heapMax": 17179869184,
  "heapUsed": 54525952,
  "nonHeapCommitted": 54919168,
  "nonHeapInit": 5111808,
  "nonHeapMax": -1,
  "nonHeapUsed": 51433912
}
```

可以看到最大堆内存已经被修改为16g

### JAR文件形式调整JVM参数

-  将`microboot-web`项目打成JAR包

`microboot-web->build.gradle`

```groovy
bootJar{
    archiveClassifier = 'zhou'
    archiveBaseName = 'zhouyfboot'
    archiveVersion = '1.0.1'
    mainClassName = 'com.zhouyf.MyApplication'
}
```

执行bootJar任务，生成JAR文件

- `SpringBoot\22_调整JVM运行参数\microboot\microboot-web\build\libs`中启动SpringBoot程序

原始参数

```
java -jar zhouyfboot-1.0.1-zhou.jar
```

回显数据：

```
http://localhost:8080/jvm/memory-info
```

```json
{
  "heapCommitted": 534773760,
  "heapInit": 534773760,
  "heapMax": 8547991552,
  "heapUsed": 115343360,
  "nonHeapCommitted": 65142784,
  "nonHeapInit": 7667712,
  "nonHeapMax": -1,
  "nonHeapUsed": 62516744
}
```

调整参数：

```
java -jar -Xmx16g zhouyfboot-1.0.1-zhou.jar
```

回显数据：

```
http://localhost:8080/jvm/memory-info
```

```json
{
  "heapCommitted": 536870912,
  "heapInit": 536870912,
  "heapMax": 17179869184,
  "heapUsed": 117440512,
  "nonHeapCommitted": 65208320,
  "nonHeapInit": 7667712,
  "nonHeapMax": -1,
  "nonHeapUsed": 62566800
}
```

