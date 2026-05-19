package com.taskflow.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UserResponseDTO(Long id, String name,String email) {

}
