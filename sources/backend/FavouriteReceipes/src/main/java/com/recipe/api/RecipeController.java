package com.recipe.api;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.recipe.openapi.RecipeRequest;
import com.recipe.openapi.RecipesApi;
import com.recipe.services.RecipeService;

@Controller
public class RecipeController implements RecipesApi {

    @Autowired
    private RecipeService recipeService;

    @Override
    public ResponseEntity<String> recipesPost(@Valid RecipeRequest recipeRequest) {
        recipeService.addRecipe(recipeRequest);

        return null;
    }

    @Override
    public ResponseEntity<String> recipesPut(@Valid RecipeRequest recipeRequest) {
        recipeService.updateRecipe(recipeRequest);

        return null;
    }
}
