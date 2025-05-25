package com.diboti.pricecomparatormarket.controller.exceptions.utils;

import com.diboti.pricecomparatormarket.controller.exceptions.BadRequestException;
import com.diboti.pricecomparatormarket.controller.exceptions.NotFoundException;
import com.diboti.pricecomparatormarket.controller.exceptions.ServerErrorException;
import com.diboti.pricecomparatormarket.dto.outgoing.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDto handleBadRequest(BadRequestException e) {
        return new ErrorDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorDto handleNotFound(NotFoundException e) {
        return new ErrorDto(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ErrorDto handleServerError(ServerErrorException e) {
        return new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
