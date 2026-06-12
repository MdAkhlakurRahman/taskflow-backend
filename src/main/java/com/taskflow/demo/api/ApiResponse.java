package com.taskflow.demo.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@AllArgsConstructor
@Getter
public class ApiResponse<T> {

    private final T data;
    private final String message;
    private final boolean success;
    private final List<ErrorDetail> errors;
    private final LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                data,
                null,
                true,
                Collections.emptyList(),
                null
        );
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(
                data,
                message,
                true,
                Collections.emptyList(),
                null
        );
    }

    public static <T> ApiResponse<T> failure(String message,
                                             List<ErrorDetail> errors) {
        return new ApiResponse<>(
                null,
                message,
                false,
                errors,
                LocalDateTime.now()
        );
    }
}