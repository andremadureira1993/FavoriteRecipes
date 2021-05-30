package com.recipe.db;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, Long> {
    Favorite findByRecipeIdAndUsername(String username, String recipeId);

    List<Favorite> findByUsername(String username);
}
