package com.zhouyf.pojo;

import java.util.Map;

public class CityTemperatureRecorder {
    private Map<String, Double> cityTemperatures;

    public Map<String, Double> getCityTemperatures() {
        return cityTemperatures;
    }

    public void setCityTemperatures(Map<String, Double> cityTemperatures) {
        this.cityTemperatures = cityTemperatures;
    }

    @Override
    public String toString() {
        return "CityTemperatureRecorder{" +
                "cityTemperatures=" + cityTemperatures +
                '}';
    }
}
