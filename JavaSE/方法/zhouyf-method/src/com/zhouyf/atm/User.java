package com.zhouyf.atm;

public class User {
    private String name;

    private double balance;

    private double allowance;

    private String  accountId;

    private String password;

    private String sex;

    public User() {
    }

    public User(String name, double balance, double allowance, String accountId, String password, String sex) {
        this.name = name;
        this.balance = balance;
        this.allowance = allowance;
        this.accountId = accountId;
        this.password = password;
        this.sex = sex;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
