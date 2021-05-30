package com.recipe.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.db.RecipeData;
import com.recipe.db.RecipeRepository;
import com.recipe.util.Utils;
import com.recipe.openapi.Recipe;

@Service
public class RecipeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    private RecipeRepository repository;

    @Autowired
    public Utils utils;

    public void addRecipe(Recipe request) {
        if (StringUtils.isBlank(request.getDish())) return;

        if(checkIfHasARecipeWithSameName(request.getDish())) {
            throw new RuntimeException("Has already a recipe with same name in database");
        } else {
            createRecipeInDatabase(request);
        }
    }

    public void updateRecipe(Recipe request) {
        if (StringUtils.isBlank(request.getDish())) return;

        RecipeData recipeDataFounded = repository.findByDish(request.getDish());

        if(recipeDataFounded != null) {
            updateRecipeFounded(request, recipeDataFounded);
        }

    }

    private void updateRecipeFounded(Recipe request, RecipeData recipeDataFounded) {
        recipeDataFounded.setVegetarian(request.getIsVegetarian());
        recipeDataFounded.setIngredients(request.getIngredients());
        recipeDataFounded.setCookingInstructions(request.getCookingInstructions());

        repository.save(recipeDataFounded);
    }

    private void createRecipeInDatabase(Recipe recipeDataRequest) {
        String abc = recipeDataRequest.toString();
        RecipeData recipeData = new RecipeData();
        recipeData.setDish(recipeDataRequest.getDish());
        recipeData.setCookingInstructions(recipeDataRequest.getCookingInstructions());
        recipeData.setDateCreation(new Date(System.currentTimeMillis()));
        recipeData.setIngredients(recipeDataRequest.getIngredients());
        recipeData.setVegetarian(recipeDataRequest.getIsVegetarian());

        repository.save(recipeData);

        LOGGER.info("Successfully created recipe in database");
    }

    public boolean checkIfHasARecipeWithSameName(String recipe) {
        RecipeData recipeDataFounded = repository.findByDish(recipe);

        if(recipeDataFounded != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<Recipe> getRecipe(String dish, Boolean isVegetarian) {
        List<RecipeData> recipesData = findRecipesInDatabase(dish, isVegetarian);

        if (recipesData == null) return null;

        return mapRecipesToResponse(recipesData);
    }

    public List<RecipeData> findRecipesInDatabase(String dish, Boolean isVegetarian)  {
        if (dish != null && !StringUtils.isBlank(dish) && isVegetarian != null) {
            return repository.findByDishAndIsVegetarian(dish, isVegetarian);
        } else if (dish != null && !StringUtils.isBlank(dish) ) {
            RecipeData recipeData = repository.findByDish(dish);
            if (recipeData != null) {
                return List.of(recipeData);
            }
        } else if (isVegetarian != null) {
            return repository.findByIsVegetarian(isVegetarian);
        }

        return null;
    }

    private List<Recipe> mapRecipesToResponse(List<RecipeData> recipesData) {
        List<Recipe> recipes = new ArrayList<>();

        for (RecipeData recipeData: recipesData) {
            Recipe recipe = new Recipe();
            recipe.setDish(recipeData.getDish());
            recipe.setCookingInstructions(recipeData.getCookingInstructions());
            recipe.setDateAndTimeOfCreation(utils.parseFromDateToString(recipeData.getDateCreation()));
            recipe.setIngredients(recipeData.getIngredients());
            recipe.setIsVegetarian(recipeData.isVegetarian());

            recipes.add(recipe);
        }

        return recipes;
    }
}
