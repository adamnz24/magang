package com.test.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomIllegalArgumentException extends RuntimeException {

    public CustomIllegalArgumentException(String message) {
        super(message);
    }
}
