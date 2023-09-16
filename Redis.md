# Redis

- Redis（Remote Dictionory Server、远程数据服务），它本身属于一种开源的，基于key-value的存储结构的缓存数据库，可以方便的构建高性能、可扩展的WEB应用程序的解决方案。
- Redis在互联网的项目开发之中可以解决80%高并发处理问题，比如秒杀，视频网站的视频推流等等，几乎都是redis身影。
- Redis从诞生开始有一个重要的光环“性能”，TPS可以达到8万/秒，QPS达到10万/秒。（TPS：每秒执行事务的数量：每秒钟处理完事务的次数，一个应用系统在1秒内可以处理完成多少次的服务请求统计）（QPS：每秒的查询率，并发查询的吞吐量。）

# 1. Redis准备

## 1.1 redis配置文件

指定配置文件启动

```shell
cp redis.conf redis.conf.bck
#备份配置文件
vim redis.conf
```

重要配置

```shell
#监听地址，，默认是127.0.0.1,会导致只能在本地访问，修改为0.0.0.0则可以在任意IP访问，生产环境不要设置为0.0.0.0
bind 0.0.0.0
#守护进程，修改为yes后可以在后台运行
daemonize yes
#密码
requirepass 
#日志
logfile
```

启动时指定配置文件

```java
redis-server redis.conf
```

查看redis进程

```java
ps -ef | grep redis
```

杀进程

```java
kill -9 xxx
```

## 1.2 设置开机自启动

```shell
#新建文件夹
vim /etc/systemd/system/redis.service
```

```shell
#在文件中添加

[Unit]
Description=redis-server
After=network.target

[Service]
Type=forking
ExecStart=/usr/local/bin/redis-server /usr/local/src/redis-6.2.6/redis.conf
PrivateTmp=true

[Install]
WantedBy=multi-user.target
```

重新加载服务

```java
systemctl daemon-reload
```

![image-20230407211215124](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230407211215124.png)

重启虚拟机，可以发现redis已经自启动。

![image-20230407212442725](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230407212442725.png)

## 1.3 redis客户端

### 1.3.1 命令行客户端

Redis安装完成后就自带了命令行客户端：redis-cil

![image-20230408114352303](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408114352303.png)

使用方法如下：

```shell
redis-cil [options][commonds]
```

常见的options有：

- `-h 127.0.0.1`：指定要连接的redis结点的IP地址。默认是127.0.0.1，代表本机。
- `-p 6379`：指定要连接的redis结点的端口，默认是6379。
- `-a 123321`：指定redis的访问密码。

其中commonds就是redis的操作命令，例如：

- `ping`：与redis服务端做心跳测试，服务端正常会返回`pong`

不指定commond时，会进入`redis-cil`的交互控制台：

首先进入安装目录：`cd /usr/local/bin/`

![image-20230408115903340](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408115903340.png)

提示不安全，我们使用AUTH命令来进入：

![image-20230408120204563](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408120204563.png)

### 1.3.2 图形化界面客户端

- AnotherRedisDesktopManager
- GitHub网址：https://github.com/qishibo/AnotherRedisDesktopManager/releases

关闭防火墙：

```shell
systemctl stop firewalld.service
```

**连接redis服务，并在DB1中添加一条数据String类型的数据，key=name，value=jack**

![image-20230408125728911](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408125728911.png)

**在命令行客户端中查询该数据**

![image-20230408125804173](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230408125804173.png)

**可以看到已经查询成功**

## 1.4 Redis多业务管理

- 同一个Redis服务器可能保存不同项目的数据，例如：在一个完整的项目之中，有些Redis缓存并没有如此高的并发量（但是也不能直接通过SQL数据加载，因为性能太差），此时就可以在Redis虚拟出不同的数据库，用于不同平台的缓存。

- 在默认情况下Redis内部会提供16个（0~15）数据库，并且会使用索引为0的数据库作为默认的数据存储，如果要修改的话，可以直接通过redis.conf配置文件来进行定义。

**修改redis.conf配置文件，将内部可用的数据库的数据修改为20个:**

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409205047237.png" alt="image-20230409205047237" style="zoom:33%;" />

查看配置文件:

![image-20230409210707330](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409210707330.png)

可以看到数据库数量为20

**数据库切换：**

![image-20230409211823561](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409211823561.png)

**配置数据，查询数据**

![image-20230409211214146](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409211214146.png)

**如果发现某些数据保存的数据库序号是错的，可以进行数据的移动，将19号数据库中的zyf-key数据项移动到18号数据库数据库：**

![image-20230409211505343](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409211505343.png)

**可以发现移动成功：**

![image-20230409211629331](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409211629331.png)

**如果说此时某一个数据库序号之中已经保存了无数个缓存数据，这个时候就直接更换序号即可:**

![image-20230409212021214](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409212021214.png)

Redis为了便于数据清空的操作提供了两个命令：

1. **清空当前数据：**

![image-20230409212428151](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409212428151.png)

2. **清空全部数据:**

![image-20230409212622556](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230409212622556.png)

# 2. Redis数据

## 2.1 基本数据操作

Redis支持`key=value`这样的存储结构，同时利用该结构可以实现文本数据（JSON、XML、CSV文件结构）或者是二进制数据的保存，如果需要进行文本的操作需要通过特定的命令完成，而这些命令可以直接通过Redis官网进行查看。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410092745993.png" alt="image-20230410092745993" style="zoom:50%;" />

【SET】：

- NX：在数据key不存在的时候才允许设置。
- XX：修改已存在的数据项。
- GET：数据覆盖时返回已经保存的旧内容
- EX：设置数据失效的时间（秒）
- PX：设置数据失效的时间（毫秒）
- EXAT：设置数据失效的时间戳（秒）
- PXAT：设置数据失效的时间戳（毫秒）
- KEEPTTL：重复设置SET时，是否保留旧值过期时间

![image-20230410095118546](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410095118546.png)

设置相同的KEY会产生覆盖的情况。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410093953094.png" alt="image-20230410093953094" style="zoom:50%;" />

【GET】根据指定的KEY获取对应的数据，不存在则返回空(nil)

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410094123984.png" alt="image-20230410094123984" style="zoom:50%;" />

【SETNX】不覆盖设置数据：可以看到返回了0，没有设置成功。

![image-20230410095509708](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410095509708.png)

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410094231377.png" alt="image-20230410094231377" style="zoom:50%;" />

设置数据的有效期（单位：秒）

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410094342695.png" alt="image-20230410094342695" style="zoom:50%;" />

【MESET】同时设置多组缓存数据，KEY重复时会出现覆盖：

![image-20230410100455719](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410100455719.png)

![image-20230410101201564](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410101201564.png)

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410094432173.png" alt="image-20230410094432173" style="zoom:50%;" />

【MSETNX】同时设置多组缓存数据，如果KEY已经存在，则不会修改，且后面的key都不会被设置：

![image-20230410101415125](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410101415125.png)



<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410094529166.png" alt="image-20230410094529166" style="zoom:50%;" />

【APPEND】在指定的KEY的原有内容之后追加新数据：

![image-20230410095956656](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410095956656.png)

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410094629618.png" alt="image-20230410094629618" style="zoom:50%;" />

【STRLEN】获取指定KEY对应数据的长度:

![image-20230410095659796](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410095659796.png)

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410094710708.png" alt="image-20230410094710708" style="zoom:50%;" />

【DEL】删除指定key：返回删除成功的数据项的个数

![image-20230410095809361](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410095809361.png)

Redis之中有一个比较重要的功能就是数据的自动删除，在现实生活中见到的手机验证码，一般都在特定的时间失效，这个失效就可以通过Redis来完成。

![image-20230410102033692](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410102033692.png)

以上操作可以在10秒内获取，超过10秒将无法获取到该数据了。

除了可以在数据key定义的时候配置数据的失效时间，也可以为已经存在的数据配置数据失效的时间。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410102412790.png" alt="image-20230410102412790" style="zoom:50%;" />

【EXPIRE】配置数据失效的时间

![image-20230410102459465](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410102459465.png)

使用EXPIRE命令确实可以实现失效时间的动态配置，但是这样一来就会让数据失去原子性操作的支持，所以常规做法还是通过SETEX一次性配置即可。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410102827822.png" alt="image-20230410102827822" style="zoom:50%;" />

【TTL】key剩余有效的时间

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410103116687.png" alt="image-20230410103116687" style="zoom:50%;" />

## 2.2 keys命令

在redis里面所有的数据都是按照`key=value`的形式存储的，所以如果要想知道数据库之中存在有多少个数据项，就可以用`keys 匹配模式`的方式进行处理了。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410103824287.png" alt="image-20230410103824287" style="zoom:50%;" />

在以后的实际开发中，keys命令可能不会经常用到，同时有些地方还会限制使用，因为Redis往往保存海量的数据，那么海量的数据是不能直接查询的。

范例：

![image-20230410114614982](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410114614982.png)

## 2.3 Hash数据类型

Redis描述一个完整的对象信息（一个对象会有多个属性的内容），按照传统的做法需要创建多个数据key，而为了便于这些数据KEY统一描述，可以采用`member:用户名:属性名称`的方式进行定义。

相关命令：

【HEST】：将指定的字段设置为其在键存储的哈希中的值。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410131408886.png" alt="image-20230410131408886" style="zoom: 50%;" />

向Redis之中设置一组哈希数据：返回3，设置三个成员

![image-20230410134957297](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410134957297.png)

【HGET】获取Hash的某个字段

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410135426457.png" alt="image-20230410135426457" style="zoom:50%;" />

【HGETALL】获取hash数据所有字段

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410135238068.png" alt="image-20230410135238068" style="zoom: 50%;" />

![image-20230410135648201](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410135648201.png)

![image-20230410135812027](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410135812027.png)

【HKEYS】：获所有成员的名称

![image-20230410140108466](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410140108466.png)

【HVALS】：获取所有的成员值

![image-20230410140229320](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410140229320.png)

【HDEL】：删除成员项

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410140532828.png" alt="image-20230410140532828" style="zoom: 67%;" />

【HEXISTS】：判断成员项是否存在

![image-20230410140723341](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410140723341.png)

【DEL】：删除hash数据

![image-20230410141012882](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230410141012882.png)

## 2.4 数字操作

在实际开发之中一般会出现这样的一种场景，在数据库之中有一个数字，而这个数字需要考虑到高并发访问下的数据更新问题（自增操作）,按照原始的做法，一个完整的自增操作可能会采用如下的步骤处理：

1. 获取数据项
2. 进行数据内容修改
3. 进行数据内容的写入

如果以上的三步的处理机制要保证其原子性，那么就要加入数据锁的支持，可是一旦加入数据锁，那么就会带来严重的性能问题，即便是Redis可以解决，但是以上的三步的处理问题也是存在的，所以在redis设计的时候，考虑到此类的需要，提供了数据的修改支持。

| 数据的操作指令                    | 描述                                            |
| --------------------------------- | ----------------------------------------------- |
| INCR key                          | 对指定的key对应的数据进行自增，如果不存在则新建 |
| DECR key                          | 对指定的key对应的数据进行自减，如果不存在则新建 |
| INCRBY key 数值                   | 每次增长指定的数值，数值设置为负数则为减少      |
| DECRBY key 数值                   | 每次减少指定的数值，数值设置为负数表示增加      |
| HINCRBY 对象key 属性key 数值      | 进行Hash数据增长                                |
| HINCRBYFLOAT 对象key 属性key 数值 | 进行Hash数据增长，可以为浮点型                  |

在整个Redis中，可以提供自定义数据存储以及便于获取操作的只有普通的数据和Hash结构的数据，所以针对于数据的操作也是这两种数据类型。

![image-20230411094619629](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411094619629.png)

Redis提供了数字操作的支持，这一支持可以有效避免多线程情况下的数据同步操作的出现，提供了强大的原子性支持（Redis内置原子性操作，支持数据同步处理）

## 2.5 List数据类型

- List集合：可以存放有重复的数据内容
- List是一种在Redis中的队列结构实现，这个队列可以实现**双端队列**的方式进行处理。



【**LPUSH**】从队列左端添加数据

```shell
LPUSH list:message left-a left-b left-c
```

输出：队列一共保存3项数据

```
3
```

【**RPUSH**】从队列右端添加数据

```
RPUSH list:message right-a right-b right-c
```

输出：队列一共保存6项数据

```
6
```

队列中的数据：

![image-20230411100124677](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411100124677.png)

【**LRANGE**】从左到右获取队列元素 [0,2]，左闭右闭

```
LRANGE list:message 0 2
```

![image-20230411100421545](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411100421545.png)

从做到右获取队列全部元素

```
LRANGE list:message 0 -1
```

![image-20230411100500145](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411100500145.png)

在队列之中进行数据保存的目的就是为了数据的弹出（队列属于一种缓存结构）

【**LPOP**】从左边弹出一个数据：

```
LPOP list:message
```

返回：可以看到弹出了左边第一个数据

```
left-c
```

![image-20230411101434522](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411101434522.png)

在使用LPOP命令的时候，如果没有严格的限制，则每次弹出一个数据，如果有需要也可以弹出多个数据项，只需要设置一个**个数**即可。

【**RPOP**】从右边弹出两个数据：

```
RPOP list:message 2
```

![image-20230411101734170](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411101734170.png)

如果队列中保存的数据个数不满足于弹出的数据的长度要求，则只会弹出队列中的全部数据。

![image-20230411102025028](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411102025028.png)

采用以上的队列结构，可以在程序开发中实现一个分布式缓存机制，因为大量的数据按照顺序进入到队列，而后再设置一个消费端不断的通过队列获取数据。（像消息组件）

以上操作实现了队列的基本结构，但是队列里面还有一些比较灵活的处理机制，可以将一个队列中弹出的数据保存在另外一个队列里面。

首先在队列左边压入5个数据

```
LPUSH zyf:message zyf-a zyf-b zyf-c zyf-d zyf-e
```

![image-20230411102955457](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411102955457.png)

【RPOPLPUSH】右弹左进：实现转存

```
RPOPLPUSH zyf:message res:message
```

![image-20230411103445752](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411103445752.png)

执行RPOPLPUSH的时候每一次会从zyf:message右边弹出一个数据，同时会返回弹出的数据，并且这一数据会保存在res:message中：

![image-20230411103623047](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411103623047.png)

创建一个包含数字的集合，进行排序

```
LPUSH zyf:data 12 24 6 90 13 45 66 1 3
```

【SORT】对队列进行降序排序

```
SORT zyf:data DESC
```

![image-20230411104231756](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411104231756.png)

![image-20230411104346212](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411104346212.png)

SORT命令并不会改变原始队列中数据的顺序，只是提供了一种操作的加强。

【LMPOP】多个队列依次弹出元素

```
LMPOP 2 zyf:data zyf:message LEFT COUNT 3
```

![image-20230411105507183](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230411105507183.png)

【UNLINK】惰性删除

数据不是立即删除，而是等到资源空闲时再进行删除，适合庞大数据集合的异步删除操作，可以保证redis的性能稳定。

## 2.6 Set数据类型

List集合可以保存重复的数据，因为它主要提供了一个队列的机制，队列的数据只要实现了缓冲的操作，而Set内部没有提供重复数据的存储，而且使用Set命令最重要的一点是为了进行一些集合操作。

| 数据操作命令                      | 描述                                                         |
| --------------------------------- | ------------------------------------------------------------ |
| SADD key 数据 数据                | 向Set集合中添加数据内容                                      |
| SMEMBERS key                      | 获取指定Set集合中的全部数据                                  |
| SREM key 数据 数据 数据           | 从SET集合中删除指定内容                                      |
| SINTER key1 key2 key3             | 返回指定几个集合的并集                                       |
| SDIFF key1 key2 key3              | 返回指定key1集合与（key1集合与（其他集合并集）的交集）的差集运算 |
| SUNION key1 key2 key3             | 返回指定集合的并集                                           |
| SCARD key                         | 返回指定集合中的存储元素个数                                 |
| SRANDMEMBER key [数量]            | 判断指定Set集合中是否包含有指定的数据                        |
| SISMEMBER key 数据                | 判断SET集合中是否存在该数据                                  |
| SINTERSTORE newSet key1 key2 key3 | 将SINTER生成的集合保存为新的集合，newSet为新集合的key        |
| SSDIFFSTORE newSet key1 key2 key3 | 将SDIFF生成的集合保存为新的集合，newSet为新集合的key         |
| SUNIONSTORE newSet key1 key2 key3 | 将SUNION生成的集合保存为新的集合，newSet为新集合的key        |

Set集合就只有一个方向进行数据的保存，一个方向实现数据的获取，但是Set集合使用的目的进行各种集合的计算操作。

【SINTERSTORE】使用方法

![image-20230412142327381](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230412142327381.png)

## 2.7 ZSet数据类型

- 在不同的站点上都存在有“热搜”的概念，当某些信息访问量极度增加的时候，就可以通过一些打分的机制，实现热搜数据的管理，当然这些数据是可以轻松地被人为控制。
- Zset集合可以实现数据的打分处理

【ZADD】

```
ZADD hotkey:919:20250919 8 java 3 python 5 golang
```

![image-20230413145523582](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413145523582.png)

创建了ZSet集合，那么尝试获取这个集合的内容

```
ZRANGE hotkey:919:20250919 0 -1
```

![image-20230413145819463](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413145819463.png)

可以看到可以按照升序返回数据

返回结果的同时加上分数，加上`WITHSCORES`

```
ZRANGE hotkey:919:20250919 0 -1 WITHSCORES
```

![image-20230413150133193](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413150133193.png)

【ZADD key GT CH】：保存的新数据，如果发现数据项内容相同，则只有分数高于原始分数之后才允许存储，同时使用CH参数让该命令返回更新的数据个数。

```
ZADD hotkey:919:20250919 GT CH 10 java 3 python 9 golang 6 SQL
```

![image-20230413150811442](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413150811442.png)

依据分数实现指定分数范围的数据查询，例如：查询分数大于5且小于等于9

```
ZRANGE hotkey:919:20250919 (5 9 BYSCORE WITHSCORES
```

![image-20230413151555827](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413151555827.png)

分页

```
ZRANGE hotkey:919:20250919 (2 9 BYSCORE WITHSCORES
```

![image-20230413151849375](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413151849375.png)

不分页的话返回三个数据

```
ZRANGE hotkey:919:20250919 (2 9 BYSCORE WITHSCORES LIMIT 0 2
```

![image-20230413151955301](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413151955301.png)

socres由大到小查询

```
ZRANGE hotkey:919:20250919 1 9 REV WITHSCORES
```

![ZRANGE hotkey:919:20250919 1 9 REV WITHSCORES](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413153202141.png)

```
ZRANGE hotkey:919:20250919 9 1 BYSCORE REV WITHSCORES
```

![image-20230413153251488](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413153251488.png)

热搜的每一次的访问，都会引起分数的变化，所以可以进行一个分数的修改：

java的score增加3

```
ZADD hotkey:919:20250919 INCR 3 java
```

![image-20230413153553176](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413153553176.png)

【ZINCRBY】

```
ZINCRBY hotkey:919:20250919 3 java
```

![image-20230413153707522](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413153707522.png)

【ZCOUNT】统计指定分数范围的数据项个数

```
ZCOUNT hotkey:919:20250919 (2 9 
```

![image-20230413154011635](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413154011635.png)

【ZRANGEBYLEX 】Zset内部还提供有一种字典排序的支持，需要进行每个数据项的分数相同

小于c

```
ZRANGEBYLEX myzset - (c
```

![image-20230413155046504](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413155046504.png)

【ZINTER】交集

![image-20230413155809387](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413155809387.png)

```
ZINTER 2 zset1 zset2 WITHSCORES
```

![image-20230413155905972](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413155905972.png)

此时不仅仅是将内容的合并，同时还会将内容项对应的分数进行了有效的相加，所以得到的是一个综合性的结果。

## 2.8 位图操作

Redis数据库是可以保存数字的，但是所有的数字都采用的十进制的方式进行存储占，用8位的字节。

Redis里面通过位的操作来实现数据的存储，最终会极大的提高存储的空间长度，从而有效提升性能优化处理。

【SETBIT 】：key的值的二进制表示中，从右向左数的偏移量为offset的二进制位设置为value(0或1)。如果key不存在，则会创建一个新的空串。

```
SETBIT clockin:zyf 3 1
SETBIT clockin:zyf 2 1
```

![image-20230413161708765](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413161708765.png)

【GETBIT】：获取位数据中的值

```
GETBIT clockin:zyf 2
```

![image-20230413162229062](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413162229062.png)

统计位数据中1的个数

【BITCOUNT】

```
BITCOUNT clockin:zyf 
```

![image-20230413162405465](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230413162405465.png)

表示位索引：

```
BITCOUNT clockin:zyf 0 2 BIT
```

表示字节索引

```
BITCOUNT clockin:zyf 0 2 BYTE
```

如果想要存具体的数值，那么数值就需要进行位的编码配置

【BITFIELD】

```
BITFIELD key [GET type offset] [SET type offset value] [INCRBY type offset increment] [OVERFLOW WRAP|SAT|FAIL]
```

如果在进行数据设置的时候要设置的是无符号数据则使用“u”的前缀，有符号使用“i”

## 2.9 HyperLogLog

HLL（HyperLogLog）是一种统计集合中数据基数的结构，例如，现在有一个数据的集合“{1，1，5，5，7，6}”，这个时候里面不重复元素的个数为4，所以基数就是4。在实际的开发之中，可以利用这样的基数来实现一些数据访问量统计，例如：可以设置几个App栏目的关键词，并且收集这些关键词的名称，进行基数的统计。

### 2.9.1 相关操作命令

![image-20230418140628098](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418140628098.png)

此时的数据一共提供了三种数据基数的内容，但是这三个不同的数据KEY里面是有重复的内容，所以可以考虑一起实现基数的统计。

![image-20230418141945815](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418141945815.png)

通过以上的统计可以发现，在实现基数操作的时候里面时没有数据统计的，和Set集合非常相似，正因为有相关联的部分，所以未来的版本之中肯定会逐步进行优化的。

### 2.9.2 使用ZSET来进行统计

```
ZADD skill-zyf 9 java 8 html
ZADD skill-slp 7 java 6 sql
```

![image-20230418143122260](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418143122260.png)

```
ZINTERCARD 2 skill-zyf skill-slp
```

![image-20230418143229235](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418143229235.png)

此时返回了一个数据1，因为这个操作在执行之前首先要进行交集统计，那么此时只有一项。

## 2.10 GEO数据类型

【GEOADD】

 <img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418145009231.png" alt="image-20230418145009231" style="zoom:50%;" />

- 东北师大：125.331845    43.870029
- 动植物园：125.340989   43.873317
- 南湖公园：125.313878  43.85712
- 桂林路：125.317508   43.872888
- 我的位置：125.317508 43.872888

```
GEOADD point 125.331845 43.870029 nenu 125.340989 43.873317 dongzhiwu 125.313878 43.85712 nanhu
```

![image-20230418145624083](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418145624083.png)

【GEOPOS】

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418145734962.png" alt="image-20230418145734962" style="zoom:50%;" />

```
GEOPOS point nenu
```

![image-20230418145957058](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418145957058.png)

【GEORADIUS】

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418150215702.png" alt="image-20230418150215702" style="zoom:50%;" />

以自己的位置（经纬坐标）为半径获取2000m以内的点，降序排列

```
GEORADIUS point 125.317508 43.872888 2000 M WITHDIST DESC
```

![image-20230418150548371](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418150548371.png)

以某个成员为半径获取2000m以内的点，降序排列

```
GEORADIUSBYMEMBER point nenu 3000 M WITHDIST DESC COUNT 2
```

![image-20230418150947068](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418150947068.png)

【GEOHASH】

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418151506511.png" alt="image-20230418151506511" style="zoom: 50%;" />

使用Hash数据存储，要比使用经纬度的方式更加简洁。

# 3. Redis服务配置

## 3.1 Redis数据持久化

软件开发优化方案：

1. 算法优化
2. 减少磁盘寻址（服务器上的磁盘都是磁盘硬盘驱动器（HDD））
3. 网络模型（IO多路复用）

![image-20230418152617332](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418152617332.png)

一旦出现了此类情况，Redis就需要快速的恢复，那么缓存中已有的数据需要立刻重新加载到缓存之中，就只有通过磁盘来进行缓存数据的存储，在启动的时候进行数据的加载。

Redis内部已经自动提供了缓存数据持久化的处理方法，而对于持久化的操作步骤一共有五步：

1. Redis客户端通过程序或者时命令向服务端发送数据库的写入操作，此时的数据时保存在客户端的内存之中；SET key value，这个时候是向Redis里面写入数据，但是这些数据是通过客户端发送的，自然就保存在客户端中。
2. Redis服务器接收到写入数据的请求，并将数据保存在Redis服务进程内存之中；
3. 服务端要将内存中保存的数据写入到磁盘，此时Redis服务端进程需要调用系统的写入进程，随后将要写入的数据写入到系统进程的内存缓冲区之中。
4. 操作系统将系统进程中的数据向磁盘缓冲区写入，最终的数据保存在了磁盘上，而考虑到磁盘的寿命肯定只能是HDD，并且也应该引入磁盘阵列进行安全存储。
5. 磁盘控制器将数据写入到磁盘的物理介质之中，实现了数据持久化存储。

Redis持久化机制有两种：RDB（Redis Database）、AFO（Appendn Only File），这两种机制都需要清楚。

## 3.2 RDB持久化机制

**程序的本质就是简化各种硬件指令，同时简化二进制数据操作的复杂度，RDB是Redis内置的一种数据持久化的处理方案，其重要的实现核心就是将数据以快照的形式保存到磁盘上，每一次进行数据写入的时候，都会通过fork方式创建一个新的写入子进程，将当前的数据以二进制的形式写入到dump.rdb文件之中。**快照（Snapshot）是指在某一时刻对系统或数据进行全量备份的操作。它可以记录一个系统或数据在某个特定时间点的状态，包括所有的数据、元数据和配置信息。快照一般用于备份、还原和灾难恢复等场景，可以帮助恢复数据到之前的某个状态。

![image-20230418155534423](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418155534423.png)

**RDB的方式本身就会出现数据不同步，某些数据可能会丢失，但是会保存大部分的数据。**

RDB持久化的操作的触发在Redis之中提供有三种处理模式，一种SAVE，另外一种是BGSAVE，以及自动触发。

这三种触发方式的具体流程如下：

1. SAVE命令手工触发：

![image-20230418160934591](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418160934591.png)

2. BGSAVE的手工触发：

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418161421841.png" alt="image-20230418161421841" style="zoom: 50%;" />

![image-20230418161504259](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418161504259.png)

3. 自动触发：

基于Redis自身的操作，实现自身的数据备份处理，那么会基于定时的方式来进行触发。

![image-20230418161716420](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418161716420.png)

想要实现自动备份，需要修改redis.conf配置文件来实现，配置文件提供有一些专属的RDB配置项（自动触发的事件、数据持久化磁盘的目录）

|           配置项            |  默认值  |                             描述                             |
| :-------------------------: | :------: | :----------------------------------------------------------: |
| `save <seconds> <changes>`  |    -     |               设置自动持久化，不设置则表示停用               |
| stop-writes-on-bgsave-error |   yes    | 当启用RDB且最后一次保存数据失败，Redis是否停止接收数据，这样可以在灾难发生时，引起使用人员的注意。 |
|       rdbcompression        |   yes    |               是否对存储到磁盘的快照进行压缩。               |
|         rdbchecksum         |   yes    | 使用CRC64算法来进行数据的校验，开启此功能后会增加10%的性能损耗。 |
|         dbfilename          | dump.rdb |                    设置快照文件的存储名称                    |
|             dir             |    ./    |            快照文件存放的路径，该选项需要设置目录            |

```
save 10 2000
```

如果在每10秒内发现有2000个变化项（2000次写入），则自动执行BGSAVE命令。

```
dir /mnt/data/redis/dbcache  数据存储的路径
```

![image-20230418183148334](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230418183148334.png)



# 4. Redis编程开发

# 5. Redis进阶编程

# 6. Redis集群架构

# 7. RedisStack

