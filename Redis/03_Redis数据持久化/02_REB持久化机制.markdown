# Redis RDB 持久化机制

## 目录

1. [简介](#简介)
2. [工作原理](#工作原理)
3. [触发方式](#触发方式)
4. [数据不同步问题](#数据不同步问题)
5. [RDB配置](#RDB配置)
6. [模拟RDB持久化](#模拟RDB持久化)
7. [补充相关概念](#补充相关概念)

## 简介

Redis的RDB（Redis DataBase）持久化机制是一种将Redis在某个时间点的所有数据生成快照并保存到磁盘的方式。

## 工作原理

- RDB持久化是通过创建数据的快照来工作的。在指定的时间间隔内，如果满足一定的写操作数量，Redis会自动地在后台创建数据的快照。
- 这个快照是一个包含了Redis在某一时刻的所有数据的文件。这个文件会被保存在磁盘上，通常是一个名为`dump.rdb`的文件。

## 触发方式

- **手动触发**:
  - RDB可以通过配置文件或者使用`SAVE`或`BGSAVE`命令来手动触发。
    - `SAVE`命令会阻塞Redis服务器进程，直到快照文件创建完毕。（同步）
    - `BGSAVE`命令会在后台创建快照，不会阻塞主服务器进程。（异步）

- **自动触发**:
  - 基于Redis自身的操作，实现自动的数据备份处理，那么会基于定时的方式进行触发（BGSAVE）。

## 数据不同步问题

- RDB快照的生成不是一个原子性操作。这意味着在快照生成过程中，数据可能会发生变化，导致快照中的数据与实际数据存在差异。
- 对于大型数据集，生成快照可能需要一些时间。在此期间，Redis中的数据可能会继续发生变化，但这些变化不会反映在即将生成的快照中。

## RDB配置

1. **`save`**:
   - 定义了在多长时间内，有多少次更新操作，就将数据同步到磁盘。
   
   - 例如：
   
     ```
     save 900 1
     save 300 10
     save 60 10000
     ```
   
     上述配置表示：
   
     - 在900秒（15分钟）内如果有1次修改，就进行同步。
     - 在300秒（5分钟）内如果有10次修改，就进行同步。
     - 在60秒内如果有10000次修改，就进行同步。
2. **`rdbcompression`**:
   - 指定是否在保存RDB文件之前使用LZF压缩。
3. **`rdbchecksum`**:
   - 决定是否在写RDB文件时，为RDB文件添加CRC64校验和。
4. **`dbfilename`**:
   - 指定RDB文件的名称。
5. **`dir`**:
   - 指定RDB文件和AOF文件的保存目录。

## 模拟RDB持久化

- 首先修改配置文件

```
vi /usr/local/redis/conf/redis.conf
```

- 修改配置项

```
save 1 2 3600 1
dbfilename dump.rdb
dir /mnt/data/redis/dbcache
```

- 删除dump.dba文件

```
rm -r /mnt/data/redis/dbcache/*
```

- 启动redis服务器

```
redis-server /usr/local/redis/conf/redis.conf
```

- 登录客户端

```
redis-cli -h redis-server -p 6379 -a 12345
```

- 添加两条数据

```
set key1 zyf key2 slp
```

- 日志

```
1887:M 24 Sep 2023 20:56:31.824 * 2 changes in 1 seconds. Saving...
1887:M 24 Sep 2023 20:56:31.825 * Background saving started by pid 2042
2042:C 24 Sep 2023 20:56:31.835 * DB saved on disk
2042:C 24 Sep 2023 20:56:31.836 * Fork CoW for RDB: current 0 MB, peak 0 MB, average 0 MB
1887:M 24 Sep 2023 20:56:31.927 * Background saving terminated with success
```

可以看到新的RDB已经写入到磁盘中

## 补充相关概念

快照就像是拍照。想象你正在玩一个电脑游戏，你玩到了一个关键的地方，不想失去进度。这时，你可以“保存游戏”，这样如果你后面在游戏中失败了，就可以从这个“保存点”重新开始，而不是从头开始。这个“保存点”就是一个快照，它记录了那个特定时刻游戏的状态。
