package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void writeMessage(String message){
        System.out.println(message);
    }
    public static String readString() throws IOException {return reader.readLine();}
    public  static List<Dish> getAllDishesForOrder() throws IOException {
        String readString;
        Dish dish;
        List<Dish> dishes =  new ArrayList<>();
        writeMessage("Введите название блюда, или 'exit'");
        writeMessage(Dish.allDishesToString());
        while (true){
            readString = readString();
            if (readString.equals("exit")) break;
            try {
                dish = Dish.valueOf(readString);
            } catch (IllegalArgumentException e) {
                writeMessage("нет в меню, повторите"); continue;
            }
            if (!dishes.contains(dish)) dishes.add(dish);
        }
        return dishes;
    }
}
