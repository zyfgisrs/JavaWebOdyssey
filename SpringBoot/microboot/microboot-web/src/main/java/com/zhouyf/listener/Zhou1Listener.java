package com.zhouyf.listener;

import com.zhouyf.event.ZhouEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Zhou1Listener {

    @EventListener
    public void handleContextStart(ZhouEvent event) {
        log.info("【@EventListener注解监听器】事件：{}", event);
        event.fire();
    }
}
