import { createContext, useContext, useEffect, useState } from "react";
export const AuthContext = createContext();
 //this makes it easy to access easy example in header and complex example in welcome
export const useAuth = ()=> useContext(AuthContext);

export default function AuthProvider({ children }) {
   // Replace with your user data
   const [category, setCategory] = useState(null);
//    const [userId, setUserId] = useState('');
// const [isAuthenticated, setIsAuthenticated] = useState(false);
const [categoryData, setCategoryData] = useState([]);
const [userId, setUserId] = useState(() => {
  // Retrieve userId from local storage, if available
  return localStorage.getItem('userId') || '';
});
const [isAuthenticated, setIsAuthenticated] = useState(() => {
  // Retrieve authentication status from local storage
  return localStorage.getItem('isAuthenticated') === 'true';
});

const[passwordContext,setPasswordContext]= useState(null);
const[usernameContext,setUsernameContext] = useState(null);
useEffect(() => {
  console.log('Updating localStorage:', { userId, isAuthenticated });
  if (isAuthenticated) {
      localStorage.setItem('userId', userId);
      localStorage.setItem('isAuthenticated', isAuthenticated.toString());
  } else {
      localStorage.removeItem('userId');
      localStorage.removeItem('isAuthenticated');
  }
}, [userId, isAuthenticated]);

// const[token,setToken] = useState(null);
  // ... other authentication logic (login, logout, etc.)

// i can also use this in another component and generate popup in all the components after spefcific time interval and many other use cases
//setInterval(   ()=> setNumber(number+1), 2000);

//login logic

const login = (userId) => {
  setUserId(userId);
  setIsAuthenticated(true);
  
};

// Logout logic example
const logout = () => {
  setUserId('');
  setIsAuthenticated(false);
  localStorage.removeItem('userId');
  localStorage.removeItem('isAuthenticated');
};

  return (
    <AuthContext.Provider value={{userId, setUserId, isAuthenticated, setIsAuthenticated, usernameContext,setUsernameContext,passwordContext,setPasswordContext,login,logout,categoryData, setCategoryData,category, setCategory}}>
      {children}
    </AuthContext.Provider>
  );
}