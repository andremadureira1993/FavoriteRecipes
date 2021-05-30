package com.recipe.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.recipe.openapi.FavoritesApi;
import com.recipe.openapi.FavoritesResponse;
import com.recipe.services.FavoriteService;
import io.swagger.models.Response;

@Controller
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
    public ResponseEntity<List<FavoritesResponse>> favoritesGet() {
        LOGGER.info("Processing get all favorites recipes");

        return ResponseEntity.ok(favoriteService.getTotalOfEachFavoriteRecipe());
    }
}
