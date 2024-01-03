package com.zhouyf.synchronized_;

import java.util.concurrent.*;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> submit = executorService.submit(() -> {
            return doSomethingTask();
        });

        Integer i = submit.get();

        System.out.println(i);

        executorService.shutdown();
    }


    public static int doSomethingTask(){
        return 43;
    }
}
