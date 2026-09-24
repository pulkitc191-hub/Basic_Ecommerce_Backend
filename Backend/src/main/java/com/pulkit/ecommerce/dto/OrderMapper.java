package com.pulkit.ecommerce.dto;

import com.pulkit.ecommerce.model.Order;
import com.pulkit.ecommerce.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public Orderdto todto(Order order){
        Orderdto orderdto = new Orderdto();

        orderdto.setId(order.getId());
        orderdto.setOrderDate(order.getOrderDate());
        orderdto.setStatus(order.getStatus());
        orderdto.setTotalPrice(order.getTotalPrice());

        List<OrderItemsdto> items = order.getOrderItems().stream().map(this :: toorderItemsdto).toList();
        orderdto.setItems(items);
        return orderdto;
    }

    public OrderItemsdto toorderItemsdto(OrderItem orderItem){
        OrderItemsdto itemsdto = new OrderItemsdto();

        itemsdto.setId(orderItem.getId());
        itemsdto.setQuantity(orderItem.getQuantity());
        itemsdto.setPrice(orderItem.getPrice());

        if(orderItem.getProduct() != null){
            itemsdto.setProductId(orderItem.getProduct().getId());
            itemsdto.setProductName(orderItem.getProduct().getName());
        }

        return itemsdto;
    }
}
