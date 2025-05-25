package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ErrorDto {
    HttpStatus status;
    String message;
}
