package com.taskflow.demo.mapper;

import com.taskflow.demo.dto.UserRequestDTO;
import com.taskflow.demo.dto.UserResponseDTO;
import com.taskflow.demo.entity.User;


public class UserMapper {
    public static User userRequestDTOToUser(UserRequestDTO userRequestDTO){
        return new User(userRequestDTO.name(), userRequestDTO.email());
    }

    public static UserResponseDTO userToResponseDTO(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

}

