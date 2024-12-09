import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../static/ProductPage.css';
import Navbar from './Navbar'; // Adjust the path to your Navbar component

function ProductPage() {
  const location = useLocation();
  const navigate = useNavigate();

  const { title, url, imageUrl } = location.state || {};

  const handleBuyNowClick = () => {
    window.location.href = url;
  };

  const handleBackClick = () => {
    navigate(-1);
  };

  return (
    <div className="page">
        <Navbar />
 
    <div className="product-page">
      
      <div className="product-details">
        <div className="product-container">
          <img src={imageUrl} alt={title} className="product-img" />
          <div className="product-info">
            <h2 className="product-title">{title}</h2>
            <div className="product-buttons">
              <button onClick={handleBuyNowClick} className="buy-now-button">Buy Now</button>
              <button onClick={handleBackClick} className="back-button">Back</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>
  );
}

export default ProductPage;
