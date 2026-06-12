package com.taskflow.demo.exception;

import com.taskflow.demo.api.ApiResponse;
import com.taskflow.demo.api.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleUserNotFoundException(UserNotFoundException e) {
        log.warn(e.getMessage());

        return ApiResponse.failure(e.getMessage(), Collections.emptyList());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidationException(
            MethodArgumentNotValidException e) {

        List<ErrorDetail> errors = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errors.add(new ErrorDetail(
                                    "VALIDATION_ERROR",
                                    fieldName,
                                    message));
                });

        return ApiResponse.failure("Validation failed", errors);
    }

    @ExceptionHandler(InvalidPageSizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleInvalidPageSizeException(InvalidPageSizeException e) {

        return ApiResponse.failure(e.getMessage(), Collections.emptyList());
    }

    @ExceptionHandler(InvalidSortParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleInvalidSortParameterException(
            InvalidSortParameterException e) {

        return ApiResponse.failure(e.getMessage(), Collections.emptyList());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleAllExceptions(
            Exception e) {

        log.error("Unexpected exception occurred", e);

        return ApiResponse.failure("An unexpected error occurred", Collections.emptyList());
    }
}