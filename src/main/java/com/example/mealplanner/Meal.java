package com.example.mealplanner;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a meal with a category, name and list of ingredients
 */
@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long meal_id;
    @Column(name = "meal")
    String name;
    @Column(name = "category")
    String category;
    @ElementCollection
    @CollectionTable(name = "food_ingredients", joinColumns = @JoinColumn(name = "food_id"))
    @OrderColumn(name = "ingredient_order")
    private List<String> ingredients;

    /** Constructor for Meal */
    public Meal(String category, String name, List<String> ingredients) {
        this.category = category;
        this.name = name;
        this.ingredients = ingredients;
    }

    /** Empty constructor for Meal */
    public Meal() {}


    /** Getters */
    public Long getMeal_id() {
        return meal_id;
    }

    public String getCategory() {
        return category;
    }
    public List<String> getIngredients() {
        return ingredients;
    }

    public String getIngredientsAsString() {
        return String.join(", ", ingredients);
    }

    public String getName() {
        return name;
    }
}

/** Factory class for creating Meal objects */
class MealFactory {
    public Meal createFood(String category, String name, List<String> ingredients) {
        String[] ingredientsArray = new String[ingredients.size()];
        ingredientsArray = ingredients.toArray(ingredientsArray);
        for (int i = 0; i < ingredientsArray.length; i++) {
            ingredientsArray[i] = ingredientsArray[i].trim();
        }
        return new Meal(category, name, Arrays.asList(ingredientsArray));

    }
}
