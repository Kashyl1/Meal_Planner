package com.example.mealplanner.repository;


import com.example.mealplanner.entity.Meal;
import com.example.mealplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This repository interface extends JpaRepository and provides
 * methods for interacting with the Meal entity in the database
 * based on the User entity.
 */
@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    /**
     * Finds all meals created by a specific user.
     * @param user the user whose meals should be retrieved.
     * @return a list of meals created by the given user.
     */
    List<Meal> findAllByUser(User user);

}
