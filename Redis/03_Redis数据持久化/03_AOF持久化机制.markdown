以下是对您提供的Markdown文档的修改和格式化：

---

# AOF (Append Only File) 持久化

## 目录

- [1. AOF 持久化定义](#1-aof-持久化定义)
- [2. AOF 的优点与缺点](#2-aof-的优点与缺点)
  - [2.1 优点](#21-优点)
  - [2.2 缺点](#22-缺点)
- [3. AOF 的工作原理](#3-aof-的工作原理)
- [4. AOF 重写](#4-aof-重写)
- [5. AOF 配置和策略](#5-aof-配置和策略)
- [6. AOF数据恢复案例](#6-aof数据恢复案例)

---

## 1. AOF 持久化定义

AOF 持久化记录服务器接收到的每一个写操作。这些操作在服务器启动时可以再次播放，从而重建原始数据集。命令使用与 Redis 协议相同的格式进行记录。

## 2. AOF 的优点与缺点

### 2.1 优点

- **持久性更强**：AOF 持久化记录了服务器接收到的每一个写操作，这意味着即使在 Redis 服务器意外宕机的情况下，只要 AOF 文件没有损坏，数据可以完整地恢复。
- **恢复数据更完整**：与 RDB 相比，AOF 的数据恢复能力更强。因为 AOF 记录了每一个写操作，所以在恢复数据时，可以确保数据的完整性。
- **人类可读的格式**：AOF 文件的内容是以 Redis 协议的格式记录的，这意味着它是人类可读的。这使得在需要时，开发者可以手动查看和编辑 AOF 文件。
- **更灵活的同步策略**：AOF 提供了多种 `fsync` 策略，允许用户根据自己的需求选择数据同步的频率，从而在数据安全性和性能之间做出权衡。
- **自动错误修复**：如果 AOF 文件在某处出现错误或损坏，Redis 在启动时可以尝试自动修复这些错误，从而避免数据丢失。
- **增量备份**：与 RDB 的全量备份相比，AOF 采用增量备份的方式，只记录发生变化的数据，这可以减少备份的大小和备份所需的时间。

### 2.2 缺点

- AOF 文件通常比相同数据集的 RDB 文件大。
- 根据具体的 fsync 策略，AOF 可能比 RDB 慢。

## 3. AOF 的工作原理

### 如何记录命令

当 Redis 完成一个写操作命令时：

1. **命令追加**：Redis 将这个命令追加到 `server.aof_buf` 缓冲区。
2. **写入内核缓冲区**：通过 `write()` 系统调用，Redis 将 `server.aof_buf` 缓冲区的内容写入到 AOF 文件。但此时，数据并不直接写入硬盘，而是先写入到操作系统的内核缓冲区，也叫 `page cache`。
3. **硬盘同步**：数据什么时候从内核缓冲区同步到硬盘是由操作系统内核决定的。

### 重启时的数据恢复

1. **加载 AOF 文件**：Redis 会检查是否存在 AOF 文件。如果存在，它会开始加载该文件。
2. **命令解析**：Redis 会逐行读取 AOF 文件中的内容。每一行都是一个已经执行过的写命令，这些命令都是以 Redis 协议格式存储的。
3. **命令执行**：对于 AOF 文件中的每一个命令，Redis 会再次执行它。这样，Redis 就可以重新构建出宕机前的数据状态。
4. **错误处理**：如果在 AOF 文件中的命令执行过程中发生错误（例如，文件损坏或命令格式不正确），Redis 会尝试修复这个错误。如果不能修复，Redis 会启动失败并给出相应的错误信息。
5. **AOF 重写**：如果在 Redis 重启之前已经启用了 AOF 重写功能，那么在数据恢复过程后，Redis 可能会触发一个 AOF 重写过程，以优化和压缩 AOF 文件的大小。
6. **恢复完成**：一旦 AOF 文件中的所有命令都被重新执行，Redis 的数据状态就会恢复到宕机前的状态。此时，Redis 会继续正常运行，并接受客户端的命令请求。

## 4. AOF 重写

- 为什么需要 AOF 重写？
- AOF 重写的工作原理

## 5. AOF 配置和策略

### fsync 策略

默认配置中默认不开启AOF，因此我们需要修改配置文件。

```shell
vi /usr/local/redis/conf/redis.conf
```

相关的配置：

|              配置               |       配置说明        |
| :-----------------------------: | :-------------------: |
|          appendonly no          | 开启AOF序列化备份机制 |
| appendfilename "appendonly.aof" |   定义AOF文件的名称   |
|  appenddirname "appendonlydir"  | 定义AOF的文件保存目录 |
|      appendfsync everysec       | AOF自动持久化触发机制 |

在 配置文件中的 `appendfsync` 配置项可以有以下 3 种参数可填：

- **always**：每次写操作命令执行完后，同步将 AOF 日志数据写回硬盘。
- **everysec**：每次写操作命令执行完后，先将命令写入到 AOF 文件的内核缓冲区，然后每隔一秒将缓冲区里的内容写回到硬盘。
- **no**：不由 Redis 控制写回硬盘的时机，转交给操作系统控制写回的时机，也就是每次写操作命令执行完后，先将命令写入到 AOF 文件的内核缓冲区，再由操作系统决定何时将缓冲区内容写回硬盘。

选择策略：

- 如果要高性能，就选择 No 策略；
- 如果要高可靠，就选择 Always 策略；
- 如果允许数据丢失一点，但又想性能高，就选择 Everysec 策略。

### AOF 重写的触发条件

1. **文件大小**：当 AOF 文件的大小超过了预设的阈值时，可以触发 AOF 重写。
2. **手动触发**：通过执行 `BGREWRITEAOF` 命令，管理员可以手动触发 AOF 重写。
3. **启动时自动触发**：如果 Redis 在启动时检测到 AOF 文件过大，它可能会在启动时自动进行 AOF

 重写。
4. **配置触发**：在 Redis 的配置文件中，可以设置 `auto-aof-rewrite-percentage` 和 `auto-aof-rewrite-min-size` 两个参数来自动触发 AOF 重写。
5. **其他策略**：除了上述常见的触发条件，还可以基于其他策略或业务需求来触发 AOF 重写。

## 6. AOF数据恢复案例

### 6.1 修改配置

首先，我们需要修改Redis的配置文件以启用AOF持久化：

```shell
appendonly yes
appendfsync always
```

### 6.2 删除rdb数据

为了模拟数据损坏的情况，我们首先删除磁盘中的RDB数据：

```shell
/mnt/data/redis/dbcache# rm -r dump.rdb
```

然后启动Redis服务器：

```shell
redis-server /usr/local/redis/conf/redis.conf
```

### 6.3 查看数据目录

查看数据目录，可以看到以下文件结构：

```shell
root@redis-server:/mnt/data/redis/dbcache# tree
.
└── appendonlydir
    ├── appendonly.aof.1.base.rdb
    ├── appendonly.aof.1.incr.aof
    └── appendonly.aof.manifest
```

这些文件是Redis 7.0引入的混合持久化模式中的关键文件。它们相互配合，提供了一种结合了RDB和AOF两种持久化机制优点的数据存储方式。

1. **appendonly.aof.1.base.rdb**：
   - 这是一个 RDB 格式的文件，它包含了数据集的一个快照。
   - 它作为混合持久化的基础部分，提供了一个完整的数据集的时间点快照。
2. **appendonly.aof.1.incr.aof**：
   - 这是一个增量 AOF 格式的文件。
   - 它记录了自 `appendonly.aof.1.base.rdb` 快照生成后的所有写操作。
   - 通过结合 RDB 快照和这个增量 AOF 文件，可以恢复完整的数据集。
3. **appendonly.aof.manifest**：
   - 这是一个元数据文件，用于管理和跟踪混合持久化中的 RDB 和 AOF 文件。
   - 它包含了关于当前使用的 RDB 和 AOF 文件的信息，如文件名、大小、创建时间等。
   - Redis 使用这个文件来确定在重启时应该加载哪些文件以及如何加载它们。

### 6.4 登录Redis客户端并添加数据

登录Redis客户端：

```shell
redis-cli -h redis-server -p 6379 -a 12345
```

添加数据：

```shell
MSET set1 zyf set2 slp
```

### 6.5 查看增量数据文件

打开`appendonly.aof.1.incr.aof`，可以看到已经有了数据：

```shell
vi appendonly.aof.1.incr.aof
```

### 6.6 手动触发AOF重写

执行以下命令：

```shell
redis-server:6379> BGREWRITEAOF
```

再次打开`appendonly.aof.1.incr.aof`，可以看到数据已经被清空。

此时的文件结构如下，可以看到base文件编号已经被更新，说明新的操作数据已经被写入到rdb中

```
root@redis-server:/mnt/data/redis/dbcache/appendonlydir# tree
.
├── appendonly.aof.1.incr.aof
├── appendonly.aof.2.base.rdb
├── appendonly.aof.2.incr.aof
└── appendonly.aof.manifest
```

当执行 `BGREWRITEAOF` 命令进行 AOF 重写时，Redis 会采用混合持久化的方式。以下是这个过程的描述：

1. **创建新的 RDB 快照**：
   - 当 `BGREWRITEAOF` 命令被执行时，Redis 会在后台启动一个子进程。
   - 这个子进程首先会创建一个新的 RDB 快照，例如 `appendonly.aof.2.base.rdb`（数字可能会变，表示版本或序列号）。
2. **清空增量 AOF 文件**：
   - 与此同时，新的增量 AOF 文件，例如 `appendonly.aof.2.incr.aof`，会被创建，但它开始时是空的。
   - 因为所有当前的数据都已经被保存在新的 RDB 快照中，所以新的增量 AOF 文件开始时不包含任何数据。
3. **继续记录写命令**：
   - 在 AOF 重写过程中，主 Redis 进程仍然可以处理客户端请求。
   - 任何新的写命令都会被追加到新的增量 AOF 文件中，即 `appendonly.aof.2.incr.aof`。
4. **完成 AOF 重写**：
   - 一旦子进程完成了 RDB 快照的创建，它会替换旧的 AOF 文件。
   - 新的 RDB 快照和增量 AOF 文件现在成为了主要的数据存储文件。

### 6.7 数据备份与恢复

为了模拟数据恢复的过程，我们首先备份当前的数据：

```shell
cp -r /mnt/data/redis/dbcache/appendonlydir /mnt/data/back
```

然后删除所有数据文件：

```shell
rm -r /mnt/data/redis/dbcache/appendonlydir/*
```

重启Redis服务器：

```shell
redis-server /usr/local/redis/conf/redis.conf
```

Redis在启动时会尝试从AOF文件恢复数据，但由于AOF文件也被删除，所以Redis会创建新的AOF数据文件夹，并尝试从其中恢复数据。

删除新生成的AOF数据，将备份数据复制到AOF文件夹中

```shell
rm -r /mnt/data/redis/dbcache/appendonlydir/*
cp -r /mnt/data/back/* /mnt/data/redis/dbcache/appendonlydir/
```

重启redis服务：

```shell
redis-server /usr/local/redis/conf/redis.conf
```

输出日志：

```
3107:M 25 Sep 2023 15:42:48.661 * RDB is base AOF
3107:M 25 Sep 2023 15:42:48.661 * Done loading RDB, keys loaded: 4, keys expired: 0.
3107:M 25 Sep 2023 15:42:48.661 * DB loaded from base file appendonly.aof.2.base.rdb: 0.000 seconds
3107:M 25 Sep 2023 15:42:48.661 * DB loaded from append only file: 0.000 seconds
3107:M 25 Sep 2023 15:42:48.662 * Opening AOF incr file appendonly.aof.2.incr.aof on server start
3107:M 25 Sep 2023 15:42:48.662 * Ready to accept connections tcp
```

### 6.8 使用RDB文件恢复数据

为了使用RDB文件恢复数据，我们首先需要将备份的RDB文件复制到Redis的数据目录下，并重命名为`dump.rdb`：

```shell
cp /mnt/data/back/appendonly.aof.2.base.rdb /mnt/data/redis/dbcache/dump.rdb
```

然后修改Redis的配置文件，禁用AOF持久化：

```shell
appendonly no
```

重新启动Redis服务器：

```shell
redis-server /usr/local/redis/conf/redis.conf
```

此时，Redis会使用RDB文件恢复数据。

日志显示同样可以进行数据的恢复，但是如果不进行配置的修改`appendonly yes`，redis将会使用AOF进行恢复，redis服务器没有检测到有AOF文件夹中的数据，那么就会创建AOF数据的文件夹，就不会读取dump.rdb。

```shell
3354:M 25 Sep 2023 16:04:58.534 * RDB is base AOF
3354:M 25 Sep 2023 16:04:58.535 * Done loading RDB, keys loaded: 4, keys expired: 0.
3354:M 25 Sep 2023 16:04:58.535 * DB loaded from disk: 0.002 seconds
3354:M 25 Sep 2023 16:04:58.535 * Ready to accept connections tcp
```

