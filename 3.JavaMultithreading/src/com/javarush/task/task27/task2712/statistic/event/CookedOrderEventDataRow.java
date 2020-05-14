package com.javarush.task.task27.task2712.statistic.event;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.util.Date;
import java.util.List;

public class CookedOrderEventDataRow implements EventDataRow {
    Date currentDate;
    String tabletName;
    String cookName;
    int cookingTimeSeconds;
    List<Dish> cookingDishs;

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishs){
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishs = cookingDishs;

        currentDate=new Date();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate()
    {
        return currentDate;
    }

    @Override
    public int getTime()
    {
        return cookingTimeSeconds;
    }


    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getTabletName() {
        return tabletName;
    }

    public void setTabletName(String tabletName) {
        this.tabletName = tabletName;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }


    public void setCookingTimeSeconds(int cookingTimeSeconds) {
        this.cookingTimeSeconds = cookingTimeSeconds;
    }

    public List<Dish> getCookingDishs() {
        return cookingDishs;
    }

    public void setCookingDishs(List<Dish> cookingDishs) {
        this.cookingDishs = cookingDishs;
    }
}
