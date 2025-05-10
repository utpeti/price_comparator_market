package com.diboti.pricecomparatormarket.service.exceptions;

public class InvalidServiceOperationException extends Exception {
    public InvalidServiceOperationException(String message) {
        super(message);
    }

    public InvalidServiceOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
