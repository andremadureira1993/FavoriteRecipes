package com.recipe.service;

import java.util.Date;
import java.util.List;

import com.recipe.db.Login;
import com.recipe.db.RecipeData;
import com.recipe.openapi.Recipe;
import com.recipe.openapi.SignUpRequest;

public final class TestsServicesUtils {

    public static SignUpRequest createInvalidSignUpRequest() {
        return new SignUpRequest();
    }

    public static SignUpRequest createValidSignUpRequest(String username, String password) {
        return new SignUpRequest()
            .username(username)
            .password(password);
    }

    public static Login createValidLogin() {
        var login = new Login();
        login.setUsername("andre");
        login.setPassword("pass");

        return login;
    }

    public static Recipe createInvalidRecipe() {
        return new Recipe();
    }

    public static Recipe createValidRecipe() {
        var recipe = new Recipe();
        recipe.dish("dish");
        recipe.cookingInstructions("Cooking");
        recipe.ingredients(List.of("Water", "Coffee"));
        recipe.isVegetarian(false);

        return recipe;
    }

    public static List<RecipeData> createValidRecipesData() {
        return List.of(createValidRecipeData());
    }

    public static RecipeData createValidRecipeData() {
        RecipeData recipeData = new RecipeData();
        recipeData.setQuantityOfPersonsSuitable(1);
        recipeData.setDish("dish");
        recipeData.setCookingInstructions("Cooking");
        recipeData.setDateCreation(new Date());
        recipeData.setVegetarian(false);

        return recipeData;
    }
}
