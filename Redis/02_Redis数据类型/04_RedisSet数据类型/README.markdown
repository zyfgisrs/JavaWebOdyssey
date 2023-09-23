对您提供的Markdown文档进行了排版和优化，以下是经过整理的内容：

---

# Redis SET 数据类型

Redis 的 SET 数据类型是无序的集合，其元素是唯一的。该数据类型支持一系列与数学集合相关的操作。

## 目录
1. [特点](#特点)
2. [重要操作](#重要操作)

## 特点
- **无序性**：SET 的元素是无序的，无法通过索引访问。
- **唯一性**：每个元素都是独特的，没有重复。
- **数据类型**：SET 存储的数据类型为字符串。
- SET只有单向数据保存和获取。

## 重要操作

- **SADD**：向集合添加一个或多个成员。
  ```markdown
  SADD key member [member ...]
  ```
- **SCARD**：获取集合的成员数。
  ```markdown
  SCARD key
  ```
- **SISMEMBER**：检查元素是否为集合的成员。
  ```markdown
  SISMEMBER key member
  ```
- **SMEMBERS**：返回集合中的所有成员。
  ```markdown
  SMEMBERS key
  ```
- **SMOVE**：移动元素从一个集合到另一个集合。
  ```markdown
  SMOVE source destination member
  ```
- **SPOP & SRANDMEMBER**：返回并删除或只返回集合中的随机元素。
  ```markdown
  SPOP key [count]
  SRANDMEMBER key [count]
  ```
- **SREM**：从集合中移除一个或多个成员。
  ```markdown
  SREM key member [member ...]
  ```
- **SDIFF**：返回给定集合的差集。
  ```markdown
  SDIFF key [key ...]
  ```
  示例：
  ```markdown
  SADD set1 1 2 3 4 5 6
  SADD set2 1 2 3 4
  SADD set3 7 8 9
  SDIFF set1 set2 set3
  ```
  输出：`“5”,”6”`
  
- **SDIFFSTORE**：计算差集并保存到新集合中。
  ```markdown
  SDIFFSTORE destination key [key ...]
  ```
  示例：
  ```markdown
  SADD set1 1 2 3 4 5
  SADD set2 1 2
  SADD set3 4
  SDIFFSTORE set4 set1 set2 set3
  SMEMBERS set4
  ```
  输出：`“3”,”5”`

- **SINTER**：返回所有给定集合的交集。
  ```markdown
  SINTER key [key ...]
  ```
  示例：
  ```markdown
  SADD set1 1 2 3
  SADD set2 1 
  SADD set3 1 
  SADD set4 1 2
  SINTER set1 set2 set3 set4
  ```
  输出：`“1”`

- **SINTERSTORE & SUNION & SUNIONSTORE**：计算交集或并集并保存到新集合或直接返回。
  ```markdown
  SINTERSTORE destination key [key ...]
  SUNION key [key ...]
  SUNIONSTORE destination key [key ...]
  ```
  示例：
  ```markdown
  SADD set1 1
  SADD set2 2 
  SADD set3 3 
  SUNION set1 set2 set3 
  ```
  输出：`“1”,“2”,“3”`

---

这些操作涵盖了大多数与 SET 数据类型相关的基础和常用操作。