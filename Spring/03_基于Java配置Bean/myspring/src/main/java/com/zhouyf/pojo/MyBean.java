package com.zhouyf.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;

public class MyBean {
    private List<String> name;

    private Map<String, Integer> scores;

    @Autowired
    public void setName(List<String> name) {
        this.name = name;
    }

    @Autowired
    @Qualifier("scoresMap2")
    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "name=" + name +
                ", scores=" + scores +
                '}';
    }
}
