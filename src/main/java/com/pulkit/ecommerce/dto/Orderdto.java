package com.pulkit.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Orderdto {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private Double totalPrice;
    private List<OrderItemsdto> items;
}

