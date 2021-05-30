package com.recipe.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.recipe.openapi.FavoritesApi;
import com.recipe.services.FavoriteService;

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
}
