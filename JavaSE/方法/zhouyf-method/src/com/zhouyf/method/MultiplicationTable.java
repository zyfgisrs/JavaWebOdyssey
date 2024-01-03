package com.zhouyf.method;

public class MultiplicationTable {
    public static void main(String[] args) {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (j <= i) {
                    System.out.printf("%d x %d = %s\t", j, i, (i * j < 10 ? " " : "") + i * j);
                }
            }
            System.out.println();
        }
    }
}
