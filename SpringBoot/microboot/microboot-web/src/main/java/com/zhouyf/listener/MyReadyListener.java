package com.zhouyf.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyReadyListener {


    @EventListener
    public void handleStartEvent(ApplicationStartedEvent even){
        System.out.println("Application has started");
    }

    @EventListener
    public void handleRefreshEvent(ContextRefreshedEvent even){
        System.out.println("ApplicationContext is refreshed and beans are fully initialized via @EventListener!");
    }

    @EventListener
    public void handleReadyEvent(ApplicationReadyEvent event){
        System.out.println("application is ready to serve requests via @EventListener!");
    }

}
