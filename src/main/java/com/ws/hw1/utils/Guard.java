package com.ws.hw1.utils;

import com.ws.hw1.exceptionhandler.exception.NotFoundException;

public class Guard {
    public static void check(boolean condition, String message) {
        if (!condition)
            throw new NotFoundException(message);
    }
}
