package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;

public interface StorageStrategy {

    boolean containsKey(Long key);
    boolean containsValue(String value);
    void put(Long key, String value) throws IOException;
    Long getKey(String value);
    String getValue(Long key);

}
