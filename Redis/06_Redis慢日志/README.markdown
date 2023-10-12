# Redis SLOWLOG

`Redis SLOWLOG` 是 Redis 提供的一种用于记录查询执行时间超过指定时长的命令的日志功能。当某些操作或查询在 Redis 中执行得很慢时，这可能会成为系统瓶颈。使用 `SLOWLOG` 可以帮助开发者和系统管理员找出这些耗时较长的查询，进而进行相应的优化。

下面是 `SLOWLOG` 的基本概念和使用方法：

1. **SLOWLOG 的基本操作**：

   - `SLOWLOG GET [N]`：返回最近执行的 N 条慢查询记录。如果不提供 N，它默认返回 10 条记录。
   - `SLOWLOG LEN`：返回 `SLOWLOG` 的长度，即当前记录的慢查询数量。
   - `SLOWLOG RESET`：重置 `SLOWLOG`，删除所有记录。

2. **配置**：

   Redis 提供了两个配置参数来管理 `SLOWLOG`：

   - `slowlog-log-slower-than <microseconds>`：设置命令执行的时长阈值，只有执行时间超过这个值的命令才会被记录。单位是微秒。如果设置为 0，则记录所有命令；如果设置为一个负数，例如 -1，则不记录任何命令。
   - `slowlog-max-len <number>`：设置 `SLOWLOG` 的最大长度。当新的慢查询记录添加进来时，如果当前记录数量超过这个值，旧的记录就会被移除。

3. **每条慢查询记录包括以下信息**：

   - `unique-id`：一个唯一的递增 ID。
   - `timestamp`：命令执行的时间戳。
   - `duration`：命令执行的时长，单位是微秒。
   - `command and arguments`：执行的命令及其参数。

4. **示例**：

   假设我们设置 `slowlog-log-slower-than` 为 10000（即10毫秒）并执行一些 Redis 命令，其中一些命令执行时间超过10毫秒。之后，我们可以使用 `SLOWLOG GET` 来查看这些慢查询记录。

5. **注意**：

   - 由于 `SLOWLOG` 是在命令执行完毕后进行记录的，因此它记录的是命令的实际执行时间，不包括队列等待时间。
   - `SLOWLOG` 使用很少的内存和计算资源，因此开启它对性能的影响微乎其微。
   - `SLOWLOG` 记录保留在内存中，不会持久化到磁盘。因此，重启 Redis 后这些记录会被清空。