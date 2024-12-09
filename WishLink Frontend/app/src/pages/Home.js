//import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../security/AuthContext';
import "../static/Home.css";
function Home() {

  const authcontext = useAuth();
  const isAuthenticated = authcontext.isAuthenticated;
  const username = authcontext.usernameContext;
  console.log(username);
  return (
    <div className="home">
    <div className='container'>
    {isAuthenticated ? (
          <div className="hero-section">
            <h2 className="heromain">Welcome back {authcontext.usernameContext}!</h2>
            <p className="herospan">Continue organizing your wish lists and discovering new products.</p>
            <Link to='/wishlist'><button className="try">Explore Your Wishlists</button></Link>
          </div>
        ) : (
          <div className="hero-section">
            <h2 className="heromain">Tired of juggling a million tabs? <span className="herospan">Organize all your favorite products in one place with Wishlink.</span></h2>
            <Link to='/login'><button className="try">Try Now!</button></Link>
          </div>
        )}
</div>
    </div>
    
    
   
    
  )
}

export default Home
