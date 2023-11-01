# 整合Email邮件服务

整合 Email 邮件服务到 Spring Boot 应用是一个很常见的需求，可以用于发送通知、确认信息、找回密码等功能。在 Spring Boot 中，这一整合变得很简单，主要得益于 Spring Boot 的自动配置特性。

## 依赖引入

```groovy
project('microboot-web') { // 子模块
    dependencies { // 配置子模块依赖
        compile("org.springframework.boot:spring-boot-starter-mail")
    }
}
```

## 开启服务

开启`POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV`服务

![image-20231030130602895](assets/image-20231030130602895.png)

## 配置SMTP邮件服务

`microboot-web\src\test\resources\application.yml`

```yaml
spring:
  mail:
    host: smtp.qq.com
    username: 36075103@qq.com
    password: fxmjcejjaqjwbjgh
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enabled: true
      mail.smtp.starttls.required: true
```

## 邮件发送

`com.zhouyf.test.mail.TestSendEmail`

```java
package com.zhouyf.test.mail;

import com.zhouyf.MyApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyApplication.class)
public class TestSendEmail {
    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void testSend(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("36075103@qq.com");
        message.setTo("zyf050399@163.com");
        message.setSubject("周杨凡");
        message.setText("是一名学生");
        this.javaMailSender.send(message);
    }
}
```





