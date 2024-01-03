package com.zhouyf.method;

import java.util.Scanner;

public class Mark {
    public double giveMark(int n) {
        int[] scores = new int[n];

        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < n; i++) {
            System.out.printf("请输入第%d个评委的打分:\n", i + 1);
            int score = scanner.nextInt();
            scores[i] = score;
        }

        int max = scores[0];
        int min = scores[0];
        int total = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, scores[i]);
            min = Math.min(min, scores[i]);
            total += scores[i];
        }

        return (double) (total - max - min) / (n - 2);
    }

    public static void main(String[] args) {
        double v = new Mark().giveMark(5);
        System.out.printf("平均的得分为%f", v);
    }
}
