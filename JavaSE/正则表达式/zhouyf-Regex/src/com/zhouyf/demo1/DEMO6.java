package com.zhouyf.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO6 {
    public static void main(String[] args) {
        String text = "dog";

        String pattern = "(cat|dog)s?";


        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);
        System.out.println(matcher.find());//true
    }
}
