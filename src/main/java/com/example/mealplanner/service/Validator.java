package com.example.mealplanner.service;

/**
 * This class provides methods for validating input data.
 */
public class Validator {
    private final static String ingredientsRegex = "([a-zA-Z]+,? ?)+(?<!,)(?<! )";

    /**
     * Validates the meal category.
     * @param category the meal category to validate.
     * @return an error message if the category is invalid, otherwise an empty string.
     */
    public static String categoryValidator(String category) {
        if (!(category.equalsIgnoreCase("breakfast") || category.equalsIgnoreCase("lunch") || category.equalsIgnoreCase("dinner"))) {
            return "Wrong meal category! Choose from: breakfast, lunch, dinner.";
        }
        return "";
    }

    /**
     * Validates the meal name.
     * @param mealName the meal name to validate.
     * @return an error message if the meal name is invalid, otherwise an empty string.
     */
    public static String mealValidator(String mealName) {
        String menuRegex = "[a-zA-Z ]+$";
        if (!mealName.matches(menuRegex)) {
            return "Wrong meal name format! Use only letters and spaces.";
        }
        return "";
    }

    /**
     * Validates the ingredients list.
     * @param ingredients the ingredients list to validate.
     * @return an error message if the ingredients list is invalid, otherwise an empty string.
     */
    public static String ingredientsValidator(String ingredients) {
        if (!ingredients.matches(ingredientsRegex)) {
            return "Wrong ingredients format! Use comma-separated list of ingredients.";
        }
        return "";
    }
}
