package com.diboti.pricecomparatormarket.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFilterException extends Exception {
    public InvalidFilterException(String message) {
        super(message);
    }
}