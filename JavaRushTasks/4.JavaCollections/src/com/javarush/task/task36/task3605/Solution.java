package com.javarush.task.task36.task3605;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        TreeSet<Character> treeSet = new TreeSet<>();
        new String(Files.readAllBytes(Paths.get(args[0])))
                .toLowerCase().chars().mapToObj(c->(char) c)
                .filter(Character::isLetter)
                .forEach(treeSet::add);

        treeSet.stream().limit(5).forEach(System.out::print);

    }
}
