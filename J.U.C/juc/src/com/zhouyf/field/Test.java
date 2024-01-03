package com.zhouyf.field;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class Obj{

    public static final AtomicIntegerFieldUpdater<Obj> updater =
            AtomicIntegerFieldUpdater.newUpdater(Obj.class, "count");
    private volatile int count = 0;

    public void increment(){
        updater.incrementAndGet(this);
    }

    public int getCount() {
        return count;
    }
}
public class Test {


    public static void main(String[] args) throws InterruptedException {
        final Obj obj = new Obj();

        for(int i = 0; i < 2000; i++){
            new Thread(()->{
                obj.increment();
            }).start();
        }

        TimeUnit.SECONDS.sleep(1);

        int count = obj.getCount();
        System.out.println(count);
    }
}
