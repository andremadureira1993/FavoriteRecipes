package com.recipe.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.recipe.services.RecipeService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link RecipeController}.
 */
@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Test
    void recipesPost() {
        // arrange
        var recipe = TestsControllerUtils.createValidRecipe();
        doNothing().when(recipeService).addRecipe(recipe);

        // act
        var responseEntity = recipeController.recipesPost(recipe);

        // asserts
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateRecipe() {
        // arrange
        var recipe = TestsControllerUtils.createValidRecipe();
        doNothing().when(recipeService).updateRecipe(recipe);

        // act
        var responseEntity = recipeController.recipesPut(recipe);

        // asserts
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void recipesGet() {
        // arrange
        var recipesResponse = TestsControllerUtils.createValidListOfRecipeResponse();
        when(recipeService.getRecipe("dish-id", false)).thenReturn(recipesResponse);

        // act
        var responseEntity = recipeController.recipesGet("dish-id", false);

        // asserts
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void recipesDishIdDelete() {
        // arrange
        var dishMocked = "dish-id";
        doNothing().when(recipeService).removeRecipe(dishMocked);

        // act
        var responseEntity = recipeController.recipesDishIdDelete(dishMocked);

        // asserts
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
