package com.recipe.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.db.Favorite;
import com.recipe.db.FavoriteRepository;
import com.recipe.db.RecipeRepository;
import com.recipe.util.Utils;

@Service
public class FavoriteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteService.class);

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private Utils utils;

    public void addRecipeToFavorite(String recipeId) {
        String username = utils.extractUsernameFromJwt();

        if (StringUtils.isBlank(recipeId) || StringUtils.isBlank(username)) {
            throw new RuntimeException("RecipeId must be not null and user must be logged with a valid token");
        }

        if (isRelationRecipeAndUserExist(username, recipeId)) {
            LOGGER.info("Favorite relation between user: " + username + " and recipeId: " + recipeId + " already exist.");
            return;
        }

        createFavoriteRelation(username, recipeId);

        LOGGER.info("Successfully created favorite relation in database");
    }

    private boolean isRelationRecipeAndUserExist(String username, String recipeId) {
        Favorite favoriteRecipeRelation = favoriteRepository.findByRecipeIdAndUsername(recipeId, username);
        if (favoriteRecipeRelation != null) {
            return true;
        } else {
            return false;
        }
    }

    private void createFavoriteRelation(String username, String recipeId) {
        Favorite favorite = new Favorite();
        favorite.setRecipeId(recipeId);
        favorite.setUsername(username);

        favoriteRepository.save(favorite);
    }
}
