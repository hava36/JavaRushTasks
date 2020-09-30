package com.javarush.task.task38.task3804;

public class ExceptionFactory {

    public static Throwable createExceptionFactory(Enum e) {
        if (e != null) {
            String message = e.toString().replaceAll("_", " ").toLowerCase();
            message = message.substring(0, 1).toUpperCase() + message.substring(1);

            if (e instanceof ApplicationExceptionMessage) {
                return new Exception(message);
            }

            if (e instanceof DatabaseExceptionMessage) {
                return new RuntimeException(message);
            }

            if (e instanceof UserExceptionMessage)
                return new Error(message);
        }
        return new IllegalArgumentException();
    }


}
