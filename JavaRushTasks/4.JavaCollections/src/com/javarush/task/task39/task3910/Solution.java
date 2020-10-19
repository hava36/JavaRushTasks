package com.javarush.task.task39.task3910;

/* 
isPowerOfThree
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isPowerOfThree(0));
    }

    public static boolean isPowerOfThree(int n) {
        boolean result = false;
        while (n % 3 == 0 && n != 0) {
            n = n / 3;
        }
        if (n == 1) {
            result = true;
        }
        return result;
    }
}
