package com.javarush.task.task32.task3210;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {

        String filename = args[0];
        int number = Integer.valueOf(args[1]);
        String text = args[2];

        RandomAccessFile raf = new RandomAccessFile(filename, "rw");

        byte[] b = new byte[text.length()];

        raf.seek(number);
        raf.read(b, 0, text.length());

        String additionalText = "";
        String textFromFile = new String(b);

        if (textFromFile.equals(text)) {
            additionalText = "true";
        } else {
            additionalText = "false";
        }
        raf.seek(raf.length());
        raf.write(additionalText.getBytes());

        raf.close();


    }
}
