package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    public void testHashMapStorage() throws IOException {

        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<Long> ids1 = new HashSet<>();
        Set<Long> ids2 = new HashSet<>();

        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        Long time1 = getTimeToGetIds(shortener1, origStrings, ids1);
        Long time2 = getTimeToGetIds(shortener2, origStrings, ids2);

        Assert.assertTrue((time1 > time2));

        time1 = getTimeToGetStrings(shortener1,ids1,new HashSet<>());
        time2 = getTimeToGetStrings(shortener2,ids2,new HashSet<>());

        Assert.assertEquals(time1, time2, 30);


    }

    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) throws IOException {

        Date startDate = new Date();
        for (String str:strings) {
            ids.add(shortener.getId(str));
        }
        Date stopDate =  new Date();

        return stopDate.getTime() - startDate.getTime();

    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {

        Date start = new Date();
        for(Long id :ids){
            strings.add(shortener.getString(id));
        }
        Date stop = new Date();
        return stop.getTime()-start.getTime();

    }

}
