package com.zhouyf.method;

import java.util.Random;

public class Captcha {
    public String createCaptcha(int n){
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for(int i = 0; i < n; i++){
            int type = random.nextInt(3);

            switch (type){
                case 0:
                    code.append(random.nextInt(10));
                    break;
                case 1:
                    code.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((char) (random.nextInt(26) + 97));
            }
        }
        return code.toString();
    }

    public static void main(String[] args) {
        String captcha = new Captcha().createCaptcha(128);
        System.out.println(captcha);
    }
}
