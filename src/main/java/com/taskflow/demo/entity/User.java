package com.taskflow.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=? ")
@Where(clause = "deleted=false")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private String email;

    private boolean deleted = Boolean.FALSE;

    public boolean isDeleted() {
        return deleted;
    }
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
