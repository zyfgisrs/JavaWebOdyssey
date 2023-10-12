package com.zhouyf.pojo;

import java.util.Set;

public class Animal {
    private Set<String> sets;

    public Set<String> getSets() {
        return sets;
    }

    public void setSets(Set<String> sets) {
        this.sets = sets;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "sets=" + sets +
                '}';
    }
}
