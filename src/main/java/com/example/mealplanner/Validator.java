package com.example.mealplanner;

public class Validator {
    private final static String ingredientsRegex = "([a-zA-Z]+,? ?)+(?<!,)(?<! )";
    public static String categoryValidator(String category) {
        if (!(category.equalsIgnoreCase("breakfast") || category.equalsIgnoreCase("lunch") || category.equalsIgnoreCase("dinner"))) {
            return "Wrong meal category! Choose from: breakfast, lunch, dinner.";
        }
        return "";
    }

    public static String mealValidator(String mealName) {
        String menuRegex = "[a-zA-Z ]+$";
        if (!mealName.matches(menuRegex)) {
            return "Wrong meal name format! Use only letters and spaces.";
        }
        return "";
    }

    public static String ingredientsValidator(String ingredients) {
        if (!ingredients.matches(ingredientsRegex)) {
            return "Wrong ingredients format! Use comma-separated list of ingredients.";
        }
        return "";
    }
}
