package com.zhouyf.synchronized_;

public class Counter {
    private static int count;


    public static void increment(){
        synchronized (Counter.class){
            count++;
        }
    }

    public static int getCount() {
        synchronized(Counter.class) {
            return count;
        }
    }
}
