package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

public class StatisticAdvertisementManager {

    private static StatisticAdvertisementManager instance;
    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {

    }

    public List<Advertisement> getActiveVideos() {

        List<Advertisement> activeVideos  = new ArrayList<>();
        List<Advertisement> storageVideos = storage.list();

        for (Advertisement ad:
             storageVideos) {
            if (ad.getHits() > 0) activeVideos.add(ad);
        }

        return activeVideos;

    }

    public List<Advertisement> getArchivedVideos() {

        List<Advertisement> archivedVieos  = new ArrayList<>();
        List<Advertisement> storageVideos = storage.list();

        for (Advertisement ad:
                storageVideos) {
            if (ad.getHits() == 0) archivedVieos.add(ad);
        }

        return archivedVieos;

    }

    public static StatisticAdvertisementManager getInstance() {

        if (instance == null) {
            instance = new StatisticAdvertisementManager();
        }

        return instance;

    }


}
