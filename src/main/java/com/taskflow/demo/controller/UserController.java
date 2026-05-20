package com.taskflow.demo.controller;

import com.taskflow.demo.dto.UserRequestDTO;
import com.taskflow.demo.dto.UserResponseDTO;
import com.taskflow.demo.entity.User;
import com.taskflow.demo.exception.InvalidPageSizeException;
import com.taskflow.demo.mapper.UserMapper;
import com.taskflow.demo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        User user= UserMapper.userRequestDTOToUser(userRequestDTO);
        return UserMapper.userToResponseDTO(userService.createUser(user));
    }

    @GetMapping
    public Page<UserResponseDTO> getAllUsers(Pageable pageable){
        if(pageable.getPageSize()>100)
            throw new InvalidPageSizeException("Page size is too big");
        Page<User> users= userService.getAllUsers(pageable);
        return users.map(UserMapper::userToResponseDTO);
    }


    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable @Positive Long id){
        User user= userService.getUserById(id);
        return UserMapper.userToResponseDTO(user);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable @Positive Long id, @RequestBody @Valid UserRequestDTO userRequestDTO){
        User updatedUser = userService.updateUser(id, UserMapper.userRequestDTOToUser(userRequestDTO));
        return UserMapper.userToResponseDTO(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @Positive Long id){
        userService.deleteUser(id);
    }

}
