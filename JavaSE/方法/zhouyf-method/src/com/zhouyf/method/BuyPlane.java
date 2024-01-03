package com.zhouyf.method;

public class BuyPlane {
    public double buyPlane(double price, int month, String economyOrFirst) {
        if (5 <= month && month <= 10) {
            switch (economyOrFirst) {
                case "economy":
                    price = price * 0.85;
                    break;
                case "first":
                    price = price * 0.9;
                    break;
                default:
                    throw new RuntimeException("不存在这种仓位的机票");
            }
        } else {
            switch (economyOrFirst) {
                case "economy":
                    price = price * 0.65;
                    break;
                case "first":
                    price = price * 0.7;
                    break;
                default:
                    throw new RuntimeException("不存在这种仓位的机票");
            }
        }
        return price;
    }

    public static void main(String[] args) {
        double economy = new BuyPlane().buyPlane(1000, 1, "first");
        System.out.println(economy);
    }
}
