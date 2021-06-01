import React, { useState } from 'react'
import { FiLogIn } from 'react-icons/fi';
import { useHistory } from "react-router-dom"

async function loginUser(credentials) {
  const {username, password } = credentials;
  const authentication = JSON.stringify({
    'username': username,
    'password': password
  });


// return fetch('https://ancient-refuge-89947.herokuapp.com/https://favorite-recipe-server.herokuapp.com/login', {
  return fetch('http://localhost:8080/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
   },
   body: authentication
 })
   .then(response=>response.json())
    .then(data=>
      { 
        const { accessToken } = data;
        localStorage.setItem('jwt', accessToken);
      })
}

export default function Login({ setToken }) {
  const [username, setUserName] = useState();
  const [password, setPassword] = useState();
  const history = useHistory();

  const handleSubmit = async e => {
    e.preventDefault();
    await loginUser({
      username,
      password
    });

    if (localStorage.getItem('jwt') !== null) {
      history.push('/recipes');
    }
  }

  return (
    <section className='section'>
        <article className='hero-info'>
          <div className='title'>
           <h1>Please Log In</h1>
           <div className='underline'></div>
          </div>
          <div className='jobs-center'>
            <div className='btn-container'>

              <form onSubmit={handleSubmit} >
                <label>
                  <p>Username</p>
                  <input type="text" onChange={e => setUserName(e.target.value)}  className='job-desc'/>
                </label>
                <label>
                  <p>Password</p>
                  <input type="password" onChange={e => setPassword(e.target.value)} />
                </label>
                <div>
                  <button type='submit' className='btn-login1'>
                    Login <FiLogIn />
                  </button>
                </div>
              </form>
            </div>
          </div>
        </article>
    </section>
  )
}
