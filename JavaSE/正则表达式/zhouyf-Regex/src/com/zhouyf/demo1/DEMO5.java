package com.zhouyf.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO5 {
    public static void main(String[] args) {
        String text1 = "Hello";
        String text2 = "HelloY";
        String pattern = "^[A-Z][a-z]+$";


        Pattern compile = Pattern.compile(pattern);
        System.out.println(compile.matcher(text1).find());//true
        System.out.println(compile.matcher(text2).find());//false
    }
}
