package com.zhouyf.demo2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//提取字符串中 姓名, 年龄，转换为 年龄: 姓名
public class DEMO1 {
    public static void main(String[] args) {
        String text = "John Doe, 30  James Harden, 32";

        String pattern = "(\\w+ \\w+), (\\d+)";


        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);

        String s = matcher.replaceAll("$2: $1");
        System.out.println(s);//30: John Doe  32: James Harden
    }
}
