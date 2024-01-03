package com.zhouyf.demo3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO1 {
    public static void main(String[] args) {
        String text = "a1 b2";


        String pattern = "(?<!a)\\d";

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);
        while(matcher.find()){
            System.out.println(matcher.group());
            //2
        }
    }
}
