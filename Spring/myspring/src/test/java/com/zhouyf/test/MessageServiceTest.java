package com.zhouyf.test;


import com.zhouyf.config.MessageConfig;
import com.zhouyf.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MessageConfig.class})
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Test
    public void test() {
        messageService.echo();
    }
}
