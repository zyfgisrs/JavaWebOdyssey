package com.zhouyf.demo4;

public class DEMO1 {
    public static void main(String[] args) {
        String text = "Hello--World-&Java%c++";

        String[] split = text.split("[-&%]+");
        for (String s : split) {
            System.out.println(s);
        }
    }
}
