package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution {

    static int countFolders = 0;
    static int countFiles = 0;
    static long countSize = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String dirName = bufferedReader.readLine();
        Path path = Paths.get(dirName);

        if (!Files.isDirectory(path)) {
            System.out.println(path.toAbsolutePath().toString() + " - не папка");
            return;
        }

        Files.walkFileTree(path, new MyFileVisitor());
        System.out.println("Всего папок - " + (countFolders-1));
        System.out.println("Всего файлов - " + countFiles);
        System.out.println("Общий размер - " + countSize);

    }

    private static class MyFileVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            countFolders++;
            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            countFiles++;
            countSize += attrs.size();
            return super.visitFile(file, attrs);
        }
    }
}
