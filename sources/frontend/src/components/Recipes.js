import React, { useState } from "react";
import { FaAngleDoubleRight } from "react-icons/fa";
import { BsFillTrashFill } from "react-icons/bs";
import { FiRefreshCw, FiHeart, FiXCircle } from "react-icons/fi";
import { useHistory } from "react-router-dom";
import Navbar from "./Navbar";
import RecipeImage from "../assets/images/recipe2.jpg";

function Recipes() {
  const [value, setValue] = useState(0);
  const [refreshed, setRefreshed] = useState(false);
  const [recipes, setRecipes] = useState([{}]);
  const [favorites, setFavorites] = useState([{}]);
  const history = useHistory();

  async function getRecipesFromServer() {
    return fetch(
      "https://favorite-recipe-server.herokuapp.com/recipes?isVegetarian=false",
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("jwt"),
        },
      }
    )
      .then((response) => response.json())
      .then((data) => {
        setRecipes(data);
      });
  }
  async function getFavoriteRecipes() {
    return fetch("https://favorite-recipe-server.herokuapp.com/favorites", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("jwt"),
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setFavorites(data);
      });
  }

  const handleGetRecipes = async (e) => {
    e.preventDefault();
    await getRecipesFromServer();
    await getFavoriteRecipes();
    setRefreshed(true);
  };

  const handleCreateRecipes = async (e) => {
    e.preventDefault();
    history.push("/createrecipe");
  };

  const removeRecipe = async (e) => {
    e.preventDefault();
    fetch("https://favorite-recipe-server.herokuapp.com/recipes/" + id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("jwt"),
      },
    }).then((response) => {
      window.location.reload();
      handleGetRecipes(e);
    });

  };

  const addFavorite = async (e) => {
    e.preventDefault();
    await fetch(
      "https://favorite-recipe-server.herokuapp.com/favorites/" + id,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("jwt"),
        },
      }
    );
    handleGetRecipes(e);
  };

  const removeFavorite = async (e) => {
    e.preventDefault();
    await fetch(
      "https://favorite-recipe-server.herokuapp.com/favorites/" + id,
      {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("jwt"),
        },
      }
    ).then(() => {
      history.push("/recipes");
    });

  };

  let {
    id,
    ingredients,
    dish,
    cookingInstructions,
    dateAndTimeOfCreation,
    isVegetarian,
    totalPersonSuitable,
  } = recipes[value];

  return (
    <div style={{ backgroundImage: `url(${RecipeImage})` }}>
      <Navbar />
      {!refreshed ? (
        <section className="section">
          <button
            type="button"
            className="btn-recipe-create"
            onClick={handleCreateRecipes}
          >
            Create Recipe
            <FiRefreshCw />
          </button>
          <button
            type="button"
            className="btn-recipe-update"
            onClick={handleGetRecipes}
          >
            Refresh Recipes
            <FiRefreshCw />
          </button>
          <div className="title">
            <h2>recipes</h2>
            <div className="underline"></div>
          </div>
        </section>
      ) : (
        <section className="section">
          <button
            type="button"
            className="btn-recipe-create"
            onClick={handleCreateRecipes}
          >
            Create Recipe
            <FiRefreshCw />
          </button>
          <button
            type="button"
            className="btn-recipe-update"
            onClick={handleGetRecipes}
          >
            Refresh Recipes
            <FiRefreshCw />
          </button>
          <div className="title">
            <h2>recipes</h2>
            <div className="underline"></div>
          </div>
          <div></div>
          <div className="jobs-center">
            {/* btn container */}
            <div className="btn-container">
              {recipes.map((item, id) => {
                return (
                  <button
                    key={item.id}
                    onClick={() => {
                      setValue(id);
                    }}
                    className={`job-btn ${id === value && "active-btn"}`}
                  >
                    {item.dish}
                  </button>
                );
              })}
            </div>
            <article className="job-info">
              <h3>{dish}</h3>
              <button
                className={`job-btn ${id === value && "active-btn"}`}
                onClick={addFavorite}
              >
                <FiHeart color="green" />
              </button>
              {favorites.map((favorite, index) => {
                if (favorite.recipe.dish === dish) {
                  return (
                    <div key={index}>
                      <button
                        key={index}
                        className={`job-btn ${id === value && "active-btn"}`}
                        onClick={removeFavorite}
                      >
                        <FiXCircle color="red" /> Remove from favorites
                      </button>
                    </div>
                  );
                }
                return <div />;
              })}
              <h5>{isVegetarian ? <p>Vegan</p> : <p>Not Vegan</p>}</h5>
              <h5>Creation date {dateAndTimeOfCreation}</h5>
              <h5>
                total of favorites:{" "}
                {totalPersonSuitable != null ? totalPersonSuitable : 0}
              </h5>
              <p className="job-date">Ingredients List</p>
              {ingredients.map((ingredient, index) => {
                return (
                  <div key={index} className="job-desc">
                    <FaAngleDoubleRight className="job-icon"></FaAngleDoubleRight>
                    <p>{ingredient}</p>
                  </div>
                );
              })}
              <p className="job-date">Cooking Instruction</p>
              <p className="job-date">{cookingInstructions}</p>
              <button
                type="button"
                className="btn-login1"
                onClick={removeRecipe}
              >
                Remove Recipe <BsFillTrashFill color="red" />
              </button>

            </article>
          </div>
        </section>
      )}
    </div>
  );
}

export default Recipes;
