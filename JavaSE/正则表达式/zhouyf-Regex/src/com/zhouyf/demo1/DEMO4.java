package com.zhouyf.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO4 {
    public static void main(String[] args) {
        String text1 = "Java";
        String text2 = "1Java";
        String pattern = "^[^0-9]";

        Pattern compile = Pattern.compile(pattern);
        System.out.println(compile.matcher(text1).find());//true
        System.out.println(compile.matcher(text2).find());//false
    }
}
