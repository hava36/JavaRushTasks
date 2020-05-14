package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        if (args.length < 2) return;

        String resultFileName = args[0];
        int filePartCount = args.length - 1;
        String[] fileNamePart = new String[filePartCount];

        for (int i = 0; i < filePartCount; i++)
        {
            fileNamePart[i] = args[i + 1];
        }

        Arrays.sort(fileNamePart);

        List<FileInputStream> fisList = new ArrayList<>();

        for (int i = 0; i < filePartCount; i++)
        {
            fisList.add(new FileInputStream(fileNamePart[i]));
        }

        SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(fisList));
        ZipInputStream zipReader = new ZipInputStream(sequenceInputStream);
        FileOutputStream fos = new FileOutputStream(resultFileName);
        byte[] buf = new byte[1024*1024];
        while (zipReader.getNextEntry() != null) {
            int count;
            while ((count = zipReader.read(buf)) != -1)
            {
                fos.write(buf, 0, count);
            }
        }
        sequenceInputStream.close();
        zipReader.close();
        fos.close();


    }
}
