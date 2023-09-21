# Redis `HASH` 数据类型及其操作

Redis 的 `HASH` 数据类型提供了一个键值对的集合, 为字符串字段与字符串值之间的映射。这使得 `HASH` 数据类型特别适用于表示对象。

## 目录

1. [添加 & 获取](#添加--获取)
2. [批量添加 & 获取](#批量添加--获取)
3. [检查字段](#检查字段)
4. [删除字段](#删除字段)
5. [字段数量](#字段数量)
6. [获取所有字段 & 值](#获取所有字段--值)
7. [增加字段的数值](#增加字段的数值)

## 添加 & 获取

- **HSET**: 设定哈希表 `key` 的字段 `field` 值为 `value`.
  ```redis
  HSET student1 name zhouyf
  HSET student1 age 25
  HSET student1 id 01
  HSET student1 hobby football
  ```

- **HGET**: 从哈希表中检索指定字段的值.
  ```redis
  HGET student1 name
  ```

## 批量添加 & 获取

- **HMSET**: 同时设置一个或多个字段的值.
  ```redis
  HMSET student2 name slp age 28 id 02 hobby dance
  ```

- **HMGET**: 获取多个给定字段的值.
  ```redis
  HMGET student2 name age id hobby
  ```

## 检查字段

- **HEXISTS**: 检查哈希表是否包含指定的字段.
  ```redis
  HEXISTS key field
  ```
  *返回*: `1` (存在) 或 `0` (不存在)

## 删除字段

- **HDEL**: 删除哈希表的一个或多个字段.
  ```redis
  HDEL student2 name
  ```

**注意**: `DEL` 命令能够删除一个哈希表.

## 字段数量

- **HLEN**: 统计哈希表的字段数量.
  ```redis
  HLEN key
  ```

## 获取所有字段 & 值

- **HKEYS**: 列出哈希表的所有字段.
  ```redis
  HKEYS key
  ```

- **HVALS**: 列出哈希表的所有值.
  ```redis
  HVALS key
  ```

- **HGETALL**: 列出哈希表的所有字段及其对应的值.
  ```redis
  HGETALL key
  ```

## 增加字段的数值

- **HINCRBY**: 为哈希表的指定字段增加整数值.
  ```redis
  HINCRBY user:1 age 1
  ```

- **HINCRBYFLOAT**: 为哈希表的指定字段增加浮点数值.
  ```redis
  HINCRBYFLOAT key field increment
  ```

