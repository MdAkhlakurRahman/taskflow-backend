package com.taskflow.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(mappedBy ="user")
    private List<Task> tasks;

    public boolean isDeleted() {
        return deleted;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
