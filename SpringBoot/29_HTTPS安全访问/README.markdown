# HTTPS

## 工作原理

> HTTPS（超文本传输安全协议）是HTTP的安全版本。其核心目的是确保在互联网上数据传输的隐私和完整性。HTTPS依赖于SSL/TLS协议来实现其安全性。

1. **TLS/SSL握手**：
    - 客户端（例如，一个浏览器）连接到基于HTTPS的服务器时，它们之间的第一个步骤是执行TLS/SSL握手。
    - 客户端发送一个“ClientHello”消息到服务器，指定支持的加密方法、随机生成的数据等。
    - 服务器回复一个“ServerHello”消息，选择一个客户端已提出的加密方法，并发送自己的随机生成的数据。
    - 服务器还发送其SSL证书，可能还包括整个证书链。
    - 客户端验证服务器的证书：检查它是否由一个受信任的证书颁发机构（CA）签名、是否在有效日期内、是否与服务器的名称匹配等。
    - 客户端使用证书的公钥加密一个新的随机生成的数据并发送给服务器，这样只有拥有私钥的服务器能够解密。
    - 双方使用前面的交换数据（三个随机值）派生出同一个密钥，称为会话密钥。此密钥用于后续的加密通信。

2. **数据传输**：
    - 一旦TLS/SSL握手完成，客户端和服务器之间的所有通信都将使用派生的会话密钥加密。
    - 这确保了数据的机密性和完整性。

3. **结束会话**：
    - 当数据传输完成后，双方可以选择结束会话。这通常涉及到发送一个“关闭通知”消息，然后断开连接。如果双方再次需要通信，它们必须开始一个新的TLS/SSL握手。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

## 生成证书

```
keytool -genkeypair -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias myalias -storepass zyf2563085
```

![image-20231030184310959](assets/image-20231030184310959.png)

将证书放到`src/main/resources`目录中

<img src="assets/image-20231030184430730.png" alt="image-20231030184430730" style="zoom:67%;" />

## 配置HTTPS证书

```java
server:
  ssl:
    key-store: classpath:my-release-key.jks
    key-store-password: zyf2563085
    key-store-type: JKS
    key-alias: myalias
  port: 8443
```

## 访问应用

```
https://localhost:8443/message/echo?title=zhouyf&pubdate=2021-11-11&content=zhouyangfan
```

![image-20231030184622629](assets/image-20231030184622629.png)