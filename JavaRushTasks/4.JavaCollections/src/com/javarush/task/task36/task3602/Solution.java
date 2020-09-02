package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/*
Найти класс по описанию Ӏ Java Collections: 6 уровень, 6 лекция
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class[] classes = Collections.class.getDeclaredClasses();

        for (Class clazz : classes) {
            if (
                    List.class.isAssignableFrom(clazz) &&
                            Modifier.isPrivate(clazz.getModifiers()) &&
                            Modifier.isStatic(clazz.getModifiers()) &&
                            clazz.getSimpleName().equals("EmptyList")
            ) {
                Method method = Arrays
                        .stream(clazz.getDeclaredMethods())
                        .filter(item ->
                                Objects.equals("get", item.getName()) &&
                                        item.getParameterCount() == 1 &&
                                        Objects.equals(item.getParameterTypes()[0].getSimpleName(), "int")
                        )
                        .findFirst()
                        .orElse(null);

                if (method != null) {
                    try {
                        Constructor constructor = clazz.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        method.setAccessible(true);
                        method.invoke(constructor.newInstance(), 0);
                    } catch (InvocationTargetException | IndexOutOfBoundsException exception) {
                        if (Objects.equals("IndexOutOfBoundsException", exception.getCause().getClass().getSimpleName())) {
                            return clazz;
                        }
                        exception.printStackTrace();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }

        return null;
    }
}
