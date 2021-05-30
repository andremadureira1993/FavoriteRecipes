package com.recipe.db;

import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public class Favorite {

    @Id
    private String id;

    @NotNull
    private String username;

    @NotNull
    private String recipeId;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
}