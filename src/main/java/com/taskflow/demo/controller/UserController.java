package com.taskflow.demo.controller;

import com.taskflow.demo.dto.UserRequestDTO;
import com.taskflow.demo.dto.UserResponseDTO;
import com.taskflow.demo.entity.User;
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
        //DTO -> entity
        User user=new User(userRequestDTO.name(),userRequestDTO.email());
        User createdUser =userService.createUser(user);
        return new UserResponseDTO(createdUser.getId(), createdUser.getName(), createdUser.getEmail());
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers(){

        List<User> userlist= userService.getAllUsers();
        List<UserResponseDTO> userResponseDTO= new ArrayList<>();

        for(User user: userlist){
            UserResponseDTO createdUserResponseDTO=new UserResponseDTO(user.getId(), user.getName(),user.getEmail());
            userResponseDTO.add(createdUserResponseDTO);
        }
        return userResponseDTO;
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id){
        User user= userService.getUserById(id);
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO){
        //DTO to entity
        User user = new User(userRequestDTO.name(),userRequestDTO.email());
        User updatedUser = userService.updateUser(id, user);

        //Entity to DTO
        return new UserResponseDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

}
