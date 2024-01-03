package com.zhouyf;

import java.util.ArrayList;
import java.util.List;


public class Demo4 {
    public void show(List<Object> list){

    }

    public static void main(String[] args) {
        Demo4 demo4 = new Demo4();
        List<String> list = new ArrayList<>();
        demo4.show(list);//编译错误
    }
}
