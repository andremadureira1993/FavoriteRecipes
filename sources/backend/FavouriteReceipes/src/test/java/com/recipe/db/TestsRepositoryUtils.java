package com.recipe.db;

import java.util.List;

import com.recipe.db.RecipeData;

/**
 * Utils class to create objects to be used during tests
 */
public final class TestsRepositoryUtils {

    private TestsRepositoryUtils() {}

    /**
     * Create a valid favorite item to be saved in database
     * @return
     */
    public static Favorite createValidFavorite() {
        var favorite = new Favorite();
        favorite.setDish("Dish");
        favorite.setUsername("andre");
        favorite.setRecipeId("id");

        return favorite;
    }

    /**
     * Create a valid login to be saved in database
     * @return
     */
    public static Login createValidLogin() {
        var login = new Login();
        login.setUsername("andre");
        login.setPassword("password");

        return login;
    }

    /**
     * Create a valid recipe to be saved in database
     * @return
     */
    public static RecipeData createValidRecipe() {
        var recipe = new RecipeData();
        recipe.setVegetarian(false);
        recipe.setIngredients(List.of("Water", "Coffee"));
        recipe.setCookingInstructions("Cooking...");
        recipe.setDish("Coffee");

        return recipe;
    }
}
