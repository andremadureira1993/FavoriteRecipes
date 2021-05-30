package com.recipe.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.db.Favorite;
import com.recipe.db.FavoriteRepository;
import com.recipe.db.RecipeData;
import com.recipe.db.RecipeRepository;
import com.recipe.openapi.FavoritesResponse;
import com.recipe.util.Utils;

@Service
public class FavoriteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteService.class);

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private Utils utils;

    public void addRecipeToFavorite(String recipeId) {
        String username = utils.extractUsernameFromJwt();

        validateRecipeIdAndUsername(username, recipeId);

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

        RecipeData recipeData = recipeRepository.findById(recipeId);
        favorite.setDish(recipeData.getDish());

        favoriteRepository.save(favorite);

        LOGGER.info("Successfully create relation for username: " + username + " and recipeId: " + recipeId);
    }

    public List<FavoritesResponse> getTotalOfEachFavoriteRecipe() {
        List<FavoritesResponse> favoritesResponses = new ArrayList<>();

        List<Favorite> favorites = favoriteRepository.findAll();

        return createTheTotalOfEachFavorite(favorites);
    }

    private List<FavoritesResponse> createTheTotalOfEachFavorite(List<Favorite> favorites) {
        if (favorites == null || favorites.size() == 0) return null;

        Map<String, Integer> totalOfEachFavorite = new HashMap<>();

        for (Favorite favorite : favorites) {
            String dishKey = favorite.getDish();
            if (totalOfEachFavorite.containsKey(dishKey)) {
                totalOfEachFavorite.put(dishKey, totalOfEachFavorite.get(dishKey) + 1);
            } else {
                totalOfEachFavorite.put(dishKey, 1);
            }
        }

        return parseResponseFavoritesTotal(totalOfEachFavorite);
    }

    private List<FavoritesResponse> parseResponseFavoritesTotal(Map<String, Integer> totals) {
        List<FavoritesResponse> favoritesTotal = new ArrayList<>();

        totals.forEach((key, total) -> {
            FavoritesResponse favorite = new FavoritesResponse();
            favorite.setDish(key);
            favorite.setTotalPersonSuitable(total);
            favoritesTotal.add(favorite);
        });

        LOGGER.info("Successfully parsed all totals of each recipe favorites");
        return favoritesTotal;
    }

    public void deleteFavorite(String recipeId) {
        String username = utils.extractUsernameFromJwt();

        validateRecipeIdAndUsername(username, recipeId);

        Favorite favorite = Optional.of(
            favoriteRepository.findByRecipeIdAndUsername(recipeId, username))
            .orElseThrow(() -> new RuntimeException("Relation was not found in database forusername: " + username + " and recipeId: " + recipeId));

        favoriteRepository.delete(favorite);

        LOGGER.info("Successfully removed relation for username: " + username + " and recipeId: " + recipeId);
    }

    public void validateRecipeIdAndUsername(String username, String recipeId) {
        if (StringUtils.isBlank(recipeId) || StringUtils.isBlank(username)) {
            throw new RuntimeException("RecipeId must be not null and user must be logged with a valid token");
        }
    }
}
