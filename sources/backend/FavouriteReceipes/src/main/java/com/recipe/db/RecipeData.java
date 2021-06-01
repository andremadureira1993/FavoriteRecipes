package com.recipe.db;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("recipeData")
public class RecipeData {

    @Id
    private String id;

    @NotNull
    private String dish;

    @NotNull
    private Date dateCreation;

    @NotNull
    private boolean isVegetarian;

    @NotNull
    private List<String> ingredients;

    @NotNull
    private String cookingInstructions;

    @NotNull Integer quantityOfPersonsSuitable;

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCookingInstructions() {
        return cookingInstructions;
    }

    public void setCookingInstructions(String cookingInstructions) {
        this.cookingInstructions = cookingInstructions;
    }

    public String getId() {
        return id;
    }

    public Integer getQuantityOfPersonsSuitable() {
        return quantityOfPersonsSuitable;
    }

    public void setQuantityOfPersonsSuitable(Integer quantityOfPersonsSuitable) {
        this.quantityOfPersonsSuitable = quantityOfPersonsSuitable;
    }
}
