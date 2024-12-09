import { faShareAlt } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { DeleteLinkApi, getLinksByuserId } from '../apicall/ApiCalls';
import Card from '../components/Card';
import Navbar from '../components/Navbar';
import { useAuth } from '../security/AuthContext';
import '../static/Wishlist.css';


function Wishlist() {
  const [cardsData, setCardsData] = useState([]);
  const authcontext = useAuth();
  const [price, setPrice]= useState(0);
  const userId = authcontext.userId;
  console.log(userId);
  function refreshTodos(){
    
    console.log(userId);
    getLinksByuserId(userId)
    .then(response => {
          console.log(response.data);
          console.log(response.data.id);
          
          setCardsData(response.data.map(card => ({ ...card, truncatedTitle: card.title.slice(0, 45) + (card.title.length > 45 ? '...' : '') })));
          
          const totalSum = response.data.reduce((acc, card) => {
            // Extract price and remove currency symbols or spaces
            const price = parseFloat(card.price.replace(/[^0-9.-]+/g, ''));
            
            // Only add the price if it's a valid number
            if (!isNaN(price)) {
              return acc + price;
            }
            return acc;
          }, 0);
          
          console.log('Total Price of Wishlist:', totalSum);
          setPrice(totalSum);
          
        })
        .catch(error => {
          console.error('There was an error!', error);
        })
        .finally(() => console.log('Showing'));

    }

    


  
  useEffect(() => refreshTodos(), [userId]) 

  function onClick(cardId) {
    const isConfirmed = window.confirm("Are you sure you want to delete this item?");
  
  // Proceed with the delete operation only if the user confirms
  if (isConfirmed) {
    console.log(`Delete of todos with id = ${cardId} successful`);
    DeleteLinkApi(cardId) // Pass the cardId to DeleteLinkApi
      .then(() => {
        refreshTodos(); // Update the UI after successful deletion
      })
      .catch(error => console.log(error))
      .finally(() => console.log('cleanup'));
  } else {
    console.log('Deletion canceled');
  }
  }

  // function shareWishlist() {
  //   const wishlistData = cardsData.map(card => ({
  //     title: card.title,
  //     url: card.url,
  //   }));
  
  //   const textToShare = wishlistData.map(item => `${item.title}\n${item.url}`).join('\n\n');
  
  //   if (navigator.share) {
  //     navigator.share({
  //       text: textToShare,
  //     })
  //     .catch(error => console.error('Error sharing:', error));
  //   } else {
  //     alert(`Share this text:\n${textToShare}`);
  //   }
  // }
  // function shareWishlist() {
  //   const wishlistData = cardsData.map(card => ({
  //     title: card.title,
  //     url: card.url,
  //   }));
  
  //   const textToShare = wishlistData.map(item => `${item.title}\n${item.url}`).join('\n\n');
  
  //   try {
  //     if (navigator.share) {
  //       navigator.share({
  //         text: textToShare,
  //       });
  //     } else {
  //       alert(`Share this text:\n${textToShare}`);
  //     }
  //   } catch (error) {
  //     console.error('Error sharing:', error);
  //     // Optionally, display a user-friendly error message here
  //   }
  // }

 
  function shareWishlist() {
    const wishlistData = cardsData.map(card => ({
      title: card.title,
      url: card.url,
    }));
  
    // Create the text to be shared (each item title and URL separated by two line breaks)
    const textToShare = wishlistData.map(item => `${item.title}\n\n${item.url}`).join('\n\n');
  
    try {
      if (navigator.share) {
        navigator.share({
          title: 'Check out my wishlist!',
          text: textToShare,  // Include the wishlist items in the text
          // No need to include the page URL
        })
        .then(() => console.log('Successfully shared'))
        .catch(error => console.error('Error sharing:', error));
      } else {
        // Fallback for unsupported browsers
        const shareMessage = `Share this wishlist:\n\n${textToShare}`;
        alert(shareMessage);
        console.log("Web Share API not supported in this browser.");
      }
    } catch (error) {
      console.error('Error sharing:', error);
      alert('Something went wrong while trying to share the wishlist.');
    }
  }
  
  
    
     
  

      
  return (
    <div className='wishlist'>
      <Navbar />
      <div>
      
      <div className="products-container">
        {cardsData.map((card) => (
          <Card 
          key={card.id}
          onClick={() => onClick(card.id)}
            title={card.truncatedTitle}
            url={card.url}
            imageUrl={card.imageUrl}
           />
        ))}
      </div>
    </div>

    <div className="total-cart-value">
        <h3>Estimated Cart Value: ₹{price}</h3>
      </div>

      <div >
      {/* <button className="shareButton" onClick={shareWishlist}>**</button> */}
      <button className="shareButton" onClick={shareWishlist}>
          <FontAwesomeIcon icon={faShareAlt} />
        </button>
        <Link to='/input'> <button className="addProduct" >+</button></Link>
      </div>
    </div>
  )
}

export default Wishlist


// const userIdInt = parseInt(userId, 10);
    // if (isNaN(userIdInt)) {
    //   console.error('Invalid user ID format');
    //   return; // Or handle the error gracefully
    // }
    // ShowDetailsBelow()
    //   .then(response => {
    //     console.log(response.data);
    //     console.log(response.data.id);
    //     setCardsData(response.data.map(card => ({ ...card, truncatedTitle: card.title.slice(0, 45) + (card.title.length > 45 ? '...' : '') })));
    //     // setCardsData(response.data); // Save the API response data in state
    //   })
    //   .catch(error => {
    //     console.error('There was an error!', error);
    //   })
    //   .finally(() => console.log('Showing'));