package com.example.mealplanner.service;

import com.example.mealplanner.entity.Meal;
import com.example.mealplanner.entity.User;

import java.util.Arrays;
import java.util.List;

/**
 * This class is a factory for creating Meal objects.
 */
public class MealFactory {
    /**
     * Creates a Meal object with the specified parameters.
     * @param category the category of the meal.
     * @param name the name of the meal.
     * @param ingredients the list of ingredients of the meal.
     * @param user the user who created the meal.
     * @return a new Meal object.
     */
    public Meal createFood(String category, String name, List<String> ingredients, User user) {
        String[] ingredientsArray = new String[ingredients.size()];
        ingredientsArray = ingredients.toArray(ingredientsArray);
        for (int i = 0; i < ingredientsArray.length; i++) {
            ingredientsArray[i] = ingredientsArray[i].trim();
        }
        return new Meal(category, name, Arrays.asList(ingredientsArray), user);

    }
}
