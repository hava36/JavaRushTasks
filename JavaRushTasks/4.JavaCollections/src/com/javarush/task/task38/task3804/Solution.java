package com.javarush.task.task38.task3804;

/* 
Фабрика исключений
*/

import java.sql.SQLException;

public class Solution {
    public static Class getFactoryClass() {
        return ExceptionFactory.class;
    }

    public static void main(String[] args) throws SQLException {
        Throwable throwable = ExceptionFactory.createExceptionFactory(ApplicationExceptionMessage.SOCKET_IS_CLOSED);
        System.out.println(throwable.toString());

    }
}