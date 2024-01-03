package com.zhouyf.demo3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO3 {
    public static void main(String[] args) {
        String text = "54_ss@qq.com";

        String pattern = "^[a-zA-Z0-9&_+*-]+(?:\\.[a-zA-Z0-9&_+*-]+)*@(?:[0-9a-zA-Z-]+\\.)+[a-zA-Z]{2,7}$";


        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);
        System.out.println(matcher.matches());//true
    }
}
