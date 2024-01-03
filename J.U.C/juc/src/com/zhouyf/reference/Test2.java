package com.zhouyf.reference;

import java.util.concurrent.atomic.AtomicStampedReference;

public class Test2 {
    public static void main(String[] args) {
        Book book = new Book("水浒传", 32.2);
        AtomicStampedReference<Book> stampedReference = new AtomicStampedReference<Book>(book, 1);
        boolean b1 = stampedReference.compareAndSet(book, new Book("格林童话", 23.3), 2, 5);
        System.out.println("×的情况" + b1 + "、对象为：" + stampedReference.getReference());
        boolean b2 = stampedReference.compareAndSet(book, new Book("格林童话", 23.3), 1, 2);
        System.out.println("√的情况" + b2 + "、对象为：" + stampedReference.getReference());
    }
}
