package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {

        List<String> fileTree = new ArrayList<String>();

        File folder = new File(root);
        Stack<File> stack = new Stack<File>();
        stack.push(folder);


        while (!stack.isEmpty()) {
            File child = stack.pop();
            if (child.isDirectory()) {
                for (File f:child.listFiles()) {
                    stack.push(f);
                }
            } else if (child.isFile()) {
                fileTree.add(child.getAbsolutePath());
            }
        }

        return fileTree;

    }

    public static void main(String[] args) {
        
    }
}
