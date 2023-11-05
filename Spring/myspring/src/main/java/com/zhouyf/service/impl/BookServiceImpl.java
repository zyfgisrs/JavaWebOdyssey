package com.zhouyf.service.impl;

import com.zhouyf.pojo.Book;
import com.zhouyf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private Book book;

    @Override
    public boolean remove() {
        System.out.println("删除成功");
        return true;
    }

    @Override
    public void show() {
        System.out.println(book);
    }

    @Override
    public void add(Book book) {
        System.out.println("添加了一本书");
    }
}
