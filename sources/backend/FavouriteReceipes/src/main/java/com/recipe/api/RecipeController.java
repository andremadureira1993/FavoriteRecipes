package com.recipe.api;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.recipe.services.RecipeService;
import com.recipeData.openapi.Recipe;
import com.recipeData.openapi.RecipesApi;

@Controller
public class RecipeController implements RecipesApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    @Override
    public ResponseEntity<Void> recipesPost(@Valid Recipe request) {
        recipeService.addRecipe(request);

        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Void> recipesPut(@Valid Recipe request) {
        recipeService.updateRecipe(request);

        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<List<Recipe>> recipesGet(@Valid String dish, @Valid Boolean isVegetarian) {

        LOGGER.info("Processing get recipes request");

        return ResponseEntity.ok(recipeService.getRecipe(dish, isVegetarian));
    }
}
