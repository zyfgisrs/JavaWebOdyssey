# 异步执行命令

在Spring Data Redis中，默认的执行模式是同步（Synchronous）。这意味着当你使用Spring Data Redis的模板类，如`StringRedisTemplate`或`RedisTemplate`，进行操作时，这些操作是阻塞的，并且会等待直到操作完成。

然而，Spring Data Redis也提供了异步（Asynchronous）和响应式（Reactive）的支持：

1. **异步执行：** 通过使用`RedisTemplate`的`execute`方法和传递一个`RedisCallback`或`SessionCallback`，你可以异步地执行Redis命令。这允许在不阻塞当前线程的情况下执行Redis操作。但是，这种方式的异步性质并不是基于事件循环或非阻塞IO，而是基于传统的多线程模型。
2. **响应式执行：** Spring Data Redis的响应式编程支持是通过`Lettuce`驱动程序实现的。`Lettuce`是一个可伸缩的线程安全的Redis客户端，支持同步、异步和响应式模式。要在Spring中使用响应式Redis，你需要使用`ReactiveRedisTemplate`。这种方式允许你利用非阻塞IO和响应式编程模型，从而提高性能并减少资源使用。

## 范例

**boot-redis项目**

```java
package com.zhouyf.server;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncRedisService {
    final RedisTemplate<String, String> redisTemplate;


    public AsyncRedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public CompletableFuture<String> asyncSetAndGet(String key, String value) {
        return CompletableFuture.supplyAsync(() -> {
            // 使用 SessionCallback 来确保操作在同一个Redis连接中执行
            List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(RedisOperations operations) {
                    operations.multi();//开始一个Redis事务的命令
                    operations.opsForValue().set(key, value);
                    operations.opsForValue().get(key);
                    // 命令被添加到队列中，并不会被立即执行
                    return operations.exec();//使用exec()命令来原子性执行事务
                }
            });

            // 从事务结果中获取并返回最后一个操作的结果
            return (String) txResults.get(txResults.size() - 1);
        });
    }
}
```

注：`operations.multi()`是用于开始一个Redis事务的命令。当你在使用`RedisTemplate`或其操作类(`RedisOperations`)时，调用`multi()`会告诉Redis开始一个新的事务。在事务中，所有后续的命令都不会立即执行，而是被放入一个队列中。

在Redis中，事务的工作流程通常如下：

1. **开始事务：** 使用`multi()`命令开始一个事务。在这个阶段，Redis会记录之后的所有命令，但不会立即执行它们。
2. **命令队列：** 在调用`multi()`之后，所有发送到Redis的命令都不会立即执行，而是被添加到队列中。
3. **执行事务：** 使用`exec()`命令来执行事务。当这个命令被调用时，Redis会原子性地执行所有队列中的命令。这意味着要么所有命令都成功执行，要么在遇到错误时全部不执行。
4. **放弃事务：** 如果需要放弃事务中的所有命令，可以使用`discard()`命令。这会清空事务队列并退出事务模式，之前队列中的所有命令都不会被执行。

## RedisCallback和SessionCallback

在Spring Data Redis中，`RedisCallback`和`SessionCallback`都用于在低级别上执行Redis操作，但它们之间有一些关键的区别：

1. **`RedisCallback`**:
   - `RedisCallback`接口用于执行一个或多个Redis命令，并允许直接访问底层的Redis连接。
   - 使用`RedisCallback`时，每个操作都是独立的。这意味着如果你在`RedisCallback`中执行多个命令，它们不会自动被包装在一个事务中。
   - `RedisCallback`更适合于单个或不需要事务支持的操作，以及当你需要更细粒度的控制Redis连接时。

2. **`SessionCallback`**:
   - `SessionCallback`接口用于执行一系列操作，这些操作通常是作为一个逻辑单元一起执行的，例如在一个事务中。
   - 当使用`SessionCallback`时，所有操作都是在同一个Redis连接上执行的，这非常重要，特别是在事务或流水线（pipelining）操作中。
   - `SessionCallback`适用于需要将多个命令作为一个单一的会话或事务执行的场景。

总的来说，选择`RedisCallback`还是`SessionCallback`取决于你的具体需求。如果你的操作不需要事务支持或者你想要更精确地控制Redis连接，那么`RedisCallback`可能是更好的选择。如果你需要在一个事务中执行多个操作或者需要确保所有操作都在同一个连接上执行，那么`SessionCallback`将是更合适的选择。