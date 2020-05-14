package com.javarush.task.task32.task3204;

import sun.security.util.Password;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Random random = new Random();

        // digits
        for (int i = 0; i < 3; i++) {
            byteArrayOutputStream.write(48 + random.nextInt(10));
        }

        // lower letters
        for (int i = 0; i < 3; i++) {
            byteArrayOutputStream.write(65 + random.nextInt(26));
        }

        // capital letters
        for (int i = 0; i < 2; i++) {
            byteArrayOutputStream.write(97 + random.nextInt(26));
        }

        return byteArrayOutputStream;
    }
}