package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds)
    {
        this.timeSeconds = timeSeconds;
    }

    private List<Advertisement> videoSearch(List<Advertisement> list, int timeSeconds)
    {
        List<Advertisement> testList = new ArrayList<>();

        for (Advertisement a : list)
        {
            if (a.getDuration() <= timeSeconds)
                testList.add(a);
        }

        Advertisement savedVideo;

        if (testList.isEmpty())
        {
            return testList;
        }

        savedVideo = testList.get(0);
        testList.remove(savedVideo);

        List<Advertisement> listK1 = videoSearch(testList, timeSeconds - savedVideo.getDuration());
        List<Advertisement> listK2 = videoSearch(testList, timeSeconds);

        listK1.add(savedVideo);

        long sum1 = 0, sum2 = 0;
        long dura1 = 0, dura2 = 0;

        for (Advertisement adv : listK1)
        {
            sum1 += adv.getAmountPerOneDisplaying();
            dura1 += adv.getDuration();
        }

        for (Advertisement adv : listK2)
        {
            sum2 += adv.getAmountPerOneDisplaying();
            dura2 += adv.getDuration();

        }

        if (sum1 > sum2)
        {
            return listK1;
        }

        if (sum1 == sum2)
        {
            if (dura1 > dura2)
                return listK1;

            else return listK2;
        }

        if (dura1 == dura2)
        {
            if (listK1.size() > listK2.size())
                return listK1;

            else return listK2;
        }

        return listK2;
    }

    public void processVideos() throws NoVideoAvailableException
    {
        List<Advertisement> listChosen = new ArrayList<>();

        for (Advertisement adv : storage.list())
        {
            if (adv.getHits() > 0)
                listChosen.add(adv);
        }

        List<Advertisement> finalList = videoSearch(listChosen, timeSeconds);

        if (finalList.isEmpty() || finalList.size() == 0)
        {

            throw new NoVideoAvailableException();
        }

        Collections.sort(finalList, new Comparator<Advertisement>()
        {
            public int compare(Advertisement obj1, Advertisement obj2)
            {
                if (obj1.getAmountPerOneDisplaying() != obj2.getAmountPerOneDisplaying())
                {
                    return Long.compare(obj2.getAmountPerOneDisplaying(), obj1.getAmountPerOneDisplaying());
                } else
                {
                    long x1 = 1000 * obj1.getAmountPerOneDisplaying() / obj1.getDuration();
                    long x2 = 1000 * obj2.getAmountPerOneDisplaying() / obj2.getDuration();

                    return Long.compare(x1, x2);
                }
            }
        });

        long sum = 0;
        int time = 0;

        for (Advertisement a : finalList)
        {
            sum += a.getAmountPerOneDisplaying();
            time += a.getDuration();
        }

        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(finalList, sum, time));

        for (Advertisement elem : finalList)
        {

            ConsoleHelper.writeMessage(elem.getName() + " is displaying... " + elem.getAmountPerOneDisplaying() + ", "
                    + (1000 * elem.getAmountPerOneDisplaying() / elem.getDuration()));

            elem.revalidate();
        }
    }
}