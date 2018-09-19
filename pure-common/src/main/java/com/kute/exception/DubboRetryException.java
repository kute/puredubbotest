package com.kute.exception;

/**
 * created by kute on 2018/08/06 13:35
 */
public class DubboRetryException extends RuntimeException {

    public DubboRetryException(String message) {
        super(message);
    }
}
