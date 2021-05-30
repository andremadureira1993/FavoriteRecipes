package com.recipe.db;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<RecipeData, Long> {
    RecipeData findById(String id);

    RecipeData findByDish(String dish);

    List<RecipeData> findByIsVegetarian(Boolean isVegetarian);

    List<RecipeData> findByDishAndIsVegetarian(String dish, Boolean isVegetarian);
}
