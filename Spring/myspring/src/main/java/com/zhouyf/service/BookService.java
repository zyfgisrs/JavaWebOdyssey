package com.zhouyf.service;

import com.zhouyf.pojo.Book;

public interface BookService {
    boolean remove();

    void show();

    void add(Book book);
}
