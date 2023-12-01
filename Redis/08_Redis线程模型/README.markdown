# Redis线程模型

## 线程模型概述

1. **单线程架构**：Redis的主体操作，包括键值对的读取、写入和大部分命令的执行，都是在单个主线程中进行的。这种单线程模型简化了并发处理，减少了上下文切换和锁的开销，保证了高性能和数据的一致性。
2. **I/O多线程**：虽然Redis的数据操作是单线程的，但在处理网络I/O时，最新版本的Redis引入了I/O线程。这些线程负责接收客户端命令和发送响应，减轻了主线程的负担。重要的是，这种多线程机制是可配置的，用户可以根据自己的需求和硬件环境调整I/O线程的数量。
3. **持久化操作**：在进行RDB快照或AOF重写的时候，Redis会使用子进程来处理这些耗时的磁盘操作。使用子进程的好处是可以利用操作系统的写时复制（copy-on-write）技术，避免在持久化过程中阻塞主线程。
4. **其它后台操作**：例如过期键的清理、集群的故障转移等，这些也是在单独的线程或进程中进行，不干扰主线程的命令执行。

## Redis中IO线程的相关配置

| 配置项                  | 描述                                 |
| ----------------------- | ------------------------------------ |
| io-threads 4            | 当前服务器的CPU为4核4线程            |
| io-threads-do-reads yes | 在进行数据读取阶段的时候使用工作线程 |

启动redis服务器查看redis的线程数

```
redis-server /usr/local/redis/conf/redis.cong
```

redis线程数显示

```
├─redis-server,1649
│   ├─{redis-server},1650
│   ├─{redis-server},1651
│   ├─{redis-server},1652
│   └─{redis-server},1653
```

这个时候主进程下面实际上由4个线程，其中一个为Redis的主线程，对外直接提供服务，除了这一操作之后还会存在有AOF磁盘同步、关闭文件描述符以及惰性删除三个附加的子线程。

修改配置：

```
io-threads 4
io-threads-do-reads yes
```

重启redis服务器：

```
kill -9 1649
redis-server /usr/local/redis/conf/redis.conf
```

查看线程数：

```
pstree -apnh
```

显示：

```
  └─redis-server,1694
      ├─{redis-server},1695
      ├─{redis-server},1696
      ├─{redis-server},1697
      ├─{redis-server},1698
      ├─{redis-server},1699
      ├─{redis-server},1700
      ├─{redis-server},1701
      └─{redis-server},1702
```

可以看到，Redis的主进程中多了三个用于用于网络请求接收的线程。