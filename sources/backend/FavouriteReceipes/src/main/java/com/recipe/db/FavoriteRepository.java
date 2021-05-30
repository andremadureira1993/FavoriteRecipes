package com.recipe.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, Long> {
    Favorite findByRecipeIdAndUsername(String username, String recipeId);
}
