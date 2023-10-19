package com.zhouyf.test;

import com.zhouyf.vo.Message;

public class MessageBuildTest {
    public static void main(String[] args) {
        Message message = Message.builder().title("zhouyf").
                content("我是zhouyf").build();

        System.out.println(message);
    }
}
