# Redis 服务配置

本指南将引导您完成 Redis 服务的配置和启动。我们将从创建数据存储目录开始，然后进行配置文件的设置，最后启动服务并进行测试。

## 目录

- [创建数据存储目录](#创建数据存储目录)
- [配置文件设置](#配置文件设置)
- [启动 Redis 服务](#启动-redis-服务)
- [验证服务状态](#验证服务状态)
- [防火墙配置](#防火墙配置)
- [客户端连接测试](#客户端连接测试)

---

## 创建数据存储目录

Ubuntu 系统通常会有一个额外的数据盘用于存储。为了遵循最佳实践，我们将在该数据盘上创建 Redis 的数据存储目录。

```bash
sudo mkdir -p /mnt/data/redis/{run,logs,dbcache}
```

## 配置文件设置

### 创建配置文件目录

在 Redis 程序目录中创建一个用于存放配置文件的目录。

```bash
sudo mkdir -p /usr/local/redis/conf
```

### 拷贝配置模板

将 Redis 源代码中的 `redis.conf` 模板文件复制到新创建的配置目录中。

```bash
sudo cp /usr/local/src/redis-7.2.1/redis.conf /usr/local/redis/conf/
```

### 编辑配置文件

使用文本编辑器打开 `redis.conf` 文件，并按照以下指导进行配置。

```bash
sudo vi /usr/local/redis/conf/redis.conf
```

#### 配置项

- **服务绑定地址**

  ```properties
  bind 192.168.146.140
  ```

- **服务端口**

  ```properties
  port 6379
  ```

- **后台运行**

  ```properties
  daemonize yes
  ```

- **进程文件路径**

  ```properties
  pidfile /mnt/data/redis/run/redis_6379.pid
  ```

- **日志文件路径**

  ```properties
  logfile "/mnt/data/redis/logs/redis_6379.log"
  ```

- **逻辑数据库数量**

  ```properties
  databases 16
  ```

- **数据存储目录**

  ```properties
  dir /mnt/data/redis/dbcache
  ```

- **关闭保护模式**

  ```properties
  protected-mode no
  ```

## 启动 Redis 服务

使用以下命令启动 Redis 服务。//

```bash
redis-server /usr/local/redis/conf/redis.conf 
```

可能会报没有日志权限，我们使用下面命令来解决：

```bash
sudo chmod 777 /mnt/data/redis/logs/redis_6379.log
```

## 验证服务状态

### 检查进程

使用 `ps` 命令检查 Redis 进程是否正在运行。

```bash
ps -ef | grep redis
```

### 检查端口

使用 `netstat` 命令查看 6379 端口是否被占用。

```bash
netstat -nptl
```

## 防火墙配置

为了让外部客户端能够访问 Redis 服务，我们需要在防火墙中开放 6379 端口。

```bash
sudo ufw allow 6379/tcp
sudo ufw reload
```

或

```bash
firewall-cmd --zone=public --add-port=6379/tcp --permanent
firewall-cmd --reload
```

## 客户端连接测试

使用 Redis 客户端进行连接测试。

```bash
redis-cli -h redis-server -p 6379
```

### 网络检查

输入 `PING`，如果返回 `PONG`，则表示连接成功。

### 查看 Redis 信息

输入 `LOLWUT` 以查看 Redis 的一些基本信息。

---

您现在已经成功配置和启动了 Redis 服务，并且也进行了客户端连接测试。
