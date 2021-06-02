import React from 'react'
import Recipes from './components/Recipes';
import Favorites from './components/Favorites';
import NotFound from './components/NotFound'
import Login from './components/Login'
import Signup from './components/Signup'
import Navbar from './components/Navbar';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import RecipeImage from "../src/assets/images/recipe.jpg";

function App() {
  return (
    <Router>
      <Navbar />
      <Switch>
        <Route exact path='/' component={Login} />
        <Route path='/recipes' component={Recipes} />
        <Route path='/favorites' component={Favorites} />
        <Route path='/login' component={Login} />
        <Route path='/signup' component={Signup} />
        <Route component={NotFound} />
      </Switch>
    </Router>
  )
}

export default App
