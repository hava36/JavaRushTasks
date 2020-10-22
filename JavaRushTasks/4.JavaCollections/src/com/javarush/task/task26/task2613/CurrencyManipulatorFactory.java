package com.javarush.task.task26.task2613;

import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {

    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory() {
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        if (map.containsKey(currencyCode.toLowerCase())) {
            return map.get(currencyCode.toLowerCase());
        }
        CurrencyManipulator currencyManipulator = new CurrencyManipulator(currencyCode);
        map.put(currencyCode.toLowerCase(), currencyManipulator);
        return currencyManipulator;
    }

}
