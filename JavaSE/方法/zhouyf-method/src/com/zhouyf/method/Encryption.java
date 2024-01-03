package com.zhouyf.method;

public class Encryption {
    public int digitEncryption(int n){
        int[] nums = new int[4];

        for(int i = 0; i < 4; i++){
            nums[i] = n % 10;
            n /= 10;
        }

        for(int i = 0; i < 4; i++){
            nums[i] = (nums[i] + 5)%10;
        }

        int left = 0;
        int right = 3;
        while(left <= right){
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }

        int res = 0;
        for(int i = 0; i < 4; i++){
            res *= 10;
            res += nums[i];
        }
        return  res;
    }

    public static void main(String[] args) {
        int i = new Encryption().digitEncryption(5555);
        System.out.println(i);
    }
}
