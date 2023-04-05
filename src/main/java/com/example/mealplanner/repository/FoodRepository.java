package com.example.mealplanner.repository;

import com.example.mealplanner.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This repository interface extends JpaRepository and provides
 * methods for interacting with the Meal entity in the database.
 */
@Repository
public interface FoodRepository extends JpaRepository<Meal, Long> {
}