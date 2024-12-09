// src/components/CategoryProducts.js
import React from "react";
import Card from "../components/Card"; // Assuming Card.js is your card component
import Navbar from "../components/Navbar";
import { useAuth } from "../security/AuthContext";
import "../static/CategoryProducts.css";
const CategoryProducts = () => {
    const authcontext = useAuth();
    const categoryData= authcontext.categoryData;
    if (!Array.isArray(categoryData)) {
        return <div>Error: Expected an array of products.</div>;
      }
  return (
    <div>
      <Navbar/>
    <div>
      <h1 className="category-name">{authcontext.category} </h1>
      <div className="products-container">
        {categoryData.map((card) => (
          <Card 
          key={card.id}
            
            title={card.title}
            url={card.url}
            imageUrl={card.imageUrl}
           />
        ))}
      </div>
    </div>
    </div>
    
  );
};

export default CategoryProducts;
