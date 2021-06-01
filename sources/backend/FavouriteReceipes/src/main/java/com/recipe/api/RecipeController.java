package com.recipe.api;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.recipe.openapi.RecipeResponse;
import com.recipe.services.RecipeService;
import com.recipe.openapi.Recipe;
import com.recipe.openapi.RecipesApi;

@Controller
@CrossOrigin
public class RecipeController implements RecipesApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    @Override
    public ResponseEntity<Void> recipesPost(@Valid Recipe request) {
        LOGGER.info("Processing create recipe request. Request: " + request.toString());

        recipeService.addRecipe(request);

        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Void> recipesPut(@Valid Recipe request) {
        LOGGER.info("Processing update recipe request. Request: " + request.toString());

        recipeService.updateRecipe(request);

        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<List<RecipeResponse>> recipesGet(@Valid String dish, @Valid Boolean isVegetarian) {
        LOGGER.info("Processing get recipes request. dish: " + dish + " - isVegetarian: " + isVegetarian);

        return ResponseEntity.ok(recipeService.getRecipe(dish, isVegetarian));
    }

    @Override
    public ResponseEntity<Void> recipesDishIdDelete(String recipeId) {
        LOGGER.info("Processing remove recipe request. Recipe id: " + recipeId);

        recipeService.removeRecipe(recipeId);

        return ResponseEntity.ok(null);
    }

}
