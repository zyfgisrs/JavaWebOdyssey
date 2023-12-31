## 基础概念

TPS：TPS 是 "每秒事务数" 的缩写，用于衡量一个系统在单位时间内能够处理的事务数量。这是一个非常重要的性能指标，尤其是在需要处理大量事务的系统（如数据库、支付系统等）中。

QPS：QPS 是 "每秒查询数" 的缩写，用于衡量一个特定查询系统（如 Web 服务器、DNS 服务器或数据库）在单位时间内能够处理的查询请求数量。

## benchmark

`redis-benchmark` 是一个用于测试 Redis 服务器性能的工具。以下表格列出了一些常用的 `redis-benchmark` 参数和它们的描述。

| 参数                | 描述                                                    |
| ------------------- | ------------------------------------------------------- |
| `-h`                | 指定 Redis 服务器主机名或 IP 地址。默认为 `127.0.0.1`。 |
| `-p`                | 指定 Redis 服务器端口号。默认为 `6379`。                |
| `-c`                | 设置并发客户端的数量。默认为 `50`。                     |
| `-n`                | 设置总请求数量。默认为 `100000`。                       |
| `-d`                | 设置数据大小（以字节为单位）。默认为 `2`。              |
| `--tls`             | 使用 TLS 连接。                                         |
| `--dbnum`           | 指定 Redis 数据库编号。默认为 `0`。                     |
| `-k`                | 1=keep alive 0=reconnect。默认为 `1`。                  |
| `-r`                | 设置键名的随机范围。                                    |
| `-P`                | 设置管道中的请求数量。                                  |
| `-q`                | 静默模式，仅显示查询/秒。                               |
| `--csv`             | 以 CSV 格式输出结果。                                   |
| `-l`                | 生成循环，永不停止。                                    |
| `-t`                | 仅运行指定的测试。例如：`-t set,get,incr,lpush,rpop`。  |
| `--threads`         | 设置 I/O 线程数。默认为 `1`。                           |
| `--cluster`         | 启用集群模式。                                          |
| `--enable-tracking` | 启用客户端端到端监控。                                  |
| `--id`              | 设置客户端 ID。                                         |

## benchmark 使用

如果你想测试一个运行在 `redis-server`（IP） 上的 Redis 服务器，使用  100个并发客户端和总共 500000 个请求，你可以使用以下命令：

```
redis-benchmark -h redis-server -p 6379 -a 12345 -n 500000 -c 100
```

