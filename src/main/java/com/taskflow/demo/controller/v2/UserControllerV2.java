package com.taskflow.demo.controller.v2;

import com.taskflow.demo.api.ApiResponse;
import com.taskflow.demo.dto.TaskRequestDTO;
import com.taskflow.demo.dto.TaskResponseDTO;
import com.taskflow.demo.dto.UserRequestDTO;
import com.taskflow.demo.dto.v2.UserResponseDTO;
import com.taskflow.demo.entity.Task;
import com.taskflow.demo.entity.User;
import com.taskflow.demo.helper.PaginationHelper;
import com.taskflow.demo.mapper.TaskMapper;
import com.taskflow.demo.mapper.UserMapper;
import com.taskflow.demo.projection.UserLightweightProjection;
import com.taskflow.demo.service.TaskService;
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

import java.util.ArrayList;
import java.util.List;


@RestController
@Validated
@RequestMapping("/api/v2/users")
public class UserControllerV2 {

    private final UserService userService;

    private final PaginationHelper paginationHelper;

    private final TaskService taskService;

    public UserControllerV2(UserService userService , PaginationHelper paginationHelper, TaskService taskService) {
        this.userService = userService;
        this.paginationHelper=paginationHelper;
        this.taskService=taskService;
    }

    @PostMapping
    public ResponseEntity<com.taskflow.demo.dto.v2.UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        User user= UserMapper.userRequestDTOToUser(userRequestDTO);
        com.taskflow.demo.dto.v2.UserResponseDTO userResponseDTO = UserMapper.userToResponseDTOV2(userService.onboardUser(user));
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
        return ResponseEntity.ok(users.map(UserMapper::userToResponseDTOV2));
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
        return ResponseEntity.ok(UserMapper.userToResponseDTOV2(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable @Positive Long id, @RequestBody @Valid UserRequestDTO userRequestDTO){
        User updatedUser = userService.updateUser(id, UserMapper.userRequestDTOToUser(userRequestDTO));
        return ResponseEntity.ok(UserMapper.userToResponseDTOV2(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/tasks")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> createTask(@PathVariable @Positive Long userId, @RequestBody @Valid TaskRequestDTO taskRequestDTO){
        TaskResponseDTO taskResponseDTO = TaskMapper.taskToTaskResponseDTO(taskService.createTaskForUser(userId,taskRequestDTO.title()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(taskResponseDTO,"Task Created Successfully"));
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getAllTask(@PathVariable @Positive Long userId){
        List<Task> tasks = taskService.getAllTasks(userId);
        List<TaskResponseDTO> lists = new ArrayList<>();
        for(Task task : tasks)
            lists.add(TaskMapper.taskToTaskResponseDTO(task));
        return ResponseEntity.ok(ApiResponse.success(lists));
    }

    @GetMapping("/allTasks")
    public ResponseEntity<Void> checkNplus(){
        userService.getAllUsersTasks();
        return ResponseEntity.noContent().build();
    }
}
