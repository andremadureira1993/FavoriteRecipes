package com.recipe.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, Long> {
    Recipe findByRecipe(String recipe);
}
