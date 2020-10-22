package com.javarush.task.task39.task3908;

import java.util.HashMap;
import java.util.Map;

/*
Возможен ли палиндром?
*/
public class Solution {
    public static void main(String[] args) {

        System.out.println(isPalindromePermutation("patatap"));

    }

    public static boolean isPalindromePermutation(String s) {
        boolean isPalindromePermutation = true;
        s = s.toLowerCase();
        HashMap<Character, Integer> result = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int value = 0;
            if (result.containsKey(ch)) {
                value = result.get(ch);
            }
            value++;
            result.put(ch, value);
        }
        boolean middleCharIsSet = false;
        for (Map.Entry<Character, Integer> entry: result.entrySet()
             ) {
            if (entry.getValue() % 2 != 0) {
                if (!middleCharIsSet) {
                    middleCharIsSet = true;
                } else {
                    isPalindromePermutation = false;
                    break;
                }
            }
        }
        return isPalindromePermutation;
    }
}
