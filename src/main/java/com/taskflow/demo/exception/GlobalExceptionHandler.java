package com.taskflow.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,List<String>> methodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, List<String>> errorResponse = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            if(!errorResponse.containsKey(fieldName)){
                errorResponse.put(fieldName, new ArrayList<>());
            }
            errorResponse.get(fieldName).add(message);
        });
        return errorResponse;
    }

    @ExceptionHandler(InvalidPageSizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> invalidPageSizeException(InvalidPageSizeException e){
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("message",e.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        return errorResponse;
    }
}
