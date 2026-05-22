package com.taskflow.demo.service;

import com.taskflow.demo.entity.User;
import com.taskflow.demo.exception.UserNotFoundException;
import com.taskflow.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
            return userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable,String search) {
        if(search==null || search.isBlank()){
            return userRepository.findAll(pageable);
        }
        else {
            return userRepository.findByNameContainingIgnoreCase(search,pageable);
        }
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                                        .orElseThrow(() -> new UserNotFoundException("User not found id:" + id));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found id:" + id));
        userRepository.delete(user);
    }
}
