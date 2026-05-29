package com.taskflow.demo.service;

import com.taskflow.demo.entity.User;
import com.taskflow.demo.exception.UserNotFoundException;
import com.taskflow.demo.projection.UserLightweightProjection;
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
    public Page<User> getAllUsers(Pageable pageable,String search,String searchDomain) {
        boolean hasSearch = search != null && !search.isBlank();
        boolean hasDomain = searchDomain != null && !searchDomain.isBlank();

        if(hasSearch && hasDomain){
            return userRepository.findUsersByDomainAndName(search,searchDomain,pageable);
        }
        else if(!hasSearch && !hasDomain) {
            return userRepository.findAll(pageable);
        }
        else if(hasSearch){
            return userRepository.findByNameContainingIgnoreCase(search,pageable);
        }
        else {
            return userRepository.findUsersByDomain(searchDomain,pageable);
        }
    }





    @Override
    public Page<UserLightweightProjection> getAllLightUsers(Pageable pageable, String search, String searchDomain) {
       return userRepository.findLightUsers(pageable,search,searchDomain);
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
