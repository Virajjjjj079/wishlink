import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { checkLoginApi, getUserId } from '../apicall/ApiCalls';
import { useAuth } from '../security/AuthContext';
import '../static/LoginForm.css';

const LoginForm = () => {
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  
  const navigate = useNavigate();
  const authcontext = useAuth();
  

  const onchangeUser=(e)=>{
    setUser(e.target.value);
  }
  
  const onchangePassword=(e)=>{
    setPassword(e.target.value);
  }
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('User:', user);
    console.log('Password:', password);
    try{
        const userDetails= {
            username:user,
            password:password
        }
        checkLoginApi(userDetails)
        .then(response=> {
          // authcontext.setIsAuthenticated(true);
          authcontext.login(authcontext.userId);
            console.log(response.data+ "login successfull")
        navigate("/wishlist")
        

        getUserId(user)
        .then(response=> {
          authcontext.setUserId(response.data)
          console.log(response.data)
        })
        })
        .catch(error => {
          // Handle failed login
          console.error('Login failed:', error.response?.data || error.message);
      
          if (error.response?.data?.message === 'Invalid username or password. Please try again.') {
            alert('Invalid username or password. Please try again.');
          } else {
            alert('Invalid username or password. Please try again.');
          }
        });

    }
    catch(error) {
            console.error('Login failed:', error.response?.data || error.message);
            alert('Invalid username or password');
    }
  };



  return (
    <div className="login-container">
      <form onSubmit={handleSubmit} className="login-form">
        <h2>Login</h2>
        <div className="form-group">
          <label>User:</label>
          <input
            type="user"
            value={user}
            onChange={onchangeUser}
            required
          />
        </div>
        <div className="form-group">
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={onchangePassword}
            required
          />
        </div>
        <button type="submit" className="login-button" >Login</button>
        <div className="signup-link">
          <span>Don't have an account? </span>
          <button type="button" onClick={() => navigate('/signup') }>Sign Up</button>
        </div>
      </form>
    </div>
  );
};

export default LoginForm;