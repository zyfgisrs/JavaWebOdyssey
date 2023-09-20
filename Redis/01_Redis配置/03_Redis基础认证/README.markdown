# Redis 基础认证配置指南

详细的 Redis 基础认证配置，包括如何打开和修改 Redis 配置文件、重启 Redis 服务以及如何进行认证。

## 目录

- [修改 Redis 配置文件](#修改-redis-配置文件)
- [重启 Redis 服务](#重启-redis-服务)
- [连接 Redis 服务](#连接-redis-服务)
- [使用 AUTH 进行认证](#使用-auth-进行认证)
- [带认证信息进行 Redis 连接](#带认证信息进行-redis-连接)

---

## 修改 Redis 配置文件

### 打开 Redis 配置文件

```bash
sudo vi /usr/local/redis/conf/redis.conf
```

### 修改配置项

在配置文件中找到或添加以下两行：

```text
protected-mode yes
requirepass 12345
```

这里，`protected-mode yes` 用于启用受保护模式，而 `requirepass` 用于设置认证密码。

---

## 重启 Redis 服务

为了使新的配置生效，需要重启 Redis 服务。

```bash
redis-server /usr/local/redis/conf/redis.conf
```

---

## 连接 Redis 服务

使用以下命令连接到 Redis 服务：

```bash
redis-cli -h redis-server -p 6379
```

---

## 使用 AUTH 进行认证

在成功连接到 Redis 服务后，使用 `AUTH` 命令进行认证：

```bash
AUTH 12345
```

---

## 带认证信息进行 Redis 连接

你也可以在连接 Redis 服务的同时提供认证信息：

```bash
redis-cli -h redis-server -p 6379 -a 12345
```

这样，你就不需要在连接后再单独执行 `AUTH` 命令。

---

通过以上步骤，成功配置了 Redis 的基础认证。这样可以增加 Redis 服务的安全性，防止未授权访问。

