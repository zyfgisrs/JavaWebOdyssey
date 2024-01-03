package com.zhouyf.varhandler;


import java.util.concurrent.atomic.AtomicReferenceArray;

public class Array_ {
    public static void main(String[] args) {
        String[] arr = new String[]{"zhouyf", "slp", "zyp"};
        AtomicReferenceArray<String> stringAtomicReferenceArray = new AtomicReferenceArray<>(arr);
        boolean isOk = stringAtomicReferenceArray.compareAndSet(0, "zhouyf", "zyf");
        System.out.println(isOk);
        System.out.println(stringAtomicReferenceArray.toString());
    }
}
