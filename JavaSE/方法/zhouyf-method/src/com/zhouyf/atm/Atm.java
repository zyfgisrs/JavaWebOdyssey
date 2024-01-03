package com.zhouyf.atm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Atm {
    private ArrayList<User> users;

    private User user = null;//在线的用户


    public void Start() {
        users = new ArrayList<>();


        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            if (user == null) {
                System.out.println("===================欢迎进入ATM系统=================");
            } else {
                System.out.println("===================欢迎进入ATM系统(" + user.getName() + ")===================");
            }
            System.out.println("===================  1.用户登录  =================");
            System.out.println("===================  2.用户开户  =================");
            System.out.println("===================  3.退出系统  =================");
            System.out.println("请选择您要的操作命令:");
            int key = scanner.nextInt();
            scanner.nextLine();  // 添加这行代码来消费换行符
            switch (key) {
                case 1:
                    while (user == null) {
                        System.out.println("请输入你的卡号：");
                        String id = scanner.nextLine();
                        if (!idValidate(id)) {
                            System.out.println("您输入的卡号系统中不存在");
                        } else {
                            while (true) {
                                System.out.println("请输入密码");
                                String password = scanner.nextLine();
                                user = login(id, password);
                                if (user == null) {
                                    System.out.println("密码输入有误");
                                } else {
                                    String ch = "";
                                    if (user.getSex().equals("男")) {
                                        ch = "先生";
                                    } else {
                                        ch = "女士";
                                    }
                                    System.out.println("恭喜尊敬的" + user.getName() + ch + ", 您成功登录系统, "
                                            + "您的卡号为" + id + "。");
                                    break;
                                }
                            }
                        }
                    }

                    boolean isOpen = true;

                    while (isOpen) {
                        String ch = "";
                        if (user.getSex().equals("男")) {
                            ch = "先生";
                        } else {
                            ch = "女士";
                        }
                        System.out.println(user.getName() + ch + ", 你可以办理以下业务：");
                        System.out.println("1.查询账户");
                        System.out.println("2.存款");
                        System.out.println("3.取款");
                        System.out.println("4.修改密码");
                        System.out.println("5.退出");
                        System.out.println("6.注销账户");

                        int service = scanner.nextInt();
                        scanner.nextLine();
                        switch (service) {
                            case 1:
                                showAccount(user);
                                break;
                            case 2:
                                System.out.println("请输入存款数额");
                                int num = scanner.nextInt();
                                if (num < 0) {
                                    System.out.println("存款数额输入有误");
                                }
                                deposit(user, num);
                                System.out.println("恭喜你存钱成功");
                                showAccount(user);
                                break;
                            case 3:
                                System.out.println("请输入取款数额");
                                int numWithDraw = scanner.nextInt();
                                if (numWithDraw < 0) {
                                    System.out.println("取款数额输入有误");
                                }
                                boolean withdraw = withdraw(user, numWithDraw);
                                if (withdraw) {
                                    System.out.println("恭喜你取钱成功");
                                    showAccount(user);
                                } else {
                                    System.out.println("取钱失败");
                                }
                                break;
                            case 4:
                                System.out.println("请输入旧密码");
                                String oldPassword = scanner.nextLine();
                                if(!oldPassword.equals(user.getPassword())){
                                    break;
                                }
                                System.out.println("请输入新密码");
                                String p1 = scanner.nextLine();
                                System.out.println("确认新密码");
                                String p2 = scanner.nextLine();
                                boolean success = updatePassword(user, p1, p2);
                                if(success){
                                    user = null;
                                    isOpen = false;
                                    System.out.println("密码修改成功，请重新登录");
                                }else{
                                    System.out.println("两次密码输入不一致，修改失败");
                                }
                                break;
                            case 5:
                                isOpen = false;
                                user = null;
                                break;
                            case 6:
                                boolean deleteUser = deleteUser(user);
                                if(deleteUser){
                                    System.out.println("注销成功");
                                    isOpen = false;
                                }
                                break;
                        }
                    }

                    break;
                case 2:
                    System.out.println("=================银行开户操作=================");
                    System.out.println("请输入账户用户名：");
                    String name = scanner.nextLine();
                    String sex = "";
                    while (true) {
                        System.out.println("请输入您的性别（男/女）：");
                        sex = scanner.nextLine();
                        if (sex.equals("男") || sex.equals("女")) {
                            break;
                        } else {
                            System.out.println("输入的性别有误");
                        }
                    }
                    String password = "";
                    while (true) {
                        System.out.println("请输入密码：");
                        String p1 = scanner.nextLine();
                        System.out.println("请输入确认密码：");
                        String p2 = scanner.nextLine();
                        if (!p1.equals(p2)) {
                            System.out.println("两次输入的密码不一致");
                        } else {
                            password = p1;
                            break;
                        }
                    }
                    double allowance;
                    while (true) {
                        System.out.println("请输入账户的存取限额：");
                        allowance = scanner.nextInt();
                        if (allowance <= 0) {
                            allowance = 0;
                            System.out.println("输入的限额有误");
                        } else {
                            break;
                        }
                    }
                    String id = addUser(name, allowance, password, sex);
                    String ch = "";
                    if (sex.equals("男")) {
                        ch = "先生";
                    } else {
                        ch = "女士";
                    }
                    System.out.println("恭喜尊敬的" + name + ch + ", 您开户成功, "
                            + "您的卡号为" + id + ", 请妥善保管你的银行卡。");
                    break;
                case 3:
                    flag = false;
                    break;
            }
        }


    }

    private String addUser(String name, double allowance, String password, String sex) {
        Random random = new Random();
        String accountId = "1";
        for (int i = 0; i < 11; i++) {
            int value = random.nextInt(10);
            accountId += value;
        }
        User user = new User();
        user.setAllowance(allowance);
        user.setName(name);
        user.setPassword(password);
        user.setAccountId(accountId);
        user.setSex(sex);
        users.add(user);
        return accountId;
    }


    private boolean deleteUser(User u) {
        return users.remove(u);
    }

    private boolean idValidate(String id) {
        for (User user : users) {
            if (id.equals(user.getAccountId())) {
                return true;
            }
        }
        return false;
    }

    private User login(String id, String password) {
        for (User user : users) {
            if (id.equals(user.getAccountId())) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }


    private void showAccount(User user) {
        System.out.println("========当前账户信息如下=======");
        System.out.println("姓名：" + user.getName());
        System.out.println("卡号：" + user.getAccountId());
        System.out.println("账户余额：" + user.getBalance());
    }

    private void deposit(User user, int num) {
        user.setBalance(user.getBalance() + num);
    }

    private boolean withdraw(User user, int num) {
        if (num > user.getBalance()) {
            return false;
        } else {
            user.setBalance(user.getBalance() - num);
            return true;
        }
    }

    private boolean updatePassword(User user, String p1, String p2) {
        if (p1.equals(p2)) {
            user.setPassword(p1);
            return true;
        } else {
            return false;
        }
    }

}
