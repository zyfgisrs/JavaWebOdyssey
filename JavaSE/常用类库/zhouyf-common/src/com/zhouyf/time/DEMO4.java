package com.zhouyf.time;

import java.time.Period;

public class DEMO4 {
    public static void main(String[] args) {
        Period period = Period.ofYears(2);
        long totalMonths = period.toTotalMonths();
        System.out.println(totalMonths);
    }
}
