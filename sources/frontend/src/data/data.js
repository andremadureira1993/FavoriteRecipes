import React from 'react';
import { FaLinkedin, FaGithub } from 'react-icons/fa';
export const linksLogged = [
  {
    id: 1,
    url: '/recipes',
    text: 'recipes',
  },
  {
    id: 2,
    url: '/favorites',
    text: 'favorites',
  }
];

export const NotLoggedLinks = [
  {
    id: 1,
    url: '/login',
    text: 'login',
  },
  {
    id: 2,
    url: '/signup',
    text: 'signup',
  }
];

export const social = [
  {
    id: 1,
    url: 'https://www.linkedin.com/in/andremssmoraes/',
    icon: <FaLinkedin />,
  },
  {
    id: 2,
    url: 'https://github.com/andremadureira1993',
    icon: <FaGithub />,
  },
];
