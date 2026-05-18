package com.taskflow.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleUserNotFoundException(UserNotFoundException e) {
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("message" , e.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        return errorResponse;
    }
}
