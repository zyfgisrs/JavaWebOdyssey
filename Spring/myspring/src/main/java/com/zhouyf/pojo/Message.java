package com.zhouyf.pojo;

import java.util.Date;

public class Message {
    private String title;

    private String name;

    private Date pubdate;

    public Message(String title, String name, Date date){
        this.name = name;
        this.title = title;
        this.pubdate = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", pubdate=" + pubdate +
                '}';
    }
}
