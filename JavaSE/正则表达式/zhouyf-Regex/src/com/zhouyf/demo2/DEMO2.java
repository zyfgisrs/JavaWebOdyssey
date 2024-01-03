package com.zhouyf.demo2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO2 {
    public static void main(String[] args) {
        String text = "123-ABC-123-ABC";

        String pattern = "([0-9]{3})-([A-Z]{3})-\\1-\\2";

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);
        System.out.println(matcher.matches());//true


    }
}
