package com.zhouyf.chapter4;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GetStream {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("tom", "lucy", "jim");
        Stream<String> stream = list.stream();

        ArrayList<String> names = new ArrayList<>();
        names.add("tom");
        names.add("zhouyf");
        names.add("lucy");
        Stream<String> nameStream = names.stream();
    }
}
