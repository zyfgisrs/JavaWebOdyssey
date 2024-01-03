package com.zhouyf.reference;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class Test3 {
    public static void main(String[] args) {
        Book book = new Book("水浒传", 37.3);
        AtomicMarkableReference<Book> markable = new AtomicMarkableReference<>(book, false);
        boolean b1 = markable.compareAndSet(book, new Book("格林童话", 37.4), true, false);
        System.out.println("×的情况" + b1 + "、对象为：" + markable.getReference());
        boolean b2 = markable.compareAndSet(book, new Book("格林童话", 37.4), false, true);
        System.out.println("√的情况" + b2 + "、对象为：" + markable.getReference());
    }
}
