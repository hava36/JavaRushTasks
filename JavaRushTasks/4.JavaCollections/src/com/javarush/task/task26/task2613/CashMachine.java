package com.javarush.task.task26.task2613;

import java.io.IOException;
import java.util.Locale;

public class CashMachine {

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.ENGLISH);
        String currencyCode = ConsoleHelper.askCurrencyCode();
        String[] digits = ConsoleHelper.getValidTwoDigits(currencyCode);
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        currencyManipulator.addAmount(Integer.parseInt(digits[0]), Integer.parseInt(digits[1]));

        int total = currencyManipulator.getTotalAmount();
    }

}
