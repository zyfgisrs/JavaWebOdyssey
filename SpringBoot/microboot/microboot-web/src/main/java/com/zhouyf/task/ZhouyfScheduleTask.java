package com.zhouyf.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class ZhouyfScheduleTask {
    public final static Logger LOGGER = LoggerFactory.getLogger(ZhouyfScheduleTask.class);
    @Scheduled(fixedRate = 2000)
    public void runJobA(){
        LOGGER.info("【RATE任务】{}",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }

    @Scheduled(cron = "* * * * * ?")
    public void runJobB(){
        LOGGER.info("【CRON任务】{}",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }
}
