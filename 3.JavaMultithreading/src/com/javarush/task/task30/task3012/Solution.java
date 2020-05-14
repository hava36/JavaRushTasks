package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

import java.util.ArrayList;
import java.util.List;

public class Solution {

    StringBuilder s = new StringBuilder();
    List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
    }

    public void createExpression(int number) {

        list.add(number);
        int i = number / 3;
        int io = number % 3;
        s.append(io);
        if (io == 2) i++;
        if (i >= 3) {
            createExpression(i);
        } else {

            List<Integer> integers = new ArrayList<>();
            integers.add(1);
            integers.add(3);
            integers.add(9);
            integers.add(27);
            integers.add(81);
            integers.add(243);
            integers.add(729);
            integers.add(2187);

            s.append(i);
            s = s.reverse();
            List<Character> chars = new ArrayList();
            char[] c = s.toString().toCharArray();
            int i2 = 0;
            if (c[0] == '2') {
                chars.add('+');
                chars.add('-');
                i2 = 1;
            }
            for (int i1 = i2; i1 < c.length; i1++) {
                if (c[i1] == '0') {
                    chars.add('0');
                } else if (c[i1] == '1') {
                    chars.add('+');
                } else if (c[i1] == '2') {
                    chars.add('-');
                }
            }
            int g = list.get(0);
            StringBuilder str = new StringBuilder();
            str.append(g).append(" ").append("=");
            for (int j = chars.size()-1; j >= 0; j--) {
                if (chars.get(j) == '0') continue;
                str.append(" ").append(chars.get(j)).append(" ").append(integers.get(chars.size()-1 -j));
            }
            System.out.println(str);
        }


    }
}