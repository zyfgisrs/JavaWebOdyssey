package com.zhouyf.test;

import com.zhouyf.MyApplication;
import com.zhouyf.action.MessageAction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)//使用JUnit5测试工具
@WebAppConfiguration //启动Web运行环境
@SpringBootTest(classes = MyApplication.class) //配置启动程序类
public class TestMessageAction {
    @Autowired
    private MessageAction messageAction;

    @BeforeAll
    public static void init() {
        System.err.println("【beforeAll】TestMessageAction类开始执行测试操作");
    }

    @AfterAll
    public static void after() {
        System.err.println("【AfterAll】TestMessageAction类测试完成");
    }

    @Test
    public void testEcho() {
        String content = this.messageAction.echo("zhouyf");
        String value = "[ECHO]zhouyf";
        System.err.println("【@Test】测试echo方法的返回值，当前返回结果为：" + content);
        Assertions.assertEquals(content, value);//测试相等
    }
}
