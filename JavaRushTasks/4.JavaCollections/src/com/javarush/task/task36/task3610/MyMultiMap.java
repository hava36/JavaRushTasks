package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        List<V> values = new ArrayList<>();

        for (Map.Entry<K, List<V>> entry : map.entrySet())
        {
            List<V> itemList = entry.getValue();
            if (itemList != null) {
                for (V v:itemList
                ) {
                    values.add(v);
                }
            }
        }

        return values.size();
    }

    @Override
    public V put(K key, V value) {

        V result = null;

        List<V> valueList = map.get(key);

        if (valueList == null) {
            valueList = new ArrayList<>();
        }

        if (valueList.size() == repeatCount) {
            valueList.remove(0);
        }

        if (valueList.size() > 0) {
            result = valueList.get(valueList.size()-1);
        }

        valueList.add(value);

        map.put(key, valueList);

        return result;

    }

    @Override
    public V remove(Object key) {

        if (map.containsKey(key)) {
            if (map.get(key).size() > 1) {
                V value = map.get(key).get(0);
                map.get(key).remove(0);
                return value;
            } else if (map.get(key).size() <= 1) {
                V result = map.get(key).get(0);
                map.remove(key);
                return result;
            }

        }
        return null;

    }

    @Override
    public Set<K> keySet() {

        Set<K> setKey = new HashSet<K>();
        map.forEach((k, v) -> {
            setKey.add(k);
        });
        return setKey;
    }

    @Override
    public Collection<V> values() {

        List<V> values = new ArrayList<>();

        for (Map.Entry<K, List<V>> entry : map.entrySet())
          {
            List<V> itemList = entry.getValue();
            if (itemList != null) {
                for (V v:itemList
                     ) {
                    values.add(v);
                }
            }
        }

        return values;

    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {

        ArrayList<V> ls = new ArrayList<>();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            for (V v : entry.getValue()) {
                ls.add(v);
            }
        }
        return ls.contains(value);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}