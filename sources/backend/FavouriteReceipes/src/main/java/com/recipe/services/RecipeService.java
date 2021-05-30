package com.recipe.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.recipe.db.RecipeData;
import com.recipe.db.RecipeRepository;
import com.recipe.exceptions.FlowException;
import com.recipe.openapi.ErrorTypeEnum;
import com.recipe.openapi.Recipe;
import com.recipe.openapi.RecipeResponse;
import com.recipe.util.Utils;

@Service
public class RecipeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    public Utils utils;

    @Autowired
    private RecipeRepository repository;

    /**
     * Search if already exist an recipe with same name and create in database case not
     *
     * @param request
     */
    public void addRecipe(Recipe request) {
        if (StringUtils.isBlank(request.getDish())) return;

        if (checkIfHasARecipeWithSameName(request.getDish())) {
            throw new FlowException("Has already a recipe with same name in database",
                ErrorTypeEnum.DATABASE,
                HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            createRecipeInDatabase(request);
        }

        LOGGER.info("Successfully created recipe in database");
    }

    /**
     * Search and update recipe in database
     *
     * @param request
     */
    public void updateRecipe(Recipe request) {
        if (StringUtils.isBlank(request.getDish())) return;

        RecipeData recipeDataFounded = repository.findByDish(request.getDish());

        if (recipeDataFounded != null) {
            updateRecipeFounded(request, recipeDataFounded);
        }

        LOGGER.info("Successfully updated recipe in database");
    }

    /**
     * Update a recipe in database
     *
     * @param request
     * @param recipeDataFounded
     */
    private void updateRecipeFounded(Recipe request, RecipeData recipeDataFounded) {
        recipeDataFounded.setVegetarian(request.getIsVegetarian());
        recipeDataFounded.setIngredients(request.getIngredients());
        recipeDataFounded.setCookingInstructions(request.getCookingInstructions());

        repository.save(recipeDataFounded);
    }

    /**
     * Create a recipe with incoming parameters in database
     *
     * @param recipeDataRequest
     */
    private void createRecipeInDatabase(Recipe recipeDataRequest) {
        RecipeData recipeData = new RecipeData();
        recipeData.setDish(recipeDataRequest.getDish());
        recipeData.setCookingInstructions(recipeDataRequest.getCookingInstructions());
        recipeData.setDateCreation(new Date(System.currentTimeMillis()));
        recipeData.setIngredients(recipeDataRequest.getIngredients());
        recipeData.setVegetarian(recipeDataRequest.getIsVegetarian());

        repository.save(recipeData);
    }

    /**
     * Search in database for other recipe with the same that we're trying to create
     *
     * @param recipe
     * @return
     */
    public boolean checkIfHasARecipeWithSameName(String recipe) {
        RecipeData recipeDataFounded = repository.findByDish(recipe);

        if (recipeDataFounded != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the recipe using the incoming parameters
     *
     * @param dish
     * @param isVegetarian
     * @return
     */
    public List<RecipeResponse> getRecipe(String dish, Boolean isVegetarian) {
        List<RecipeData> recipesData = findRecipesInDatabase(dish, isVegetarian);

        if (recipesData == null) return null;

        LOGGER.info("Successfully founded items from database.");
        return mapRecipesToResponse(recipesData);
    }

    /**
     * Search for recipes in database
     *
     * @param dish
     * @param isVegetarian
     * @return
     */
    public List<RecipeData> findRecipesInDatabase(String dish, Boolean isVegetarian) {
        if (dish != null && !StringUtils.isBlank(dish) && isVegetarian != null) {
            return repository.findByDishAndIsVegetarian(dish, isVegetarian);
        } else if (dish != null && !StringUtils.isBlank(dish)) {
            RecipeData recipeData = repository.findByDish(dish);
            if (recipeData != null) {
                return List.of(recipeData);
            }
        } else if (isVegetarian != null) {
            return repository.findByIsVegetarian(isVegetarian);
        }

        return null;
    }

    /**
     * Parse the database retrieved information for the expected RecipeResponse.
     *
     * @param recipesData
     * @return
     */
    private List<RecipeResponse> mapRecipesToResponse(List<RecipeData> recipesData) {
        List<RecipeResponse> recipes = new ArrayList<>();

        for (RecipeData recipeData : recipesData) {
            RecipeResponse recipe = utils.mapRecipeDataToRecipeResponse(recipeData);

            recipes.add(recipe);
        }

        LOGGER.info("Response recipe items: " + recipes.toString());

        return recipes;
    }

    /**
     * Removes a recipe from database
     *
     * @param recipeId
     */
    public void removeRecipe(String recipeId) {
        RecipeData recipeData = Optional.of(
            repository.findById(recipeId))
            .orElseThrow(() -> new FlowException("Not found",
                ErrorTypeEnum.DATABASE,
                HttpStatus.NOT_FOUND));

        repository.delete(recipeData);

        LOGGER.info("Successfully removed recipe with recipeId: " + recipeId + " from database");
    }
}
