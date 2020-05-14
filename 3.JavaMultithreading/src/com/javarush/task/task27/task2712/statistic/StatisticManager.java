package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class StatisticManager {

    private static StatisticManager ourInstance = new StatisticManager();
    private StatisticStorage storage = new StatisticStorage();
    private Set<Cook> cooks = new HashSet<>();

    public static StatisticManager getInstance()
    {
        return ourInstance;
    }

    private StatisticManager()
    {
    }

    public void register(EventDataRow data)
    {
        if (data == null) return;
        storage.put(data);
    }

    public Map<Date, Double> getDateSum()
    {
        Map<Date, Double> resultMap = new TreeMap<>(Collections.reverseOrder());
        for (EventDataRow event : storage.getEventByType(EventType.SELECTED_VIDEOS))
        {
            Date date = dateToStringMidnight(event.getDate());
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
        for (EventDataRow event : storage.getEventByType(EventType.COOKED_ORDER))
        {
            Date date = dateToStringMidnight(event.getDate());
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
    private Date dateToStringMidnight(Date date) //приведение даты к полночи
    {
        GregorianCalendar roundedDate = new GregorianCalendar();
        roundedDate.setTime(date);
        roundedDate.set(Calendar.HOUR_OF_DAY, 0);
        roundedDate.set(Calendar.MINUTE, 0);
        roundedDate.set(Calendar.SECOND, 0);
        roundedDate.set(Calendar.MILLISECOND, 0);
        // roundedDate.add(Calendar.DAY_OF_MONTH, (int) (Math.random()*3)); // рандом даты для тестов
        //  roundedDate.add(Calendar.MONTH, (int) (Math.random()*3));       //
        return roundedDate.getTime();
    }
    private static class StatisticStorage  //"хранилище должно быть приватным иннер(вложенным) классом." пробовал и вложенный и иннер.
    {
        private Map<EventType, List<EventDataRow>> map = new HashMap<>();
        private StatisticStorage()
        {
            for (EventType eventType : EventType.values())
            {
                map.put(eventType, new ArrayList<EventDataRow>());
            }
        }
        private void put(EventDataRow data)
        {
            if (data == null) return;
            map.get(data.getType()).add(data);
        }
        private List<EventDataRow> getEventByType (EventType type) //Возвращает событие по типу
        {
            return map.get(type);
        }
    }

}
