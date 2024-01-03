package com.zhouyf.demo2;

import java.io.FileNotFoundException;

public class Test {
    public static void main(String[] args) throws FileNotFoundException, IllegalAccessException {
        Student zhouyf = new Student("zhouyf", "3年级2班");
        Person person = new Person("丁老师", 36, '女', "00001", "长春");

        ObjectFrame.saveObject(zhouyf);
        ObjectFrame.saveObject(person);
    }
}
