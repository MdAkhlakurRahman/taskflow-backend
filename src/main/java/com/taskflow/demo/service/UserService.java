package com.taskflow.demo.service;

import com.taskflow.demo.entity.User;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    Page<User> getAllUsers(Pageable pageable);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
