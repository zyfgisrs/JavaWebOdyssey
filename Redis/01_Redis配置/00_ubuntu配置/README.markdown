# Linux 配置

 Linux 系统配置指南，包括 root 账户登录、SSH 配置、基础工具包安装、静态 IP 配置以及数据存储扩展。

## 目录

- [登录 Root 账户](#登录-root-账户)
- [SSH 配置](#ssh-配置)
- [安装基础工具包](#安装基础工具包)
- [配置静态 IP](#配置静态-ip)
- [扩展数据存储](#扩展数据存储)

---

## 登录 Root 账户

### 更改 Root 用户密码

```bash
sudo passwd root
```

### 注销当前登录

```bash
logout
```

### 登录 Root 账户

---

## SSH 配置

### 查看服务器的 IP 地址

```bash
ip addr
```

### 安装 OpenSSH 工具

```bash
sudo apt install openssh-server
```

### 修改 SSH 配置

```bash
vi /etc/ssh/sshd_config
```

在文件中添加或修改以下行：

```text
PermitRootLogin yes
```

### 重启 SSH 服务

```bash
/etc/init.d/ssh stop
/etc/init.d/ssh start
```

---

## 安装基础工具包

### 安装命令

```bash
apt-get -y install make g++ gcc libpcre3 libpcrecpp* libpcre3-dev libpcre3-dev libssl-dev autoconf automake libtool libncurses5-dev libaio-dev iputils-ping net-tools libncurses5 tree zlib1g  zlib1g-dev libnl-genl-3-dev libnl-route-3-dev unzip zip
```

### 工具包简介

#### 开发工具

- `make`: 自动化编译和构建程序。
- `g++`: GNU C++ 编译器。
- `gcc`: GNU C 编译器。

#### 库和开发库

- `libpcre3`, `libpcrecpp*`, `libpcre3-dev`: PCRE（Perl 兼容正则表达式）库。
- `libssl-dev`: OpenSSL 的开发库，用于 SSL/TLS 支持。
- `libaio-dev`: 异步 I/O 库的开发文件。
- `zlib1g`, `zlib1g-dev`: 压缩库及其开发文件。

#### 系统工具

- `autoconf`, `automake`, `libtool`: 自动配置和构建工具。
- `libncurses5-dev`, `libncurses5`: 文本用户界面库。
- `iputils-ping`: 包含 `ping` 命令的工具包。
- `net-tools`: 网络配置工具。
- `tree`: 树状图目录列出工具。
- `unzip`, `zip`: ZIP 文件处理工具。

---

## 配置静态 IP

### 编辑网络配置文件

```bash
sudo vim /etc/netplan/01-network-manager-all.yaml
```

### 配置内容示例

```yaml
# This is the network config written by 'subiquity'
network:
  ethernets:
    ens33:
      dhcp4: false
      dhcp6: false
      addresses:
        - 192.168.146.141/24
        - fe80::20c:29ff:febc:d735/64
      routes:
        - to: default
          via: 192.168.146.2
      nameservers:
        addresses: [223.5.5.5, 180.76.76.76, 114.114.114.114]
  version: 2
  renderer: networkd
```

### 测试网络连通性

```bash
ping www.baidu.com
```

---

## 扩展数据存储

### 添加新硬盘（通过虚拟机设置）

### 确认硬盘状态

```bash
fdisk -l
```

### 创建分区

```bash
fdisk /dev/sdb
```

创建磁盘分区

```
Changes will remain in memory only, until you decide to write them.
Be careful before using the write command.

Device does not contain a recognized partition table.
Created a new DOS (MBR) disklabel with disk identifier 0x9c821f2b.

Command (m for help): n
```

磁盘分区类型：

```
Partition type
   p   primary (0 primary, 0 extended, 4 free)
   e   extended (container for logical partitions)
Select (default p):p
```

定义分区数量

```
Partition number (1-4, default 1): 1
```

定义数据块：默认（直接回车）

```
First sector (2048-62914559, default 2048):
```

分区配置写入

```
Command (m for help): wq
```

分区完成：

```
The partition table has been altered.
Calling ioctl() to re-read partition table.
Syncing disks.
```

查看分区

```shell
fdisk -l
```

当看到下面信息说明分区创建成功

```
Disk /dev/sdb: 30 GiB, 32212254720 bytes, 62914560 sectors
Disk model: VMware Virtual S
Units: sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disklabel type: dos
Disk identifier: 0x9c821f2b

Device     Boot Start      End  Sectors Size Id Type
/dev/sdb1        2048 62914559 62912512  30G 83 Linux
```

### 格式化分区

```bash
mkfs.ext3 /dev/sdb1
```

当看到一下内容说明格式化成功

```
mke2fs 1.47.0 (5-Feb-2023)
Creating filesystem with 7864064 4k blocks and 1966080 inodes
Filesystem UUID: 9fbb9f0a-fcd3-49e5-abed-229f3c68ac7f
Superblock backups stored on blocks:
        32768, 98304, 163840, 229376, 294912, 819200, 884736, 1605632, 2654208,
        4096000

Allocating group tables: done
Writing inode tables: done
Creating journal (32768 blocks): done
Writing superblocks and filesystem accounting information: done
```

### 更新文件系统表并挂载

```bash
echo '/dev/sdb1  /mnt ext3  defaults   0 0' >> /etc/fstab
mount -a
```

### 查看磁盘信息

```bash
df -lh
```

输出

```bash
Filesystem                         Size  Used Avail Use% Mounted on
tmpfs                              1.6G  1.6M  1.6G   1% /run
/dev/mapper/ubuntu--vg-ubuntu--lv   14G  3.8G  9.3G  29% /
tmpfs                              7.9G     0  7.9G   0% /dev/shm
tmpfs                              5.0M     0  5.0M   0% /run/lock
/dev/sda2                          2.0G  155M  1.7G   9% /boot
tmpfs                              1.6G  4.0K  1.6G   1% /run/user/0
/dev/sdb1                           30G  156K   28G   1% /mnt
```

---

这样，你就完成了 Linux 系统的基础配置和扩展存储设置。后续的所有服务数据存储都将在新配置的数据盘分区中进行。