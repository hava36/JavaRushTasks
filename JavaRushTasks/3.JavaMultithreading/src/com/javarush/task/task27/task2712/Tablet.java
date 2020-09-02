package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {

    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    public void createOrder() {

        try {
            final Order newOrder = new Order(this);
            insideOrder(newOrder);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            return;
        }

    }

    public void createTestOrder() {

        try {
            final Order newOrder = new TestOrder(this);
            insideOrder(newOrder);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            return;
        }

    }

    private void insideOrder(Order order) {
        if (!order.isEmpty()) {
            AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
            try {
                advertisementManager.processVideos();
            } catch (NoVideoAvailableException e) {
                StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(order.getTotalCookingTime() * 60));
                logger.log(Level.INFO, "No video is available for the order " + order.toString());
            }
        }
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Tablet");
        stringBuilder.append("{");
        stringBuilder.append("number=");
        stringBuilder.append(number);
        stringBuilder.append("}");

        return stringBuilder.toString();

    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }
}
