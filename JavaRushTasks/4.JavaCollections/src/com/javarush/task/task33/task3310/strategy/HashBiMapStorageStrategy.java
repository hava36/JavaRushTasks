package com.javarush.task.task33.task3310.strategy;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.io.IOException;

public class HashBiMapStorageStrategy implements StorageStrategy {

    private HashBiMap data = HashBiMap.create();

    @Override
    public boolean containsKey(Long key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {
        return data.containsValue(value);
    }

    @Override
    public void put(Long key, String value) throws IOException {

        data.put(key,value);

    }

    @Override
    public Long getKey(String value) {

        BiMap<String, Long> reverseMap = data.inverse();

        return reverseMap.get(value);
        
    }

    @Override
    public String getValue(Long key) {
        return (String) data.get(key);
    }
}
