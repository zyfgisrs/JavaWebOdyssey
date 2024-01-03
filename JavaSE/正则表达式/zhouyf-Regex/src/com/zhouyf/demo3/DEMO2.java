package com.zhouyf.demo3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO2 {
    public static void main(String[] args) {
        String text = "123-ABC-abc";


        String pattern = "(?:[0-9]{3})-([A-Z]{3})-([a-z]{3})";

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);
        while(matcher.find()){
            String group = matcher.group(0);
            String group1 = matcher.group(1);
            String group2 = matcher.group(2);
            System.out.println(group);//123-ABC-abc
            System.out.println(group1);//ABC
            System.out.println(group2);//abc
        }
    }
}
