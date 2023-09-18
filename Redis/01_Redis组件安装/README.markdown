# Redis 安装

## 前言

本节引导你完成 Redis 的安装和配置。Redis 是一个开源的、内存中的数据结构存储系统，用作数据库、缓存和消息代理。

## 修改系统名称

首先，我们将系统的主机名修改为 `redis-server`。

1. **修改主机名**

    打开 `/etc/hostname` 文件并修改主机名。

    ```bash
    sudo vim /etc/hostname
    ```

2. **更新 hosts 文件**

    我们还需要更新 `/etc/hosts` 文件，以便通过新的主机名 `redis-server` 访问 Redis 和其他应用程序。

    ```bash
    sudo vim /etc/hosts
    ```

    在文件中添加以下内容：

    ```properties
    192.168.146.xxx redis-server
    fe80::9166:d779:8cd:xxxx redis-server
    ```
    
    `192.168.146.xxx`是你IPv4地址；`fe80::9166:d779:8cd:xxxx`是你IPv6

## 安装步骤

> 参考：[官方安装指南](https://redis.io/docs/getting-started/installation/install-redis-from-source/)

### 1. 更新系统包列表

更新 Ubuntu 系统的软件包列表。

```bash
sudo apt update
```

### 2. 安装编译工具和依赖

安装必要的编译工具和依赖。

```bash
sudo apt-get install build-essential
sudo apt-get install tcl
```

### 3. 下载 Redis 源代码

- 切换到源代码下载路径：

    ```bash
    cd /usr/local/src/
    ```

- 下载 Redis 安装包：

    ```bash
    wget https://github.com/redis/redis/archive/7.2.1.tar.gz
    ```

### 4. 解压源代码

解压下载的 Redis 安装包。

```bash
sudo tar xzvf /usr/local/src/redis-7.2.1.tar.gz -C /usr/local/src/
```

### 5. 进入源代码目录

切换到解压后的 Redis 源代码目录。

```bash
cd /usr/local/src/redis-7.2.1/
```

### 6. 编译 Redis 源码

使用 `make` 命令编译源码。`BUILD_TLS=yes` 选项用于启用 TLS 支持。

```bash
sudo make BUILD_TLS=yes;
```

### 7. 运行测试（可选）

运行 Redis 的测试套件以确保一切正常。

```bash
sudo make test
```

### 8. 安装 Redis

将编译好的 Redis 程序安装到 `/usr/local/redis` 目录。

```bash
make PREFIX=/usr/local/redis install
```

### 9. 查看 Redis 安装目录

使用 `tree` 命令查看 Redis 的安装目录结构。

```bash
tree /usr/local/redis/
```

你应该看到以下目录结构：

```bash
/usr/local/redis/
└── bin
    ├── redis-benchmark
    ├── redis-check-aof -> redis-server
    ├── redis-check-rdb -> redis-server
    ├── redis-cli
    ├── redis-sentinel -> redis-server
    └── redis-server
```

### 10. 修改内存分配策略

由于 Redis 主要用于内存缓存，我们需要修改内存分配策略。

```bash
echo "vm.overcommit_memory=1" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p
```

### 11. 应用内核参数设置

将当前的配置写入 Linux 内核。

```bash
sudo /sbin/sysctl -p
```

### 12. 环境变量配置

将 Redis 的命令目录添加到系统环境变量中。

- 打开配置文件：

    ```bash
    vi /etc/profile
    ```

- 添加以下配置项：

    ```bash
    export REDIS_HOME=/usr/local/redis
    export PATH=$PATH:$REDIS_HOME/bin:
    ```

- 使配置项生效：

    ```bash
    source /etc/profile
    ```

### 13. 验证安装

查询 Redis 服务的版本号以验证安装是否成功。

```bash
redis-server --version
```

如果你看到版本信息，那么恭喜你，Redis 已经成功安装！

---

这样，你就完成了 Redis 的安装和基础配置。

