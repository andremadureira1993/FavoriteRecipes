package com.recipe.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.recipe.openapi.FavoritesApi;
import com.recipe.openapi.FavoritesResponse;
import com.recipe.openapi.UserFavoritesResponse;
import com.recipe.services.FavoriteService;
import io.swagger.models.Response;

@Controller
@CrossOrigin
public class FavoriteController implements FavoritesApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteController.class);

    @Autowired
    private FavoriteService favoriteService;

    @Override
    public ResponseEntity<Void> favoritesDishIdPost(String dishId) {
        LOGGER.info("Processing create favorite relation");

        favoriteService.addRecipeToFavorite(dishId);

        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<List<FavoritesResponse>> favoritesAllGet() {
        LOGGER.info("Processing get all favorites recipes");

        return ResponseEntity.ok(favoriteService.getTotalOfEachFavoriteRecipe());
    }

    @Override
    public ResponseEntity<Void> favoritesDishIdDelete(String dishId) {
        LOGGER.info("Processing remove favorite request");

        favoriteService.deleteFavorite(dishId);

        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<List<UserFavoritesResponse>> favoritesGet() {
        LOGGER.info("Processing get favorites by user");

        return ResponseEntity.ok(favoriteService.getFavoritesByUser());
    }
}
