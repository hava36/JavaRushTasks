package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {

    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    protected void initDishes() throws IOException {

        ConsoleHelper.writeMessage(Dish.allDishesToString());
        dishes = ConsoleHelper.getAllDishesForOrder();

    }

    @Override
    public String toString() {

        if (dishes.isEmpty()) return "";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Your order:[");

        for (Dish dish:
                dishes) {
            stringBuilder.append(dish.toString());
            stringBuilder.append(", ");
        }
        stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());

        stringBuilder.append("] of ");
        stringBuilder.append(tablet.toString());
        stringBuilder.append(",cooking time ");
        stringBuilder.append(getTotalCookingTime());
        stringBuilder.append("min");

        return stringBuilder.toString();
    }

    public Tablet getTablet() {
        return tablet;
    }

    public int getTotalCookingTime() {

        int totalTime = 0;

        for (Dish dish: dishes
        ) {
            totalTime += dish.getDuration();
        }

        return totalTime;

    }

    public boolean isEmpty() {
        return dishes.size() == 0;
    }

}
