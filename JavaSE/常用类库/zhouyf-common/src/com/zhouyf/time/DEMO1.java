package com.zhouyf.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.TUESDAY;

public class DEMO1 {
    public static void main(String[] args) {
        LocalDate birthday = LocalDate.of(1999, 5, 3);
        String format = birthday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format);

        LocalDate parse = LocalDate.parse("2021/02/02", DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        LocalDate first = parse.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = parse.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(parse.getDayOfWeek());
        LocalDate with = parse.with(TemporalAdjusters.next(TUESDAY));
        System.out.println(with);
    }
}
