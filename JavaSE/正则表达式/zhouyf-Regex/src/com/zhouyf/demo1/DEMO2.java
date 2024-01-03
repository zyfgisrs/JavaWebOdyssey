package com.zhouyf.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO2 {
    public static void main(String[] args) {
        String text = "zyf123";

        String pattern = "[a-z]*";

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);

        String slp = matcher.replaceAll("slp");
        System.out.println(slp);//slpslp1slp2slp3slp
    }
}
