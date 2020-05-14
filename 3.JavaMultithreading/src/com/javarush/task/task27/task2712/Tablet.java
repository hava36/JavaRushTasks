package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;


import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet extends Observable {
    final int number;
    static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Tablet{number="+number+"}";
    }

    public Order createOrder(){
        Order order;
        try {
            order = new Order(this);
            if (order.isEmpty()) return null;
            setChanged();
            notifyObservers(order);
            ConsoleHelper.writeMessage(order.toString());
        } catch (IOException e) {
            //e.printStackTrace();
            logger.log(Level.SEVERE,"Console is unavailable.");
            return null;
        }
        try {
            (new AdvertisementManager(order.getTotalCookingTime()*60)).processVideos();
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO,"No video is available for the order " + order);
        }
        return order;
    }

}
