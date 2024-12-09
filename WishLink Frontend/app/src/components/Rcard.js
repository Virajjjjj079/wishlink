import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../static/Rcard.css';

function Rcard({ title, url, imageUrl, price, onClick }) {
  const navigate = useNavigate();

  const handleViewProductClick = () => {
    navigate('/product', { state: { title, url, imageUrl } });
  };

  return (
    <div className="card">
      <div className="close-button">
        <button className="delete" onClick={onClick}>X</button>
      </div>
      <img src={imageUrl} alt="myimage" className="card-img" />
      <div className="card-body">
        <a href=""><h5 onClick={handleViewProductClick}   className="card-title">{title}</h5></a>
        <p className="card-price">₹{price}</p>
        <div className="buttons">
          <button onClick={handleViewProductClick} className="card-button">View Product</button>
          {/* <button onClick={handleBuyNowClick} className="card-button">Buy Now</button> */}
        </div>
      </div>
    </div>
  );
}

export default Rcard;