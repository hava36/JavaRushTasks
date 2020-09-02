package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {

    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        Locale.setDefault(Locale.ENGLISH);
        Cook cookAmigo = new Cook("Amigo");
        cookAmigo.setQueue(orderQueue);
        Cook cookJohn = new Cook("Mamigo");
        cookJohn.setQueue(orderQueue);
        //StatisticManager.getInstance().register(cookAmigo);
        //StatisticManager.getInstance().register(cookJohn);
        List<Tablet> tablets = new ArrayList<>();
        //OrderManager orderManager = new OrderManager();
        for(int i = 0;i < 5; i++){
            Tablet tablet = new Tablet(i);
           // tablet.addObserver(orderManager);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }
        Waiter waitor = new Waiter(); // Задача 19 Прошла после того, как объявление waitor опустил вниз, добавил в main InterruptedException
        cookAmigo.addObserver(waitor);
        cookJohn.addObserver(waitor);

        Thread t = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        t.start();
        try
        {
            Thread.sleep(2000);

        }
        catch (InterruptedException e)
        {

        }
        t.interrupt();


        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }

}
