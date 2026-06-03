package com.taskflow.demo.mapper;

import com.taskflow.demo.dto.UserRequestDTO;
import com.taskflow.demo.dto.v1.UserResponseDTO;
import com.taskflow.demo.entity.User;
import com.taskflow.demo.enums.AccountStatus;


public class UserMapper {
    public static User userRequestDTOToUser(UserRequestDTO userRequestDTO){
        return new User(userRequestDTO.name(), userRequestDTO.email());
    }

    public static UserResponseDTO userToResponseDTO(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    public static com.taskflow.demo.dto.v2.UserResponseDTO userToResponseDTOV2(User user){
        return new com.taskflow.demo.dto.v2.UserResponseDTO(user.getId(), user.getName(),user.getEmail(), AccountStatus.ACTIVE);
    }

}

