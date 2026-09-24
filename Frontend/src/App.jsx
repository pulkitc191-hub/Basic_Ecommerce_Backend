import { useState } from 'react'
import heroImg from './assets/hero.png'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import './App.css'
import Navbar from './components/Navbar'
import Products from './pages/Products'
import Cart from './pages/Cart'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Orders from './pages/Orders'
import Footer from './components/Footer'

function App() {
    return (
        <BrowserRouter>
        <div>
            <Navbar />
            <Routes>
                <Route path="/" element={<Products />} />
                <Route path="/products" element={<Products />} />
                <Route path="/cart" element={<Cart />} />
                <Route path="/orders" element={<Orders />} />
            </Routes>
            <Footer />
        </div>
        </BrowserRouter>
    )
}

export default App
