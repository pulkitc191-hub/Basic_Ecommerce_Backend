package com.pulkit.ecommerce.service;

import com.pulkit.ecommerce.dto.OrderMapper;
import com.pulkit.ecommerce.dto.Orderdto;
import com.pulkit.ecommerce.exception.ResourceNotFound;
import com.pulkit.ecommerce.model.Cart;
import com.pulkit.ecommerce.model.CartItem;
import com.pulkit.ecommerce.model.Order;
import com.pulkit.ecommerce.model.OrderItem;
import com.pulkit.ecommerce.repository.CartRepository;
import com.pulkit.ecommerce.repository.CartitemRepository;
import com.pulkit.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final CartitemRepository cartitemRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, OrderMapper orderMapper, CartitemRepository cartitemRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.orderMapper = orderMapper;
        this.cartitemRepository = cartitemRepository;
    }

    @Transactional
    public Orderdto createOrder(Long cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        if(cart.getCartItem().isEmpty()){
            throw new ResourceNotFound("Cart is empty");
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Placed");

        double totalPrice = 0.0;

        for(CartItem cartItem : cart.getCartItem()){
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());

            order.getOrderItems().add(orderItem);

            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        order.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);

        cartitemRepository.deleteAll(cart.getCartItem());
        cart.getCartItem().clear();

        return orderMapper.todto(savedOrder);
    }

    public Orderdto getOrderById(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.todto(order);
    }
}
