package com.taskflow.demo.repository;

import com.taskflow.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
}
//“Spring, create a database manager for User entities whose IDs are Long.”