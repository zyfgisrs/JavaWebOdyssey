package com.zhouyf.service.impl;

import com.zhouyf.pojo.Book;
import com.zhouyf.service.IBookService;

public class BookServiceImpl implements IBookService {
    @Override
    public void insertBook(Book book) {
        System.out.println("插入了一本书:" + book.getName() + " " + book.getPrice());
    }
}
