package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class StatisticManager {

    private static StatisticManager instance;
    private StatisticStorage statisticStorage = new StatisticStorage();
    private StatisticManager() {

    }



    public static StatisticManager getInstance() {

        if (instance == null)  {
            instance = new StatisticManager();
        }

        return instance;

    }

    public Map<Date, Double> getDateSum()
    {
        Map<Date, Double> resultMap = new TreeMap<>(Collections.reverseOrder());
        for (EventDataRow event : statisticStorage.getEventByType(EventType.SELECTED_VIDEOS))
        {
            Date date = roundDate(event.getDate());
            VideoSelectedEventDataRow eventData = (VideoSelectedEventDataRow) event;
            if (resultMap.containsKey(date))
            {
                resultMap.put(date, resultMap.get(date) + (0.01d * (double) eventData.getAmount()));
            } else
            {
                resultMap.put(date, (0.01d * (double) eventData.getAmount()));
            }
        }
        return resultMap;
    }

    public Map<Date, Map<String, Integer>> getCookTime()
    {
        Map<Date, Map<String, Integer>> resultMap = new TreeMap<>(Collections.reverseOrder());
        for (EventDataRow event : statisticStorage.getEventByType(EventType.COOKED_ORDER))
        {
            Date date = roundDate(event.getDate());
            CookedOrderEventDataRow eventData = (CookedOrderEventDataRow) event;
            int time = eventData.getTime();
            if (time==0)
                continue;
            if (time % 60 == 0) time = time / 60;
            else time = time / 60 + 1;
            if (resultMap.containsKey(date))
            {
                Map<String, Integer> cookInfo = resultMap.get(date);
                if (cookInfo.containsKey(eventData.getCookName()))
                {
                    cookInfo.put(eventData.getCookName(), cookInfo.get(eventData.getCookName()) + time);
                } else
                {
                    cookInfo.put(eventData.getCookName(), time);
                }
            } else
            {
                TreeMap<String, Integer> cookInfo = new TreeMap<>();
                cookInfo.put(eventData.getCookName(), time);
                resultMap.put(date, cookInfo);
            }
        }
        return resultMap;
    }

    private Date roundDate(Date date) {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();

    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }


    protected class StatisticStorage {

        private HashMap<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage=new HashMap<>();
            for (EventType event:EventType.values()){
                storage.put(event,new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {

            List<EventDataRow> list = storage.get(data.getType());
            list.add(data);

        }

        private List<EventDataRow> getEventByType (EventType type) //Возвращает событие по типу
        {
            return storage.get(type);
        }

    }

}
