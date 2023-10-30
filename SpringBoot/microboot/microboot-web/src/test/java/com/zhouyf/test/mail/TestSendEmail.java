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
