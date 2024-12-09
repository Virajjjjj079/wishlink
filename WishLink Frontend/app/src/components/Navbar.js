
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../security/AuthContext';
import '../static/Navbar.css';



function Navbar() {
 
  const [menuOpen, setMenuOpen] = useState(false);
  const [icon, setIcon]= useState('☰');
  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
    setIcon(menuOpen ? '☰' : 'X');
  };

  const authcontext = useAuth();
 const navigate = useNavigate();
  const { logout } = useAuth();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };
  // function handleLogout(){
  //   authcontext.setIsAuthenticated(false);
  //   authcontext.setUserId('');
  //   console.log(authcontext.userId)
  // }

  
  
  return (
    <div className={`navbar ${menuOpen ? 'expanded' : ''}`}>
    <nav>
      <div className="logo"><h2 className='Logo'>WishLink</h2></div>
      <div className="menu-icon" onClick={toggleMenu}>
      {icon}
      </div>
      <div className="list">
        <ul className='menuitems'>
         <Link to='/'><li>Home </li></Link>
         {authcontext.isAuthenticated?(<Link to='/wishlist'><li>My Wishlist</li></Link>):'' }
         {authcontext.isAuthenticated?(<Link to='/categories'><li>Categories</li></Link>):'' }

          
         {authcontext.isAuthenticated?(<Link to='/logout'><li onClick={handleLogout}>Logout</li></Link>):(<Link to='/login'><li>Login</li></Link>) } 
          
          
          
          
        </ul>
      </div>
    </nav>
  </div>
  );
}

export default Navbar;



