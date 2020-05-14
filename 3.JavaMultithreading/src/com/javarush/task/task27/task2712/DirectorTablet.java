package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {

    private DateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    public void printAdvertisementProfit()
    {

        double total = 0d;

        for (Map.Entry<Date, Double> entry : StatisticManager.getInstance().getDateSum().entrySet())
        {
            double profit = entry.getValue();
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s - %.2f", df.format(entry.getKey()), profit));
            total += profit;
        }

        ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "Total - %.2f", total));

    }

    public void printCookWorkloading()
    {

        for (Map.Entry<Date, Map<String, Integer>> entry : StatisticManager.getInstance().getCookTime().entrySet())

        {
            ConsoleHelper.writeMessage(df.format(entry.getKey()));
            for (Map.Entry<String, Integer> cooksEntry : entry.getValue().entrySet())
            {
                ConsoleHelper.writeMessage(String.format("%s - %d min", cooksEntry.getKey(), cooksEntry.getValue()));
            }
            ConsoleHelper.writeMessage("");
        }

    }

    public void printActiveVideoSet()
    {

    }

    public void printArchivedVideoSet()
    {

    }
}
