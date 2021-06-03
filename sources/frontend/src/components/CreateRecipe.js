import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import Navbar from "./Navbar";
import { FaSave } from "react-icons/fa";
import RecipeImage from "../assets/images/recipe5.jpg";

function CreateRecipes() {
  const history = useHistory();
  const [recipe, setRecipe] = useState("");
  const [ingredients, setIngredients] = useState("");
  const [cooking, setCooking] = useState("");
  const [isVegetarian, setIsVegetarian] = useState("");
  

  const handleCreateRecipe = (e) => {
    let ingredientsArray = ingredients.split(",");
    let isVegan = isVegetarian.toUpperCase === "YES" ? true : false;
    let requestRecipe = JSON.stringify({
      dish: recipe,
      isVegetarian: isVegan,
      ingredients: ingredientsArray,
      cookingInstructions: cooking,
    });
    e.preventDefault();
    fetch("https://favorite-recipe-server.herokuapp.com/recipes", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("jwt"),
      },
      body: requestRecipe,
    }).then((response) => {
      history.push("/recipes");
    });
  };

  return (
    <div style={{ backgroundImage: `url(${RecipeImage})` }}>
      <Navbar />
      <section className="section">
        <div className="title">
          <h2>create recipe</h2>
          <div className="underline"></div>
        </div>
        <form onSubmit={handleCreateRecipe} className="form-centralized">
          <label>
            <p>Recipe Name</p>
            <input
              type="text"
              name="Recipe"
              onChange={(e) => setRecipe(e.target.value)}
              className="input-rounded"
            />
          </label>
          <label>
            <p>Ingredients</p>
            <textarea
              type="text"
              name="Ingredients"
              onChange={(e) => setIngredients(e.target.value)}
              placeholder="Water,Coffe"
              className="input-rounded"
            />
          </label>
          <label>
            <p>Cooking details</p>
            <textarea
              type="text"
              name="Cooking details"
              onChange={(e) => setCooking(e.target.value)}
              className="input-rounded"
            />
          </label>
          <label>
            <p>Vegetarian/Vegan?</p>
            <input
              type="textarea"
              name="vegetarian"
              onChange={(e) => setIsVegetarian(e.target.value)}
              placeholder="yes/no"
              className="input-rounded"
            />
          </label>
          <button type="submit" className="btn-login1">
            Save <FaSave />
          </button>
        </form>
        <br />
      </section>
    </div>
  );
}

export default CreateRecipes;
