package com.example.mealplanner.controller;


import com.example.mealplanner.entity.Meal;
import com.example.mealplanner.entity.User;
import com.example.mealplanner.repository.FoodRepository;
import com.example.mealplanner.repository.MealRepository;
import com.example.mealplanner.repository.UserRepository;
import com.example.mealplanner.service.MealFactory;
import com.example.mealplanner.service.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Controller class for the Meal Planner application.
 * Handles HTTP requests related to meal management, such as adding meals,
 * displaying meals, and generating a meal plan.
 */
@Controller
public class MealController {

    FoodRepository foodRepository;
    @Autowired
    MealRepository mealRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    public MealController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
        System.out.println("FoodRepository instance successfully injected");
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/main-menu")
    public String mainMenu() {
        return "main-menu";
    }

    /** Mapping for adding a meal */
    @GetMapping("/add-meal")
    public String add() {
        return "add";
    }

    /**
     * Handles a POST request to add a new meal for the database.
     * @param category the meal category
     * @param name the meal name
     * @param ingredients a comma-separated list of ingredients
     * @param model the model for the view
     * @param authentication the authenticated user
     * @return the view to render after processing the request
     */
    @PostMapping("/add-meal")
    public String addMeal(@RequestParam String category, @RequestParam String name, @RequestParam String ingredients, Model model, Authentication authentication) {
        String categoryError = Validator.categoryValidator(category);
        String mealError = Validator.mealValidator(name);
        String ingredientsError = Validator.ingredientsValidator(ingredients);

        if (categoryError.isEmpty() && mealError.isEmpty() && ingredientsError.isEmpty()) {
            String loggedInUsername = authentication.getName();
            User loggedInUser = userRepository.findByUsername(loggedInUsername);

            MealFactory mealFactory = new MealFactory();
            List<String> ingredientsList = Arrays.asList(ingredients.split(","));
            ingredientsList.replaceAll(String::trim);
            Meal meal = mealFactory.createFood(category, name, ingredientsList, loggedInUser);
            foodRepository.save(meal);
            model.addAttribute("success", "The meal has been added!");
        } else {
            model.addAttribute("categoryError", categoryError);
            model.addAttribute("mealError", mealError);
            model.addAttribute("ingredientsError", ingredientsError);
        }
        return "add";
    }


    /**
     * Handles a GET request to display all meals stored in the database.
     * @param model the model for the view
     * @param authentication the authenticated user
     * @return the view to render after processing the request
     */
    @GetMapping("/show-db-meals")
    public String showMeals(Model model, Authentication authentication) {
        String loggedInUsername = authentication.getName();
        User loggedInUser = userRepository.findByUsername(loggedInUsername);
        List<Meal> meals = mealRepository.findAllByUser(loggedInUser);
        model.addAttribute("meals", meals);
        return "show-databases-meals";
    }

    /**
     * Handles a GET request to save all meals to a file.
     * @param response the HttpServletResponse to write the file content to
     * @param authentication the authenticated user
     */
    @GetMapping("/save-to-file")
    public void saveToFile(HttpServletResponse response, Authentication authentication) {
        String loggedInUsername = authentication.getName();
        User loggedInUser = userRepository.findByUsername(loggedInUsername);
        List<Meal> meals = mealRepository.findAllByUser(loggedInUser);

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=meals.txt");

        try (PrintWriter writer = response.getWriter()) {
            for (Meal meal : meals) {
                writer.printf("Category: %s\n", meal.getCategory());
                writer.printf("Name: %s\n", meal.getName());
                writer.printf("Ingredients: %s\n\n", meal.getIngredientsAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Handles a GET request to plan a week's meals.
     * @param model the model for the view
     * @param authentication the authenticated user
     * @return the view to render after processing the request
     */
    @GetMapping("/plan-week-diet")
    public String planWeekDiet(Model model, Authentication authentication) {
        List<String> mealCategories = Arrays.asList("Breakfast", "Lunch", "Dinner");
        model.addAttribute("mealCategories", mealCategories);

        List<String> daysOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        model.addAttribute("daysOfWeek", daysOfWeek);

        String loggedInUsername = authentication.getName();
        User loggedInUser = userRepository.findByUsername(loggedInUsername);

        List<Meal> meals = mealRepository.findAllByUser(loggedInUser);
        model.addAttribute("meals", meals);

        return "plan_weekend";
    }

    /**
     * Handles a POST request to generate a meal plan based on user selections.
     * @param mealSelection a map containing the user's meal selections for each day and meal category
     * @param response the HttpServletResponse to write the meal plan file content to
     */
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