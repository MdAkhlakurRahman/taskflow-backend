package com.taskflow.demo.dto.v2;

import com.taskflow.demo.enums.AccountStatus;

public record UserResponseDTO(Long id, String fullName, String email, AccountStatus accountStatus) {
}
