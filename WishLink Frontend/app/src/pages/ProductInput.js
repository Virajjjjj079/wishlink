import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getRecommendations, webScrapeApi } from '../apicall/ApiCalls';
import Navbar from '../components/Navbar';
import Rcard from '../components/Rcard';
import { useAuth } from '../security/AuthContext';
import '../static/ProductInput.css';
function ProductInput() {
  const [products, setProducts] = useState([]);
  const [loadingRecommendations, setLoadingRecommendations] = useState(false);
  const[recommendations,setRecommendations] = useState([]);
  const [url, setUrl] = useState("");
  const [showAlert, setShowAlert] = useState(false);
  const [loading, setLoading] = useState(false);
  const authcontext = useAuth()
 const userId = authcontext.userId;
const [added, setAdded]= useState(true);
const[title, setTitle] = useState('');
 const navigate = useNavigate();
 let cleanedUrl = url.replace(/.*?(http.*)/, '$1'); // Remove anything before 'http'
  const handleScrape = () => {
    setLoading(true);
    webScrapeApi(cleanedUrl,userId)
      .then(response => {
        setShowAlert(true);
        setAdded(false);
        setTitle(response.data[0])
        console.log(response.data[0])
        setLoadingRecommendations(true); // Start loading recommendations
        getRecommendations(response.data[0])
        .then(response=>{
          console.log(response)
          console.log(response.data)
          response.data.forEach((item, index) => {
            // console.log(`Item ${index}:`, item);
          });
          
          const slicedData = response.data.slice(4, 10).map(item => ({
            title: item[0].slice(0, 45) + (item[0].length > 45 ? '...' : ''),  // Assuming the title is in the first element
            imageUrl: item[1], // Assuming the image URL is in the second element
            price:item[2],
            url: item[3]     // Assuming the product URL is in the third element
          }));
          
          console.log('Sliced Recommendations Data:', slicedData);
          setRecommendations(slicedData);
         

        })
        .catch(error=>console.log(error))
        .finally(() => {
          setLoadingRecommendations(false); // End loading recommendations after they are fetched
        });

        console.log(response);
        setProducts(response.data);
        console.log(recommendations)
          
    
        // Update products state here if needed
      })
      .finally(() => {
        setLoading(false);
        alert('product added') // End loading after recommendations are fetched
      })
      .catch(error => {
        console.error('There was an error!', error);
      })
      .finally(() => console.log('Scraping finished'));
     
    
      
      
  }

  const navigatetowishlist = ()=>{
    navigate('/wishlist');
  }

  
  return (
    <div>
      <Navbar />
    <div className='container'>
   
      <h2>Enter Product Url</h2>
      <div className="input">
      <input type="text" value={url} onChange={(e) => setUrl(e.target.value)} />
      </div>
      <div className="button"> 
      {added?(<button className='button' onClick={handleScrape}>Add Product</button>):(<button className='button' onClick={navigatetowishlist}>Go To Mywishlist</button>)}
      </div>
      <div>
      {loading ? <p>Adding Product...</p> : <p></p>}
      {/* Rest of your component code */}
    </div>
    </div>
    <div className="products">
    <h2 className='similar'>Similar Products</h2>
    {loadingRecommendations ? ( 
          // <p>Loading Recommendations...</p> 
          <div className="loader-container"><div className="loader"></div></div>
        ) : (
          recommendations.length > 0 && (
            <div className="card-container">
              {recommendations.map((recommendation, index) => (
                <Rcard
                  key={index}
                  title={recommendation.title}
                  imageUrl={recommendation.imageUrl}
                  price={recommendation.price}
                  url={recommendation.url}
                  onClick={() => navigate(recommendation.url)} // Navigate to the product URL
                />
              ))}
            </div>
          )
        )}
          </div>
        
      
    

   
    </div>
  );
}

export default ProductInput;
