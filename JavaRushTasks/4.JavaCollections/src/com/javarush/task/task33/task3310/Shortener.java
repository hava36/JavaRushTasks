package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.io.IOException;

public class Shortener {

    private String id;
    private Long lastId = 0L;
    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public synchronized Long getId(String string) throws IOException {

        Long key = storageStrategy.getKey(string);
        if (key == null) {
            lastId++;
            storageStrategy.put(lastId, string);
            return lastId;
        }

        return key;
    }

    public synchronized String getString(Long id) {
        return storageStrategy.getValue(id);
    }
}
