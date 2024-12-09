import React from 'react'
import Navbar from '../components/Navbar'
import '../static/Logout.css'
function Logout() {
  return (
    <div>
        <Navbar/>
        <div className="text">
      <p>You are Logged Out!</p><br />
      <p>Thanks for choosing our app..</p>
      </div>
    </div>
  )
}

export default Logout
