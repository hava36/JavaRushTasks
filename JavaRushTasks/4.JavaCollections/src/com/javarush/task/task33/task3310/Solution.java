package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    public static void main(String[] args) throws IOException {

        testStrategy(new HashMapStorageStrategy(), 10000);
        
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) throws IOException {
        Set<Long> result = new HashSet<>();
        for (String str : strings) {
            result.add(shortener.getId(str));
        }
        return result;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> result = new HashSet<>();
        for (Long key : keys) {
            result.add(shortener.getString(key));
        }
        return result;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) throws IOException {
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> strings = new HashSet<>();
        Set<Long> resultKeys;
        Set<String> resultStrings;
        for (long i = 0; i < elementsNumber; i++) {
            strings.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);
        Date startDate = new Date();
        resultKeys = getIds(shortener, strings);
        Date endDate = new Date();
        long resultTime = endDate.getTime() - startDate.getTime();
        Helper.printMessage(Long.toString(resultTime));
        startDate = new Date();
        resultStrings = getStrings(shortener, resultKeys);
        endDate = new Date();
        resultTime = endDate.getTime() - startDate.getTime();
        Helper.printMessage(Long.toString(resultTime));
        if (strings.equals(resultStrings)) {
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");
        }
    }


}
