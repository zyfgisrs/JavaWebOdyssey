package com.zhouyf.synchronized_;

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        synchronized (this) {
            if (amount > 0) {
                balance += amount;
                System.out.println(Thread.currentThread().getName() + "deposited" + amount
                        + ". New balance: " + balance);
            }
        }
    }

    public void withdraw(double amount){
        synchronized (this){
            if(amount>0 && balance >= amount){
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + "withdraw" + amount
                        + ". New balance: " + balance);
            }
        }
    }

    public double getBalance() {
       synchronized (this){
           return balance;
       }
    }
}
