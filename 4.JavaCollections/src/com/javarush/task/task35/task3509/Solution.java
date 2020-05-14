package com.javarush.task.task35.task3509;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/*
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
    }

    public static <T> ArrayList<T> newArrayList(T... elements) {

        ArrayList<T> list = new ArrayList();

        for (T object:elements
             ) {
            list.add(object);
        }

        return list;

    }

    public static <T> HashSet<T> newHashSet(T... elements) {
        HashSet hashSet = new HashSet();
        for (T object:elements
        ) {
            hashSet.add(object);
        }
        return hashSet;
    }

    public static <K,V> HashMap<K,V> newHashMap(List<? extends K> keys, List<? extends V> values) {

        if (keys.size() != values.size()) throw new IllegalArgumentException();

        HashMap<K, V> res = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            res.put(keys.get(i), values.get(i));
        }

        return res;
    }
}
