import React from 'react'
import Recipes from './components/Recipes';
import CreateRecipes from './components/CreateRecipe';
import NotFound from './components/NotFound'
import Login from './components/Login'
import Signup from './components/Signup'
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path='/' component={Login} />
        <Route path='/recipes' component={Recipes} />
        <Route path='/createrecipe' component={CreateRecipes} />
        <Route path='/login' component={Login} />
        <Route path='/signup' component={Signup} />
        <Route component={NotFound} />
      </Switch>
    </Router>
  )
}

export default App
