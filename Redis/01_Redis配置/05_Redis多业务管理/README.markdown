## Redis多业务管理

### 场景分析

#### 多应用共享
如果你管理着多个应用或服务，并希望避免为每个应用启动单独的Redis实例，你可以通过使用不同的数据库来实现它们之间的隔离。

#### 数据分类
在单一应用内，你也可能希望根据数据类型将数据存储在不同的数据库中。

### `databases` 参数配置

在Redis的配置文件（通常名为 `redis.conf`）中，`databases` 参数用于设置可用数据库的数量。默认情况下，Redis提供16个数据库（索引从0到15）。你可以通过编辑配置文件来自定义这个数量。

#### 如何修改 `databases` 参数

1. 打开Redis配置文件：

    ```bash
    vi /usr/local/redis/conf/redis.conf
    ```

2. 修改 `databases` 参数：

    ```bash
    databases 20
    ```

3. 重启Redis服务并登录客户端：

    ```bash
    killall redis-server
    redis-server /usr/local/redis/conf/redis.conf
    redis-cli -h redis-server -p 6379 -a 12345
    ```

4. 验证修改是否成功：

    ```bash
    CONFIG GET databases
    ```

    返回信息应为：

    ```bash
    1) "databases"
    2) "20"
    ```

### 数据库切换

要切换到不同的数据库，你可以使用 `SELECT` 命令，但请注意索引不能超过配置的数据库数量。

- 切换到索引为7的数据库：

    ```bash
    SELECT 7
    ```

    此时，客户端会显示为：

    ```bash
    redis-server:6379[7]>
    ```

- 切换回索引为0的数据库：

    ```bash
    SELECT 0
    ```

    注意，当索引为0时，客户端不会显示数据库索引：

    ```bash
    redis-server:6379>
    ```

这样，你就可以更灵活地管理Redis数据库，以满足多业务或多应用的需求。