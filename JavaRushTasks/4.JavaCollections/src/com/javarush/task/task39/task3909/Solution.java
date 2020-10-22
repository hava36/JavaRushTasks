package com.javarush.task.task39.task3909;

import java.util.Arrays;

/*
Одно изменение
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isOneEditAway("pathp", "patap"));
    }

    public static boolean isOneEditAway(String first, String second) {
        char[] f = first.toCharArray();
        char[] s = second.toCharArray();
        boolean result = false;
        if (f.length == s.length) {
            return checkerWithoutLengthDifference(f, s);
        } else {
            result = checkerWithLengthDifference(f, s);
            if (!result) {
                return checkerWithLengthDifference(s, f);
            }
        }
        return false;
    }

    private static boolean checkerWithLengthDifference(char[] f, char[] s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < f.length; i++) {
            sb.setLength(0);
            for (int j = 0; j < f.length; j++) {
                if (i == j) continue;
                sb.append(f[j]);
            }
            if (Arrays.equals(sb.toString().toCharArray(), s)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkerWithoutLengthDifference(char[] f, char[] s) {
        if (Arrays.equals(f, s)) return true;
        int index = 0;
        for (int i = 0; i < f.length; i++) {
            if (f[i] != s[i]) {
                index++;
            }
        }
        return index == 1;
    }

}
