package com.example.mealplanner.repository;

import com.example.mealplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This repository interface extends JpaRepository and provides
 * methods for interacting with the User entity in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     * @param username the username of the user to find.
     * @return the User object if found, otherwise null.
     */
    User findByUsername(String username);
}