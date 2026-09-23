package com.pulkit.ecommerce.controller;

import com.pulkit.ecommerce.dto.Orderdto;
import com.pulkit.ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Orderdto createOrder(@RequestParam("cartId") Long cartId){
        return orderService.createOrder(cartId);
    }

    @GetMapping("/{orderId}")
    public Orderdto getOrderById(@PathVariable("orderId") Long orderId){
        return orderService.getOrderById(orderId);
    }
}
