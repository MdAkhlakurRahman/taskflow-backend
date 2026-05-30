package com.taskflow.demo.controller;

import com.taskflow.demo.dto.UserRequestDTO;
import com.taskflow.demo.dto.UserResponseDTO;
import com.taskflow.demo.entity.User;
import com.taskflow.demo.helper.PaginationHelper;
import com.taskflow.demo.mapper.UserMapper;
import com.taskflow.demo.projection.UserLightweightProjection;
import com.taskflow.demo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;


@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final PaginationHelper paginationHelper;

    public UserController(UserService userService , PaginationHelper paginationHelper) {
        this.userService = userService;
        this.paginationHelper=paginationHelper;
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        User user= UserMapper.userRequestDTOToUser(userRequestDTO);
        return UserMapper.userToResponseDTO(userService.createUser(user));
    }

    @GetMapping
    public Page<UserResponseDTO> getAllUsers(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                             @RequestParam(required = false) @Positive Integer size,
                                             @RequestParam(required = false) String sortBy,
                                             @RequestParam(defaultValue = "asc") String sortDir,
                                             @RequestParam(required = false) String search,
                                             @RequestParam(required = false) String searchDomain){

        Pageable pageable = paginationHelper.buildPageable(page,size,sortBy,sortDir);

        Page<User> users= userService.getAllUsers(pageable, search, searchDomain);
        return users.map(UserMapper::userToResponseDTO);
    }


    @GetMapping("/lightweight")
    public Page<UserLightweightProjection> getAllUsers(@RequestParam(required = false) Pageable pageable,
                                                       @RequestParam(required = false) String search,
                                                       @RequestParam(required = false) String searchDomain){
        return userService.getAllLightUsers(pageable,search,searchDomain);
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
