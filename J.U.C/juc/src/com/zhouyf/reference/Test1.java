package com.zhouyf.reference;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

class Book{
    private String name;

    private double price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
public class Test1 {
    public static void main(String[] args) {
        AtomicReference<Book> book = new AtomicReference<>(new Book("水浒传", 56.5));
        boolean b = book.compareAndSet(new Book("水浒传", 56.5), new Book("格林童话", 45.0));
        System.out.println(b);//false
    }
}
