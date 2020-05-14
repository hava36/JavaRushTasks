package com.javarush.task.task31.task3101;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        final ArrayList<File> list = new ArrayList<>();//
        File path = new File(args[0]);//
        File resultFileAbsolutePath = new File(args[1]);//
        File allFilesContent = new File(resultFileAbsolutePath.getParent() + "/allFilesContent.txt");//

        try (FileOutputStream writer = new FileOutputStream(allFilesContent);)// поток записи
        {
            FileUtils.renameFile(resultFileAbsolutePath, allFilesContent);//
            Files.walkFileTree(path.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toFile().length() > 50) FileUtils.deleteFile(file.toFile());
                    else list.add(file.toFile());
                    return FileVisitResult.CONTINUE;
                }
            });
            Collections.sort(list, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            for (File file : list) {
                FileReader reader = new FileReader(file);
                while (reader.ready()) writer.write(reader.read());
                reader.close();
                writer.write("\n".getBytes());
            }
            writer.close();
        }
    }

    public static void deleteFile(File file) {
        if (!file.delete()) System.out.println("Can not delete file with name " + file.getName());
    }
}
