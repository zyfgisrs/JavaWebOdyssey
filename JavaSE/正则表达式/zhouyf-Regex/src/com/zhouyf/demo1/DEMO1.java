package com.zhouyf.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO1 {
    public static void main(String[] args) {
        String text = "zhou1-yf";

        String pattern = "\\bzhou\\b";

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);

        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }
}
