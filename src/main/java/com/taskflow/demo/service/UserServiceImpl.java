package com.taskflow.demo.service;

import com.taskflow.demo.entity.AuditEvent;
import com.taskflow.demo.entity.User;
import com.taskflow.demo.enums.ActionTaken;
import com.taskflow.demo.exception.UserNotFoundException;
import com.taskflow.demo.projection.UserLightweightProjection;
import com.taskflow.demo.repository.AuditEventRepository;
import com.taskflow.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final AuditEventRepository auditEventRepository;

    public UserServiceImpl(UserRepository userRepository,AuditEventRepository auditEventRepository) {
        this.userRepository = userRepository;
        this.auditEventRepository=auditEventRepository;
    }


//    @Override
//    public User createUser(User user) {
//        User userCreated = userRepository.save(user);
//        log.info("User created with id {}",userCreated.getId());
//            return userCreated;
//    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User lookup failed. User id={} not found", id);
                    return new UserNotFoundException("User not found with id: " + id);
                });
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
                                        .orElseThrow(() ->{
                                            log.warn("User update failed. User id={} not found", id);
                                            return new UserNotFoundException("User not found id:" + id);});
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        User updatedUser =  userRepository.save(existingUser);
        log.info("User updated with User id ={}",id);
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->{
            log.warn("User delete failed. User id={} not found",id);
            return new UserNotFoundException("User Not Found id:" + id);
        } );
        userRepository.delete(user);
        log.info("User deleted with User id={}",id);
    }

    @Transactional
    @Override
    public User onboardUser(User user) {
            User userCreated = userRepository.save(user);
            log.info("User created with id {}",userCreated.getId());

            AuditEvent auditEvent= new AuditEvent(ActionTaken.USER_CREATED,userCreated.getId(),LocalDateTime.now());
            auditEventRepository.save(auditEvent);

            throw new RuntimeException("Boom");
    }
}