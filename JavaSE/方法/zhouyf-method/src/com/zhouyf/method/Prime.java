package com.zhouyf.method;


public class Prime {
    public void calPrime(int start, int end) {
        boolean[] primes = new boolean[end + 1];

        for (int i = 1; i <= end; i++) {
            primes[i] = true;
        }

        for (int factor = 2; factor * factor <= end; factor++) {
            if (primes[factor]) {
                for (int j = factor * factor; j <= end; j += factor) {
                    primes[j] = false;
                }
            }
        }

        for (int i = start; i <= end; i++) {
            if(primes[i]){
                System.out.print(i + " ");
            }
        }
    }

    public static void main(String[] args) {
        new Prime().calPrime(100, 2000);
    }
}
