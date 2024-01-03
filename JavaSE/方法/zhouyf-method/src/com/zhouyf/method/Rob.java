package com.zhouyf.method;

import java.util.Random;
import java.util.Scanner;

public class Rob {
    public void robRedPacket(int[] packet){
        int size = packet.length;
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < size; i++){
            System.out.println("请按任意键完成抽奖....");
            String s = scanner.nextLine();
            while(true){
                int index = random.nextInt(size);
                if(packet[index] > 0){
                    System.out.println("恭喜你抽中红包：" +  packet[index] + "元");
                    packet[index] = -1;
                    break;
                }
            }
        }
        System.out.println("红包已经全部抢完.....");
    }

    public static void main(String[] args) {
        int[] packet = {11,777,666,222,23};
        new Rob().robRedPacket(packet);
    }
}
