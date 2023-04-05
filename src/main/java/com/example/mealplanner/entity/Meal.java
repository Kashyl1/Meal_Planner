package com.example.mealplanner.entity;

import javax.persistence.*;
import java.util.List;

/**
 * This entity class represents a Meal in the application.
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ElementCollection
    @CollectionTable(name = "food_ingredients", joinColumns = @JoinColumn(name = "food_id"))
    @OrderColumn(name = "ingredient_order")
    private List<String> ingredients;

    /**
     * Constructs a new Meal with the specified parameters.
     * @param category the category of the meal.
     * @param name the name of the meal.
     * @param ingredients the list of ingredients of the meal.
     * @param user the user who created the meal.
     */
    public Meal(String category, String name, List<String> ingredients, User user) {
        this.category = category;
        this.name = name;
        this.ingredients = ingredients;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

