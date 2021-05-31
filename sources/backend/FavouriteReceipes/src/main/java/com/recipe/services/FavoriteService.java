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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.recipe.db.Favorite;
import com.recipe.db.FavoriteRepository;
import com.recipe.db.RecipeData;
import com.recipe.db.RecipeRepository;
import com.recipe.exceptions.FlowException;
import com.recipe.openapi.ErrorTypeEnum;
import com.recipe.openapi.FavoritesResponse;
import com.recipe.openapi.RecipeResponse;
import com.recipe.openapi.UserFavoritesResponse;
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

        increaseTheQuantityOfSuitablePersonsByRecipe(recipeData);

        LOGGER.info("Successfully create relation for username: " + username + " and recipeId: " + recipeId);
    }

    public List<FavoritesResponse> getTotalOfEachFavoriteRecipe() {
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

        totals.forEach((dish, total) -> {
            FavoritesResponse favorite = new FavoritesResponse();
            favorite.setDish(dish);
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
            .orElseThrow(() -> {
                throw new FlowException("Relation was not found in database for username: " + username + " and recipeId: " + recipeId,
                    ErrorTypeEnum.DATABASE,
                    HttpStatus.INTERNAL_SERVER_ERROR);
            });

        favoriteRepository.delete(favorite);

        decreaseTheQuantityOfSuitablePersonsByRecipe(favorite.getDish());

        LOGGER.info("Successfully removed relation for username: " + username + " and recipeId: " + recipeId);
    }

    public void validateRecipeIdAndUsername(String username, String recipeId) {
        if (StringUtils.isBlank(recipeId) || StringUtils.isBlank(username)) {
            throw new FlowException("RecipeId must be not null and user must be logged with a valid token",
                ErrorTypeEnum.INVALID_REQUEST,
                HttpStatus.BAD_REQUEST);
        }
    }

    public List<UserFavoritesResponse> getFavoritesByUser() {
        String username = utils.extractUsernameFromJwt();

        if (StringUtils.isBlank(username)) {
            throw new FlowException("User must be logged with a valid token",
                ErrorTypeEnum.INVALID_REQUEST,
                HttpStatus.BAD_REQUEST);
        }

        List<Favorite> favorites = Optional.of(
            favoriteRepository.findByUsername(username))
            .orElseThrow(() -> {
                throw new FlowException("No favorite was found for the username: " + username,
                    ErrorTypeEnum.INVALID_REQUEST,
                    HttpStatus.NOT_FOUND);
            });

        return parseResponseFavoritesByUserFavorites(favorites);
    }

    private List<UserFavoritesResponse> parseResponseFavoritesByUserFavorites(List<Favorite> favorites) {
        List<UserFavoritesResponse> response = new ArrayList<>();

        for (Favorite favorite : favorites) {
            UserFavoritesResponse userFavorite = new UserFavoritesResponse();

            userFavorite.setFavoriteId(favorite.getId());
            RecipeData recipeData = recipeRepository.findByDish(favorite.getDish());
            RecipeResponse recipe = utils.mapRecipeDataToRecipeResponse(recipeData);

            userFavorite.setRecipe(recipe);

            response.add(userFavorite);
        }

        LOGGER.info("Successfully created user favorites response: " + response.toString());

        return response;
    }

    private void increaseTheQuantityOfSuitablePersonsByRecipe(RecipeData recipeData) {
        if (recipeData.getQuantityOfPersonsSuitable() == null) {
            recipeData.setQuantityOfPersonsSuitable(1);
        } else {
            Integer quantity = recipeData.getQuantityOfPersonsSuitable() + 1;
            recipeData.setQuantityOfPersonsSuitable(quantity);
        }

        recipeRepository.save(recipeData);
    }

    private void decreaseTheQuantityOfSuitablePersonsByRecipe(String dish) {
        RecipeData recipeData = Optional.of(
            recipeRepository.findByDish(dish))
            .orElseThrow(() -> {
                throw new FlowException("Could not update the recipe decreasing the quantity of suitable persons",
                    ErrorTypeEnum.DATABASE,
                    HttpStatus.INTERNAL_SERVER_ERROR);
            });

        if (recipeData.getQuantityOfPersonsSuitable() == null) {
            recipeData.setQuantityOfPersonsSuitable(0);
        } else {
            Integer quantity = recipeData.getQuantityOfPersonsSuitable() - 1;
            recipeData.setQuantityOfPersonsSuitable(quantity);
        }

        recipeRepository.save(recipeData);
    }
}
