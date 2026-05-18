package com.taskflow.demo.controller;

import com.taskflow.demo.dto.UserRequestDTO;
import com.taskflow.demo.dto.UserResponseDTO;
import com.taskflow.demo.entity.User;
import com.taskflow.demo.mapper.UserMapper;
import com.taskflow.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO){
        User user= UserMapper.userRequestDTOToUser(userRequestDTO);
        return UserMapper.userToResponseDTO(userService.createUser(user));
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers(){

        List<User> userlist= userService.getAllUsers();
        List<UserResponseDTO> userResponseDTO= new ArrayList<>();

        for(User user: userlist){
            userResponseDTO.add(UserMapper.userToResponseDTO(user));
        }
        return userResponseDTO;
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id){
        User user= userService.getUserById(id);
        return UserMapper.userToResponseDTO(user);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO){
        User updatedUser = userService.updateUser(id, UserMapper.userRequestDTOToUser(userRequestDTO));
        return UserMapper.userToResponseDTO(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

}
