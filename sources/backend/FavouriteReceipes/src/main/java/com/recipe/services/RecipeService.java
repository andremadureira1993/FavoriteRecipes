package com.recipe.services;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.db.Recipe;
import com.recipe.db.RecipeRepository;
import com.recipe.openapi.RecipeRequest;

@Service
public class RecipeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    private RecipeRepository recipeRepository;

    public void addRecipe(RecipeRequest request) {
        if (StringUtils.isBlank(request.getRecipe())) return;

        if(checkIfHasARecipeWithSameName(request.getRecipe())) {
            throw new RuntimeException("Has already a recipe with same name in database");
        } else {
            createRecipeInDatabase(request);
        }
    }

    public void updateRecipe(RecipeRequest request) {
        if (StringUtils.isBlank(request.getRecipe())) return;

        Recipe recipeFounded = recipeRepository.findByRecipe(request.getRecipe());

        if(recipeFounded != null) {
            updateRecipeFounded(request, recipeFounded);
        }

    }

    private void updateRecipeFounded(RecipeRequest request, Recipe recipeFounded) {
        recipeFounded.setVegetarian(request.getIsVegeratian());
        recipeFounded.setIngredients(request.getIngredients());
        recipeFounded.setCookingInstructions(request.getCookingInstructions());

        recipeRepository.save(recipeFounded);
    }

    private void createRecipeInDatabase(RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe();
        recipe.setRecipe(recipeRequest.getRecipe());
        recipe.setCookingInstructions(recipeRequest.getCookingInstructions());
        recipe.setDateCreation(new Date(System.currentTimeMillis()));
        recipe.setIngredients(recipeRequest.getIngredients());
        recipe.setVegetarian(recipeRequest.getIsVegeratian());

        recipeRepository.save(recipe);

        LOGGER.info("Successfully created recipe in database");
    }

    public boolean checkIfHasARecipeWithSameName(String recipe) {
        Recipe recipeFounded = recipeRepository.findByRecipe(recipe);

        if(recipeFounded != null) {
            return true;
        } else {
            return false;
        }
    }

}
