package com.zhouyf.test.event;

import com.zhouyf.MyApplication;
import com.zhouyf.event.ZhouEvent;
import com.zhouyf.vo.EMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest(classes = MyApplication.class)
public class TestZhouEvent {
    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    void testEvent() {
        this.publisher.publishEvent(new ZhouEvent(this, new EMessage("巴以冲突", "www.baidu.com")));
    }

}
