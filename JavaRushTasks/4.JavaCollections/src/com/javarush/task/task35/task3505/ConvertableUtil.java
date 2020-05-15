package com.javarush.task.task35.task3505;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertableUtil {

    public static <T extends Convertable> Map convert(List<T> list) {
        Map result = new HashMap();
        for (Convertable element: list
             )
        {
            result.put(element.getKey(), element);
        }
        return result;
    }
}
