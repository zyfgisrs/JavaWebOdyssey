## 目录

- [Redis zset（有序集合）](#redis-zset（有序集合）)
- [使用特点：](#使用特点：)
- [使用场景：](#使用场景：)
- [重要的操作：](#重要的操作：)

## Redis zset（有序集合）

`zset` 是 Redis 的一种数据类型，它结合了 Set 和 Hash 的特点：每个元素都是唯一的，就像 Set，但每个元素都有一个分数（score）与之关联，这使得我们可以根据分数对集合中的元素进行排序。

### 使用特点：

1. **唯一性**：zset 中的每个元素都是唯一的。
2. **排序**：元素在 zset 中是根据其关联的分数进行排序的。分数可以是整数或双精度浮点数。
3. **性能**：获取最高分数或最低分数的元素的操作非常快，时间复杂度为 O(1)。而添加、删除和更新元素的时间复杂度为 O(log N)。

### 使用场景：

1. **排行榜**：例如游戏分数排行榜，可以使用分数作为玩家的得分，元素作为玩家的 ID 或名称。
2. **时间线或优先队列**：可以使用时间戳作为分数，以此来实现时间线功能。
3. **延迟队列**：使用未来的时间戳作为分数，当当前时间超过或等于该时间戳时，任务可以被执行。
4. **带权重的集合**：例如，根据页面的点击次数为搜索结果排序。

### 重要的操作：

1. **添加元素**：

   ZADD 命令用于向有序集合中添加一个或多个成员，或更新它们的分数。如果键不存在，它将创建该键。

   ```
   ZADD key [NX | XX] [GT | LT] [CH] [INCR] score member [score member...]
   ```

   - **key**：要操作的有序集合的键。
   - **[NX | XX]**：
     - **NX**：仅添加新元素。不更新已存在的元素。
     - **XX**：仅更新已存在的元素。不添加新元素。
   - **[GT | LT]**：
     - **GT**：只有当新分数大于当前分数时才更新已存在的元素。此标志不会阻止添加新元素。
     - **LT**：只有当新分数小于当前分数时才更新已存在的元素。此标志不会阻止添加新元素。
   - **[CH]**：修改返回值，从添加的新元素数量变为总共改变的元素数量。改变的元素包括添加的新元素和更新分数的已存在元素。如果命令行中指定的元素的分数与过去相同，则不计算这些元素。通常，ZADD 的返回值只计算添加的新元素数量。
   - **[INCR]**：当指定此选项时，ZADD 的行为类似于 ZINCRBY。在此模式下，只能指定一个分数-元素对。
   - **score**：成员的分数。
   - **member**：要添加或更新的成员。

   注意：GT、LT 和 NX 选项是互斥的。

   ```
   ZADD zset 12 zyf
   ZADD zset GT 11 zyf
   ZSCORE zset zyf
   “12”
   ZADD zset GT 13 zyf
   “13”
   ```

   ```
   ZADD userPoints 10 tom
   ZADD userPoints INCR 50 tom
   ZSCORE userPoints tom
   “60”
   ```

2. **删除元素**：

   - `ZREM key member [member ...]`：从 zset 中删除一个或多个元素。

3. **获取分数**：
   - `ZSCORE key member`：获取元素的分数。

4. **根据分数范围获取元素**：
   - `ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]`：获取分数在指定范围内的元素。

     ```
     ZADD studentScores 75 Alice
     ZADD studentScores 85 Bob
     ZADD studentScores 90 Charlie
     ZADD studentScores 82 David
     ZADD studentScores 88 Eve
     ZADD studentScores 92 Frank
     ZADD studentScores 78 Grace
     ZADD studentScores 95 Hannah
     ZADD studentScores 89 Ian
     ZADD studentScores 91 Jane
     
     ZRANGEBYSCORE studentScores 80 100 WITHSCORES LIMIT 0 3
     
     1) "David"
     2) "82"
     3) "Bob"
     4) "85"
     5) "Eve"
     6) "88"
     ```

   - `ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]`：与上一个命令相反，这个命令是按分数从高到低排序。

5. **根据排名范围获取元素**：
   - 获取 zset 中排名在指定范围内的元素。

     ```
     ZRANGE key start stop [BYSCORE | BYLEX] [REV] [LIMIT offset count] [WITHSCORES]
     ```

     - **key**：您想查询的有序集合的名称。

     - **start**：开始的索引。0 表示第一个元素，1 表示第二个元素，依此类推。您也可以使用负数，例如 -1 表示最后一个元素。

     - **stop**：结束的索引。与 `start` 参数相同，您可以使用正数或负数。

     - **[WITHSCORES]**：这是一个可选参数。如果您使用它，命令不仅会返回元素，还会返回每个元素的分数。

     - **[BYSCORE | BYLEX]**：这些是可选参数，允许您按分数或字典顺序查询范围。默认情况下，命令按索引查询。

     - **[REV]**：这是一个可选参数。如果使用，它将反转排序顺序。默认情况下，元素是从最低分数到最高分数排序的。使用 `REV` 会从最高分数到最低分数排序。

     - **[LIMIT offset count]**：这是一个可选参数，允许您限制返回的元素数量。`offset` 是您想开始的位置，`count` 是您想返回的元素数量。

       ```
       ZADD bookRankings 600 "Book A"
       ZADD bookRankings 900 "Book B"
       ZADD bookRankings 910 "Book C"
       ZADD bookRankings 870 "Book D"
       ZADD bookRankings 860 "Book E"
       ZADD bookRankings 1000 "Book F"
       ZADD bookRankings 760 "Book G"
       ZADD bookRankings 810 "Book H"
       ZADD bookRankings 820 "Book I"
       ZADD bookRankings 830 "Book J"
       
       ZRANGE bookRankings 0 -1
       
       输出：
        1) "Book A"
        2) "Book G"
        3) "Book H"
        4) "Book I"
        5) "Book J"
        6) "Book E"
        7) "Book D"
        8) "Book B"
        9) "Book C"
       10) "Book F"
       
       ZRANGE bookRankings 0 -1 REV
       
       输出：
        1) "Book F"
        2) "Book C"
        3) "Book B"
        4) "Book D"
        5) "Book E"
        6) "Book J"
        7) "Book I"
        8) "Book H"
        9) "Book G"
       10) "Book A"
       
       ZRANGE bookRankings 0 3 WITHSCORES
       
       输出：
       1) "Book A"
       2) "600"
       3) "Book G"
       4) "760"
       5) "Book H"
       6) "810"
       7) "Book I"
       8) "820"
       ```

   - `ZREVRANGE key start stop [WITHSCORES]`：与上一个命令相反，这个命令是按分数从高到低排序。

6. **获取元素的排名**：

   - `ZRANK key member`：获取元素的排名，分数从低到高。
   - `ZREVRANK key member`：获取元素的排名，分数从高到低。

7. **删除指定排名范围的元素**：
   - `ZREMRANGEBYRANK key start stop`：删除 zset 中排名在指定范围内的元素。

8. **删除指定分数范围的元素**：

   - `ZREMRANGEBYSCORE key min max`：删除分数在指定范围内的元素。

9. **计数**：
   - `ZCARD key`：获取 zset 的元素数量。
   - `ZCOUNT key min max`：计算分数在指定范围内的元素数量。

   ```
   ZADD bookRankings 600 "Book A"
   ZADD bookRankings 900 "Book B"
   ZADD bookRankings 910 "Book C"
   ZADD bookRankings 870 "Book D"
   ZADD bookRankings 860 "Book E"
   ZADD bookRankings 1000 "Book F"
   ZADD bookRankings 760 "Book G"
   ZADD bookRankings 810 "Book H"
   ZADD bookRankings 820 "Book I"
   ZADD bookRankings 830 "Book J"
   
   ZCOUNT bookRankings 900 1000
   输出：3
   ```

10. **增加元素的分数**：
  - `ZINCRBY key increment member`：增加元素的分数。

这些操作为 zset 提供了强大的功能，使其成为 Redis 中非常有用的数据类型。