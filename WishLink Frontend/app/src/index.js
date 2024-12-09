import React from 'react';
import ReactDOM from 'react-dom/client';
import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom';
import App from './App';
import ProductPage from './components/ProductPage';
import './index.css';
import CategoryCards from './pages/CaregoryCards';
import CategoryProducts from './pages/CategoryProducts';
import LoginForm from './pages/LoginForm';
import Logout from './pages/Logout';
import ProductInput from './pages/ProductInput';

import SignupForm from './pages/SignupForm';
import Wishlist from './pages/Wishlist';
import reportWebVitals from './reportWebVitals';
import AuthProvider, { useAuth } from './security/AuthContext';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';

// AuthenticatedRoute Component
function AuthenticatedRoute({ children }) {
  const { isAuthenticated } = useAuth();
  if (isAuthenticated) return children;
  return <Navigate to="/login" />;
}

// Router configuration
const router = createBrowserRouter([
  { path: '/', element: <App /> },
  { path: '/wishlist', element: (<AuthenticatedRoute><Wishlist/></AuthenticatedRoute>) },
  { path: '/input', element: (<AuthenticatedRoute><ProductInput/></AuthenticatedRoute>) },
  { path: '/categories', element: (<AuthenticatedRoute><CategoryCards/></AuthenticatedRoute>) },
  { path: '/products', element: (<AuthenticatedRoute><CategoryProducts/></AuthenticatedRoute>) },
  { path: '/logout', element: <Logout /> },
  { path: '/login', element: <LoginForm /> },
  { path: '/signup', element: <SignupForm /> },
  { path: '/product', element: <ProductPage /> },
  
]);

// Create the root and render the app
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>
);

// Register the service worker
serviceWorkerRegistration.register();
let deferredPrompt;
window.addEventListener('beforeinstallprompt', (e) => {
  e.preventDefault(); // Prevent the default prompt
  deferredPrompt = e; // Save the event for later use
  const installButton = document.getElementById('installButton');
  if (installButton) {
    installButton.style.display = 'block'; // Show the install button
  }
});

// When the user clicks the install button
document.getElementById('installButton')?.addEventListener('click', () => {
  if (deferredPrompt) {
    deferredPrompt.prompt(); // Show the install prompt
    deferredPrompt.userChoice.then((choiceResult) => {
      if (choiceResult.outcome === 'accepted') {
        console.log('User accepted the install prompt');
      } else {
        console.log('User dismissed the install prompt');
      }
      deferredPrompt = null; // Clear the saved prompt
    });
  }
});
reportWebVitals();
