package com.zhouyf.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DEMO3 {
    public static void main(String[] args) {
        String text1 = "zhouyf468@qq.com";
        String text2 = "-zhouyf468@qq.com";

        boolean email = isEmail(text1);
        System.out.println(email);
        System.out.println(isEmail(text2));
    }

    public static boolean isEmail(String email){
        String pattern = "^[a-zA-Z0-9]{2,}@[a-z]+\\.[a-z]{2,}$";

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(email);
        return matcher.matches();
    }
}
