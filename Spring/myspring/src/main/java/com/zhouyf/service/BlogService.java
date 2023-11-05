package com.zhouyf.service;

import com.zhouyf.pojo.Post;

import java.util.List;

public interface BlogService {
    void addPost(String title, String content);

    void removePost(int id);

    void updatePost(int id, String title);

    List<Post> showAllPosts();
}
