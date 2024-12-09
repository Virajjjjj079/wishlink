import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { checkUsernameApi, createUser } from '../apicall/ApiCalls';
import '../static/SignupForm.css';
const SignupForm = ({ onChangeForm }) => {
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const navigate = useNavigate();


const onchangeUser=(e)=>{
  setUser(e.target.value);
}

const onchangePassword=(e)=>{
  setPassword(e.target.value);
}



  // const handleSubmit = (e) => {
  //   e.preventDefault();
  //   if (password !== confirmPassword) {
  //     alert('Passwords do not match!');
  //     return;
  //   }else{
  //     const userDetails = {
  //       username:user,
  //       password:password
    
  //     } 
  //     createUser(userDetails)
  //     .then(
  //       response=>console.log('User created:', response.data)
  //     )
  //     .catch(error=>console.log(error))
  //   }
  //   console.log('User:', user);
  //   console.log('Password:', password);
  

    

  // };

  
      
  const handleSubmit = async (e) => {
    e.preventDefault();
  
    if (password !== confirmPassword) {
      alert('Passwords do not match!');
      return;
    }
  
    try {
      // Check if username already exists
      const response = await checkUsernameApi(user);
  
      if (response.data.exists) {
        alert('Username already exists');
      } else {
        // Proceed with user creation
        const createUserResponse = await createUser({ username: user, password });
        console.log('User created:', createUserResponse.data);
        alert('User created successfully');
        navigate('/login');
      }
    } catch (error) {
      console.error('Error during signup:', error);
      alert('An error occurred. Please try again later.');
    }
  };
  
    
      

       
   
  





  return (
    <div className="signup-container">
      <form onSubmit={handleSubmit} className="signup-form">
        <h2>Sign Up</h2>
        <div className="form-group">
          <label>User:</label>
          <input
            type="user"
            value={user}
            onChange={onchangeUser}
            required
            // (e) => setUser(e.target.value)
          />
        </div>
        <div className="form-group">
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={onchangePassword}
            required
            // (e) => setPassword(e.target.value)
          />
        </div>
        <div className="form-group">
          <label>Confirm Password:</label>
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="signup-button">Sign Up</button>
        <div className="login-link">
          <span>Already have an account? </span>
          <button type="button" onClick={() => navigate('/login')}>Login</button>
        </div>
      </form>
    </div>
  );
};

export default SignupForm;
