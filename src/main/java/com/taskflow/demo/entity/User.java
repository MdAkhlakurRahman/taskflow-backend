package com.taskflow.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Table(name = "users")
    private Long id;

    private String name;
    private String email;

    //CONSTRUCTORS

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }


    //getters and setters
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
