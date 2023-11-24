package com.zhouyf.listener;

import com.zhouyf.event.ZhouEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ZhouListener implements ApplicationListener<ZhouEvent> {
    @Override
    public void onApplicationEvent(ZhouEvent event) {
        log.info("事件处理：{}", event);
        event.fire();
    }
}
