package com.zhouyf.time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;

public class DEMO2 {
    public static void main(String[] args) {
        Duration twoHours = Duration.ofHours(2);
        LocalTime time = LocalTime.of(2, 30);
        LocalTime plus = time.plus(twoHours);
        System.out.println(plus);
    }
}
