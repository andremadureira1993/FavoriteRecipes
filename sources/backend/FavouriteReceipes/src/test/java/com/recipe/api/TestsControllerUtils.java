package com.recipe.api;

import java.util.List;

import com.recipe.openapi.FavoritesResponse;
import com.recipe.openapi.LoginRequest;
import com.recipe.openapi.LoginResponse;
import com.recipe.openapi.Recipe;
import com.recipe.openapi.RecipeResponse;
import com.recipe.openapi.SignUpRequest;
import com.recipe.openapi.UserFavoritesResponse;

/**
 * Utils class that create request and response objects to be used durint unit tests
 */
public final class TestsControllerUtils {

    private TestsControllerUtils() {}

    public static LoginRequest createValidLoginRequest() {
        return new LoginRequest().username("andre").password("andre");
    }

    public static LoginResponse createValidLoginResponse() {
        return new LoginResponse()
            .accessToken("access-token")
            .refreshToken("");
    }

    public static List<FavoritesResponse> createValidFavoriteListResponse() {
        return List.of(new FavoritesResponse().dish("dish").totalPersonSuitable(1));
    }

    public static List<UserFavoritesResponse> createValidUserFavoriteListResponse() {
        return List.of(new UserFavoritesResponse().recipe(new RecipeResponse()).favoriteId("favoriteId"));
    }

    public static Recipe createValidRecipe() {
        return new Recipe().dish("dish")
            .cookingInstructions("Cooking")
            .isVegetarian(false)
            .ingredients(List.of("water", "coffee"));
    }

    public static RecipeResponse createValidRecipeResponse() {
        return new RecipeResponse()
            .totalPersonSuitable(1)
            .dish("dish")
            .id("1")
            .cookingInstructions("Cooking")
            .ingredients(List.of("water", "Coffee"));
    }

    public static List<RecipeResponse> createValidListOfRecipeResponse() {
        return List.of(createValidRecipeResponse());
    }

    public static SignUpRequest createValidSignUpRequest() {
        return new SignUpRequest()
            .username("andre")
            .password("pass");
    }
}
