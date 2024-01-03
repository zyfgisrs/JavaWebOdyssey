package com.zhouyf.demo2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

public class ObjectFrame {
    public static void saveObject(Object obj) throws FileNotFoundException, IllegalAccessException {
        PrintStream ps = new PrintStream(new FileOutputStream("src/com/zhouyf/demo2/data.txt", true));
        Class<?> clazz = obj.getClass();

        String simpleName = clazz.getSimpleName();
        ps.println("------------" + simpleName + "-------------");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            String value = field.get(obj) + "";

            ps.println(name + "----->" + value);
        }
        ps.close();
    }
}
