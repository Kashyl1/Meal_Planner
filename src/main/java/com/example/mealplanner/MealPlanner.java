package com.example.mealplanner;

import java.util.ArrayList;
import java.util.List;

public class MealPlanner {

    private static MealPlanner instance;

    private List<Meal> meals;

    private MealPlanner() {
        this.meals = new ArrayList<>();
    }

    public static MealPlanner getInstance() {
        if (instance == null) {
            instance = new MealPlanner();
        }
        return instance;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }
}