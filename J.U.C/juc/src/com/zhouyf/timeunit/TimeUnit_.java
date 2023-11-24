package com.zhouyf.timeunit;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUnit_ {
    public static void main(String[] args) {
        long hour = 1;
        long seconds = TimeUnit.SECONDS.convert(hour, TimeUnit.HOURS);
        System.out.println("转换的秒数: "+seconds);


        long current = System.currentTimeMillis();
        long after = current + TimeUnit.MILLISECONDS.convert(180, TimeUnit.DAYS);
        String afterDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(after));
        System.out.println(afterDate);

        long seconds1 = Duration.ofHours(2).plusHours(2).getSeconds();
        long hour1 = TimeUnit.HOURS.convert(seconds1, TimeUnit.SECONDS);
        System.out.println(hour1);


        for(int i = 0; i < 5; i++){
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("hello,world");
            }
        }
    }
}
