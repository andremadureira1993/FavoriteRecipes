import React, { useState } from "react";
import { FiLogIn } from "react-icons/fi";
import { useHistory } from "react-router-dom";
import Navbar from "./Navbar";
import RecipeImage from "../assets/images/recipe.jpg";

async function loginUser(credentials) {
  const { username, password } = credentials;
  const authentication = JSON.stringify({
    username: username,
    password: password,
  });

  return fetch("https://favorite-recipe-server.herokuapp.com/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: authentication,
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      console.log(data.headers);
      const { accessToken } = data;
      localStorage.setItem("jwt", accessToken);
    });
}

export default function Login({ setToken }) {
  const [username, setUserName] = useState();
  const [password, setPassword] = useState();
  const history = useHistory();

  const handleSubmit = async (e) => {
    e.preventDefault();
    await loginUser({
      username,
      password,
    });

    if (localStorage.getItem("jwt") !== null) {
      history.push("/recipes");
    }
  };

  return (
    <div style={{ backgroundImage: `url(${RecipeImage})` }}>
      <Navbar />
      <section className="section">
        <div className="title">
          <h1>Login</h1>
          <div className="underline"></div>
        </div>
        <form onSubmit={handleSubmit} className="form-centralized">
          <label>
            <p>Username</p>
            <input
              type="text"
              className="input-rounded"
              onChange={(e) => setUserName(e.target.value)}
            />
          </label>
          <label>
            <p>Password</p>
            <input
              type="password"
              onChange={(e) => setPassword(e.target.value)}
              className="input-rounded"
            />
          </label>
          <div>
            <button type="submit" className="btn-login1">
              Login <FiLogIn />
            </button>
          </div>
          <br />
        </form>
      </section>
    </div>
  );
}
