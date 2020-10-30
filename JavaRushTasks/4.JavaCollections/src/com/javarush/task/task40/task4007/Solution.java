package com.javarush.task.task40.task4007;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* 
Работа с датами
*/

public class Solution {
    public static void main(String[] args) {
        printDate("21.4.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {

        String formatstring = "";
        boolean printDate = false;
        boolean printTime = false;

        if (date.matches("[\\d]+[.][\\d]+[.][\\d]+")) {
            formatstring = "dd.M.yyyy";
            printDate = true;
        } else if (date.matches("[\\d]+[.][\\d]+[.][\\d]+[\\s][\\d]+[:][\\d]+[:][\\d]+")) {
            formatstring = "dd.M.yyyy hh:mm:ss";
            printTime = true;
            printDate = true;
        } else if ((date.matches("[\\d]+[:][\\d]+[:][\\d]+"))) {
            formatstring = "hh:mm:ss";
            printTime = true;
        }
        if (!formatstring.isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatstring);
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(simpleDateFormat.parse(date));
                if (printDate) {
                    System.out.println(String.format("День: %s", calendar.get(Calendar.DATE)));
                    System.out.println(String.format("День недели: %s", (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1)));
                    System.out.println(String.format("День месяца: %s", calendar.get(Calendar.DAY_OF_MONTH)));
                    System.out.println(String.format("День года: %s", calendar.get(Calendar.DAY_OF_YEAR)));
                    System.out.println(String.format("Неделя месяца: %s", calendar.get(Calendar.WEEK_OF_MONTH)));
                    System.out.println(String.format("Неделя года: %s", calendar.get(Calendar.WEEK_OF_YEAR)));
                    System.out.println(String.format("Месяц: %s", (calendar.get(Calendar.MONTH) + 1)));
                    System.out.println(String.format("Год: %s", calendar.get(Calendar.YEAR)));
                }
                if (printTime) {
                    System.out.println(String.format("AM или PM: %s", (calendar.get(Calendar.AM_PM) == Calendar.PM ? "PM" : "AM")));
                    System.out.println(String.format("Часы: %s", calendar.get(Calendar.HOUR)));
                    System.out.println(String.format("Часы дня: %s", calendar.get(Calendar.HOUR_OF_DAY)));
                    System.out.println(String.format("Минуты: %s", calendar.get(Calendar.MINUTE)));
                    System.out.println(String.format("Секунды: %s", calendar.get(Calendar.SECOND)));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
