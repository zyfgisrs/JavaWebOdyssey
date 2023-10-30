package com.zhouyf.volatile_;

public class VolatileExample {

    private volatile  boolean isDataAvailable = false;
    private int data = -1;

    public static void main(String[] args) throws InterruptedException {
        VolatileExample example = new VolatileExample();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(100); // 模拟数据产生的延迟
                    System.out.println("produce: " + i);
                    example.produceData(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                while (!example.isDataAvailable()) {
                    // busy wait until data is available
                }
                int consumedData = example.consumeData();
                System.out.println("Consumed: " + consumedData);
            }
        });

        producer.start();
        consumer.start();

    }

    public void produceData(int newData) {
        data = newData;
        isDataAvailable = true;
    }

    public boolean isDataAvailable() {
        return isDataAvailable;
    }

    public int consumeData() {
        isDataAvailable = false;
        return data;
    }
}


