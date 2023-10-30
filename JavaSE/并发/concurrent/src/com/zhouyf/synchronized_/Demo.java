package com.zhouyf.synchronized_;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount(1000);


        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                bankAccount.deposit(50);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "deposit::");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                bankAccount.withdraw(30);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "withdraw::");

        thread2.start();
        thread1.start();

    }
}
