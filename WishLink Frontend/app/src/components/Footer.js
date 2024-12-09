import React from 'react'
import { Link } from 'react-router-dom'
import '../static/Footer.css'
function Footer() {
  return (
    <div>
      <footer>
          <ul>
            <li><Link to="/about">About Us</Link></li>
            <li><Link to="/contact">Contact</Link></li>
            <li><Link to="/privacy">Privacy Policy</Link></li>
            <li><Link to="/terms">Terms of Service</Link></li>
          </ul>
          <div className="social-media">
            {/* Add your social media icons here */}
          </div>
          <p>&copy; 2024 Wishlink. All rights reserved.</p>
        </footer>
    </div>
  )
}

export default Footer
