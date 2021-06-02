package com.recipe.service;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.recipe.db.RecipeData;
import com.recipe.db.RecipeRepository;
import com.recipe.exceptions.FlowException;
import com.recipe.openapi.RecipeResponse;
import com.recipe.services.RecipeService;
import com.recipe.util.Utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Integration tests for {@link RecipeService}.
 */
@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeData recipeData;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private Utils utils;

    @Test
    void addInvalidRecipe() {
        // arrange
        var request = TestsServicesUtils.createInvalidRecipe();

        // act and assert
        assertThatThrownBy(
            () -> recipeService.addRecipe(request))
            .isInstanceOf(FlowException.class);
    }

    @Test
    void addValidRecipe() {
        // arrange
        var request = TestsServicesUtils.createValidRecipe();
        when(recipeRepository.findByDish(request.getDish())).thenReturn(null);

        // act and assert
        Assertions.assertDoesNotThrow(() -> recipeService.addRecipe(request));
    }

    @Test
    void addDuplicatedRecipe() {
        // arrange
        var request = TestsServicesUtils.createValidRecipe();
        when(recipeRepository.findByDish(request.getDish())).thenReturn(new RecipeData());

        // act and assert
        assertThatThrownBy(
            () -> recipeService.addRecipe(request))
            .isInstanceOf(FlowException.class);
    }

    @Test
    void updateInvalidRecipe() {
        // arrange
        var request = TestsServicesUtils.createInvalidRecipe();

        // act and assert
        assertThatThrownBy(
            () -> recipeService.updateRecipe(request))
            .isInstanceOf(FlowException.class);
    }

    @Test
    void updateValidRecipe() {
        // arrange
        var request = TestsServicesUtils.createValidRecipe();
        when(recipeRepository.findByDish(request.getDish())).thenReturn(null);

        // act and assert
        Assertions.assertDoesNotThrow(() -> recipeService.updateRecipe(request));
    }

    @Test
    void updateDuplicatedRecipe() {
        // arrange
        var request = TestsServicesUtils.createValidRecipe();
        when(recipeRepository.findByDish(request.getDish())).thenReturn(new RecipeData());

        // act and assert
        Assertions.assertDoesNotThrow(() -> recipeService.updateRecipe(request));
    }

    @Test
    void getRecipe() {
        // arrange
        var recipesData = TestsServicesUtils.createValidRecipesData();
        when(recipeRepository.findByDishAndIsVegetarian("dish", false)).thenReturn(recipesData);

        // act
        List<RecipeResponse> recipes = recipeService.getRecipe("dish", false);

        // assert
        assertThat(recipes).isNotNull();
        assertThat(recipes)
            .hasSize(1);
    }

    @Test
    void getNullRecipe() {
        // arrange
        when(recipeRepository.findByDishAndIsVegetarian("dish", false)).thenReturn(null);

        // act
        List<RecipeResponse> recipes = recipeService.getRecipe("dish", false);

        // assert
        assertThat(recipes).isNull();
    }

    @Test
    void getNullRecipeByIsVegetarian() {
        // arrange
        when(recipeRepository.findByIsVegetarian(false)).thenReturn(null);

        // act
        List<RecipeResponse> recipes = recipeService.getRecipe(null,false);

        // assert
        assertThat(recipes).isNull();
    }

    @Test
    void getRecipeByIsVegetarian() {
        // arrange
        var recipesData = TestsServicesUtils.createValidRecipesData();
        when(recipeRepository.findByIsVegetarian(false)).thenReturn(recipesData);

        // act
        List<RecipeResponse> recipes = recipeService.getRecipe(null,false);

        // assert
        assertThat(recipes).isNotNull();
        assertThat(recipes).hasSize(1);
    }

    @Test
    void getNullRecipeByDish() {
        // arrange
        when(recipeRepository.findByDish("dish")).thenReturn(null);

        // act
        List<RecipeResponse> recipes = recipeService.getRecipe("dish",null);

        // assert
        assertThat(recipes).isNull();
    }

    @Test
    void getRecipeByDish() {
        // arrange
        var recipesData = TestsServicesUtils.createValidRecipeData();
        when(recipeRepository.findByDish("dish")).thenReturn(recipesData);

        // act
        List<RecipeResponse> recipes = recipeService.getRecipe("dish",null);

        // assert
        assertThat(recipes).isNotNull();
        assertThat(recipes).hasSize(1);
    }

    @Test
    void getRecipeByDishNullAndIsVegetarianNull() {
        // act
        List<RecipeResponse> recipesResponse = recipeService.getRecipe(null, null);

        // assert
        assertThat(recipesResponse).isNull();
    }

    @Test
    void errorRemovingNullRecipe() {
        // act and assert
        assertThatThrownBy(
            () -> recipeService.removeRecipe(null))
            .isInstanceOf(FlowException.class);
    }

    @Test
    void removeRecipe() {
        //arrange
        RecipeData recipeData = TestsServicesUtils.createValidRecipeData();
        when(recipeRepository.findById("id")).thenReturn(recipeData);

        // act and assert
        Assertions.assertDoesNotThrow(() -> recipeService.removeRecipe("id"));
    }
}
