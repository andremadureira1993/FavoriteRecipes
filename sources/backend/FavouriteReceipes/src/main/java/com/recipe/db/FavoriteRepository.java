package com.recipe.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, Long> {
    Favorite findByUsername(String username);

    Favorite findByRecipeId(String recipeId);

    Favorite findByRecipeIdAndUsername(String username, String recipeId);
}
