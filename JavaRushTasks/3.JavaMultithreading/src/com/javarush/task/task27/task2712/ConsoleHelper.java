package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bufferedReader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {

        List<Dish> dishList = new ArrayList<Dish>();

        writeMessage("Выберите блюдо: "+Dish.allDishesToString());

        String currentString = "";

        while (true) {
            currentString = readString();
            if (currentString.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                dishList.add(Dish.valueOf(currentString));
                writeMessage("Выберите блюдо: "+Dish.allDishesToString());
                continue;
            } catch (IllegalArgumentException e ) {
                writeMessage("такого блюда нет, повторите");
            }
        }

        return dishList;

    }

}
