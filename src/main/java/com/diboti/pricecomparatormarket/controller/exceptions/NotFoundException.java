package com.diboti.pricecomparatormarket.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
    public NotFoundException(Class<?> entityClass, String id) {
        super(entityClass.getSimpleName() + " with id " + id + " not found.");
    }
}
