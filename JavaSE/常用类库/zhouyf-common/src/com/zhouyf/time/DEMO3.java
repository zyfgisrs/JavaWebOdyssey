package com.zhouyf.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DEMO3 {
    public static void main(String[] args) {
        LocalTime time1 = LocalTime.parse("07-07-12", DateTimeFormatter.ofPattern("HH-mm-ss"));
        String strTime = time1.format(DateTimeFormatter.ofPattern("HH mm ss"));
    }
}
