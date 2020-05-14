package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Dish;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import javafx.scene.control.Tab;

import java.io.IOException;

public class Restaurant {

    public static void main(String[] args) throws Exception {

        Cook cook = new Cook("Amigo");
        Tablet tablet = new Tablet(5);
        Waiter waiter = new Waiter();

        cook.addObserver(waiter);
        tablet.addObserver(cook);
        tablet.createOrder();

        DirectorTablet directorTablet = new DirectorTablet();

        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }

}
