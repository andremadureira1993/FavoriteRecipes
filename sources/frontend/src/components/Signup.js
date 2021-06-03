import React, { useState } from "react";
import { FiLogIn } from "react-icons/fi";
import { useHistory } from "react-router-dom";
import Navbar from "./Navbar";
import RecipeImage from "../assets/images/recipe3.jpg";

export default function Signup({ setToken }) {
  const [username, setUserName] = useState();
  const [password, setPassword] = useState();
  const history = useHistory();

  const handleSubmit = async (e) => {
    e.preventDefault();

    await fetch("https://favorite-recipe-server.herokuapp.com/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username,
        password,
      }),
    }).then((data) => {
      history.push("/login");
    });
  };

  return (
    <div style={{ backgroundImage: `url(${RecipeImage})` }}>
      <Navbar />
      <section className="section">
        <div className="title">
          <h1>SignUp</h1>
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
              className="input-rounded"
              onChange={(e) => setPassword(e.target.value)}
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
