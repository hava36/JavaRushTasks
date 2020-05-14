package com.javarush.task.task27.task2712.kitchen;

public enum Dish {
    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);

    private int duration;

    public int getDuration() {
        return duration;
    }

    Dish (int duration) {
        this.duration = duration;
    }

    public static String allDishesToString(){
        StringBuilder allDishes = new StringBuilder("");
        boolean first = false;
        for (Dish dish : Dish.values()) {
            if (first) allDishes.append(",");
            first=true;
            allDishes.append(dish);
        }
        return allDishes.toString();
    }
}
