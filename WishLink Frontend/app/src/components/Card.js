
// import React from 'react';

// import '../static/Card.css';

// function Card({ title, url, imageUrl ,onClick }) {

//   const handleClick = () => {
//     window.location.href = url; // Redirect to the product URL when clicking the "Buy Now" button
//   };

    



  
//     return (
//       <div className="card">
//         <div className="close-button">
//         <button className='delete' onClick={onClick}>X</button>
//       </div>
//         <img src={imageUrl} alt='myimage' className="card-img" />
//         <div className="card-body">
//           <h5 className="card-title">{title}</h5>
//           <div className="buttons">
//             <button onClick={handleClick} className="card-button">View Product</button>
           
//           </div>
//         </div>
//       </div>
//     );
// }

// export default Card


import React from 'react';
import { useNavigate } from 'react-router-dom'; // Updated to use useNavigate
import '../static/Card.css';

function Card({ title, url, imageUrl, onClick }) {
  const navigate = useNavigate(); // Updated to use useNavigate

  const handleViewProductClick = () => {
    navigate('/product', { state: { title, url, imageUrl } }); // Updated to use navigate
  };

  // const handleBuyNowClick = () => {
  //   window.location.href = url; 
  // };

  return (
    <div className="card">
      <div className="close-button">
        <button className='delete' onClick={onClick}>X</button>
      </div>
      <img src={imageUrl} alt='myimage' className="card-img" />
      <div className="card-body">
        <h5 className="card-title">{title}</h5>
        <div className="buttons">
          <button onClick={handleViewProductClick} className="card-button">View Product</button>
          {/* <button onClick={handleBuyNowClick} className="card-button">Buy Now</button> */}
        </div>
      </div>
    </div>
  );
}

export default Card;
