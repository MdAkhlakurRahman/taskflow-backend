package com.taskflow.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TaskRequestDTO(@NotBlank(message = "Title cannot be blank")
                             @Size(max = 100, message = "Title cannot exceed 100 characters")
                             String title
) {}
