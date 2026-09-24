import {useEffect, useState} from 'react'
import axios from 'axios'

function Cart() {
    const [cart, setCart] = useState(null)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        axios
            .get('http://localhost:8080/api/carts/1')
            .then(response => {
                setCart(response.data)
            })
            .catch(error => {
                console.error('Error fetching cart:', error)
            })
    }, [])


    const updateQuantity = (itemId, quantity) => {
        axios
            .put(
                `http://localhost:8080/api/carts/1/items/${itemId}?quantity=${quantity}`
            )
            .then(response => {
                setCart(response.data)
                setLoading(false)
            })
            .catch(error => {
                console.error('Error fetching cart:', error)
                setError('Unable to load your cart. Please try again.')
                setLoading(false)
            })
    }

    const removeItem = (itemId) => {
        axios
            .delete(`http://localhost:8080/api/carts/1/items/${itemId}`)
            .then(response => {
                setCart(response.data)
            })
            .catch(error => {
                console.error('Error removing item:', error)
            })
    }

    const placeOrder = () => {
        axios
            .post(`http://localhost:8080/api/orders?cartId=1`)
            .then(response => {
                alert(`Order placed successfully! Order ID: ${response.data.id}`)
                setCart({
                    id: 1,
                    items: [],
                    totalPrice: 0
                })
            })
            .catch(error => {
                console.error('Error placing order:', error)
                alert(error.response?.data?.message || error.message)
            })
    }

    if (loading) {
        return <p className="loading-message">Loading cart...</p>
    }

    if (error) {
        return <p className="error-message">{error}</p>
    }


    return (
        <div className="cart-page">
            <h1>Your Cart</h1>

            {cart.items.length === 0 ? (
                <p>Your cart is empty.</p>
            ) : (
                cart.items.map(item => (
                    <div className="cart-item" key={item.id}>
                        <h3>{item.productname}</h3>
                        <p>Price: ₹{item.price}</p>
                        <p>
                            <div>
                                <button
                                    onClick={() => updateQuantity(item.id, item.quantity - 1)}
                                    disabled={item.quantity <= 1}>
                                    -

                                </button>
                                <span style={{margin: '0 15px'}}>
                                {item.quantity}
                                </span>

                                <button onClick={() => updateQuantity(item.id, item.quantity + 1)}>
                                    +
                                </button>
                            </div>
                        </p>
                        <button onClick={() => removeItem(item.id)}>
                            Remove
                        </button>
                        <p>
                            Subtotal: ₹{item.price * item.quantity}
                        </p>
                    </div>
                ))
            )}

            <h2>Total: ₹{cart.totalPrice}</h2>
            {cart.items.length > 0 && (
                <button onClick={placeOrder}>
                    Place Order
                </button>
            )}
        </div>
    )
}

export default Cart