package com.recipe.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.recipe.db.FavoriteRepository;
import com.recipe.services.FavoriteService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link FavoriteController}.
 */
@ExtendWith(MockitoExtension.class)
public class FavoriteControllerTest {

    @InjectMocks
    private FavoriteController favoriteController;

    @Mock
    private FavoriteService favoriteService;

    @Test
    void favoritesDishIdPostTest() {
        // arrange
        var dishIdMocked = "test-dish";
        doNothing().when(favoriteService).addRecipeToFavorite(dishIdMocked);

        // act
        var responseEntity = favoriteController.favoritesDishIdPost(dishIdMocked);

        // assert
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getAllFavorites() {
        // arrange
        var favoritesResponse = TestsControllerUtils.createValidFavoriteListResponse();
        when(favoriteService.getTotalOfEachFavoriteRecipe()).thenReturn(favoritesResponse);

        // act
        var responseEntity = favoriteController.favoritesAllGet();

        // assert
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void favoritesDishIdDelete() {
        // arrange
        var dishIdMocked = "test-dish";
        doNothing().when(favoriteService).deleteFavorite(dishIdMocked);

        // act
        var responseEntity = favoriteController.favoritesDishIdDelete(dishIdMocked);

        // assert
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void favoritesGet() {
        // arrange
        var favoritesResponse = TestsControllerUtils.createValidUserFavoriteListResponse();
        when(favoriteService.getFavoritesByUser()).thenReturn(favoritesResponse);

        // act
        var responseEntity = favoriteController.favoritesGet();

        // assert
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
