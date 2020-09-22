package com.javarush.task.task38.task3803;

/*
Runtime исключения (unchecked exception)
*/

import java.util.Date;
import java.util.List;

public class VeryComplexClass {

    public void methodThrowsClassCastException() {
        Object date = new Date();
        int i = (int) date;
    }

    public void methodThrowsNullPointerException() {
        List<String> list = null;
        list.size();
    }

    public static void main(String[] args) {
        VeryComplexClass solution = new VeryComplexClass();
        //solution.methodThrowsClassCastException();
        solution.methodThrowsNullPointerException();
    }
}
