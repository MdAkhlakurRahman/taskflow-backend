package com.taskflow.demo.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDetail {
    private final String code;
    private final String field;
    private final String message;
}
