package com.ws.hw1.utils;

import com.ws.hw1.exceptionHandler.exception.NotFoundException;

public class Guard {
    public static void check(boolean condition, String error) {
        if (!condition)
            throw new NotFoundException(error);
    }
}
