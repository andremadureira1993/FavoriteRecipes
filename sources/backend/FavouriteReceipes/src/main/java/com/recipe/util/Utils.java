package com.recipe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.recipe.db.RecipeData;
import com.recipe.openapi.RecipeResponse;

@Component
public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    public Date parseFromStringToDate(String dateValue) {
        Date date = null;
        try {

            date = sdf.parse(dateValue);
            if (!dateValue.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            LOGGER.error("Error parsing string date to format dd-MM-yyyy HH:mm. Error: " + ex.getMessage());
        }

        return date;
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
