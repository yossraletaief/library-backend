package com.library.management.library_Backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)  // 502 Bad Gateway
public class AiApiException extends RuntimeException {
    public AiApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
