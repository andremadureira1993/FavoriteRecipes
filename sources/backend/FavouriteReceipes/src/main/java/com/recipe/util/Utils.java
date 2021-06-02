package com.recipe.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.recipe.db.RecipeData;
import com.recipe.openapi.RecipeResponse;

@Component
public class Utils {

    @Autowired
    StringEncryptor encryptor;

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public String encodePassword(String password) {
        return encryptor.encrypt(password);
    }

    public String parseFromDateToString(Date date) {
      return sdf.format(date);
    }

    public String extractUsernameFromJwt() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return principal.toString();
    }

    public RecipeResponse mapRecipeDataToRecipeResponse(RecipeData recipeData) {

        RecipeResponse recipe = new RecipeResponse();

        recipe.setId(recipeData.getId());
        recipe.setDish(recipeData.getDish());
        recipe.setCookingInstructions(recipeData.getCookingInstructions());
        recipe.setDateAndTimeOfCreation(parseFromDateToString(recipeData.getDateCreation()));
        recipe.setIngredients(recipeData.getIngredients());
        recipe.setIsVegetarian(recipeData.isVegetarian());
        recipe.setTotalPersonSuitable(recipeData.getQuantityOfPersonsSuitable());

        return recipe;
    }
}
