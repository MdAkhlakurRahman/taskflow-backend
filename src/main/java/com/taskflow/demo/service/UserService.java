package com.taskflow.demo.service;

import com.taskflow.demo.entity.User;
import com.taskflow.demo.projection.UserLightweightProjection;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface UserService {
    //User createUser(User user);
    User getUserById(Long id);
    Page<User> getAllUsers(Pageable pageable, String search,String searchDomain);
    User updateUser(Long id, User user);
    void deleteUser(Long id);

    Page<UserLightweightProjection> getAllLightUsers(Pageable pageable, String search, String searchDomain);
    User onboardUser(User user);
    void getAllUsersTasks();
}
