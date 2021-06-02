package com.recipe.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Integration tests for {@link RecipeRepository}.
 */
@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeRepositoryIT {
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setup() {
        recipeRepository.deleteAll();
    }

    @Test
    void canSave() {
        // arrange
        var recipe = TestsRepositoryUtils.createValidRecipe();

        // act and assert
        Assertions.assertDoesNotThrow(() -> recipeRepository.save(recipe));
    }

}
