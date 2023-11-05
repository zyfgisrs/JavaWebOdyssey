package com.zhouyf.pojo;

import java.util.Date;

public class Post {
    private int id;
    private String title;

    private String content;

    private Date published_at;

    public Post(int id, String title, String content, Date published_at){
        this.id = id;
        this.title = title;
        this.content = content;
        this.published_at = published_at;
    }

    public Post(){
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublished_at(Date published_at) {
        this.published_at = published_at;
    }

    public String getTitle() {
        return title;
    }

    public Date getPublished_at() {
        return published_at;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", published_at=" + published_at +
                '}';
    }
}
