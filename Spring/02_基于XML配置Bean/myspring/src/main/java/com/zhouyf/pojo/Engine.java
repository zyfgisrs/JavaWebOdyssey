package com.zhouyf.pojo;

public class Engine {
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "model='" + model + '\'' +
                '}';
    }
}
