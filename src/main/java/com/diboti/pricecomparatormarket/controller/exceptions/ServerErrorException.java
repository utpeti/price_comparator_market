package com.diboti.pricecomparatormarket.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerErrorException extends Exception{
    public ServerErrorException(Exception e) {
        super(e.getMessage());
    }
}
