import React, { useState, useRef, useEffect } from 'react';
import { FaBars } from 'react-icons/fa';
import { FiLogOut } from 'react-icons/fi';
import { NotLoggedLinks, linksLogged, social } from '../data/data';
import { Link } from 'react-router-dom';
import { useHistory } from "react-router-dom"


const Navbar = () => {
  const [showLinks, setShowLinks] = useState(false);
  const linksContainerRef = useRef(null);
  const linksRef = useRef(null);
  const history = useHistory();
  const toggleLinks = () => {
    setShowLinks(!showLinks);
  };
  useEffect(() => {
    const linksHeight = linksRef.current.getBoundingClientRect().height;
    if (showLinks) {
      linksContainerRef.current.style.height = `${linksHeight}px`;
    } else {
      linksContainerRef.current.style.height = '0px';
    }
  }, [showLinks]);

  const handleLogout = e => {
    e.preventDefault();

    localStorage.clear();
        
    history.push('/login');
    
  }
  return (
    <nav>
      <div className='nav-center'>
        <div className='nav-header'>
          <h3>Favorite Recipes</h3>
          <button className='nav-toggle' onClick={toggleLinks}>
            <FaBars />
          </button>
        </div>
        <div className='links-container' ref={linksContainerRef}>
          <ul className='links' ref={linksRef}>
            { localStorage.getItem('jwt') !== null && localStorage.getItem('jwt') !== undefined? (
              linksLogged.map((link) => {
                const { id, url, text } = link;
                return (
                  <li key={id}>
                    <Link to={url}>{text}</Link>
                  </li>
                );
              })
            ) :
            (
              NotLoggedLinks.map((link) => {
                const { id, url, text } = link;
                return (
                  <li key={id}>
                    <Link to={url}>{text}</Link>
                  </li>
                );
              })
            )}
          </ul>
        </div>
        <ul className='social-icons'>
          {social.map((socialIcon) => {
            const { id, url, icon } = socialIcon;
            return (
              <li key={id}>
                <a href={url}>{icon}</a>
              </li>
            );
          })}
        </ul>
        <ul className='social-icons'>
        { localStorage.getItem('jwt') != null? 
            <div>
                <button type='submit' onClick={handleLogout}>
                  Logout <FiLogOut />
                </button>
              </div>
         : <div/>}
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
