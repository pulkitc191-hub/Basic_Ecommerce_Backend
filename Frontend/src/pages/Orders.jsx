import { useEffect, useState } from 'react'
import axios from 'axios'

function Orders() {
    const [orders, setOrders] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        axios
            .get('http://localhost:8080/api/orders')
            .then(response => {
                setOrders(response.data)
                setLoading(false)
            })
            .catch(error => {
                console.error('Error fetching orders:', error)
                setError('Unable to load your orders. Please try again.')
                setLoading(false)
            })
    }, [])

    if (loading) {
        return <p className="loading-message">Loading orders...</p>
    }

    if (error) {
        return <p className="error-message">{error}</p>
    }

    return (
        <div className="orders-page">
            <h1>Your Orders</h1>

            {orders.length === 0 ? (
                <p>No orders found.</p>
            ) : (
                orders.map(order => (
                    <div className="order-card" key={order.id}>
                        <h3>Order #{order.id}</h3>

                        <p className="order-status">
                            {order.status}
                        </p>
                        <p>Date: {new Date(order.orderDate).toLocaleString()}</p>

                        <h4>Items</h4>

                        {order.items.map(item => (
                            <div key={item.id}>
                                <p className="order-product">
                                    {item.productName}
                                    <span>× {item.quantity}</span>
                                </p>

                                <p className="order-item-price">
                                    ₹{item.price.toLocaleString('en-IN')}
                                </p>
                            </div>
                        ))}

                        <h3>Total: ₹{order.totalPrice}</h3>
                    </div>
                ))
            )}
        </div>
    )
}

export default Orders