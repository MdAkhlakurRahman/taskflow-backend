package com.taskflow.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(@NotBlank(message = "Name can not be blank") String name, @NotBlank(message = "Email cant be blank") @Email(message = "Enter valid email") String email) {

}
