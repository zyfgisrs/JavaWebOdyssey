package com.zhouyf.chapter1;


public class Lambda1 {
    public static void main(String[] args) {
        Print p = () -> System.out.println("hello, lambda!");
        p.print();

        PrintNum printnum = n -> System.out.println(n);
        printnum.print(1);

        Add add = (int a, int b) -> System.out.println(a + b);
        add.add(1, 3);

        ToUpper upper = (String s) -> {
            String upperCase = s.toUpperCase();
            System.out.println(upperCase);
        };

        upper.toUpperCase("zhouyf");
    }
}
