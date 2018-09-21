package com.kute.exception;

/**
 * created by kute on 2018/09/20 17:20
 */
public class MiddlewareException extends RuntimeException {

    public MiddlewareException() {
    }

    public MiddlewareException(String message) {
        super(message);
    }
}
