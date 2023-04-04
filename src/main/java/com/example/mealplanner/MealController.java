package com.example.mealplanner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/** Controller for handling meal planner operations */
@Controller
public class MealController {

    private MealPlanner mealPlanner = MealPlanner.getInstance();
    FoodRepository foodRepository;
    @Autowired
    MealRepository mealRepository;
    @Autowired
    public MealController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
        System.out.println("FoodRepository instance successfully injected");
    }

    /** Mapping for index */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /** Mapping for adding a meal */
    @GetMapping("/add-meal")
    public String add() {
        return "add";
    }

    /** Mapping for adding a meal with POST request */
    @PostMapping("/add-meal")
    public String addMeal(@RequestParam String category, @RequestParam String name, @RequestParam String ingredients, Model model) {
        String categoryError = Validator.categoryValidator(category);
        String mealError = Validator.mealValidator(name);
        String ingredientsError = Validator.ingredientsValidator(ingredients);

        if (categoryError.isEmpty() && mealError.isEmpty() && ingredientsError.isEmpty()) {
            MealFactory mealFactory = new MealFactory();
            List<String> ingredientsList = Arrays.asList(ingredients.split(","));
            ingredientsList.replaceAll(String::trim);
            Meal meal = mealFactory.createFood(category, name, ingredientsList);
            mealPlanner.addMeal(meal);
            foodRepository.save(meal);
            model.addAttribute("success", "The meal has been added!");
        } else {
            model.addAttribute("categoryError", categoryError);
            model.addAttribute("mealError", mealError);
            model.addAttribute("ingredientsError", ingredientsError);
        }
        return "add";
    }


    /** Mapping for showing current session meals */
    @GetMapping("/show-current-session-meals")
    public String show(Model model) {
        model.addAttribute("meals", mealPlanner.getMeals());
        return "show";
    }

    /** Mapping for showing database meals */
    @GetMapping("/show-db-meals")
    public String showMeals(Model model) {
        List<Meal> meals = mealRepository.findAll();
        model.addAttribute("meals", meals);
        return "show-databases-meals";
    }
    /** Mapping for saving meals to a file */
    @GetMapping("/save-to-file")
    public void saveToFile(HttpServletResponse response) {
        List<Meal> meals = mealRepository.findAll();

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=meals.txt");

        try (PrintWriter writer = response.getWriter()) {
            for (Meal meal : meals) {
                writer.printf("Category: %s\n", meal.getCategory());
                writer.printf("Name: %s\n", meal.getName());
                writer.printf("Ingredients: %s\n\n", meal.getIngredientsAsString());
            }
        } catch (IOException e) {
            // Obsłuż błąd zapisywania pliku, np. logując wyjątek
            e.printStackTrace();
        }
    }


    /** Mapping for planning a week's diet */
    @GetMapping("/plan-week-diet")
    public String planWeekDiet(Model model) {
        List<String> mealCategories = Arrays.asList("Breakfast", "Lunch", "Dinner");
        model.addAttribute("mealCategories", mealCategories);

        List<String> daysOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        model.addAttribute("daysOfWeek", daysOfWeek);

        List<Meal> meals = mealRepository.findAll();
        model.addAttribute("meals", meals);

        return "plan_weekend";
    }
    /** Mapping for generating meal plan with POST request */
    @PostMapping("/plan-week-diet")
    public void generateMealPlan(@RequestParam Map<String, String> mealSelection, HttpServletResponse response) {
        List<String> daysOfWeek = Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday");
        List<String> mealCategories = Arrays.asList("breakfast", "lunch", "dinner");

        StringBuilder mealPlanBuilder = new StringBuilder();

        for (String day : daysOfWeek) {
            mealPlanBuilder.append(day.substring(0, 1).toUpperCase()).append(day.substring(1)).append(":\n");
            for (String category : mealCategories) {
                String key = day + "_" + category;
                if (mealSelection.containsKey(key)) {
                    Long mealId = Long.parseLong(mealSelection.get(key));
                    mealRepository.findById(mealId).ifPresent(meal -> mealPlanBuilder.append(category.substring(0, 1).toUpperCase()).append(category.substring(1))
                            .append(": ")
                            .append(meal.getName())
                            .append("\n"));
                }
            }
            mealPlanBuilder.append("\n");
        }

        String mealPlan = mealPlanBuilder.toString();

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=meal_plan.txt");

        try (PrintWriter writer = response.getWriter()) {
            writer.print(mealPlan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}