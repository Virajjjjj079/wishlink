import { faShareAlt } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { getLinksBycategoryAndUserId } from "../apicall/ApiCalls";
import Navbar from "../components/Navbar";
import { useAuth } from "../security/AuthContext";
import "../static/CategoryCards.css"; // Import the custom CSS file
const categories = [
  "Clothing",
  "Electronics",
  "Furniture",
  "Beauty & Personal Care",
  "Books",
  "Toys & Games",
  "Jewelry & Watches",
  "Kitchen & Home Appliances",
  "Tools & Hardware",
  "Sports & Outdoors",
  "Other",
];

const CategoryCards = () => {
    
    const authcontext  = useAuth();
    const category = authcontext.category;
    const setCategory=  authcontext.setCategory;
    const userId = authcontext.userId;
    const navigate = useNavigate();
    const handleOnClick = (category) => {
        setCategory(category);
    
        // Call the API with userId and selected category
        getLinksBycategoryAndUserId(userId, category)
          .then(response => {
            console.log("Links for category:", category, response);
            // You can update the state with response data or perform other actions
            const flattenedData = response.data.flat();

        // Update the state with response data
        authcontext.setCategoryData(flattenedData);
            
         navigate("/products");
          })
          .catch(error => {
            console.error("Error fetching links:", error);
          });
      };




  return (
    <div>
      <Navbar/>
    <div className="category-container">
      {categories.map((category) => (
        <div key={category} className="category-card">
          <h2>{category}</h2>
          <p>Explore a wide range of {category.toLowerCase()} products.</p>
          <button onClick={() => handleOnClick(category)} className="cta-button">Check Products</button>
        </div>
      ))}
    </div>
    <div >
      {/* <button className="shareButton" onClick={shareWishlist}>**</button> */}
      <button className="shareButton" >
          <FontAwesomeIcon icon={faShareAlt} />
        </button>
        <Link to='/input'> <button className="addProduct" >+</button></Link>
      </div>
    </div>
  );
};

export default CategoryCards;
