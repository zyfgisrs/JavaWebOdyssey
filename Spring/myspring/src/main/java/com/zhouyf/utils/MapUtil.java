package com.zhouyf.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static Map<String, String> createMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "zyf");
        map.put("age", "12");
        return map;
    }
}
