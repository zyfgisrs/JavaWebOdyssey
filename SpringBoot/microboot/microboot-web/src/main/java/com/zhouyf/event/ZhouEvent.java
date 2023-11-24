package com.zhouyf.event;

import com.zhouyf.vo.EMessage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

@Getter
public class ZhouEvent extends ApplicationEvent {
    private final static Logger LOGGER = LoggerFactory.getLogger(ZhouEvent.class);
    private EMessage message;

    public ZhouEvent(Object source, EMessage message) {
        super(source);
        this.message = message;
    }

    public void fire(){
        LOGGER.info("message = {}", this.message);
    }
}
