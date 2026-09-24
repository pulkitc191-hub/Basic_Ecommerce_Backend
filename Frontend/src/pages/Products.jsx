import { useEffect, useState } from 'react'
import axios from 'axios'

function Products() {
    const [products, setProducts] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        axios
            .get('http://localhost:8080/api/products')
            .then(response => {
                setProducts(response.data.content)
                setLoading(false)
            })
            .catch(error => {
                console.error('Error fetching products:', error)
                setError('Unable to load products. Please try again.')
                setLoading(false)
            })
    }, [])

    const addToCart = (productId) => {
        axios
            .post(
                `http://localhost:8080/api/carts/1/items?productId=${productId}&quantity=1`
            )
            .then(response => {
                alert('Product added to cart!')
            })
            .catch(error => {
                console.error('Error adding product to cart:', error)
                alert('Failed to add product to cart')
            })
    }

    return (
        <div className="products-page">
            <section className="hero-section">
                <div>
                    <p className="hero-tag">WELCOME TO OUR STORE</p>
                    <h1>Everything you need,<br />all in one place.</h1>
                    <p>
                        Discover quality products at great prices.
                        Shop our latest collection today.
                    </p>
                    <button
                        className="hero-button"
                        onClick={() => document.getElementById('products').scrollIntoView({
                            behavior: 'smooth'
                        })}
                    >
                        Explore Products
                    </button>
                </div>

                <div className="hero-icon">
                    🛍️
                </div>
            </section>
            <h1>Latest Products</h1>

            {loading && (
                <p className="loading-message">Loading products...</p>
            )}

            {error && (
                <p className="error-message">{error}</p>
            )}

            <div className="product-grid" id="products">
                {products.map(product => (
                    <div className="product-card" key={product.id}>
                        <div className="product-image">
                            <span>📱</span>
                        </div>

                        <h3>{product.name}</h3>

                        <p>{product.description}</p>

                        <h2>₹{product.price.toLocaleString('en-IN')}</h2>

                        <p className="stock-badge">
                            {product.quantity > 0 ? `${product.quantity} in stock` : 'Out of stock'}
                        </p>

                        <button onClick={() => addToCart(product.id)}>
                            Add to Cart
                        </button>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default Products