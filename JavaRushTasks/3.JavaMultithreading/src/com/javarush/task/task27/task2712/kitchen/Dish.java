package com.javarush.task.task27.task2712.kitchen;

public enum Dish {

    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);

    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public static String allDishesToString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Dish value:Dish.values()
             ) {
            stringBuilder.append(value.toString());
            stringBuilder.append(",");
        }

        return stringBuilder.toString();

    }

    public int getDuration() {
        return duration;
    }
}
