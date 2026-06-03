package com.taskflow.demo.controller.v1;

import com.taskflow.demo.dto.UserRequestDTO;
import com.taskflow.demo.dto.v1.UserResponseDTO;
import com.taskflow.demo.entity.User;
import com.taskflow.demo.helper.PaginationHelper;
import com.taskflow.demo.mapper.UserMapper;
import com.taskflow.demo.projection.UserLightweightProjection;
import com.taskflow.demo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;


@RestController
@Validated
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    private final UserService userService;

    private final PaginationHelper paginationHelper;

    public UserControllerV1(UserService userService , PaginationHelper paginationHelper) {
        this.userService = userService;
        this.paginationHelper=paginationHelper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        User user= UserMapper.userRequestDTOToUser(userRequestDTO);
        UserResponseDTO userResponseDTO = UserMapper.userToResponseDTO(userService.createUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                             @RequestParam(required = false) @Positive Integer size,
                                             @RequestParam(required = false) String sortBy,
                                             @RequestParam(defaultValue = "asc") String sortDir,
                                             @RequestParam(required = false) String search,
                                             @RequestParam(required = false) String searchDomain){

        Pageable pageable = paginationHelper.buildPageable(page,size,sortBy,sortDir);

        Page<User> users= userService.getAllUsers(pageable, search, searchDomain);
        return ResponseEntity.ok(users.map(UserMapper::userToResponseDTO));
    }


    @GetMapping("/lightweight")
    public ResponseEntity<Page<UserLightweightProjection>> getAllUsers(@RequestParam(required = false) Pageable pageable,
                                                       @RequestParam(required = false) String search,
                                                       @RequestParam(required = false) String searchDomain){
        return ResponseEntity.ok(userService.getAllLightUsers(pageable,search,searchDomain));
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable @Positive Long id){
        User user= userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.userToResponseDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable @Positive Long id, @RequestBody @Valid UserRequestDTO userRequestDTO){
        User updatedUser = userService.updateUser(id, UserMapper.userRequestDTOToUser(userRequestDTO));
        return ResponseEntity.ok(UserMapper.userToResponseDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive Long id){
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
}
