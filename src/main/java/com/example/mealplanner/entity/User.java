package com.example.mealplanner.entity;

import javax.persistence.*;
import java.util.List;

/**
 * This entity class represents a User in the application.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Constructs a new User with the specified parameters.
     * @param username the username of the user.
     * @param password the password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User() {}
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meal> meals;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}